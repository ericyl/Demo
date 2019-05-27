package com.ericyl.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession
import tk.mybatis.spring.annotation.MapperScan

@SpringBootApplication
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600)
@MapperScan("com.ericyl.demo.dao")
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}