package com.kakacat.minitool.textencryption

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.kakacat.minitool.R
import com.kakacat.minitool.common.base.BaseActivity
import com.kakacat.minitool.common.util.SystemUtil.copyToClipboard
import com.kakacat.minitool.common.util.UiUtil
import com.kakacat.minitool.common.util.UiUtil.closeKeyboard
import com.kakacat.minitool.common.util.UiUtil.showToast

class TextEncryptionActivity : BaseActivity(), Contract.View {

    private var presenter: Contract.Presenter? = null

    private val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }
    private val tvOutput by lazy { findViewById<TextView>(R.id.tv_output) }
    private val editText by lazy { findViewById<EditText>(R.id.edit_text) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_encryption)
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
        UiUtil.initToolbar(this)
        val chipGroup = findViewById<ChipGroup>(R.id.chip_group)
        for (s in presenter!!.encryptionMethods) {
            val chip = Chip(context)
            chip.isCheckable = true
            chip.isChipIconVisible = false
            chip.isCloseIconVisible = false
            chip.setCheckedIconResource(R.drawable.ic_mtrl_chip_checked_black)
            chip.setOnCheckedChangeListener { _: CompoundButton?, b: Boolean ->
                if (b) {
                    toolbar.subtitle = s
                    toolbar.setSubtitleTextColor(getColor(android.R.color.white))
                }
            }
            chip.text = s
            chipGroup.addView(chip)
        }
        (chipGroup.getChildAt(0) as Chip).isChecked = true
        editText.onFocusChangeListener = OnFocusChangeListener { v: View?, hasFocus: Boolean ->
            if (!hasFocus) {
                closeKeyboard(context, v!!)
            }
        }
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.bt_code -> {
                presenter!!.encryptText(editText!!.text.toString(), toolbar!!.subtitle)
            }
            R.id.bt_decode -> {
            }
            R.id.bt_delete_input -> {
                editText!!.setText("")
            }
            R.id.bt_copy -> {
                copyToClipboard(context, "codeContent", tvOutput!!.text)
                showToast(context, "复制成功")
            }
        }
    }

    override fun onEncryptResult(decode: String?) {
        if (!TextUtils.isEmpty(decode)) {
            tvOutput!!.text = decode
            showToast(context, "加密成功")
        } else {
            showToast(context, "加密失败")
        }
    }
}