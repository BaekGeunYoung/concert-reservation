package com.practice.concert_reservation_app.domain.concert.repository

import com.practice.concert_reservation_app.domain.concert.domain.Seat
import com.practice.concert_reservation_app.domain.concert.domain.SeatId
import org.springframework.data.jpa.repository.JpaRepository

interface SeatRepository : JpaRepository<Seat, SeatId>