package com.ericyl.demo.model

import com.ericyl.demo.util.UUIdGenId
import tk.mybatis.mapper.annotation.KeySql
import javax.persistence.Column
import javax.persistence.Id

data class Role(
        @Id
        @KeySql(genId = UUIdGenId::class)
        var rid: String? = null,
        @Column(name = "role_name")
        val name: String? = null,
        @Column(name = "role_key")
        var key: String? = null,
        val root: Int? = null
)