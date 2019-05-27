package com.ericyl.demo.controller.rest

import com.ericyl.demo.common.REDIS_OPERATE_KEY
import com.ericyl.demo.common.REDIS_ROLE_KEY
import com.ericyl.demo.exception.RestException
import com.ericyl.demo.model.Operate
import com.ericyl.demo.model.web.PageList
import com.ericyl.demo.service.OperateService
import com.ericyl.demo.service.RoleOperateService
import com.ericyl.demo.util.setByFiled
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("operate")
class RestOperateController {
    @Autowired
    private val operateService: OperateService? = null

    @Autowired
    private val redisTemplate: StringRedisTemplate? = null

    @Autowired
    private val roleOperateService: RoleOperateService? = null

    @GetMapping
    fun get(name: String?,url: String?,method: String?, offset: Int, limit: Int, sort: String?, order: String?): PageList<Operate> {
        val operate = Operate(
                name = name,
                url = url,
                method = method
        )
        var orderBy: String? = null
        if (StringUtils.isNotBlank(sort))
            orderBy = sort + "," + if (StringUtils.isNotBlank(order)) order else "asc"
        val result = operateService?.findBySomething(operate, if (limit == 0) 0 else offset / limit + 1, limit, orderBy)
                ?: throw RestException()
        return PageList(result.total, result.list)
    }

    @PostMapping("update")
    fun update(name: String, value: String, @RequestParam("pk") id: String) {
        val operate = Operate(oid = id).setByFiled(name, value)
        operateService?.update(operate) ?: throw RestException()
        redisTemplate?.delete(REDIS_OPERATE_KEY)
        redisTemplate?.delete(REDIS_ROLE_KEY)
    }

    @PostMapping
    fun save(@RequestBody operate: Operate): String {
        val result = operateService?.save(operate) ?: throw RestException()
        redisTemplate?.delete(REDIS_OPERATE_KEY)
        redisTemplate?.delete(REDIS_ROLE_KEY)
        return result
    }

    @PostMapping("delete/{id}")
    fun delete(@PathVariable("id") id: String?): Int {
        val result = operateService?.delete(id) ?: throw RestException()
        redisTemplate?.delete(REDIS_OPERATE_KEY)
        redisTemplate?.delete(REDIS_ROLE_KEY)
        return result
    }

    @GetMapping("tree")
    fun getTree(@RequestParam("rid") rid: String, @RequestParam(value = "disable", required = false) disable: Boolean?): List<Map<String, Any?>> {
        val operates = operateService?.findAll() ?: throw RestException()
        val roleOperates = roleOperateService?.findByRid(rid)
        return operates.map {
            mapOf(
                    Pair("text", it.name),
                    Pair("dataAttr", mapOf(
                            Pair("id", it.oid),
                            Pair("value", roleOperates?.firstOrNull { ro -> ro.oid == it.oid }?.roid)
                    )),
                    Pair("id", "operate-${it.oid}"),
                    Pair("state", mapOf(
                            Pair("checked", roleOperates?.any { ro -> ro.oid == it.oid } ?: false),
                            Pair("disabled", disable == true
                            ))
                    )
            )
        }


    }

}
