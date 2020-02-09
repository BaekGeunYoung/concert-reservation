package com.practice.concert_reservation_app.domain.user.domain

import javax.persistence.*

@Entity
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
        var roles: MutableSet<Role>
) {
    constructor() : this(
            username = "",
            password = "",
            firstName = "",
            lastName = "",
            roles = mutableSetOf(Role.USER)
    )
}