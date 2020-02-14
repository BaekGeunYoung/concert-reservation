package com.practice.concert_reservation_app.domain.reservation.application.impl

import com.practice.concert_reservation_app.domain.concert.application.GetConcertService
import com.practice.concert_reservation_app.domain.concert.application.TakeConcertSeatService
import com.practice.concert_reservation_app.domain.reservation.application.ReservationService
import com.practice.concert_reservation_app.domain.reservation.domain.Reservation
import com.practice.concert_reservation_app.domain.reservation.exception.InvalidSeatNumberException
import com.practice.concert_reservation_app.domain.reservation.exception.SeatAlreadyTakenException
import com.practice.concert_reservation_app.domain.reservation.repository.ReservationRepository
import com.practice.concert_reservation_app.domain.user.application.UserFindService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ReservationServiceImpl(
        @Autowired private val reservationRepository: ReservationRepository,
        @Autowired private val getConcertService: GetConcertService,
        @Autowired private val takeConcertSeatService: TakeConcertSeatService,
        @Autowired private val userFindService: UserFindService
): ReservationService {
    override fun reserve(concertId: Long, seatNumber: Int, username: String): Reservation {
        val concert = getConcertService.getConcert(concertId)
        val user = userFindService.findByUsername(username)

        if(!concert.isValidSeat(seatNumber)) throw InvalidSeatNumberException(concertId, seatNumber)
        if(!concert.canReserve(seatNumber)) throw SeatAlreadyTakenException(concertId, seatNumber)

        takeConcertSeatService.takeConcertSeat(concertId, seatNumber)

        val newReservation = Reservation(
                concertId = concertId,
                username = username,
                seatNumber = seatNumber
        )

        return reservationRepository.save(newReservation)
    }
}