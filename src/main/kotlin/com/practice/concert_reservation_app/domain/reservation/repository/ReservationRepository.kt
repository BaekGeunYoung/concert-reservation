package com.practice.concert_reservation_app.domain.reservation.repository

import com.practice.concert_reservation_app.domain.reservation.domain.Reservation
import org.springframework.data.jpa.repository.JpaRepository

interface ReservationRepository : JpaRepository<Reservation, Reservation> {
    fun findByUserId(userId: Long)
}