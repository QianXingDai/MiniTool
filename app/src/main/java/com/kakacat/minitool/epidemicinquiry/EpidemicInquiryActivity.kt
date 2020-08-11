package com.kakacat.minitool.epidemicinquiry

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import com.kakacat.minitool.R
import com.kakacat.minitool.common.base.BaseActivity
import com.kakacat.minitool.common.util.UiUtil.initToolbar
import com.kakacat.minitool.common.util.UiUtil.setTranslucentStatusBarBlack
import com.kakacat.minitool.common.util.UiUtil.showToast
import com.kakacat.minitool.epidemicinquiry.adapter.FragmentAdapter
import com.kakacat.minitool.epidemicinquiry.fragment.MyFragment

class EpidemicInquiryActivity : BaseActivity(), Contract.View {

    private var presenter: Contract.Presenter? = null
    private lateinit var myFragmentList: MutableList<MyFragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_epidemic_inquiry)

        initData()
        initView()
    }

    override fun onDestroy() {
        presenter = null
        super.onDestroy()
    }

    override fun initData() {
        presenter = Presenter(this)
        presenter!!.initData()
    }

    override fun initView() {
        fun initTabs(tabLayout : TabLayout){
            tabNames.forEach {
                run {
                    val tab = tabLayout.newTab()
                    tab.text = it
                    tabLayout.addTab(tab)
                }
            }
        }

        fun initFragments(){
            myFragmentList = ArrayList()
            myFragmentList.add(MyFragment(presenter!!))
            myFragmentList.add(MyFragment(presenter!!))
            myFragmentList.add(MyFragment(presenter!!))
        }

        setTranslucentStatusBarBlack(this)
        initToolbar(this, false)

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val viewPager2 = findViewById<ViewPager2>(R.id.fragment_container)
        initTabs(tabLayout)
        initFragments()
        viewPager2.adapter = FragmentAdapter(this, myFragmentList)
        TabLayoutMediator(tabLayout, viewPager2, true, TabConfigurationStrategy { tab: TabLayout.Tab, position: Int -> tab.text = tabNames[position] }).attach()
    }

    override fun onUpdateViewSuccessful() {
        myFragmentList[0].updateView()
    }

    override fun onUpdateViewError(error: String) {
        showToast(this, error)
    }

    companion object{
        private val tabNames = arrayOf("国内疫情","国外疫情","实况播报")
    }
}