package cn.rubintry.rtmanager.callback

import cn.rubintry.rtmanager.db.CustomRuntimeInfo
import java.io.Serializable

interface RuntimeAddCallback : Serializable {
    fun onAdd(info: CustomRuntimeInfo)
}