package com.mianeko.gateway

import com.mianeko.common.exceptions.exceptionHadlers.ApiExceptionHandler
import com.mianeko.common.health.HealthController
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Import

@SpringBootApplication
@EnableFeignClients
@Import(
	ApiExceptionHandler::class,
	HealthController::class
)
class GatewayApplication

fun main(args: Array<String>) {
	runApplication<GatewayApplication>(*args)
}
