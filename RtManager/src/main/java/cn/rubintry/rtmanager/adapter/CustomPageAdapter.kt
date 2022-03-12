package cn.rubintry.rtmanager.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class CustomPageAdapter(private val fragmentList : List<Fragment> , fm: FragmentManager, behavior: Int) : FragmentPagerAdapter(fm, behavior) {
    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }
}