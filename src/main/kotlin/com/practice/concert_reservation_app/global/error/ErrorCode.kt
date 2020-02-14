package com.practice.concert_reservation_app.global.error

enum class ErrorCode(
        val code: String,
        val message: String
) {
    AUTHENTICATION_FAILED("AC_001", "authentication failed."),
    DUPLICATE_USERNAME("AC_002", "username already exists.")
}