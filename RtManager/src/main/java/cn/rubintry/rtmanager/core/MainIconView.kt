package cn.rubintry.rtmanager.core


import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.customview.widget.ViewDragHelper
import cn.rubintry.rtmanager.R
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.SizeUtils


class MainIconView : FrameLayout , IRuntimeView {

    private var mRootView : View ?= null
    private var imgMainIcon : ImageView ?= null
    private var mViewDragHelper : ViewDragHelper ?= null

    constructor(context: Context) : super(context){
        initView()
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
        initView()
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        initView()
    }

    private fun initView(){
        mRootView = LayoutInflater.from(context).inflate(R.layout.icon_main, null)
        this.addView(mRootView)
        this.id = R.id.runtime_contentview_id
        imgMainIcon = mRootView?.findViewById(R.id.img_main_icon)
        val bgDrawable = ContextCompat.getDrawable(context , R.mipmap.icon_tools)
        var bitmap = ImageUtils.toRound(bgDrawable?.toBitmap(bgDrawable.intrinsicWidth, bgDrawable.intrinsicHeight , Bitmap.Config.ARGB_8888))
        bitmap = ImageUtils.addCircleBorder(bitmap , SizeUtils.dp2px(7f).toFloat() , Color.parseColor("#C1C1C1"))
        imgMainIcon?.setImageBitmap(bitmap)
    }

    fun setViewDragHelper(helper: ViewDragHelper) {
        this.mViewDragHelper = helper
    }


    override fun computeScroll() {
        super.computeScroll()
        mViewDragHelper?.let {
            if(null != it.capturedView && it.continueSettling(true)){
                invalidate()
            }
        }
    }
}