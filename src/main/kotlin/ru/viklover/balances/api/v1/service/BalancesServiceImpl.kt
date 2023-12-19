package ru.viklover.balances.api.v1.service

import java.time.Instant

import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.coroutineScope

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import ru.viklover.balances.repository.BalanceRepository

import ru.viklover.balances.api.v1.exception.NotEnoughPointsException
import ru.viklover.balances.contracts.v1.models.BalanceDto
import ru.viklover.balances.repository.Balance

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

        val balances = balancesRepository.findBalancesByCardIdOrderByCreatedAt(cardId).toList()

        var balance = 0

        balances.forEach {
            balance += it.value
        }

        if (balance < points) {
            throw NotEnoughPointsException(points, balance, cardId)
        }

        val updatedBalances = mutableListOf<Balance>()
        val removedBalances = mutableListOf<Balance>()

        var bufferPoints = points

        balances.forEach {

            if (bufferPoints == 0)
                return@forEach

            when {
                it.value >= bufferPoints -> {
                    it.value -= bufferPoints
                    bufferPoints = 0
                }
                it.value < bufferPoints -> {
                    bufferPoints -= it.value
                    it.value = 0
                }
            }

            if (it.value == 0) {
                removedBalances.add(it)
            } else {
                updatedBalances.add(it)
            }
        }

        updatedBalances.forEach {
            balancesRepository.updateValueBalanceById(it.id, it.value)
        }

        removedBalances.forEach {
            balancesRepository.deleteById(it.id)
        }
    }
}
