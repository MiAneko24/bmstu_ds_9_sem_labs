package com.mianeko.gateway.api

import com.mianeko.gateway.api.clients.LoyaltyClient
import com.mianeko.gateway.api.clients.PaymentClient
import com.mianeko.gateway.api.clients.ReservationClient
import com.mianeko.gateway.api.models.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/me")
class UserApiHandler(
    private val reservationClient: ReservationClient,
    private val loyaltyClient: LoyaltyClient,
    private val paymentClient: PaymentClient
) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)
    @GetMapping
    fun getUserInfo(
        @RequestHeader("X-User-Name") username: String
    ): UserInfo {
        log.info("Got user info request for user $username")
        val reservations = reservationClient
            .getInfoByUserIdWithFallback(username)
            .map { reservation ->
                paymentClient.getPaymentById(reservation.paymentUid)
                    ?.let { paymentInfo ->
                        PaidReservationInfo(
                            reservationUid = reservation.reservationUid,
                            hotel = reservation.hotel,
                            startDate = reservation.startDate,
                            endDate = reservation.endDate,
                            status = reservation.status,
                            payment = ShortPaymentInfo(
                                status = paymentInfo.status,
                                price = paymentInfo.price
                            )
                        )
                    } ?: EmptyPaidReservationInfo
            }
        log.info("Reservations are $reservations")
        val loyalty = loyaltyClient
            .getLoyaltyForUserWithFallback(username)
            ?.let {
                ShortLoyaltyInfo(
                    status = it.status,
                    discount = it.discount
                )
            } ?: EmptyShortLoyaltyInfo
        log.info("Loyalty is {}", loyalty)
        return UserInfo(reservations, loyalty)
    }
}
