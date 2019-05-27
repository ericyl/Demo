package com.ericyl.demo.service

import com.ericyl.demo.model.Role
import org.springframework.data.domain.Page

interface RoleService {
    fun admin(): String

    fun findByRole(role: Role, pageNum: Int, pageSize: Int, orderBy: String?): Page<Role>

    fun findAll(): List<Role>?

    fun save(role: Role): String?

    fun delete(id: String): Int?

    fun update(role: Role): Int?
}
