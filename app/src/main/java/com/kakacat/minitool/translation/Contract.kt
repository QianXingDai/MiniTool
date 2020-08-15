package com.kakacat.minitool.translation

import com.kakacat.minitool.common.base.IPresenter
import com.kakacat.minitool.common.base.IView

interface Contract {
    interface View : IView<Presenter?> {
        fun onAddToMyFavouriteCallBack(s: String?)
        fun showCollectionWindow()
        fun onRequestCallBack(result: String?, warn: String?)
        fun showSelectLanguageWindow(flag: Int)
    }

    interface Presenter : IPresenter {
        val collectionList: MutableList<String>
        val languageList1: List<String>
        val languageList2: List<String>
        fun requestData(input: String?, from: CharSequence, to: CharSequence)
        fun addToMyFavourite(source: String?, target: String?)
    }
}