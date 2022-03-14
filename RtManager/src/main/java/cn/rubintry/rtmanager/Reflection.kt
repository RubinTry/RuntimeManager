package cn.rubintry.rtmanager

import java.lang.reflect.Field
import java.lang.reflect.Method


internal  fun Any.getDeclaredField(name: String) : Any? {
    try {
        val field = this.javaClass.getDeclaredField(name)
        field.isAccessible = true
        return field.get(this)
    }catch (e : Exception){
        e.printStackTrace()
    }
    return null
}


internal  fun Any.getDeclaredMethod(name: String): Field? {
    try {
        val method = this.javaClass.getDeclaredField(name)
        method.isAccessible = true
        return method
    }catch (e : Exception){
        e.printStackTrace()
    }
    return null
}

internal fun Any.getDeclaredMethod(name: String , vararg params: Class<*>): Method? {
    try {
        val method = this.javaClass.getDeclaredMethod(name , *params)
        method.isAccessible = true
        return method
    }catch (e: Exception){
        e.printStackTrace()
    }
    return null
}