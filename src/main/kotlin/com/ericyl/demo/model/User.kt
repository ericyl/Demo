package com.ericyl.demo.model

import com.ericyl.demo.util.UUIdGenId
import tk.mybatis.mapper.annotation.KeySql
import javax.persistence.Column
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Transient

@Table(name = "USERS")
data class User(
        @Id
        @KeySql(genId = UUIdGenId::class)
        @Column(name = "user_id")
        var uid: String? = null,
        val username: String? = null,
        var password: String? = null,
        @Column(name = "zhname")
        val zhName: String? = null,
        var status: Int? = null,
        val did: String? = null,
        val phone: String? = null,
        @Transient
        val dept: Dept? = null,
        @Transient
        val roles: List<Role>? = null
)