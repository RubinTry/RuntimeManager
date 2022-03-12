package cn.rubintry.rtmanager

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.core.view.children
import java.lang.ref.WeakReference


object RuntimeManager{

    @JvmStatic
    fun with(context: Context): RuntimeBuilder {
        if(context is Application){
            RuntimeEnv.app = context
        }
        return RuntimeBuilder(context)
    }
}

