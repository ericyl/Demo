package com.ericyl.demo.service.impl

import com.ericyl.demo.model.Operate
import com.ericyl.demo.service.OperateService
import org.springframework.data.domain.Page

class OperateServiceImpl : OperateService {

    override fun list(): List<Operate> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun rbac(roles: Array<String>): Set<Operate> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findBySomething(operate: Operate, pageNum: Int, pageSize: Int, orderBy: String?): Page<Operate> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun save(operate: Operate): String? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(operate: Operate): Int? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(id: String?): Int? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAll(): List<Operate>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}