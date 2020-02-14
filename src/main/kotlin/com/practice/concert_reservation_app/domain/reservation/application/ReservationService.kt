package com.practice.concert_reservation_app.domain.reservation.application

import com.practice.concert_reservation_app.domain.reservation.domain.Reservation
import org.springframework.http.ResponseEntity

interface ReservationService {
    fun getReservationsByUser(username: String): Set<Reservation>

    fun getReservationsByConcert(concertId: Long): Set<Reservation>

    fun reserve(concertId: Long, seatNumber: Int, username: String): Reservation

    fun cancelReservation(concertId: Long, seatNumber: Int, username: String)

    fun modifyReservation(concertId: Long, seatNumber: Int, username: String): Reservation
}