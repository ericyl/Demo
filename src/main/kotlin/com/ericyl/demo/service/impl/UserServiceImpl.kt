package com.ericyl.demo.service.impl

import com.ericyl.demo.dao.UserDAO
import com.ericyl.demo.model.User
import com.ericyl.demo.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : UserService {

    @Autowired
    private val userDAO: UserDAO? = null
    
    override fun save(user: User): String? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findByUsername(username: String): User? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(user: User): Int? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findBySomething(zhName: String?, username: String?, rid: String?, did: String, pageNum: Int, pageSize: Int, orderBy: String?): Page<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findById(uid: String): User? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(uid: String): Int? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}