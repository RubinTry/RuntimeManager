package cn.rubintry.rtmanager

import android.content.Context
import android.content.SharedPreferences

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



    private fun getSp() : SharedPreferences{
        return RuntimeEnv.requireApp().getSharedPreferences(SP_NAME , Context.MODE_PRIVATE)
    }
}