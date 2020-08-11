package com.kakacat.minitool.currencyconversion

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.IntRange
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import com.kakacat.minitool.R
import com.kakacat.minitool.common.ui.RecycleViewListener.OnItemClick
import com.kakacat.minitool.common.ui.view.MyPopupWindow
import com.kakacat.minitool.currencyconversion.model.CountryBean

class CountryFragment(private val mainView: Contract.View, private val countryBeanList: List<CountryBean>, @IntRange(from = 1,to = 2) private val flag: Int) : Fragment() {

    lateinit var et: EditText
    lateinit var countryBean: CountryBean

    private lateinit var tvCountryName: TextView
    private lateinit var tvMoneyUnit: TextView
    private lateinit var sdv: SimpleDraweeView
    private var popupWindow: MyPopupWindow? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.country_view_layout, container, false)

        fun initView(){
            if (flag == 1) {
                countryBean = countryBeanList[0]
            } else if (flag == 2) {
                countryBean = countryBeanList[1]
            }
            tvCountryName = view.findViewById(R.id.tv_country_name)
            tvMoneyUnit = view.findViewById(R.id.tv_money_unit)
            et = view.findViewById(R.id.et)
            sdv = view.findViewById(R.id.sdv)
        }

        fun setView(){
            tvCountryName.setText(countryBean.nameId)
            tvMoneyUnit.setText(countryBean.unitId)
            et.addTextChangedListener(textWatcher)
            sdv.setActualImageResource(countryBean.iconId)
            sdv.setOnClickListener { showDialog(view) }
        }

        initView()
        setView()

        return view
    }

    private fun showDialog(view: View) {
        fun initView(){
            val contentView = View.inflate(context, R.layout.dialog_select_country, null)
            popupWindow = MyPopupWindow(context!!, contentView, ViewGroup.LayoutParams.WRAP_CONTENT, 1000)
            val countryAdapter = CountryAdapter(countryBeanList)
            val rv: RecyclerView = contentView.findViewById(R.id.rv_country)
            rv.adapter = countryAdapter
            rv.layoutManager = GridLayoutManager(context, 3)
            countryAdapter.setOnClickListener(object : OnItemClick{
                override fun onClick(v: View?, position: Int) {
                    countryBean = countryBeanList[position]
                    sdv.setActualImageResource(countryBean.iconId)
                    tvCountryName.setText(countryBean.nameId)
                    tvMoneyUnit.setText(countryBean.unitId)
                    popupWindow!!.dismiss()
                }
            })
        }

        if(popupWindow == null){
            initView()
        }
        popupWindow!!.showAtLocation(view, Gravity.CENTER, 0, 100)
    }

    private val textWatcher: TextWatcher
        get() = object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (et.isFocused) {
                    mainView.onTextChanged(charSequence, flag)
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        }

}