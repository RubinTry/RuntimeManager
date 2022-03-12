package cn.rubintry.rtmanager.fragments

import android.os.Bundle
import cn.rubintry.rtmanager.R


class RuntimeListFragment : BaseFragment() {


    companion object{
        @JvmStatic
        fun newInstance(): RuntimeListFragment {
            val fragment = RuntimeListFragment()
            val argument = Bundle()
            fragment.arguments = argument
            return fragment
        }
    }

    override fun setLayout(): Int {
        return R.layout.fragment_runtime_list
    }

    override fun initViews() {

    }

}