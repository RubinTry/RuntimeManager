package cn.rubintry.rtmanager

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.SpanUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

internal class UrlRuntimeDialog : BottomSheetDialogFragment() , IRuntimeView, View.OnClickListener {

    var runtimeBuilder: RuntimeBuilder ?= null
    var productUrl: String = ""
    var testUrl: String = ""
    private var mRootView : View ?= null

    private var tvRuntimeProduct: TextView ?= null
    private var tvRuntimeTest: TextView ?= null
    private var tvRuntimeCustom: TextView ?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView = inflater.inflate(R.layout.dialog_url_runtime , container , false)
        mRootView?.let {
            initViews(it)
        }
        return mRootView
    }

    private fun initViews(mRootView: View) {
        tvRuntimeProduct = mRootView.findViewById(R.id.tv_runtime_product)
        tvRuntimeTest = mRootView.findViewById(R.id.tv_runtime_test)
        tvRuntimeCustom = mRootView.findViewById(R.id.tv_runtime_custom)

        tvRuntimeTest?.setOnClickListener(this)
        tvRuntimeProduct?.setOnClickListener(this)
        tvRuntimeCustom?.setOnClickListener(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SpanUtils.with(tvRuntimeProduct)
            .append("我要切换到生产环境\n")
            .setFontSize(14 , true)
            .setForegroundColor(Color.parseColor("#222222"))
            .append(productUrl)
            .setFontSize(14 , true)
            .setForegroundColor(Color.parseColor("#999999"))
            .create()

        SpanUtils.with(tvRuntimeTest)
            .append("我要切换到测试环境\n")
            .setFontSize(14 , true)
            .setForegroundColor(Color.parseColor("#222222"))
            .append(testUrl)
            .setFontSize(14 , true)
            .setForegroundColor(Color.parseColor("#999999"))
            .create()

        val db = RtViewManager.getInstance().db
        if(db.customDao().getAll().isNotEmpty()){
            val customInfo = db.customDao().getAll().first()
            SpanUtils.with(tvRuntimeCustom)
                .append("自定义环境\n")
                .setFontSize(14 , true)
                .setForegroundColor(Color.parseColor("#222222"))
                .append(customInfo.host)
                .setFontSize(14 , true)
                .setForegroundColor(Color.parseColor("#999999"))
                .create()
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_runtime_custom -> {
                context?.let {
                    CustomRuntimeDialog().apply {
                        this.onConfirmClickListener = { info ->
                            this@UrlRuntimeDialog.runtimeBuilder?.onRuntimeChangeListener?.onChange(info.host , RuntimeType.CUSTOM)
                            this@UrlRuntimeDialog.dismissAllowingStateLoss()
                        }
                    }.show((context as AppCompatActivity).supportFragmentManager , "UrlRuntime")
                }

            }

            R.id.tv_runtime_product -> {
                this.runtimeBuilder?.onRuntimeChangeListener?.onChange(productUrl , RuntimeType.PRODUCT)
                this@UrlRuntimeDialog.dismissAllowingStateLoss()
            }

            R.id.tv_runtime_test -> {
                this.runtimeBuilder?.onRuntimeChangeListener?.onChange(testUrl , RuntimeType.TEST)
                this@UrlRuntimeDialog.dismissAllowingStateLoss()
            }
        }
    }
}