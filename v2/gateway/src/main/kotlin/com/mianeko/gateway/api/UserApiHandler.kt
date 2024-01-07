package com.mianeko.gateway.api

import com.mianeko.gateway.api.clients.LoyaltyClient
import com.mianeko.gateway.api.clients.PaymentClient
import com.mianeko.gateway.api.clients.ReservationClient
import com.mianeko.gateway.api.models.PaidReservationInfo
import com.mianeko.gateway.api.models.ShortLoyaltyInfo
import com.mianeko.gateway.api.models.ShortPaymentInfo
import com.mianeko.gateway.api.models.UserInfo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/me")
class UserApiHandler(
    private val reservationClient: ReservationClient,
    private val loyaltyClient: LoyaltyClient,
    private val paymentClient: PaymentClient
) {

    @GetMapping
    fun getUserInfo(
        @RequestHeader("X-User-Name") username: String
    ): UserInfo {
        val reservations = reservationClient
            .getInfoByUserId(username)
            .map { reservation ->
                val paymentInfo = paymentClient.getPaymentById(reservation.paymentUid)
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
            }
        val loyalty = loyaltyClient
            .getLoyaltyForUser(username)
            .let {
                ShortLoyaltyInfo(
                    status = it.status,
                    discount = it.discount
                )
            }
        return UserInfo(reservations, loyalty)
    }
}
