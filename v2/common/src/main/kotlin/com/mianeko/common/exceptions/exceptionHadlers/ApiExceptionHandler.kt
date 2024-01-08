package com.mianeko.common.exceptions.exceptionHadlers

import com.mianeko.common.exceptions.*
import com.mianeko.common.exceptions.models.ErrorDescription
import com.mianeko.common.exceptions.models.FieldError
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest


@ControllerAdvice
class ApiExceptionHandler {
    @ExceptionHandler(ReservationApiException::class)
    fun handleReservationApiException(e: ReservationApiException, request: WebRequest): ResponseEntity<ErrorDescription> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorDescription(e.message ?: "Reservation service exception occurred"))
    }

    @ExceptionHandler(HotelNotFoundApiException::class)
    fun handleHotelNotFoundApiException(e: HotelNotFoundApiException, request: WebRequest): ResponseEntity<ErrorDescription> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorDescription(
                message = e.message ?: "",
                errors = listOf(
                    FieldError("hotelUid", "No hotel with id ${e.id}")
                )
            ))
    }

    @ExceptionHandler(ReservationNotFoundApiException::class)
    fun handleReservationNotFoundApiException(e: ReservationNotFoundApiException, request: WebRequest): ResponseEntity<ErrorDescription> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ErrorDescription(e.message ?: ""))
    }

    @ExceptionHandler(LoyaltyApiException::class)
    fun handleLoyaltyApiException(e: LoyaltyApiException, webRequest: WebRequest): ResponseEntity<ErrorDescription> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorDescription(e.message ?: "Loyalty service exception occurred"))
    }

    @ExceptionHandler(PaymentApiException::class)
    fun handlePaymentApiException(e: PaymentApiException,  webRequest: WebRequest): ResponseEntity<ErrorDescription> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorDescription(e.message ?: "Payment service exception occurred"))
    }
}
