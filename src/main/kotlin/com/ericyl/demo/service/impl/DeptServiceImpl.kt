package com.ericyl.demo.service.impl

import com.ericyl.demo.dao.DeptDAO
import com.ericyl.demo.dao.UserDAO
import com.ericyl.demo.model.Dept
import com.ericyl.demo.model.User
import com.ericyl.demo.service.DeptService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tk.mybatis.mapper.entity.Example

@Service
class DeptServiceImpl : DeptService {

    @Autowired
    private val deptDAO: DeptDAO? = null

    @Autowired
    private val userDAO: UserDAO? = null

    override fun findTree(): List<Dept>? {
        return deptDAO?.selectByTree()
    }

    override fun findChildTree(code: String): List<Dept>? {
        val example = Example(Dept::class.java)
        example.createCriteria().andEqualTo("parentId", code)
        return deptDAO?.selectByExample(example)
    }

    override fun save(dept: Dept): String? {
        deptDAO?.insertSelective(dept)
        return dept.did
    }

    override fun findName(id: String): String? {
        return deptDAO?.selectNameById(id)
    }

    override fun update(dept: Dept): Int? {
        return deptDAO?.updateByPrimaryKeySelective(dept)
    }

    @Transactional
    override fun delete(id: String): Int? {
        val example = Example(Dept::class.java)
        example.createCriteria().andCondition("did in (select did from dept start WITH  did = '$id' connect by prior did = parent_id)")

        val userExample = Example(User::class.java)
        userExample.createCriteria().andCondition("did in (select did from dept start WITH  did = '$id' connect by prior did = parent_id)")
        userDAO?.deleteByExample(userExample)
        return deptDAO?.deleteByExample(example)
    }

    override fun findSelectTree(name: String?): List<Dept>? {
        return deptDAO?.selectTree(name)
    }
}
