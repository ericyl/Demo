package com.ericyl.demo.service

import com.ericyl.demo.model.User
import org.springframework.data.domain.Page


interface UserService {
    fun save(user: User): String?

    fun findByUsername(username: String): User?

    fun update(user: User): Int?

    fun findBySomething(zhName: String?, username: String?, rid: String?, did: String, pageNum: Int, pageSize: Int, orderBy: String?): Page<User>

    fun findById(uid: String): User?

    fun delete(uid: String): Int?
}
