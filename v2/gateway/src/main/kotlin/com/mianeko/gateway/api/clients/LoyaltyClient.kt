package com.mianeko.gateway.api.clients

import com.mianeko.common.loyalty.Loyalty
import feign.RetryableException
import feign.Retryer
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*
import javax.naming.ServiceUnavailableException

@Component
class LoyaltyRetryer: Retryer {
    override fun clone(): Retryer {
        return LoyaltyRetryer()
    }

    override fun continueOrPropagate(e: RetryableException?) {
        try {
            Thread.sleep(RETRY_TIMEOUT_MS)
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
            throw e
        }
    }

    companion object {
        private const val RETRY_TIMEOUT_MS = 10000L
    }
}


@FeignClient(
    value = "loyaltyService",
    url = "\${custom.services.loyalty}",
    fallback = LoyaltyClientFallback::class
)
@Primary
interface LoyaltyClient {
    @GetMapping("/user_info/{username}")
    fun getLoyaltyForUser(
        @PathVariable username: String
    ): Loyalty

    @GetMapping("/user_info/{username}")
    fun getLoyaltyForUserWithFallback(
            @PathVariable username: String
    ): Loyalty?

    @PostMapping("/actions/increment_reservations_count/{username}")
    fun incrementReservations(
        @PathVariable username: String
    ): Loyalty

    @PostMapping("/actions/increment_reservations_count/{username}")
    fun incrementReservationsWithRetry(
        @PathVariable username: String,
        @RequestHeader /* Will go to body otherwise */ failCallback: () -> Unit
    ): Loyalty?

    @PostMapping("/actions/decrement_reservations_count/{username}")
    fun decrementReservations(
        @PathVariable username: String
    ): Loyalty

    @PostMapping("/actions/decrement_reservations_count/{username}")
    fun decrementReservationsWithRetry(
        @PathVariable username: String,
        @RequestHeader /* Will go to body otherwise */ failCallback: () -> Unit
    ): Loyalty?
}

@Component
class LoyaltyClientFallback: LoyaltyClient {
    override fun getLoyaltyForUser(username: String): Loyalty {
        throw ServiceUnavailableException(SERVICE_NOT_AVAILABLE_MESSAGE)
    }

    override fun getLoyaltyForUserWithFallback(username: String): Loyalty? {
        return null
    }

    override fun incrementReservations(username: String): Loyalty {
        throw ServiceUnavailableException(SERVICE_NOT_AVAILABLE_MESSAGE)
    }

    override fun incrementReservationsWithRetry(
        username: String,
        failCallback: () -> Unit,
    ): Loyalty? {
        failCallback()
        return null
    }

    override fun decrementReservations(username: String): Loyalty {
        throw ServiceUnavailableException(SERVICE_NOT_AVAILABLE_MESSAGE)
    }

    override fun decrementReservationsWithRetry(
        username: String,
        failCallback: () -> Unit
    ): Loyalty? {
        failCallback()
        return null
    }

    companion object {
        private const val SERVICE_NOT_AVAILABLE_MESSAGE = "Loyalty service is not available"
    }
}
