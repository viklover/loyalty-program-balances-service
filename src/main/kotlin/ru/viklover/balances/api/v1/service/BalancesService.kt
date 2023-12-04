package ru.viklover.balances.api.v1.service

import ru.viklover.balances.contracts.v1.models.BalanceDto

interface BalancesService {
    suspend fun addPoints(cardId: Long, points: Int, expirationDate: String)
    suspend fun getBalanceByCard(cardId: Long): BalanceDto
    suspend fun spendPoints(cardId: Long, points: Int)
}
