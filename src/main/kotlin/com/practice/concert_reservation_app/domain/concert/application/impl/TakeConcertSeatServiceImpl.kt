package com.practice.concert_reservation_app.domain.concert.application.impl

import com.practice.concert_reservation_app.domain.concert.application.GetConcertService
import com.practice.concert_reservation_app.domain.concert.application.TakeConcertSeatService
import com.practice.concert_reservation_app.domain.concert.repository.ConcertRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TakeConcertSeatServiceImpl(
        @Autowired private val concertRepository: ConcertRepository,
        @Autowired private val getConcertService: GetConcertService
): TakeConcertSeatService {
    override fun takeConcertSeat(concertId: Long, seatNumber: Int) {
        val concert = getConcertService.getConcert(concertId)
        concert.takeSeat(seatNumber)
    }
}