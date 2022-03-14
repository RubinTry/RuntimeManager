package cn.rubintry.rtmanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment(){

    protected var mRootView : View ?= null

    var type = FragmentType.PREVIEW_LIST

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView = inflater.inflate(setLayout() , container , false)
        initViews()
        initData()
        return mRootView
    }

    @LayoutRes
    abstract fun setLayout() : Int

    abstract fun initViews()

    abstract fun initData()
}