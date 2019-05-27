package com.ericyl.demo.model

import com.ericyl.demo.util.UUIdGenId
import tk.mybatis.mapper.annotation.KeySql
import javax.persistence.Column
import javax.persistence.Id

data class UserRole(
        @Id
        @KeySql(genId = UUIdGenId::class)
        var urid: String? = null,
        @Column(name = "user_id")
        var uid: String? = null,
        var rid: String? = null
)
