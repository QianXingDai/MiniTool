package com.kakacat.minitool.textencryption

import android.text.TextUtils

class Presenter(private val view: Contract.View) : Contract.Presenter {

    private val model by lazy { Model() }

    override fun initData() {}

    override val encryptionMethods: Array<String>
        get() = Model.encryptionMethods

    override fun encryptText(content: String?, method: CharSequence?) {
        if (TextUtils.isEmpty(method)) {
            view.onEncryptResult(null)
        } else {
            view.onEncryptResult(model.encryptText(content!!, method!!))
        }
    }
}