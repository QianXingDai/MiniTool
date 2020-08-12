package com.kakacat.minitool.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kakacat.minitool.main.MainContract
import com.kakacat.minitool.main.fragment.DailyFragment
import com.kakacat.minitool.main.fragment.GeekFragment
import com.kakacat.minitool.main.fragment.MyFragment

class FragmentAdapter(fragmentActivity: FragmentActivity, private val presenter: MainContract.Presenter) : FragmentStateAdapter(fragmentActivity) {

    private val myFragments by lazy {
        val myFragment: Array<MyFragment> = arrayOf(DailyFragment(presenter),GeekFragment(presenter))
        myFragment
    }

    override fun createFragment(position: Int): Fragment {
        return myFragments[position]
    }

    override fun getItemCount(): Int {
        return myFragments.size
    }
}