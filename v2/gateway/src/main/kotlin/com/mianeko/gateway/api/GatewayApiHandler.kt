package com.mianeko.gateway.api

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.RestController

@RestController
class GatewayApiHandler(
    @Value("\${spring.datasource.url}") val url: String,
    @Value("\${spring.datasource.driver-class-name}") val driverClassName: String,
    @Value("\${spring.datasource.username}") val username: String,
    @Value("\${spring.datasource.password}") val password: String,
) {
//    @GetMapping("/hotels")
//    fun getHotels(
//        @RequestParam(required = false, defaultValue = "1") page: Int,
//        @RequestParam(required = false, defaultValue = "1") size: Int
//    ): HotelInfo {
//
//    }
}
