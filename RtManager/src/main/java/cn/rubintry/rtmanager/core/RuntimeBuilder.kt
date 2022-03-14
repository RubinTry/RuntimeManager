package cn.rubintry.rtmanager.core

import android.app.Application
import android.content.Context
import cn.rubintry.rtmanager.BuildConfig
import cn.rubintry.rtmanager.callback.OnRuntimeChangeListener
import cn.rubintry.rtmanager.callback.RuntimeLifecycleCallback
import java.lang.ref.WeakReference

class RuntimeBuilder(context: Context) {

    var mDebugOnly: Boolean = false
    private val contextWeak = WeakReference(context)

    var onRuntimeChangeListener : OnRuntimeChangeListener?= null

    var testUrl: String = ""
    var productUrl: String = ""

    fun addTestUrl(testUrl: String) : RuntimeBuilder {
        assert(testUrl.startsWith("http://") || testUrl.startsWith("https://"))
        this.testUrl = testUrl
        return this
    }

    fun addProductUrl(productUrl: String): RuntimeBuilder {
        assert(productUrl.startsWith("http://") || productUrl.startsWith("https://"))
        this.productUrl = productUrl
        return this
    }

    fun addRuntimeChangeListener(listener : OnRuntimeChangeListener) : RuntimeBuilder {
        this.onRuntimeChangeListener = listener
        return this
    }

    fun install(){
        //注册activity生命周期回调
        if(mDebugOnly){
            if(BuildConfig.DEBUG){
                registerActivityLifecycleCallback()
            }
        }else{
            registerActivityLifecycleCallback()
        }
    }

    fun debugOnly(): RuntimeBuilder {
        this.mDebugOnly = true
        return this
    }



    /**
     * 注册activity生命周期回调
     *
     */
    private fun registerActivityLifecycleCallback() {
        if(contextWeak.get() is Application){
            (contextWeak.get() as Application).registerActivityLifecycleCallbacks(
                RuntimeLifecycleCallback(this)
            )
        }

    }



}