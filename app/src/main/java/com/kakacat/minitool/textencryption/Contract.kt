package com.kakacat.minitool.textencryption

import com.kakacat.minitool.common.base.IPresenter
import com.kakacat.minitool.common.base.IView

interface Contract {
    interface Presenter : IPresenter {
        val encryptionMethods: Array<String>
        fun encryptText(content: String?, method: CharSequence?)
    }

    interface View : IView<Presenter?> {
        fun onEncryptResult(decode: String?)
    }
}