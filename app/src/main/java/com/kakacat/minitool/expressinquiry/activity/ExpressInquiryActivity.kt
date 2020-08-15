package com.kakacat.minitool.expressinquiry.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kakacat.minitool.R
import com.kakacat.minitool.common.base.BaseActivity
import com.kakacat.minitool.common.ui.DepthPageTransformer
import com.kakacat.minitool.common.ui.view.MyPopupWindow
import com.kakacat.minitool.common.util.UiUtil.initToolbar
import com.kakacat.minitool.common.util.UiUtil.setTranslucentStatusBarBlack
import com.kakacat.minitool.common.util.UiUtil.showToast
import com.kakacat.minitool.expressinquiry.Contract
import com.kakacat.minitool.expressinquiry.MyFragment
import com.kakacat.minitool.expressinquiry.Presenter
import com.kakacat.minitool.expressinquiry.adapter.FragmentAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.function.Consumer


@SuppressLint("SetTextI18n")
class ExpressInquiryActivity : BaseActivity(), Contract.View {

    private var presenter : Contract.Presenter? = null
        get() {
            if(field == null){
                field = Presenter(this)
            }
            return field
        }

    private val swipeRefreshLayout: SwipeRefreshLayout by lazy {
        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener { presenter!!.refreshAll() }
        swipeRefreshLayout
    }

    private val viewPager2: ViewPager2 by lazy {
        val viewPager2 = findViewById<ViewPager2>(R.id.fragment_container)
        viewPager2.setPageTransformer(DepthPageTransformer())
        viewPager2.registerOnPageChangeCallback(onPageChangeCallback)
        viewPager2.adapter = FragmentAdapter(this, myFragmentList)
        viewPager2
    }

    private val btmNav: BottomNavigationView by lazy {
        val btmNav = findViewById<BottomNavigationView>(R.id.btm_nav)
        btmNav.setOnNavigationItemSelectedListener(bottomOnNavigationItemSelectedListener)
        btmNav.selectedItemId = R.id.unsigned
        btmNav
    }

    private val ppwQuery by lazy {
        val view = LayoutInflater.from(this).inflate(R.layout.input_code, swipeRefreshLayout, false)
        val ppwQuery = MyPopupWindow(this, view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val editText = view.findViewById<EditText>(R.id.et_code)
        val button = view.findViewById<Button>(R.id.btn)
        editText.setOnLongClickListener {
            val data = presenter!!.dataFromClipBoard
            editText.setText(editText.text.toString() + data)
            true
        }
        button.setOnClickListener {
            val code = editText.text.toString()
            presenter!!.requestData(code)
            editText.setText("")
        }
        ppwQuery
    }

    private val myFragmentList: MutableList<MyFragment> by lazy {
        val myFragmentList : MutableList<MyFragment> = ArrayList()
        myFragmentList.add(MyFragment(presenter!!.getDeliveryList(0)!!))
        myFragmentList.add(MyFragment(presenter!!.getDeliveryList(1)!!))
        myFragmentList.add(MyFragment(presenter!!.getDeliveryList(2)!!))
        myFragmentList
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_express_inquiry)
        initData()
        initView()
    }

    override fun onDestroy() {
        presenter!!.saveData()
        presenter = null
        super.onDestroy()
    }

    override fun initData() {
        presenter = Presenter(this)
        presenter!!.initData()
    }

    override fun initView() {
        setTranslucentStatusBarBlack(this)
        initToolbar(this, true)
    }

    override fun onRequestCallback(result: String?, needRefresh: Boolean) {
        GlobalScope.launch(Dispatchers.Main) {
            showToast(context, result)
            if (needRefresh) {
                myFragmentList.forEach(Consumer { myFragment: MyFragment -> myFragment.adapter.notifyDataSetChanged() })
            }
            if (swipeRefreshLayout.isRefreshing) {
                swipeRefreshLayout.isRefreshing = false
            }
            if (ppwQuery.isShowing) {
                ppwQuery.dismiss()
            }
        }
    }

    override fun showWindows(fab: View?) {
        ppwQuery.showAtLocation(swipeRefreshLayout, Gravity.CENTER, 0, 0)
    }

    private val onPageChangeCallback: OnPageChangeCallback
        get() = object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when(position){
                    0 -> btmNav.selectedItemId = R.id.unsigned
                    1 -> btmNav.selectedItemId = R.id.signed
                    2 -> btmNav.selectedItemId = R.id.all
                }
            }
        }

    private val bottomOnNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener
        get() = BottomNavigationView.OnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.unsigned -> {
                    viewPager2.setCurrentItem(0, true)
                }
                R.id.signed -> {
                    viewPager2.setCurrentItem(1, true)
                }
                R.id.all -> {
                    viewPager2.setCurrentItem(2, true)
                }
            }
            true
        }
}