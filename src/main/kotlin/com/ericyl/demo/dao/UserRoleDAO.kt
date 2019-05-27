package com.ericyl.demo.dao

import com.ericyl.demo.model.UserRole
import com.ericyl.demo.util.BasicMapper
import org.apache.ibatis.annotations.Mapper
import org.springframework.stereotype.Component

@Component
@Mapper
interface UserRoleDAO : BasicMapper<UserRole>