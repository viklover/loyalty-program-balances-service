package ru.viklover.balances.api.v1.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

import ru.viklover.balances.api.v1.service.BalancesService

import ru.viklover.balances.contracts.v1.controller.BalancesControllerV1
import ru.viklover.balances.contracts.v1.models.BalanceDto

@RestController
class BalancesController(
    private val balancesService: BalancesService
) : BalancesControllerV1 {

    override suspend fun addPoints(cardId: Long, points: Int, expirationDate: String): ResponseEntity<Unit> {
        balancesService.addPoints(cardId, points, expirationDate)
        return ResponseEntity(HttpStatus.OK)
    }

    override suspend fun getBalanceByCard(cardId: Long): ResponseEntity<BalanceDto> {
        return ResponseEntity.ok(balancesService.getBalanceByCard(cardId))
    }

    override suspend fun spendPoints(cardId: Long, points: Int): ResponseEntity<Unit> {
        balancesService.spendPoints(cardId, points)
        return ResponseEntity(HttpStatus.OK)
    }
}
