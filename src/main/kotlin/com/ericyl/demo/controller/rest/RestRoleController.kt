package com.ericyl.demo.controller.rest

import com.ericyl.demo.common.REDIS_ROLE_KEY
import com.ericyl.demo.exception.RestException
import com.ericyl.demo.model.Role
import com.ericyl.demo.model.web.PageList
import com.ericyl.demo.service.RoleService
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("role")
class RestRoleController {

    @Autowired
    private val roleService: RoleService? = null

    @Autowired
    private val redisTemplate: StringRedisTemplate? = null

    @GetMapping("tree")
    fun tree(): List<Map<String, Any?>> {
        val roles = roleService?.findAll() ?: throw RestException()
        return roles.map {
            mapOf(
                    Pair("text", it.name),
                    Pair("id", "role-${it.rid}"),
                    Pair("dataAttr", mapOf(
                            Pair("id", it.rid),
                            Pair("value", it.key),
                            Pair("root", it.root)
                    )),
                    Pair("state", mapOf(
                            Pair("selected", true)
                    )),
                    Pair("class", if (it.root == 1) "context-menu-disabled" else "")
            )
        }

    }

    @GetMapping
    operator fun get(roleName: String?, roleKey: String?, offset: Int, limit: Int, sort: String?, order: String?): PageList<Role> {
        var orderBy: String? = null
        if (StringUtils.isNotBlank(sort))
            orderBy = sort + "," + if (StringUtils.isNotBlank(order)) order else "asc"

        val result = roleService?.findByRole(Role(name = roleName, key = roleKey), if (limit == 0) 0 else offset / limit + 1, limit, orderBy)
                ?: throw RestException()
        return PageList(result.total, result.list)
    }

    //    @GetMapping("all")
//    fun get(): List<RoleDTO> {
//        val restResult = roleClient!!.getByRole()
//        return RestResultUtils.doSomething(restResult, object : BasicDo<List<RoleDTO>>() {
//            fun todoIt(roleDTOListModel: List<RoleDTO>) {
//                if (CollectionUtils.isEmpty(roleDTOListModel))
//                    throw RestApiException(RestResultEnum.NO_HANDLER_FOUND)
//            }
//        })
//    }
//
    @PostMapping
    fun save(@RequestBody role: Role): String {
        return roleService?.save(role) ?: throw RestException()
    }


    @PostMapping("update/{id}")
    fun update(@PathVariable("id") id: String, @RequestBody role: Role): Boolean? {
        role.key = null
        role.rid = id
        val result = roleService?.update(role) ?: throw RestException()
        redisTemplate?.delete(REDIS_ROLE_KEY)
        return result > 0
    }


    @PostMapping("delete/{id}")
    fun delete(@PathVariable("id") id: String): Int {
        val restResult = roleService?.delete(id) ?: throw RestException()
        redisTemplate?.delete(REDIS_ROLE_KEY)
        return restResult
    }


}
