package cn.rubintry.rtmanager.sp

import android.content.Context
import android.content.SharedPreferences
import cn.rubintry.rtmanager.core.RuntimeEnv

object RuntimeSPUtils {

    private val SP_NAME = "RUBINTRY_RUNTIME_SP"

    @JvmStatic
    fun putInt(key: String , value: Int){
        getSp().edit().putInt(key , value).apply()
    }

    @JvmStatic
    fun getInt(key: String , defaultValue: Int) : Int{
        return getSp().getInt(key , defaultValue)
    }


    @JvmStatic
    fun putString(key: String , value: String){
        getSp().edit().putString(key , value).apply()
    }

    @JvmStatic
    fun getString(key: String): String{
        return getSp().getString(key , "").toString()
    }

    private fun getSp() : SharedPreferences{
        return RuntimeEnv.requireApp().getSharedPreferences(SP_NAME , Context.MODE_PRIVATE)
    }
}