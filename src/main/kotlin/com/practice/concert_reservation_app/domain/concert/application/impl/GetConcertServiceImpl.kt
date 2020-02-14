package com.practice.concert_reservation_app.domain.concert.application.impl

import com.practice.concert_reservation_app.domain.concert.application.GetConcertService
import com.practice.concert_reservation_app.domain.concert.domain.Concert
import com.practice.concert_reservation_app.domain.concert.exception.ConcertNotFoundException
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

    override fun getConcert(concertId: Long): Concert {
        val concert = concertRepository.findById(concertId)
        if(concert.isEmpty) throw ConcertNotFoundException(concertId)

        return concert.get()
    }
}