package cn.rubintry.rtmanager.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import cn.rubintry.rtmanager.fragments.BaseFragment


class CustomPageAdapter(private val fragments: MutableList<BaseFragment> , private val fm : FragmentManager, private val lifecycle: Lifecycle) : FragmentStateAdapter(fm , lifecycle){

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}