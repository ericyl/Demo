package com.ericyl.demo.model

import com.ericyl.demo.util.UUIdGenId
import tk.mybatis.mapper.annotation.KeySql
import javax.persistence.Id

data class RoleOperate(
        @Id
        @KeySql(genId = UUIdGenId::class)
        var roid: String? = null,
        var oid: String? = null,
        var rid: String? = null
)
