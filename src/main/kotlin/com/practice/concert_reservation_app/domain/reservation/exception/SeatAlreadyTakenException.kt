package com.practice.concert_reservation_app.domain.reservation.exception

import java.lang.RuntimeException

class SeatAlreadyTakenException(concertId: Long, seatNumber: Int): RuntimeException()