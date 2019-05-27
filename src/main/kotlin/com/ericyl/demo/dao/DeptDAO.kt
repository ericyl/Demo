package com.ericyl.demo.dao

import com.ericyl.demo.model.Dept
import com.ericyl.demo.util.BasicMapper
import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Component


@Component
@Mapper
interface DeptDAO : BasicMapper<Dept> {

    @Select("SELECT * FROM dept WHERE did = #{did}")
    @Results(id = "dept",
            value = [
                Result(column = "did", property = "did", javaType = String::class),
                Result(column = "dept_name", property = "name", javaType = String::class),
                Result(column = "parent_id", property = "parentId", javaType = String::class)
            ])
    fun selectById(@Param("did") did: String): Dept

    @Select("SELECT * FROM DEPT WHERE PARENT_ID IN ('0', '1')")
    @ResultMap("dept")
    fun selectByTree(): List<Dept>

    @Select("SELECT LISTAGG(d.dept_name, '>') WITHIN GROUP ( ORDER BY d.did ) FROM dept d START WITH d.did = #{did} CONNECT BY d.did = prior d.parent_id")
    fun selectNameById(@Param("did") did: String): String?

    @Select("<script>select substr(sys_connect_by_path(t.dept_name, '>'), 2) dept_name, t.did, t.parent_id from dept t  where 1=1 <if test='name != null'> and t.dept_name like '%'||#{name}||'%' </if> start with t.PARENT_ID = '0' connect by t.PARENT_ID = prior t.DID</script>")
    @ResultMap("dept")
    fun selectTree(@Param("name") name: String?): List<Dept>

}
