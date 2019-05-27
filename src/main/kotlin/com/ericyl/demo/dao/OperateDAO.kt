package com.ericyl.demo.dao

import com.ericyl.demo.model.Operate
import com.ericyl.demo.util.BasicMapper
import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Component

@Component
@Mapper
interface OperateDAO : BasicMapper<Operate> {

    @Select("select o.* from role r, role_operate ro, operate o where r.rid = ro.rid and o.oid = ro.oid and r.role_key = #{roleKey}")
    @Results(id = "operate",
            value = [
                Result(column = "oid", property = "oid", javaType = String::class),
                Result(column = "operate_name", property = "name", javaType = String::class),
                Result(column = "url", property = "url", javaType = String::class),
                Result(column = "method", property = "method", javaType = String::class)
            ])
    fun selectByRole(@Param("roleKey") roleKey: String): List<Operate>?

    @Select("select * from operate")
    @Results(id = "fullOperate",
            value = [
                Result(column = "oid", property = "oid", javaType = String::class),
                Result(column = "operate_name", property = "name", javaType = String::class),
                Result(column = "url", property = "url", javaType = String::class),
                Result(column = "method", property = "method", javaType = String::class),
                Result(column = "oid", property = "roles", many = Many(select = "com.ericyl.demo.dao.RoleDAO.selectByOid"))
            ])
    override fun selectAll(): List<Operate>?
}
