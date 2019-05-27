package com.ericyl.demo.model

import javax.persistence.*

@Entity
@Table(name="user")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "uid", nullable = false, length = 10)
        val uid: Int? = null,
        @Column(name = "username", nullable = false, length = 20, unique = true)
        val username: String? = null,
        @Column(name = "password", nullable = false, length = 100)
        val password: String? = null,
        @Column(name = "zhname", nullable = true, length = 100)
        val zhName: String? = null,
        @Column(name = "status", nullable = true, length = 1)
        val status: Int? = null,
        @Column(name = "did", nullable = false, length = 10)
        val did: Int? = null,
        @Column(name = "phone", nullable = true, length = 20)
        val phone: String? = null,
        @Transient
        val dept: Dept? = null,
        @Transient
        val roles: List<Role>? = null
)