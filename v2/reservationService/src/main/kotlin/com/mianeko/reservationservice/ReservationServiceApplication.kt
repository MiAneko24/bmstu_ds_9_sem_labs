package com.mianeko.reservationservice

import com.mianeko.common.health.HealthController
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(
	HealthController::class
)
class ReservationServiceApplication

fun main(args: Array<String>) {
	runApplication<ReservationServiceApplication>(*args)
}
