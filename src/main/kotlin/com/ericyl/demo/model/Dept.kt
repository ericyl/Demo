package com.ericyl.demo.model

import com.ericyl.demo.util.UUIdGenId
import tk.mybatis.mapper.annotation.KeySql
import javax.persistence.Column
import javax.persistence.Id

data class Dept(
        @Id
        @KeySql(genId = UUIdGenId::class)
        var did: String? = null,
        @Column(name = "DEPT_NAME")
        var name: String? = null,
        val parentId: String? = null
)
