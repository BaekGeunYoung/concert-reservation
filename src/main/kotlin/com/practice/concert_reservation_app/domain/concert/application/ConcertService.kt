package com.practice.concert_reservation_app.domain.concert.application

import com.practice.concert_reservation_app.domain.concert.domain.Concert

interface ConcertService {
    fun getConcerts(): Set<Concert>
}