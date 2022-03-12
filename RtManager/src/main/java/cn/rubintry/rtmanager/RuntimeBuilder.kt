package cn.rubintry.rtmanager

import android.app.Application
import android.content.Context
import java.lang.ref.WeakReference

class RuntimeBuilder(context: Context) {

    private val contextWeak = WeakReference(context)

    var onRuntimeChangeListener : OnRuntimeChangeListener ?= null

    var testUrl: String = ""
    var productUrl: String = ""

    fun addTestUrl(testUrl: String) : RuntimeBuilder{
        assert(testUrl.startsWith("http://") || testUrl.startsWith("https://"))
        assert(testUrl.urlEndWith("/")){"$ testUrl 尾部不正确，请以“/”结尾，如：“https://www.baidu.com/”"}
        this.testUrl = testUrl
        return this
    }

    fun addProductUrl(productUrl: String): RuntimeBuilder {
        assert(productUrl.startsWith("http://") || productUrl.startsWith("https://"))
        assert(productUrl.urlEndWith("/")){"$ productUrl 尾部不正确，请以“/”结尾，如：“https://www.baidu.com/”"}
        this.productUrl = productUrl
        return this
    }

    fun addRuntimeChangeListener(listener : OnRuntimeChangeListener) : RuntimeBuilder{
        this.onRuntimeChangeListener = listener
        return this
    }

    fun install(){
        //注册activity生命周期回调
        registerActivityLifecycleCallback()
    }



    /**
     * 注册activity生命周期回调
     *
     */
    private fun registerActivityLifecycleCallback() {
        if(contextWeak.get() is Application){
            (contextWeak.get() as Application).registerActivityLifecycleCallbacks(RuntimeLifecycleCallback(this))
        }

    }

    private fun String.isUrlCorrect() : Boolean{
        if(!this.startsWith("http://") && !this.startsWith("https://")){
            return false
        }
        return true
    }

    private fun String.urlEndWith(suffix: String) : Boolean{
        if(this.isEmpty()){
            return false
        }
        if(this.length == 1){
            return this.endsWith(suffix)
        }
        if(this.endsWith(suffix) && this[this.length - 2].equals(suffix)){
            return false
        }
        return this.endsWith(suffix)
    }
}