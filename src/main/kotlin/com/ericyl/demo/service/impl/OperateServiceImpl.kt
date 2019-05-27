package com.ericyl.demo.service.impl

import com.ericyl.demo.common.REDIS_OPERATE_KEY
import com.ericyl.demo.common.REDIS_ROLE_KEY
import com.ericyl.demo.dao.OperateDAO
import com.ericyl.demo.dao.RoleDAO
import com.ericyl.demo.dao.RoleOperateDAO
import com.ericyl.demo.model.Operate
import com.ericyl.demo.model.Role
import com.ericyl.demo.model.RoleOperate
import com.ericyl.demo.service.OperateService
import com.ericyl.demo.util.genericType
import com.github.pagehelper.Page
import com.github.pagehelper.PageHelper
import com.github.pagehelper.PageSerializable
import com.google.gson.Gson
import org.apache.commons.lang3.BooleanUtils
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tk.mybatis.mapper.entity.Example

@Service
class OperateServiceImpl : OperateService {

    @Autowired
    private val operateDAO: OperateDAO? = null

    @Autowired
    private val redisTemplate: StringRedisTemplate? = null

    @Autowired
    private val roleDAO: RoleDAO? = null

    @Autowired
    private val roleOperateDAO: RoleOperateDAO? = null

    override fun list(): List<Operate> {
        return if (BooleanUtils.isTrue(redisTemplate?.hasKey(REDIS_OPERATE_KEY))) {
            redisTemplate?.opsForList()?.range(REDIS_OPERATE_KEY, 0, -1)?.map { e -> Gson().fromJson(e, Operate::class.java) }
                    ?: listOf()
        } else {
            val list = operateDAO?.selectAll() ?: listOf()
            redisTemplate?.opsForList()?.leftPushAll(REDIS_OPERATE_KEY, list.map { Gson().toJson(it) })
            list
        }
    }

    override fun rbac(roles: Array<String>): Set<Operate> {
        return roles.map { e ->
            if (BooleanUtils.isTrue(redisTemplate?.opsForHash<String, List<Operate>>()?.hasKey(REDIS_ROLE_KEY, e))) {
                val str = redisTemplate?.opsForHash<String, String>()?.get(REDIS_ROLE_KEY, e)
                if (StringUtils.isBlank(str))
                    listOf()
                else
                    Gson().fromJson<List<Operate>>(str, genericType<List<Operate>>())
            } else {
                val list = operateDAO?.selectByRole(e.toUpperCase().substringAfter("ROLE_")) ?: listOf()
                redisTemplate?.opsForHash<String, String>()?.put(REDIS_ROLE_KEY, e, Gson().toJson(list))
                list
            }
        }.flatMap { e -> e.map { it } }.toSet()

    }

    override fun findBySomething(operate: Operate, pageNum: Int, pageSize: Int, orderBy: String?): PageSerializable<Operate> {
        PageHelper.startPage<Operate>(pageNum, pageSize, orderBy)
        val example = Example(Operate::class.java)
        val criteria = example.createCriteria()
        if (StringUtils.isNotBlank(operate.name))
            criteria.andLike("name", "%${operate.name}%")
        if (StringUtils.isNotBlank(operate.method))
            criteria.andEqualTo("method", operate.method)
        if (StringUtils.isNotBlank(operate.url))
            criteria.andLike("url", "%${operate.url}%")
        return (operateDAO?.selectByExample(example) as Page<Operate>).doSelectPageSerializable<Operate> { }
    }

    @Transactional
    override fun save(operate: Operate): String? {
        operateDAO?.insertSelective(operate)

        val example = Example(Role::class.java)
        example.createCriteria().andEqualTo("root", 1)
        val rid = roleDAO?.selectOneByExample(example)?.rid
        if (rid != null) {
            val roleOperate = RoleOperate(oid = operate.oid, rid = rid)
            roleOperateDAO?.insertSelective(roleOperate)
        }
        return operate.oid
    }

    @Transactional
    override fun update(operate: Operate): Int? {
        return operateDAO?.updateByPrimaryKeySelective(operate)
    }

    @Transactional
    override fun delete(id: String?): Int? {
        return operateDAO?.deleteByPrimaryKey(id)
    }

    override fun findAll(): List<Operate>? {
        return operateDAO?.selectAll()
    }
}
