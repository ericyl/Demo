package com.ericyl.demo.service

import com.ericyl.demo.model.RoleOperate

interface RoleOperateService {

    fun findByRid(rid: String): List<RoleOperate>?

    fun save(roleOperate: RoleOperate): String?

    fun delete(id: String): Int?

    fun saveList(roleOperates: List<RoleOperate>): List<Map<String, String?>>?

    fun deleteByRid(rid: String): Int?

}
