package com.ericyl.demo.service

import com.ericyl.demo.model.Role
import com.github.pagehelper.PageSerializable

interface RoleService {
    fun admin(): String

    fun findByRole(role: Role, pageNum: Int, pageSize: Int, orderBy: String?): PageSerializable<Role>

    fun findAll(): List<Role>?

    fun save(role: Role): String?

    fun delete(id: String): Int?

    fun update(role: Role): Int?
}
