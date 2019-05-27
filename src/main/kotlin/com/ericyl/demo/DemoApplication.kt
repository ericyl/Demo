package com.ericyl.demo

import com.alibaba.druid.pool.DruidDataSource
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}