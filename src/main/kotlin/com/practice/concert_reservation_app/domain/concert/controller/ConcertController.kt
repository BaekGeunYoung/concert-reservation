package com.practice.concert_reservation_app.domain.concert.controller

import com.practice.concert_reservation_app.domain.concert.application.GetConcertService
import com.practice.concert_reservation_app.domain.concert.domain.Concert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/concerts")
class ConcertController(
        @Autowired val getConcertService: GetConcertService
) {
    @GetMapping("/")
    fun getConcerts(): ResponseEntity<List<Concert>> {
        val concerts = getConcertService.getConcerts()
        return ResponseEntity(concerts, HttpStatus.OK)
    }
}