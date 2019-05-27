package com.ericyl.demo.controller.rest

import com.ericyl.demo.exception.RestException
import com.ericyl.demo.model.Dept
import com.ericyl.demo.service.DeptService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("dept")
class RestDeptController {

    @Autowired
    private val deptService: DeptService? = null

    @GetMapping("tree")
    fun tree(): List<Map<String, Any?>> {
        val depts = deptService?.findTree() ?: throw RestException()
        return depts.filter { it.parentId == "0" }.map {
            mapOf(
                    Pair("text", it.name),
                    Pair("id", "did-${it.did}"),
                    Pair("dataAttr", hashMapOf(Pair("did", it.did))),
                    Pair("state", hashMapOf(Pair("selected", true))),
                    Pair("class", "root"),
                    Pair("nodes", depts.filter { n -> n.parentId == "1" }.map { n ->
                        mapOf(
                                Pair("text", n.name),
                                Pair("id", "did-${n.did}"),
                                Pair("dataAttr", hashMapOf(Pair("did", n.did))),
                                Pair("lazyLoad", true)
                        )
                    })
            )
        }

    }

    @GetMapping("tree/child")
    fun tree(code: String): List<Map<String, Any?>> {
        val depts = deptService?.findChildTree(code) ?: throw RestException()
        return depts.map {
            mapOf(
                    Pair("text", it.name),
                    Pair("id", "did-${it.did}"),
                    Pair("dataAttr", hashMapOf(Pair("did", it.did))),
                    Pair("lazyLoad", true)
            )
        }
    }

    @GetMapping("name/{id}")
    fun getName(@PathVariable("id") id: String): String {
        return deptService?.findName(id) ?: throw RestException()
    }

    @PostMapping
    fun save(@RequestBody dept: Dept): String {
        return deptService?.save(dept) ?: throw RestException()
    }

    @PostMapping("delete/{id}")
    fun delete(@PathVariable("id") id: String): Boolean? {
        val result = deptService?.delete(id) ?: throw RestException()
        return result > 0
    }

    @PostMapping("update/{id}")
    fun update(@PathVariable("id") id: String, @RequestBody dept: Dept): Boolean? {
        dept.did = id
        val result = deptService?.update(dept) ?: throw RestException()
        return result > 0
    }

    @GetMapping("selectTree")
    fun selectTree(name: String?): List<Map<String, Any?>> {
        val depts = deptService?.findSelectTree(name) ?: throw RestException()
        return depts.map {
            mapOf(
                    Pair("id", it.did),
                    Pair("text", it.name)
            )
        }
    }

}
