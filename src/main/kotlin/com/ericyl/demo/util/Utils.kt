package com.ericyl.demo.util

import com.google.gson.reflect.TypeToken

fun <T : Any> T.setByFiled(name: String, value: Any): T {
    val field = this.javaClass.getDeclaredField(name)
    field.isAccessible = true
    field.set(this, value)
    return this
}


inline fun <reified T> genericType() = object : TypeToken<T>() {}.type!!

