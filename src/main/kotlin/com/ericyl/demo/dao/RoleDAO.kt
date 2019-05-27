package com.ericyl.demo.dao

import com.ericyl.demo.model.Role
import com.ericyl.demo.util.BasicMapper
import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Component

@Component
@Mapper
interface RoleDAO : BasicMapper<Role> {

    @Select("select r.* from role r, user_role ur where r.rid = ur.rid and ur.user_id = #{uid}")
    @Results(id = "role",
            value = [
                Result(column = "rid", property = "rid", javaType = String::class),
                Result(column = "role_name", property = "name", javaType = String::class),
                Result(column = "role_key", property = "key", javaType = String::class),
                Result(column = "root", property = "root", javaType = Int::class)])
    fun selectByUid(@Param("uid") uid: String): List<Role>

    @Select("select r.* from role r, role_operate ro where r.rid = ro.rid and ro.oid = #{oid}")
    @ResultMap("role")
    fun selectByOid(@Param("oid") oid: String): List<Role>

}
