package com.ericyl.demo.service

import com.ericyl.demo.model.Operate
import com.github.pagehelper.PageSerializable

interface OperateService {

    fun list(): List<Operate>

    fun rbac(roles: Array<String>): Set<Operate>

    fun findBySomething(operate: Operate, pageNum: Int, pageSize: Int, orderBy: String?): PageSerializable<Operate>

    fun save(operate: Operate): String?

    fun update(operate: Operate): Int?

    fun delete(id: String?): Int?

    fun findAll(): List<Operate>?

}
