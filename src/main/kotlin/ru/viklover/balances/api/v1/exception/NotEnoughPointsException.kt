package ru.viklover.balances.api.v1.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
open class NotEnoughPointsException(requiredPoints: Int, currentBalance: Int, cardId: Long) :
    RuntimeException("Not enough points to spending ($requiredPoints < $currentBalance). Card identifier is '$cardId'")
