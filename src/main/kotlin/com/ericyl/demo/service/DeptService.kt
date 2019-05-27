package com.ericyl.demo.service


import com.ericyl.demo.model.Dept

interface DeptService {

    fun findTree(): List<Dept>?

    fun findChildTree(code: String): List<Dept>?

    fun save(dept: Dept): String?

    fun findName(id: String): String?

    fun update(dept: Dept): Int?

    fun delete(id: String): Int?

    fun findSelectTree(name: String?): List<Dept>?

}
