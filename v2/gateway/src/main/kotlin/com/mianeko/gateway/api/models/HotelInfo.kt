package com.mianeko.gateway.api.models

data class HotelInfo(
    val page: Int,
    val pageSize: Int,
    val totalElements: Int,
    val items: List<Hotel>
)
