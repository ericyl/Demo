package com.ericyl.demo.controller.rest

import com.ericyl.demo.exception.RestException
import com.ericyl.demo.model.User
import com.ericyl.demo.model.web.PageList
import com.ericyl.demo.model.web.UserProcedure
import com.ericyl.demo.service.UserService
import org.apache.commons.lang3.BooleanUtils
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("user")
class RestUserController {

    @Autowired
    private val userService: UserService? = null

    @Value("\${init-password}")
    private val initPassword: String? = null

    @PostMapping("update/{id}")
    fun update(@PathVariable("id") id: String, @RequestBody user: User) {
        user.uid = id
        val restResult = userService?.update(user) ?: throw RestException()
        if (BooleanUtils.isNotTrue(restResult > 0))
            throw RestException()
    }

    @PostMapping("initPwd/{id}")
    fun update(@PathVariable("id") id: String) {
        val user = User(uid = id, password = initPassword)
        val restResult = userService?.update(user) ?: throw RestException()
        if (BooleanUtils.isNotTrue(restResult > 0))
            throw RestException()
    }

    @GetMapping
    fun get(zhname: String?, username: String?, rid: String?, did: String?, offset: Int, limit: Int, sort: String?, order: String?): PageList<UserProcedure> {
        if (did == null)
            return PageList(0, listOf())
        var orderBy: String? = null
        if (StringUtils.isNotBlank(sort))
            orderBy = sort + "," + if (StringUtils.isNotBlank(order)) order else "asc"
        val restResult = userService?.findBySomething(zhname, username, rid, did, if (limit == 0) 0 else offset / limit + 1, limit, orderBy)
                ?: throw RestException()
        return PageList(restResult.total, restResult.list)
    }

    @GetMapping("{uid}")
    fun get(@PathVariable("uid") uid: String): User {
        return userService?.findById(uid) ?: throw RestException()
    }

    @PostMapping
    fun save(@RequestBody user: User): String? {
        user.password = initPassword
        user.status = 1
        return userService?.save(user) ?: throw RestException()
    }

    @PostMapping("delete/{uid}")
    fun delete(@PathVariable("uid") id: String): Boolean {
        val result = userService?.delete(id) ?: throw RestException()
        return result > 0
    }
}
