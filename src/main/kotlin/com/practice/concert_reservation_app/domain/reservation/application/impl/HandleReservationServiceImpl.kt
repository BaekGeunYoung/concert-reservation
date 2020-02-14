package com.practice.concert_reservation_app.domain.reservation.application.impl

import com.practice.concert_reservation_app.domain.concert.application.GetConcertService
import com.practice.concert_reservation_app.domain.reservation.application.HandleReservationService
import com.practice.concert_reservation_app.domain.reservation.domain.Reservation
import com.practice.concert_reservation_app.domain.reservation.exception.InvalidSeatNumberException
import com.practice.concert_reservation_app.domain.reservation.exception.ReservationNotFoundException
import com.practice.concert_reservation_app.domain.reservation.exception.SeatAlreadyTakenException
import com.practice.concert_reservation_app.domain.reservation.repository.ReservationRepository
import com.practice.concert_reservation_app.domain.user.application.UserFindService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class HandleReservationServiceImpl(
        @Autowired private val reservationRepository: ReservationRepository,
        @Autowired private val getConcertService: GetConcertService,
        @Autowired private val userFindService: UserFindService
): HandleReservationService {
    override fun cancelReservation(concertId: Long, seatNumber: Int, username: String) {
        val concert = getConcertService.getConcert(concertId)
        val user = userFindService.findByUsername(username)

        val reservation = Reservation(
                concertId = concertId,
                seatNumber = seatNumber,
                username = username
        )

        if (!reservationRepository.existsById(reservation)) throw ReservationNotFoundException(concertId, seatNumber, username)

        concert.untakeSeat(seatNumber)

        reservationRepository.delete(reservation)
    }

    override fun modifyReservation(concertId: Long, seatNumber: Int, newSeatNumber: Int, username: String): Reservation {
        val concert = getConcertService.getConcert(concertId)
        val user = userFindService.findByUsername(username)

        val reservation = Reservation(
                concertId = concertId,
                seatNumber = seatNumber,
                username = username
        )

        if (!reservationRepository.existsById(reservation)) throw ReservationNotFoundException(concertId, seatNumber, username)

        if (!concert.isValidSeat(newSeatNumber)) throw InvalidSeatNumberException(concertId, newSeatNumber)
        if (!concert.canReserve(newSeatNumber)) throw SeatAlreadyTakenException(concertId, newSeatNumber)

        concert.untakeSeat(seatNumber)
        concert.takeSeat(newSeatNumber)

        val newReservation = Reservation(
                concertId = concertId,
                seatNumber = newSeatNumber,
                username = username
        )

        reservationRepository.delete(reservation)
        return reservationRepository.save(newReservation)
    }
}