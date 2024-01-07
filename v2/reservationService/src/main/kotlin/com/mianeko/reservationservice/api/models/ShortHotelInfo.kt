package com.mianeko.reservationservice.api.models

import java.util.*

data class ShortHotelInfo(
    val hotelUid: UUID,
    val name: String,
    val fullAddress: String,
    val stars: Int?
)
