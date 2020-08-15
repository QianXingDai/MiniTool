package com.kakacat.minitool.currencyconversion

import android.os.Bundle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kakacat.minitool.R
import com.kakacat.minitool.common.base.BaseActivity
import com.kakacat.minitool.common.util.UiUtil.initToolbar
import com.kakacat.minitool.common.util.UiUtil.setTranslucentStatusBarWhite
import com.kakacat.minitool.common.util.UiUtil.showSnackBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : BaseActivity(), Contract.View {

    private var presenter: Contract.Presenter? = null

    private val swipeRefreshLayout by lazy { findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout) }
    private val countryFragment1 by lazy { CountryFragment(this, presenter!!.countryList, 1) }
    private val countryFragment2 by lazy { CountryFragment(this, presenter!!.countryList, 2) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_conversion)

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
        setTranslucentStatusBarWhite(this)
        initToolbar(this, true)

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            presenter!!.refreshExchangeRate()
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, countryFragment1)
        transaction.add(R.id.fragment_container, countryFragment2)
        transaction.commit()
    }

    override fun onTextChanged(s: CharSequence, flag: Int) {
        if (flag == 1) {
            val result = presenter!!.getResult(s, countryFragment1.countryBean.rate, countryFragment2.countryBean.rate)
            countryFragment2.et.setText(result)
        } else if (flag == 2) {
            val result = presenter!!.getResult(s, countryFragment2.countryBean.rate, countryFragment1.countryBean.rate)
            countryFragment1.et.setText(result)
        }
    }

    override fun onRefreshExchangeRateAsync(result: String?) {
        GlobalScope.launch(Dispatchers.Main) {
            swipeRefreshLayout.isRefreshing = false
            showSnackBar(swipeRefreshLayout, result)
        }
    }
}