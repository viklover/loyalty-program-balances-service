package ru.viklover.balances.repository

import java.util.UUID

import java.time.Instant
import java.time.LocalDateTime

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table
class Balance(
    var cardId: Long, var expirationDate: Instant, @Column var value: Int
) {
    @Id
    @Column("id")
    private var _id: UUID? = null;

    @Column("created_at")
    private var _createdAt: LocalDateTime? = null

    @Column("updated_at")
    private var _updatedAt: LocalDateTime? = null

    val id
        get() = checkNotNull(_id)

    val createdAt
        get() = checkNotNull(_createdAt)

    val updatedAt
        get() = checkNotNull(_updatedAt)
}
