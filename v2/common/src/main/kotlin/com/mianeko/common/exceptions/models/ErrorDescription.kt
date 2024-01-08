package com.mianeko.common.exceptions.models

data class ErrorDescription(
    val message: String = "Unknown error",
    val errors: List<FieldError> = listOf()
)

data class FieldError(
    val field: String,
    val error: String
)
