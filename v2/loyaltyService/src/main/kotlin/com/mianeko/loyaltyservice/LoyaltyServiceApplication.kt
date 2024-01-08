package com.mianeko.loyaltyservice

import com.mianeko.common.exceptions.exceptionHadlers.ApiExceptionHandler
import com.mianeko.common.health.HealthController
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(
    ApiExceptionHandler::class,
    HealthController::class
)
class LoyaltyServiceApplication

fun main(args: Array<String>) {
    runApplication<LoyaltyServiceApplication>(*args)
}
