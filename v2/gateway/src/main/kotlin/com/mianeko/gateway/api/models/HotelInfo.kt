package com.mianeko.gateway.api.models

import com.mianeko.common.reservation.Hotel

data class HotelInfo(
    val page: Int,
    val pageSize: Int,
    val totalElements: Int,
    val items: List<Hotel>
)
