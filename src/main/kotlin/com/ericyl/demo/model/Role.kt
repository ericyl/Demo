package com.ericyl.demo.model

import javax.persistence.*

@Entity
@Table(name = "role")
data class Role(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "rid", nullable = false, length = 10)
        val rid: Int? = null,
        @Column(name = "name", nullable = false, length = 100)
        val name: String? = null,
        @Column(name = "role_key", nullable = false, length = 20, unique = true)
        val key: String? = null,
        @Column(name = "root", nullable = false, columnDefinition = "int(1) default 0")
        val root: Int? = null
)