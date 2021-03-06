package com.practice.concert_reservation_app.domain.concert.controller

import com.practice.concert_reservation_app.domain.concert.application.GetConcertService
import com.practice.concert_reservation_app.domain.concert.application.MakeDummyConcertService
import com.practice.concert_reservation_app.domain.concert.domain.Concert
import com.practice.concert_reservation_app.domain.reservation.controller.ReservationController
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/concerts")
class ConcertController(
        @Autowired private val getConcertService: GetConcertService,
        @Autowired private val makeDummyConcertService: MakeDummyConcertService
) {
    private val logger: Logger = LoggerFactory.getLogger(ReservationController::class.java)
    private val className: String = this::class.java.simpleName

    @GetMapping("")
    fun getConcerts(): ResponseEntity<List<Concert>> {
        logger.info("$className : ${Thread.currentThread().stackTrace[1].methodName} - start")
        val concerts = getConcertService.getConcerts()
        logger.info("$className : ${Thread.currentThread().stackTrace[1].methodName} - end")
        return ResponseEntity(concerts, HttpStatus.OK)
    }

    @PostMapping("")
    fun initialize(): ResponseEntity<Unit> {
        logger.info("$className : ${Thread.currentThread().stackTrace[1].methodName} - start")
        makeDummyConcertService.makeDummyConcerts()
        logger.info("$className : ${Thread.currentThread().stackTrace[1].methodName} - end")
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}