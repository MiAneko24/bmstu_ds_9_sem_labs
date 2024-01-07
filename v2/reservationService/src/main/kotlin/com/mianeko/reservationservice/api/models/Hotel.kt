package com.mianeko.reservationservice.api.models

import java.util.*

data class Hotel(
    val hotelUid: UUID,
    val name: String,
    val country: String,
    val city: String,
    val address: String,
    val stars: Int?,
    val price: Int
)
