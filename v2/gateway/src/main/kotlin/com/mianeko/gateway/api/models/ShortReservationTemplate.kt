package com.mianeko.gateway.api.models

import java.time.LocalDate
import java.util.*

data class ShortReservationTemplate(
    val hotelUid: UUID,
    val startDate: LocalDate,
    val endDate: LocalDate
)
