package com.ericyl.demo.model

import javax.persistence.*

@Entity
@Table(name = "operate", uniqueConstraints = [UniqueConstraint(name = "operate_uindex", columnNames = ["url", "method"])])
data class Operate(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "oid", nullable = false, length = 10)
        val oid: Int? = null,
        @Column(name = "name", nullable = false, length = 100)
        val name: String? = null,
        @Column(name = "url", nullable = false, length = 100)
        val url: String? = null,
        @Column(name = "method", nullable = false, length = 100)
        val method: String? = null,
        @Transient
        val roles: List<Role>? = null
)
