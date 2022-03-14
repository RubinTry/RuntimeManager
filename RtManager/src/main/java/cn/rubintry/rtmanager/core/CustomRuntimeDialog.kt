package cn.rubintry.rtmanager.core

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import cn.rubintry.rtmanager.db.CustomRuntimeInfo
import cn.rubintry.rtmanager.R
import cn.rubintry.rtmanager.adapter.CustomPageAdapter
import cn.rubintry.rtmanager.callback.RuntimeAddCallback
import cn.rubintry.rtmanager.fragments.BaseFragment
import cn.rubintry.rtmanager.fragments.FragmentType
import cn.rubintry.rtmanager.fragments.RuntimeAddFragment
import cn.rubintry.rtmanager.fragments.RuntimeListFragment
import com.blankj.utilcode.util.ScreenUtils
import android.view.ViewGroup




internal class CustomRuntimeDialog() : DialogFragment() , IRuntimeView, View.OnClickListener,
    RuntimeAddCallback {



    private lateinit var vpCustom: ViewPager2

    private var mRootView : View ?= null

    var onConfirmClickListener : ((CustomRuntimeInfo) -> Unit) ?= null

    private val fragments = mutableListOf<BaseFragment>()

    private var customPageAdapter : CustomPageAdapter ?= null

    private var tvAdd: TextView?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView = inflater.inflate(R.layout.dialog_url_runtime_custom, container , false)
        initView(mRootView)
        return mRootView
    }



    private fun initView(mRootView: View?) {

        vpCustom = mRootView?.findViewById(R.id.vp_custom)!!
        tvAdd = mRootView.findViewById(R.id.tv_add)

        val runtimeListFragment = RuntimeListFragment.newInstance(this)
        runtimeListFragment.type = FragmentType.PREVIEW_LIST
        fragments.add(runtimeListFragment)
        val runtimeAddFragment = RuntimeAddFragment.newInstance(this)
        runtimeAddFragment.type = FragmentType.EDIT
        fragments.add(runtimeAddFragment)

        vpCustom.offscreenPageLimit = fragments.size
        customPageAdapter = CustomPageAdapter(fragments , childFragmentManager , this.lifecycle)
        vpCustom.adapter = customPageAdapter
        //默认不可左右滑动
        vpCustom.isUserInputEnabled = false
        vpCustom.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val view: View? = fragments[position].view
                updatePagerHeightForChild(view, vpCustom)
            }

            // 解决ViewPager2 切换时高度问题
            private fun updatePagerHeightForChild(view: View?, pager: ViewPager2) {
                view?.post {
                    val wMeasureSpec =
                        View.MeasureSpec.makeMeasureSpec(view?.width ?: 0, View.MeasureSpec.EXACTLY)
                    val hMeasureSpec =
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                    view.measure(wMeasureSpec, hMeasureSpec)
                    if (pager.layoutParams.height != view.measuredHeight) {
                        val layoutParams = pager.layoutParams
                        layoutParams.height = view?.measuredHeight ?: 0
                        pager.layoutParams = layoutParams
                    }
                }
            }
        })
        tvAdd?.setOnClickListener(this)
    }


    override fun show(manager: FragmentManager, tag: String?) {
        super.show(manager, tag)
        val window = dialog?.window
        window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
        // 然后弹出输入法
        window?.decorView?.setPadding(0, 0, 0, 0)
        window?.setGravity(Gravity.CENTER)
        //调整窗体大小
        val params = window?.attributes
        val screenWidth = ScreenUtils.getScreenWidth()
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.height = WindowManager.LayoutParams.MATCH_PARENT
        window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        window?.attributes = params
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_add -> {
                vpCustom.currentItem = 1
            }
        }
    }

    override fun onAdd(info: CustomRuntimeInfo) {
        onConfirmClickListener?.invoke(info)
        this.dismissAllowingStateLoss()
    }


}