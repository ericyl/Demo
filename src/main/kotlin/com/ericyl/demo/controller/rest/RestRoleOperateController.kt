package com.ericyl.demo.controller.rest

import com.ericyl.demo.common.REDIS_OPERATE_KEY
import com.ericyl.demo.common.REDIS_ROLE_KEY
import com.ericyl.demo.exception.RestException
import com.ericyl.demo.model.RoleOperate
import com.ericyl.demo.service.RoleOperateService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("roleOperate")
class RestRoleOperateController {

    @Autowired
    private val roleOperateService: RoleOperateService? = null

    @Autowired
    private val redisTemplate: StringRedisTemplate? = null


    @PostMapping
    fun save(@RequestBody roleOperate: RoleOperate): String {
        val result = roleOperateService?.save(roleOperate) ?: throw RestException()
        redisTemplate?.delete(REDIS_ROLE_KEY)
        redisTemplate?.delete(REDIS_OPERATE_KEY)
        return result
    }

    @PostMapping("delete/{id}")
    fun delete(@PathVariable("id") id: String): Boolean {
        val result = roleOperateService?.delete(id) ?: throw RestException()
        redisTemplate?.delete(REDIS_ROLE_KEY)
        redisTemplate?.delete(REDIS_OPERATE_KEY)
        return result > 0
    }

    @PostMapping("list/{rid}")
    fun saveList(@PathVariable("rid") rid: String, @RequestBody oids: List<String>): List<Map<String, String?>> {
        val roleOperates = oids.map {
            RoleOperate(rid = rid, oid = it)
        }
        val list = roleOperateService?.saveList(roleOperates) ?: throw RestException()
        redisTemplate?.delete(REDIS_ROLE_KEY)
        redisTemplate?.delete(REDIS_OPERATE_KEY)
        return list
    }

    @PostMapping("delete/list/{rid}")
    fun deleteList(@PathVariable("rid") rid: String): Boolean {
        val result = roleOperateService?.deleteByRid(rid) ?: throw RestException()
        redisTemplate?.delete(REDIS_ROLE_KEY)
        redisTemplate?.delete(REDIS_OPERATE_KEY)
        return result > 0
    }

//    private fun copy(roleOperate: RoleOperate): RoleOperateDTO {
//        val roleOperateDTO = RoleOperateDTO()
//        BeanUtils.copyProperties(roleOperate, roleOperateDTO)
//        return roleOperateDTO
//    }
//
//    private fun copy(roleOperateDTO: RoleOperateDTO): RoleOperate {
//        val roleOperate = RoleOperate()
//        BeanUtils.copyProperties(roleOperateDTO, roleOperate)
//        return roleOperate
//    }
}
