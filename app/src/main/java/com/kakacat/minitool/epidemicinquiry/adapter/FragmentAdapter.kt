package com.kakacat.minitool.epidemicinquiry.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kakacat.minitool.epidemicinquiry.fragment.MyFragment

class FragmentAdapter(fragmentActivity: FragmentActivity, private val myFragmentList: MutableList<MyFragment>) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return myFragmentList[position]
    }

    override fun getItemCount(): Int {
        return myFragmentList.size
    }
}