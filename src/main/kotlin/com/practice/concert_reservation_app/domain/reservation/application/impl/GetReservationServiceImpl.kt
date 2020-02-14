package com.practice.concert_reservation_app.domain.reservation.application.impl

import com.practice.concert_reservation_app.domain.concert.application.GetConcertService
import com.practice.concert_reservation_app.domain.reservation.application.GetReservationService
import com.practice.concert_reservation_app.domain.reservation.domain.Reservation
import com.practice.concert_reservation_app.domain.reservation.repository.ReservationRepository
import com.practice.concert_reservation_app.domain.user.application.UserFindService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GetReservationServiceImpl(
        @Autowired private val reservationRepository: ReservationRepository,
        @Autowired private val userFindService: UserFindService,
        @Autowired private val getConcertService: GetConcertService
): GetReservationService {
    override fun getReservationsByUser(username: String): List<Reservation> {
        val user = userFindService.findByUsername(username)
        return reservationRepository.findByUsername(username)
    }

    override fun getReservationsByConcert(concertId: Long): List<Reservation> {
        val concert = getConcertService.getConcert(concertId)
        return reservationRepository.findByConcertId(concertId)
    }
}