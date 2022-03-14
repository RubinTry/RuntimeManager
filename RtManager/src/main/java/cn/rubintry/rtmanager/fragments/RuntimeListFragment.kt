package cn.rubintry.rtmanager.fragments

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.rubintry.rtmanager.BundleKey
import cn.rubintry.rtmanager.db.CustomRuntimeInfo
import cn.rubintry.rtmanager.R
import cn.rubintry.rtmanager.core.RtViewManager
import cn.rubintry.rtmanager.adapter.RuntimeListAdapter
import cn.rubintry.rtmanager.callback.RuntimeAddCallback


class RuntimeListFragment : BaseFragment() {

    private var callback: RuntimeAddCallback?= null
    private var cacheRuntimeList: List<CustomRuntimeInfo> = listOf()
    private val runtimeListAdapter = RuntimeListAdapter(cacheRuntimeList) { info ->
        callback?.onAdd(info)
    }

    private var rvRuntimeList: RecyclerView ?= null

    companion object{
        @JvmStatic
        fun newInstance(callback: RuntimeAddCallback): RuntimeListFragment {
            val fragment = RuntimeListFragment()
            val argument = Bundle()
            argument.putSerializable(BundleKey.RUNTIME_ADD_CALLBACK , callback)
            fragment.arguments = argument
            return fragment
        }
    }

    override fun setLayout(): Int {
        return R.layout.fragment_runtime_list
    }

    override fun initViews() {
        rvRuntimeList = mRootView?.findViewById(R.id.rv_runtime_list)
        rvRuntimeList?.layoutManager = LinearLayoutManager(context)
        rvRuntimeList?.isNestedScrollingEnabled = false
        rvRuntimeList?.adapter = runtimeListAdapter
        callback = arguments?.getSerializable(BundleKey.RUNTIME_ADD_CALLBACK) as? RuntimeAddCallback
    }

    override fun initData() {
        val db = RtViewManager.getInstance().db
        cacheRuntimeList = db.customDao().getAll()
        if(cacheRuntimeList.isNotEmpty()){
            runtimeListAdapter.dataList = cacheRuntimeList
        }
    }


}