package com.practice.concert_reservation_app.domain.reservation.application

import com.practice.concert_reservation_app.domain.reservation.domain.Reservation
import org.springframework.http.ResponseEntity

interface ReservationService {
    fun getReservationInfo(userId: Long): Set<Reservation>
}