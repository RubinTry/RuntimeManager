package cn.rubintry.rtmanager.fragments

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import cn.rubintry.rtmanager.CustomRuntimeInfo
import cn.rubintry.rtmanager.R
import cn.rubintry.rtmanager.RtViewManager
import cn.rubintry.rtmanager.showToast
import com.blankj.utilcode.util.BusUtils

class RuntimeAddFragment : BaseFragment(), View.OnClickListener {

    private lateinit var edtRuntimePrincipal: EditText
    private lateinit var edtHostName: EditText
    private lateinit var tvConfirmRuntime: TextView

    companion object{
        @JvmStatic
        fun newInstance(): RuntimeAddFragment {
            val fragment = RuntimeAddFragment()
            val argument = Bundle()
            fragment.arguments = argument
            return fragment
        }
    }

    override fun setLayout(): Int {
        return R.layout.fragment_runtime_add
    }

    override fun initViews() {
        edtRuntimePrincipal = mRootView?.findViewById(R.id.edt_runtime_principal)!!
        edtHostName = mRootView?.findViewById(R.id.edt_host_name)!!
        tvConfirmRuntime = mRootView?.findViewById(R.id.tv_confirm_runtime)!!
        tvConfirmRuntime.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_confirm_runtime -> {
                val principal = edtRuntimePrincipal.text.toString()
                val url = edtHostName.text.toString()

                //确定
                if(principal.isEmpty()){
                    showToast("请输入接口负责人")
                    return
                }
                if(url.isEmpty()){
                    showToast("请输入接口主机地址或域名")
                    return
                }

                if(!url.startsWith("http://") && !url.startsWith("https://")){
                    showToast("请输入接口主机地址或域名有误，请重新输入")
                    return
                }

                var customRuntimeInfo : CustomRuntimeInfo?= null
                val db = RtViewManager.getInstance().db
                if(db.customDao().getAllByPrincipal(principal).isNotEmpty()){
                    customRuntimeInfo = db.customDao().getAllByPrincipal(principal).first()
                }else{
                    customRuntimeInfo = CustomRuntimeInfo(host = url , principal = principal)
                    db.customDao().insert(customRuntimeInfo)
                }
                BusUtils.post("RUNTIME_ADD" , customRuntimeInfo)
            }
        }
    }
}