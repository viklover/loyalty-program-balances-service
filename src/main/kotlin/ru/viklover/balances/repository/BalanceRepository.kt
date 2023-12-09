package ru.viklover.balances.repository

import java.util.UUID
import java.time.Instant
import kotlinx.coroutines.flow.Flow

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BalanceRepository : CoroutineCrudRepository<Balance, UUID> {

    @Query("insert into balance (card_id, value, expiration_date) values (:cardId, :points, :expirationDate)")
    suspend fun createBalance(cardId: Long, points: Int, expirationDate: Instant)

    @Query("select sum(balance.value) from balance where card_id=:cardId")
    suspend fun getBalanceByCardId(cardId: Long): Int?

    @Query("update balance set value=:value where id=:id")
    suspend fun updateValueBalanceById(id: UUID, value: Int)

    @Query("select * from balance where card_id=:cardId order by created_at")
    fun findBalancesByCardIdOrderByCreatedAt(cardId: Long): Flow<Balance>
}
