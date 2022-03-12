package cn.rubintry.rtmanager

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import cn.rubintry.rtmanager.adapter.CustomPageAdapter
import cn.rubintry.rtmanager.db.RuntimeDatabase
import cn.rubintry.rtmanager.fragments.RuntimeAddFragment
import cn.rubintry.rtmanager.fragments.RuntimeListFragment
import com.blankj.utilcode.util.BusUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.ToastUtils
import java.lang.ref.WeakReference

internal class CustomRuntimeDialog() : DialogFragment() , IRuntimeView{



    private lateinit var vpCustom: ViewPager

    private var mRootView : View ?= null

    var onConfirmClickListener : ((CustomRuntimeInfo) -> Unit) ?= null

    private val fragments = mutableListOf<Fragment>()

    private var customPageAdapter : CustomPageAdapter ?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView = inflater.inflate(R.layout.dialog_url_runtime_custom , container , false)
        BusUtils.register(this)
        initView(mRootView)
        return mRootView
    }

    override fun onDestroy() {
        super.onDestroy()
        BusUtils.unregister(this)
    }

    @BusUtils.Bus(tag = "RUNTIME_ADD")
    fun runtimeAdd(customRuntimeInfo: CustomRuntimeInfo){
        onConfirmClickListener?.invoke(customRuntimeInfo)
        showToast("已切换到自定义环境")
        this.dismissAllowingStateLoss()
    }

    private fun initView(mRootView: View?) {

        vpCustom = mRootView?.findViewById(R.id.vp_custom)!!



        val runtimeListFragment = RuntimeListFragment.newInstance()
        fragments.add(runtimeListFragment)
        val runtimeAddFragment = RuntimeAddFragment.newInstance()
        fragments.add(runtimeAddFragment)

        vpCustom.offscreenPageLimit = fragments.size
        customPageAdapter = CustomPageAdapter(fragments , childFragmentManager , FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        vpCustom.adapter = customPageAdapter
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
        params?.height = WindowManager.LayoutParams.WRAP_CONTENT
        window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        window?.attributes = params
    }




}