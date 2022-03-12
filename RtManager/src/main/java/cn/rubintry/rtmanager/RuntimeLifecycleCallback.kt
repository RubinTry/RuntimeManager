package cn.rubintry.rtmanager

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.ViewGroup

class RuntimeLifecycleCallback(private val runtimeBuilder: RuntimeBuilder) : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        addMainIcon(activity)
    }



    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {
        RtViewManager.getInstance().resumeMainIcon(activity)
    }



    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {
        RtViewManager.getInstance().stopMainIcon(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }


    @SuppressLint("ClickableViewAccessibility")
    private fun addMainIcon(activity: Activity) {
        RtViewManager.getInstance().attachToActivity(activity , MainIconView(activity).apply {
            this.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT , ViewGroup.LayoutParams.WRAP_CONTENT)
        } , runtimeBuilder)
    }

}