package com.practice.concert_reservation_app.global.error

enum class ErrorCode(
        val code: String,
        val message: String
) {
    AUTHENTICATION_FAILED("AC_001", "authentication failed."),
    DUPLICATE_USERNAME("AC_002", "username already exists."),
    CONCERT_NOT_FOUND("CO_001", "cannot find concert."),
    INVALID_SEAT_NUMBER("CO_002", "invalid seat number."),
    SEAT_ALREADY_TAKEN("CO_003", "the seat is already taken."),
    RESERVATION_NOT_FOUND("RE_001", "cannot find reservation."),
    INVALID_JWT("AU_001", "jwt validation failed.")
}