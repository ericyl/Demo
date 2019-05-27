package com.ericyl.demo.service

import com.ericyl.demo.model.User
import com.ericyl.demo.model.web.UserProcedure
import com.github.pagehelper.PageSerializable

interface UserService {
    fun save(user: User): String?

    fun findByUsername(username: String): User?

    fun update(user: User): Int?

    fun findBySomething(zhName: String?, username: String?, rid: String?, did: String, pageNum: Int, pageSize: Int, orderBy: String?): PageSerializable<UserProcedure>

    fun findById(uid: String): User?

    fun delete(uid: String): Int?
}
