package ru.viklover.balances.repository

import java.util.UUID

import java.time.Instant
import java.time.LocalDateTime

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
class Balance(
    @Id
    val id: UUID,
    val value: Int,
    var cardId: Long,
    var expirationDate: Instant,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
