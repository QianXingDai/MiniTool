package com.kakacat.minitool.bingpic

import com.facebook.drawee.view.SimpleDraweeView
import com.kakacat.minitool.common.base.IPresenter
import com.kakacat.minitool.common.base.IView

interface Contract {
    interface View : IView<Presenter?> {
        fun showBigImage(view: android.view.View?)
        fun showOptionDialog(view: android.view.View?)
        fun saveImage()
        fun onUpdateImagesCallBack()
        fun onSaveImageCallBack(result: String?)
    }

    interface Presenter : IPresenter {
        fun loadMore()
        fun saveImage(imageView: SimpleDraweeView?)
        val addressList: List<String?>?
    }
}