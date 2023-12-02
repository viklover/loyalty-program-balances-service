package ru.viklover.balances

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BalancesServiceApplication

fun main(args: Array<String>) {
    runApplication<BalancesServiceApplication>(*args)
}
