package com.practice.concert_reservation_app.domain.concert.exception

import java.lang.RuntimeException

class ConcertNotFoundException(val concertId: Long): RuntimeException()