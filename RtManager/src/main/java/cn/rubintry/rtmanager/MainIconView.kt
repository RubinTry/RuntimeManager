package cn.rubintry.rtmanager


import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.view.MotionEvent
import com.blankj.utilcode.util.ScreenUtils


class MainIconView : FrameLayout , IRuntimeView {

    private var mRootView : View ?= null

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
        mRootView = LayoutInflater.from(context).inflate(R.layout.icon_main , null)
        this.addView(mRootView)
        this.id = R.id.runtime_contentview_id
    }





}