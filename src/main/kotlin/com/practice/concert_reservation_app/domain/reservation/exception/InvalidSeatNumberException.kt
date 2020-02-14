package com.practice.concert_reservation_app.domain.reservation.exception

import java.lang.RuntimeException

class InvalidSeatNumberException(concertId: Long, seatNumber: Int): RuntimeException()