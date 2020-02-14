package com.practice.concert_reservation_app.domain.concert.application.impl

import com.practice.concert_reservation_app.domain.concert.application.GetConcertService
import com.practice.concert_reservation_app.domain.concert.domain.Concert
import com.practice.concert_reservation_app.domain.concert.repository.ConcertRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GetConcertServiceImpl(
        @Autowired val concertRepository: ConcertRepository
) : GetConcertService {
    override fun getConcerts(): List<Concert> {
        return concertRepository.findAll()
    }
}