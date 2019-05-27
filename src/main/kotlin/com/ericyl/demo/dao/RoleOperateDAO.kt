package com.ericyl.demo.dao

import com.ericyl.demo.model.RoleOperate
import com.ericyl.demo.util.BasicMapper
import org.apache.ibatis.annotations.Mapper
import org.springframework.stereotype.Component

@Component
@Mapper
interface RoleOperateDAO : BasicMapper<RoleOperate>
