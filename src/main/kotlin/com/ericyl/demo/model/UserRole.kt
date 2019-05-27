package com.ericyl.demo.model

import javax.persistence.*

@Entity
@Table(name = "user_role", uniqueConstraints = [UniqueConstraint(name = "USER_ROLE_UINDEX", columnNames = ["uid", "rid"])])
data class UserRole(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "urid", nullable = false, length = 10)
        var urid: Int? = null,
        @Column(name = "uid", nullable = false, length = 10)
        var uid: Int? = null,
        @Column(name = "rid", nullable = false, length = 10)
        var rid: Int? = null
)
