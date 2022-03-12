package cn.rubintry.rtmanager

import android.view.Gravity
import com.blankj.utilcode.util.ToastUtils


internal fun showToast(message: String){
    ToastUtils.getDefaultMaker().setGravity(Gravity.CENTER , 0 , 0).show(message)
}