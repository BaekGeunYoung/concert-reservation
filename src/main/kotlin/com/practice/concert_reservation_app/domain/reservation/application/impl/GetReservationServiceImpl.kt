package com.practice.concert_reservation_app.domain.reservation.application.impl

import com.practice.concert_reservation_app.domain.reservation.application.GetReservationService
import com.practice.concert_reservation_app.domain.reservation.domain.Reservation
import com.practice.concert_reservation_app.domain.reservation.repository.ReservationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GetReservationServiceImpl(
        @Autowired private val reservationRepository: ReservationRepository
): GetReservationService {
    override fun getReservationsByUser(username: String): List<Reservation> {
        return reservationRepository.findByUsername(username)
    }

    override fun getReservationsByConcert(concertId: Long): List<Reservation> {
        return reservationRepository.findByConcertId(concertId)
    }
}