package com.ericyl.demo.service.impl

import com.ericyl.demo.dao.DeptDAO
import com.ericyl.demo.dao.UserDAO
import com.ericyl.demo.dao.UserRoleDAO
import com.ericyl.demo.model.User
import com.ericyl.demo.model.UserRole
import com.ericyl.demo.model.web.UserProcedure
import com.ericyl.demo.service.UserService
import com.github.pagehelper.Page
import com.github.pagehelper.PageHelper
import com.github.pagehelper.PageSerializable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tk.mybatis.mapper.entity.Example

@Service
class UserServiceImpl : UserService {
    @Autowired
    private val userDAO: UserDAO? = null

    @Autowired
    private val passwordEncoder: PasswordEncoder? = null

    @Autowired
    private val userRoleDAO: UserRoleDAO? = null

    @Autowired
    private val deptDAO: DeptDAO? = null

    @Transactional
    override fun save(user: User): String? {
        if (passwordEncoder == null)
            return null
        user.password = passwordEncoder.encode(user.password)
        userDAO?.insertSelective(user)

        if (user.roles?.isNotEmpty() == true) {
            val userRoles = user.roles.map {
                UserRole(
                        uid = user.uid,
                        rid = it.rid
                )
            }
            userRoleDAO?.insertList(userRoles)
        }
        return user.uid
    }

    override fun findByUsername(username: String): User? {
        return userDAO?.selectByUsername(username)
    }

    @Transactional
    override fun update(user: User): Int? {
        if (passwordEncoder == null)
            return null
        if (user.password?.isNotEmpty() == true) {
            user.password = passwordEncoder.encode(user.password)
        }

        if (user.roles?.isNotEmpty() == true) {

            val example = Example(UserRole::class.java)
            example.createCriteria().andEqualTo("uid", user.uid)
            userRoleDAO?.deleteByExample(example)

            val userRoles = user.roles.map {
                UserRole(
                        uid = user.uid,
                        rid = it.rid
                )
            }
            userRoleDAO?.insertList(userRoles)
        }
        return userDAO?.updateByPrimaryKeySelective(user)
    }

    override fun findBySomething(zhName: String?, username: String?, rid: String?, did: String, pageNum: Int, pageSize: Int, orderBy: String?): PageSerializable<UserProcedure> {
        PageHelper.startPage<UserProcedure>(pageNum, pageSize, orderBy)
        return (userDAO?.selectBySomething(zhName, username, rid, did) as Page<UserProcedure>).doSelectPageSerializable<UserProcedure> { }
    }

    override fun findById(uid: String): User? {
        val user = userDAO?.selectById(uid)
        if (user?.did != null)
            user.dept?.name = deptDAO?.selectNameById(user.did)
        return user
    }

    @Transactional
    override fun delete(uid: String): Int? {
        val example = Example(UserRole::class.java)
        example.createCriteria().andEqualTo("uid", uid)
        userRoleDAO?.deleteByExample(example)
        return userDAO?.deleteByPrimaryKey(uid)
    }


}
