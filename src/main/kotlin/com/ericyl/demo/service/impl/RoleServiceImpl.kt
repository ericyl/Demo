package com.ericyl.demo.service.impl

import com.ericyl.demo.common.REDIS_PERMISSION_ADMIN_KEY
import com.ericyl.demo.dao.RoleDAO
import com.ericyl.demo.dao.RoleOperateDAO
import com.ericyl.demo.dao.UserRoleDAO
import com.ericyl.demo.model.Role
import com.ericyl.demo.model.RoleOperate
import com.ericyl.demo.model.UserRole
import com.ericyl.demo.model.web.UserProcedure
import com.ericyl.demo.service.RoleService
import com.github.pagehelper.Page
import com.github.pagehelper.PageHelper
import com.github.pagehelper.PageSerializable
import org.apache.commons.lang3.BooleanUtils
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tk.mybatis.mapper.entity.Example

@Service
class RoleServiceImpl : RoleService {

    @Autowired
    private val roleDAO: RoleDAO? = null

    @Autowired
    private val roleOperateDAO: RoleOperateDAO? = null

    @Autowired
    private val userRoleDAO: UserRoleDAO? = null

    @Autowired
    private val redisTemplate: StringRedisTemplate? = null

    override fun admin(): String {
        return if (BooleanUtils.isTrue(redisTemplate?.hasKey(REDIS_PERMISSION_ADMIN_KEY))) {
            redisTemplate?.opsForValue()?.get(REDIS_PERMISSION_ADMIN_KEY) ?: "ADMIN"
        } else {
            val example = Example(Role::class.java)
            example.createCriteria().andEqualTo("root", 1)
            val key = roleDAO?.selectOneByExample(example)?.key?.toUpperCase() ?: "ADMIN"
            redisTemplate?.opsForValue()?.set(REDIS_PERMISSION_ADMIN_KEY, key)
            key
        }
    }

    override fun findByRole(role: Role, pageNum: Int, pageSize: Int, orderBy: String?): PageSerializable<Role> {
        PageHelper.startPage<UserProcedure>(pageNum, pageSize, orderBy)
        val example = Example(Role::class.java)
        val criteria = example.createCriteria()
        if (StringUtils.isNotBlank(role.name))
            criteria.andLike("name", "%${role.name}%")
        if (StringUtils.isNotBlank(role.key))
            criteria.andEqualTo("key", role.key)
        return (roleDAO?.selectByExample(example) as Page<Role>).doSelectPageSerializable<Role> { }
    }

    override fun findAll(): List<Role>? {
        return roleDAO?.selectAll()
    }

    @Transactional
    override fun save(role: Role): String? {
        roleDAO?.insertSelective(role)
        return role.rid
    }


    @Transactional
    override fun delete(id: String): Int? {
        val result = roleDAO?.deleteByPrimaryKey(id)

        val userRoleExample = Example(UserRole::class.java)
        userRoleExample.createCriteria().andEqualTo("rid", id)
        userRoleDAO?.deleteByExample(userRoleExample)

        val roleOperateExample = Example(RoleOperate::class.java)
        roleOperateExample.createCriteria().andEqualTo("rid", id)
        roleOperateDAO?.deleteByExample(userRoleExample)
        return result
    }

    override fun update(role: Role): Int? {
        return roleDAO?.updateByPrimaryKeySelective(role)
    }

}
