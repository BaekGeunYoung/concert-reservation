package com.practice.concert_reservation_app.domain.concert.application

interface TakeConcertSeatService {
    fun takeConcertSeat(concertId: Long, seatNumber: Int)
}