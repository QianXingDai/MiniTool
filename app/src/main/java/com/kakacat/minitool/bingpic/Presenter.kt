package com.kakacat.minitool.bingpic

import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Presenter(private val view: Contract.View) : Contract.Presenter {

    private val model: Model = Model()

    override fun initData() {
        loadMore()
    }

    override fun loadMore() {
        model.loadMore()
        view.onUpdateImagesCallBack()
    }

    override fun saveImage(imageView: SimpleDraweeView?) {
        GlobalScope.launch {
            val result = model.saveImage(imageView!!)
            GlobalScope.launch(Dispatchers.Main) {
                view.onSaveImageCallBack(result)
            }
        }
    }

    override val addressList: List<String>
        get() = model.getAddressList()
}