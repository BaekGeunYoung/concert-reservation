package com.practice.concert_reservation_app.domain.reservation.exception

import java.lang.RuntimeException

class ReservationNotFoundException(concertId: Long, seatNumber: Int, username: String): RuntimeException()