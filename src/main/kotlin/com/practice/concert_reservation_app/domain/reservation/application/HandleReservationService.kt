package com.practice.concert_reservation_app.domain.reservation.application

import com.practice.concert_reservation_app.domain.reservation.domain.Reservation

interface HandleReservationService {
    fun cancelReservation(concertId: Long, seatNumber: Int, username: String)

    fun modifyReservation(concertId: Long, seatNumber: Int, newSeatNumber: Int, username: String): Reservation
}