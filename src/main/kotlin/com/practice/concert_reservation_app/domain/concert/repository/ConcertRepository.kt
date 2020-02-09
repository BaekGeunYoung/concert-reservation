package com.practice.concert_reservation_app.domain.concert.repository

import com.practice.concert_reservation_app.domain.concert.domain.Concert
import org.springframework.data.jpa.repository.JpaRepository

interface ConcertRepository : JpaRepository<Concert, Long>