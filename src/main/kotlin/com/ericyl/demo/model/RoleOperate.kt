package com.ericyl.demo.model

import javax.persistence.*

@Entity
@Table(name = "role_operate", uniqueConstraints = [UniqueConstraint(name = "ROLE_OPERATE_UINDEX1", columnNames = ["oid", "rid"])])
data class RoleOperate(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "roid", nullable = false, length = 10)
        var roid: Int? = null,
        @Column(name = "oid", nullable = false, length = 10)
        var oid: Int? = null,
        @Column(name = "rid", nullable = false, length = 10)
        var rid: Int? = null
)
