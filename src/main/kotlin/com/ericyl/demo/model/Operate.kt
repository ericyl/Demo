package com.ericyl.demo.model

import com.ericyl.demo.util.UUIdGenId
import tk.mybatis.mapper.annotation.KeySql
import javax.persistence.Column
import javax.persistence.Id
import javax.persistence.Transient

data class Operate(
        @Id
        @KeySql(genId = UUIdGenId::class)
        val oid: String? = null,
        @Column(name = "operate_name")
        val name: String? = null,
        val url: String? = null,
        val method: String? = null,
        @Transient
        val roles: List<Role>? = null
)
