package com.practice.concert_reservation_app.domain.user.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*

@Entity
@JsonIgnoreProperties(value = ["password"])
data class User(
        @Id @GeneratedValue
        var id: Long? = null,

        @Column(name="username", unique = true, length = 200)
        var username: String,
        var password: String,
        var firstName: String,
        var lastName: String,

        @Enumerated(EnumType.STRING)
        @ElementCollection(fetch = FetchType.EAGER)
        var roles: MutableList<Role>
) {
    constructor() : this(
            username = "",
            password = "",
            firstName = "",
            lastName = "",
            roles = mutableListOf(Role.USER)
    )
}