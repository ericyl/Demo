package com.ericyl.demo.model.web

data class PageList<T>(
        val total: Long? = 0,
        val rows: List<T> = listOf()
)