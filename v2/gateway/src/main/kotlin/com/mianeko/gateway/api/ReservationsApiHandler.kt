package com.mianeko.gateway.api

import com.mianeko.common.payment.PaymentTemplate
import com.mianeko.common.reservation.ReservationInfo
import com.mianeko.common.reservation.ReservationTemplate
import com.mianeko.gateway.api.clients.LoyaltyClient
import com.mianeko.gateway.api.clients.PaymentClient
import com.mianeko.gateway.api.clients.ReservationClient
import com.mianeko.gateway.api.models.BookReservationInfo
import com.mianeko.gateway.api.models.PaidReservationInfo
import com.mianeko.gateway.api.models.ShortPaymentInfo
import com.mianeko.gateway.api.models.ShortReservationTemplate
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/reservations")
class ReservationsApiHandler(
    private val reservationClient: ReservationClient,
    private val paymentClient: PaymentClient,
    private val loyaltyClient: LoyaltyClient
) {
    @GetMapping
    fun getReservationForUser(
        @RequestHeader("X-User-Name") username: String
    ): List<PaidReservationInfo> {
        return reservationClient
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
    }

    @PostMapping
    fun bookHotel(
        @RequestHeader("X-User-Name") username: String,
        @RequestBody template: ShortReservationTemplate
    ): BookReservationInfo {
        val reserveDays = template.startDate.until(template.endDate).days
        val loyalty = loyaltyClient.getLoyaltyForUser(username)
        val price = reservationClient.getHotelPrice(template.hotelUid) * reserveDays * (1 - loyalty.discount / 100)

        val payment = paymentClient.create(PaymentTemplate(price))

        loyaltyClient.incrementReservations(username)

        val createdBook = reservationClient.createReservation(
            ReservationTemplate(
                hotelUid = template.hotelUid,
                startDate = template.startDate,
                endDate = template.endDate,
                paymentUid = payment.paymentUid,
                username = username
            )
        )

        return BookReservationInfo(
            reservationUid = createdBook.reservationUid,
            hotelUid = createdBook.hotel.hotelUid,
            startDate = createdBook.startDate,
            endDate = createdBook.endDate,
            discount = loyalty.discount,
            status = createdBook.status,
            payment = ShortPaymentInfo(
                status = payment.status,
                price = payment.price
            )
        )
    }

    @GetMapping("/{reservationUid}")
    fun getBookInfo(
        @RequestHeader("X-User-Name") username: String,
        @PathVariable reservationUid: UUID
    ): PaidReservationInfo {
        val reservation = reservationClient.getReservationInfo(reservationUid)
        val payment = paymentClient.getPaymentById(reservation.paymentUid)
        return PaidReservationInfo(
            reservationUid = reservation.reservationUid,
            hotel = reservation.hotel,
            startDate = reservation.startDate,
            endDate = reservation.endDate,
            status = reservation.status,
            payment = ShortPaymentInfo(payment.status, payment.price)
        )
    }

    @DeleteMapping("/{reservationUid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun cancelReservation(
        @RequestHeader("X-User-Name") username: String,
        @PathVariable reservationUid: UUID
    ) {
        reservationClient.deleteReservation(reservationUid)
        val reservation = reservationClient.getReservationInfo(reservationUid)
        paymentClient.deleteById(reservation.paymentUid)
        loyaltyClient.decrementReservations(username)
    }
}
