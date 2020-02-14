package com.practice.concert_reservation_app.domain.reservation.application

import com.practice.concert_reservation_app.domain.reservation.domain.Reservation

interface GetReservationService {
    fun getReservationsByUser(username: String): List<Reservation>

    fun getReservationsByConcert(concertId: Long): List<Reservation>
}