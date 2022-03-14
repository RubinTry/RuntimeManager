package cn.rubintry.rtmanager.core

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.core.view.ViewCompat
import androidx.room.Room
import cn.rubintry.rtmanager.R
import cn.rubintry.rtmanager.db.RuntimeDatabase
import com.blankj.utilcode.util.SizeUtils

class RtViewManager private constructor(){

    val db : RuntimeDatabase by lazy {
        Room.databaseBuilder(
            RuntimeEnv.requireApp(),
            RuntimeDatabase::class.java,
            "runtime-database")
                //允许在主线程上查询
            .allowMainThreadQueries()
            .build()
    }

    companion object{
        @Volatile
        private var instance : RtViewManager?= null

        @JvmStatic
        fun getInstance(): RtViewManager {
            if(null == instance){
                synchronized(RtViewManager::class.java){
                    if(null == instance){
                        instance = RtViewManager()
                    }
                }
            }

            return instance!!
        }
    }

    fun attachToActivity(activity: Activity, view: View, runtimeBuilder: RuntimeBuilder){
        if(activity is UniversalActivity){
            return
        }
        getDecorView(activity).also {
            it.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener{
                override fun onGlobalLayout() {
                    it.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    dealTouch(view , runtimeBuilder)
                }
            })
            it.addView(view)
        }
    }



    fun resumeMainIcon(activity: Activity) {
        if(activity is UniversalActivity){
            return
        }
        val mainIconView = activity.findViewById<MainIconView>(R.id.runtime_contentview_id)
        val iconPosList = db.iconPositionDao().getAll()
        if(iconPosList.isNotEmpty()){
            val iconPos = iconPosList.first()
            val parent = mainIconView.parent
            if(null != parent && parent is ViewGroup){
                mainIconView.left = iconPos.left - 1
                mainIconView.top = iconPos.top - 1
                ViewDragProxy.getInstance().smoothSlideViewTo(parent , iconPos.left , iconPos.top)
            }
        }

    }

    private fun getDecorView(activity: Activity) : ViewGroup{
        return activity.window.decorView as ViewGroup
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun dealTouch(view: View, runtimeBuilder: RuntimeBuilder) {
        if(view is ViewGroup && view is IRuntimeView){
            ViewCompat.offsetTopAndBottom(view, SizeUtils.dp2px(50f))
            val parent = view.parent
            if(null != parent && parent is ViewGroup){
                parent.setOnTouchListener { v, event ->  ViewDragProxy.getInstance().proxyDrag(parent , event , runtimeBuilder)}
            }
        }
    }
}