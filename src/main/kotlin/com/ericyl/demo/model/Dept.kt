package com.ericyl.demo.model

import javax.persistence.*

@Entity
@Table(name="dept")
data class Dept(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "did", nullable = false, length = 10)
        var did: Int? = null,
        @Column(name = "name", nullable = false, length = 100)
        var name: String? = null,
        @Column(name = "parent_id", nullable = false, length = 10)
        val parentId: Int? = null
)
