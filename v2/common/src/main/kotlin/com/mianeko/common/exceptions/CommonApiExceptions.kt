package com.mianeko.common.exceptions

open class CommonApiException(
    override val message: String?
): RuntimeException(message)

class ServiceNotAvailableApiException(
    override val message: String?
): CommonApiException(message)