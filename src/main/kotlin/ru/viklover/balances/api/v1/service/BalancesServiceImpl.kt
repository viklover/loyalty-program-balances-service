package ru.viklover.balances.api.v1.service

import java.time.Instant

import kotlinx.coroutines.launch
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.coroutineScope

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import ru.viklover.balances.repository.BalanceRepository

import ru.viklover.balances.api.v1.exception.NotEnoughPointsException
import ru.viklover.balances.contracts.v1.models.BalanceDto

@Service
class BalancesServiceImpl(
    private val balancesRepository: BalanceRepository
) : BalancesService {

    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun addPoints(cardId: Long, points: Int, expirationDate: String) {
        val instant = Instant.parse(expirationDate)

        GlobalScope.launch {
            balancesRepository.createBalance(cardId, points, instant)
        }
    }

    override suspend fun getBalanceByCard(cardId: Long): BalanceDto {
        val balance = balancesRepository.getBalanceByCardId(cardId) ?: 0
        return BalanceDto(cardId, balance)
    }

    @Transactional
    override suspend fun spendPoints(cardId: Long, points: Int) = coroutineScope {

        val balance = balancesRepository.getBalanceByCardId(cardId) ?: 0

        if (balance < points) {
            throw NotEnoughPointsException(points, balance, cardId)
        }

        val balances = balancesRepository.findBalancesByCardIdOrderByCreatedAt(cardId)

        var bufferPoints = points

        balances.collect {

            if (bufferPoints == 0)
                return@collect

            var balanceValue = it.value

            when {
                it.value >= bufferPoints -> {
                    balanceValue -= bufferPoints
                    bufferPoints = 0
                }
                it.value < bufferPoints -> {
                    bufferPoints -= it.value
                    balanceValue = 0
                }
            }

            if (balanceValue == 0) {
                balancesRepository.deleteById(it.id)
            } else {
                balancesRepository.updateValueBalanceById(it.id, balanceValue)
            }
        }
    }
}
