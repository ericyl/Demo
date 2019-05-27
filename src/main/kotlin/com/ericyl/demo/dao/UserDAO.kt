package com.ericyl.demo.dao

import com.ericyl.demo.dao.provider.UserProvider
import com.ericyl.demo.model.User
import com.ericyl.demo.model.web.UserProcedure
import com.ericyl.demo.util.BasicMapper
import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Component

@Component
@Mapper
interface UserDAO : BasicMapper<User> {

    @Select("select * from users where username = #{username}")
    @Results(id = "user",
            value = [
                Result(column = "user_id", property = "uid", javaType = String::class),
                Result(column = "username", property = "username", javaType = String::class),
                Result(column = "password", property = "password", javaType = String::class),
                Result(column = "zhname", property = "zhName", javaType = String::class),
                Result(column = "status", property = "status", javaType = Int::class),
                Result(column = "did", property = "did", javaType = String::class),
                Result(column = "phone", property = "phone", javaType = String::class),
                Result(column = "did", property = "dept", one = One(select = "com.ericyl.demo.dao.DeptDAO.selectById")),
                Result(column = "user_id", property = "roles", many = Many(select = "com.ericyl.demo.dao.RoleDAO.selectByUid"))
            ])
    fun selectByUsername(@Param("username") username: String): User

    @SelectProvider(type = UserProvider::class, method = "selectBySomething")
    @Results(id = "userProcedure",
            value = [
                Result(column = "user_id", property = "uid", javaType = String::class),
                Result(column = "username", property = "username", javaType = String::class),
                Result(column = "zhname", property = "zhName", javaType = String::class),
                Result(column = "status", property = "status", javaType = Int::class),
                Result(column = "dept_name", property = "deptName", javaType = String::class),
                Result(column = "role_name", property = "roleName", javaType = String::class),
                Result(column = "phone", property = "phone", javaType = String::class),
                Result(column = "root", property = "root", javaType = Boolean::class)
            ])
    fun selectBySomething(@Param("zhName") zhName: String?, @Param("username") username: String?, @Param("rid") rid: String?, @Param("did") did: String): MutableList<UserProcedure>


    @Select("select * from users where user_id = #{uid}")
    @ResultMap("user")
    fun selectById(@Param("uid") uid: String): User

}
