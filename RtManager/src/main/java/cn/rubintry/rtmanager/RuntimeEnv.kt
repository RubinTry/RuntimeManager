package cn.rubintry.rtmanager

import android.app.Application

object RuntimeEnv {

    @JvmField
    var app : Application ?= null

    @JvmStatic
    fun requireApp(): Application {
        return app ?: throw IllegalArgumentException("Runtime context not set")
    }
}