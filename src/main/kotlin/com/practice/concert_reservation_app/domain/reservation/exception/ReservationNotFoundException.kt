package com.practice.concert_reservation_app.domain.reservation.exception

import java.lang.RuntimeException

class ReservationNotFoundException(val concertId: Long, val seatNumber: Int, val username: String): RuntimeException()