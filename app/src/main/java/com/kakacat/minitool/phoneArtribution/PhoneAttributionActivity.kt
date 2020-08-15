package com.kakacat.minitool.phoneartribution

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import com.kakacat.minitool.R
import com.kakacat.minitool.common.base.BaseActivity
import com.kakacat.minitool.common.util.UiUtil.initToolbar
import com.kakacat.minitool.common.util.UiUtil.setTranslucentStatusBarBlack
import com.kakacat.minitool.common.util.UiUtil.showToast
import com.kakacat.minitool.phoneartribution.model.PhoneNumber
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PhoneAttributionActivity : BaseActivity(), Contract.View {

    private var presenter: Contract.Presenter? = null

    private val tvProvince by lazy { findViewById<TextView>(R.id.tv_province) }
    private val tvCity by lazy { findViewById<TextView>(R.id.tv_city) }
    private val tvAreaCode by lazy { findViewById<TextView>(R.id.tv_area_code) }
    private val tvZip by lazy { findViewById<TextView>(R.id.tv_zip) }
    private val tvCompany by lazy { findViewById<TextView>(R.id.tv_company) }
    private val tvNumber by lazy { findViewById<TextView>(R.id.tv_number) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_attribution)
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
        setTranslucentStatusBarBlack(this)
        initToolbar(this, true)
        val til = findViewById<TextInputLayout>(R.id.text_input_layout)
        val et = findViewById<EditText>(R.id.et_input)
        til.setStartIconOnClickListener { presenter!!.requestData(et.text.toString()) }
    }

    override fun onRequestDataCallBack(phoneNumber: PhoneNumber?, result: String?) {
        GlobalScope.launch(Dispatchers.Main) {
            if (phoneNumber != null) {
                tvProvince.text = phoneNumber.province
                tvCity.text = phoneNumber.city
                tvAreaCode.text = phoneNumber.areaCode
                tvZip.text = phoneNumber.zip
                tvCompany.text = phoneNumber.company
                tvNumber.text = phoneNumber.number
            }
            showToast(context, result)
        }
    }
}