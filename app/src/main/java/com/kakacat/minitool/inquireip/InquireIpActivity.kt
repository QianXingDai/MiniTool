package com.kakacat.minitool.inquireip

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import com.kakacat.minitool.R
import com.kakacat.minitool.common.base.BaseActivity
import com.kakacat.minitool.common.util.UiUtil.initToolbar
import com.kakacat.minitool.common.util.UiUtil.showToast

class InquireIpActivity : BaseActivity(), Contract.View {

    private var presenter: Contract.Presenter? = null

    private val tvCountry by lazy { findViewById<TextView>(R.id.tv_country) }
    private val tvProvince by lazy { findViewById<TextView>(R.id.tv_province) }
    private val tvCity by lazy { findViewById<TextView>(R.id.tv_city) }
    private val tvIsp by lazy { findViewById<TextView>(R.id.tv_isp) }
    private val tvIp by lazy { findViewById<TextView>(R.id.tv_ip) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inquire_ip)
        initData()
        initView()
    }

    override fun onDestroy() {
        presenter = null
        super.onDestroy()
    }

    override fun initData() {
        presenter = Presenter(this)
    }

    override fun initView() {
        initToolbar(this, false)
        val til = findViewById<TextInputLayout>(R.id.text_input_layout)
        val editText = findViewById<EditText>(R.id.et_input_ip)
        til.setStartIconOnClickListener { presenter!!.requestIpData(editText.text.toString()) }
    }

    override fun onUpdateDataCallBack(ipBean: IpBean?, result: String?) {
        showToast(this, result)
        if (ipBean != null) {
            tvCountry.text = ipBean.country
            tvProvince.text = ipBean.province
            tvCity.text = ipBean.city
            tvIsp.text = ipBean.isp
            tvIp.text = ipBean.ipAddress
        }
    }
}