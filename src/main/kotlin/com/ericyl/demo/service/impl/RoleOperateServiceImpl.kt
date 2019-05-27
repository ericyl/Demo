package com.ericyl.demo.service.impl

import com.ericyl.demo.dao.RoleOperateDAO
import com.ericyl.demo.model.RoleOperate
import com.ericyl.demo.service.RoleOperateService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tk.mybatis.mapper.entity.Example

@Service
class RoleOperateServiceImpl : RoleOperateService {

    @Autowired
    private val roleOperateDAO: RoleOperateDAO? = null

    override fun findByRid(rid: String): List<RoleOperate>? {
        val example = Example(RoleOperate::class.java)
        example.createCriteria().andEqualTo("rid", rid)
        return roleOperateDAO?.selectByExample(example)
    }

    @Transactional
    override fun save(roleOperate: RoleOperate): String? {
        roleOperateDAO?.insertSelective(roleOperate)
        return roleOperate.roid
    }

    @Transactional
    override fun delete(id: String): Int? {
        return roleOperateDAO?.deleteByPrimaryKey(id)
    }

    @Transactional
    override fun saveList(roleOperates: List<RoleOperate>): List<Map<String, String?>>? {
        roleOperateDAO?.insertList(roleOperates)
        return roleOperates.map {
            mapOf(
                    Pair("oid", it.oid),
                    Pair("roid", it.roid)
            )
        }
    }

    @Transactional
    override fun deleteByRid(rid: String): Int? {
        val example = Example(RoleOperate::class.java)
        example.createCriteria().andEqualTo("rid", rid)
        return roleOperateDAO?.deleteByExample(example)
    }
}