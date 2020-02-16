package com.practice.concert_reservation_app.domain.concert.application.impl

import com.practice.concert_reservation_app.domain.concert.application.MakeDummyConcertService
import com.practice.concert_reservation_app.domain.concert.domain.Concert
import com.practice.concert_reservation_app.domain.concert.domain.Seat
import com.practice.concert_reservation_app.domain.concert.repository.ConcertRepository
import com.practice.concert_reservation_app.domain.concert.repository.SeatRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class MakeDummyConcertServiceImpl(
        @Autowired private val concertRepository: ConcertRepository,
        @Autowired private val seatRepository: SeatRepository
): MakeDummyConcertService {
    override fun makeDummyConcerts() {
        val seats1 = mutableSetOf<Seat>()
        val seats2 = mutableSetOf<Seat>()
        val seats3 = mutableSetOf<Seat>()

        for(i in (0 until 100)) {
            var seat = Seat(1, i, false)
            seatRepository.save(seat)
            seats1.add(seat)

            seat = Seat(2, i, false)
            seatRepository.save(seat)
            seats2.add(seat)

            seat = Seat(3, i, false)
            seatRepository.save(seat)
            seats3.add(seat)
        }

        val concert1 = Concert(
                id = 1,
                concertName = "concert 1",
                startTime = LocalDateTime.of(
                        2020,
                        2,
                        1,
                        17,
                        0,
                        0
                ),
                endTime = LocalDateTime.of(
                        2020,
                        2,
                        1,
                        22,
                        0,
                        0
                ),
                seats = seats1
        )

        val concert2 = Concert(
                id = 2,
                concertName = "concert 2",
                startTime = LocalDateTime.of(
                        2020,
                        2,
                        3,
                        17,
                        0,
                        0
                ),
                endTime = LocalDateTime.of(
                        2020,
                        2,
                        3,
                        22,
                        0,
                        0
                ),
                seats = seats2
        )

        val concert3 = Concert(
                id = 3,
                concertName = "concert 3",
                startTime = LocalDateTime.of(
                        2020,
                        2,
                        5,
                        17,
                        0,
                        0
                ),
                endTime = LocalDateTime.of(
                        2020,
                        2,
                        5,
                        22,
                        0,
                        0
                ),
                seats = seats3
        )

        concertRepository.save(concert1)
        concertRepository.save(concert2)
        concertRepository.save(concert3)
    }
}