package com.ericyl.demo.dao

import com.ericyl.demo.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserDAO : JpaRepository<User, Int> {

}