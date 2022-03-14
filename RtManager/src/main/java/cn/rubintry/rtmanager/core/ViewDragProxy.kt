package cn.rubintry.rtmanager.core

import android.util.ArrayMap
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.OverScroller
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper
import cn.rubintry.rtmanager.*
import cn.rubintry.rtmanager.db.IconPos
import cn.rubintry.rtmanager.getDeclaredField
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ScreenUtils


/**
 * 触摸事件代理
 *
 */
internal class ViewDragProxy private constructor(){

    private var lastX: Float = 0f
    private var lastY: Float = 0f
    private val helperMap = ArrayMap<View , ViewDragHelper>()

    private var downTime = 0L

    companion object{
        @Volatile
        private var instance : ViewDragProxy?= null
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


    /**
     * 代理掉悬浮窗的触摸事件
     *
     * @param view
     * @param ev
     * @param runtimeBuilder
     * @return
     */
    fun proxyDrag(view: ViewGroup, ev: MotionEvent, runtimeBuilder: RuntimeBuilder): Boolean {
        var helper = helperMap[view]
        val mainIconView = view.findViewById<MainIconView>(R.id.runtime_contentview_id)

        if(null == helper){
            helper = obtainHelper(view , mainIconView)
            helperMap[view] = helper
        }
        mainIconView.setViewDragHelper(helper)
        if(mainIconView.visibleRect.contains(ev.x.toInt() , ev.y.toInt())){
            when(ev.action){
                MotionEvent.ACTION_DOWN -> {
                    lastX = ev.x
                    lastY = ev.y
                    downTime = System.currentTimeMillis()
                }

                MotionEvent.ACTION_UP -> {
                    //按下松开的时间间隔小于200毫秒时，视为点击，其他情况依旧拖拽
                    if(System.currentTimeMillis() - downTime < 200 && ev.y == lastY && ev.x == lastX) {
                        dealClick(mainIconView , runtimeBuilder)
                        return false
                    }
                }
            }
        }
        helper.processTouchEvent(ev)
        return true
    }


    /**
     * 恢复到上一次的位置
     *
     * @param view
     * @param left
     * @param top
     */
    fun smoothSlideViewTo(view: ViewGroup , left: Int , top: Int){
        var helper = helperMap[view]
        val mainIconView = view.findViewById<MainIconView>(R.id.runtime_contentview_id)
        if(null == helper){
            helper = obtainHelper(view , mainIconView)
            helperMap[view] = helper
        }
        forceSettleCapturedViewAt(helper , mainIconView , left , top)
    }

    /**
     * 滚动到上次的位置
     *
     * @param helper
     * @param mCapturedView
     * @param finalLeft
     * @param finalTop
     */
    private fun forceSettleCapturedViewAt(helper : ViewDragHelper , mCapturedView: View , finalLeft: Int, finalTop: Int) {
        val startLeft: Int = mCapturedView.left
        val startTop: Int = mCapturedView.top
        val dx = finalLeft - startLeft
        val dy = finalTop - startTop
        try {
            val mScroller = helper.getDeclaredField("mScroller") as OverScroller
            val methodComputeDuration = helper.getDeclaredMethod("computeSettleDuration" , View::class.java , Int::class.java , Int::class.java , Int::class.java , Int::class.java)
            val duration: Int = methodComputeDuration?.invoke(helper , mCapturedView , dx , dy , 0 , 0) as Int
            val mScollerMethod = mScroller.getDeclaredMethod("startScroll" , Int::class.java , Int::class.java , Int::class.java , Int::class.java , Int::class.java)
            mScollerMethod?.invoke(mScroller , startLeft , startTop , dx , dy , duration)
            val methodSetDragState = helper.getDeclaredMethod("setDragState" , Int::class.java)
            methodSetDragState?.invoke(helper , ViewDragHelper.STATE_SETTLING)

            //开始计算偏移量
            mCapturedView.postDelayed(Runnable {
                val offsetX: Int = mScroller.currX - mCapturedView.left
                val offsetY: Int = mScroller.currY - mCapturedView.top
                if(offsetX != 0){
                    ViewCompat.offsetLeftAndRight(mCapturedView, offsetX)
                }
                if(offsetY != 0){
                    ViewCompat.offsetTopAndBottom(mCapturedView, offsetY)
                }
            } , 200)
        }catch (e: Exception){
            e.printStackTrace()
        }

    }


    /**
     * 获得一个ViewDragHelper
     *
     * @param view
     * @param childView
     * @return
     */
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

            override fun onViewPositionChanged(
                changedView: View,
                left: Int,
                top: Int,
                dx: Int,
                dy: Int
            ) {
                super.onViewPositionChanged(changedView, left, top, dx, dy)
                val curActivity = ActivityUtils.getTopActivity()
                val db = RtViewManager.getInstance().db
                val lastPositions = db.iconPositionDao().getAll()
                if(lastPositions.isNotEmpty()){
                    val lastIconPos = lastPositions.first()
                    db.iconPositionDao().update( IconPos(id = lastIconPos.id  , left = left , top = top))
                }else{
                    db.iconPositionDao().insert( IconPos(left = left , top = top))
                }
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

