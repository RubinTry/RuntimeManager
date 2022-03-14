package cn.rubintry.rtmanager.core

import android.app.Application
import android.content.Context
import cn.rubintry.rtmanager.sp.RuntimeSPUtils
import cn.rubintry.rtmanager.sp.SharedPreferencesKey


object RuntimeManager{

    @JvmStatic
    fun with(context: Context): RuntimeBuilder {
        if(context is Application){
            RuntimeEnv.app = context
        }
        return RuntimeBuilder(context)
    }

    @JvmStatic
    fun getCurrentRuntime() : String{
        return RuntimeSPUtils.getString(SharedPreferencesKey.SELECTED_RUNTIME)
    }
}

