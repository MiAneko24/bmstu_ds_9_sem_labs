package com.mianeko.common.health

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController {
    @GetMapping("/manage/health")
    @ResponseStatus(HttpStatus.OK)
    fun health() {}
}
