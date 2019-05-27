package com.ericyl.demo.service.impl

import com.ericyl.demo.model.Role
import com.ericyl.demo.service.RoleService
import org.springframework.data.domain.Page

class RoleServiceImpl : RoleService {
    override fun admin(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findByRole(role: Role, pageNum: Int, pageSize: Int, orderBy: String?): Page<Role> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAll(): List<Role>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun save(role: Role): String? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(id: String): Int? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(role: Role): Int? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}