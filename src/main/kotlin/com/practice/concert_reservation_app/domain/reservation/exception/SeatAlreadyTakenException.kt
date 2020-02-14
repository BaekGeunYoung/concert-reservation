package com.practice.concert_reservation_app.domain.reservation.exception

import java.lang.RuntimeException

class SeatAlreadyTakenException(val concertId: Long, val seatNumber: Int): RuntimeException()