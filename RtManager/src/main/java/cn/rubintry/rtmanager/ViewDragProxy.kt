package cn.rubintry.rtmanager

import android.util.ArrayMap
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.customview.widget.ViewDragHelper
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ScreenUtils


internal class ViewDragProxy private constructor(){

    private val helperMap = ArrayMap<View , ViewDragHelper>()

    private var downTime = 0L

    companion object{
        @Volatile
        private var instance : ViewDragProxy ?= null
        @JvmStatic
        fun getInstance(): ViewDragProxy {
            if(null == instance){
                synchronized(ViewDragProxy::class.java){
                    if(null == instance){
                        instance = ViewDragProxy()
                    }
                }
            }
            return instance!!
        }
    }

    fun proxyDrag(view: ViewGroup, ev: MotionEvent, runtimeBuilder: RuntimeBuilder): Boolean {
        var helper = helperMap[view]
        val mainIconView = view.findViewById<MainIconView>(R.id.runtime_contentview_id)
        if(null == helper){
            helper = obtainHelper(view , mainIconView)
            helperMap[view] = helper
        }
        if(mainIconView.visibleRect.contains(ev.x.toInt() , ev.y.toInt())){
            when(ev.action){
                MotionEvent.ACTION_DOWN -> {
                    downTime = System.currentTimeMillis()
                }

                MotionEvent.ACTION_UP -> {
                    //按下松开的时间间隔小于200毫秒时，视为点击，其他情况依旧拖拽
                    if(System.currentTimeMillis() - downTime < 200) {
                        dealClick(mainIconView , runtimeBuilder)
                        return false
                    }
                }
            }
        }
        helper.processTouchEvent(ev)
        return true
    }


    private fun obtainHelper(view: ViewGroup , childView: View): ViewDragHelper {

        val viewDragHelper = ViewDragHelper.create(view , object : ViewDragHelper.Callback(){
            override fun tryCaptureView(child: View, pointerId: Int): Boolean {
                return child == childView
            }

            override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
                val screenWidth = ScreenUtils.getScreenWidth()
                var newLeft = left
                if(newLeft > screenWidth - childView.width){
                    newLeft = screenWidth - childView.width
                }
                if(newLeft < 0){
                    newLeft = 0
                }
                return newLeft
            }

            override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
                val screenHeight = ScreenUtils.getScreenHeight()
                val statusBarHeight = BarUtils.getStatusBarHeight()
                var newTop = top
                if(newTop > screenHeight - childView.height){
                    newTop = screenHeight - childView.height
                }
                if(newTop < statusBarHeight){
                    newTop = statusBarHeight
                }
                return newTop
            }
        })

        return viewDragHelper
    }


    /**
     * 处理点击事件
     *
     * @param view
     */
    private fun dealClick(view: View, runtimeBuilder: RuntimeBuilder) {
        val context = view.context
        if(context is AppCompatActivity){
            UrlRuntimeDialog().apply {
                this.productUrl = runtimeBuilder.productUrl
                this.testUrl = runtimeBuilder.testUrl
                this.runtimeBuilder = runtimeBuilder
            }.show(context.supportFragmentManager , "UrlRuntimeReplace")
        }
    }

}

