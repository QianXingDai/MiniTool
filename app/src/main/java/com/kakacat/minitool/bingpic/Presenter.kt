package com.kakacat.minitool.bingpic

import bolts.Continuation
import bolts.Task
import com.facebook.drawee.view.SimpleDraweeView

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
        Task.callInBackground { model.saveImage(imageView!!) }.onSuccess(Continuation<String, Void?> { task: Task<String> ->
            view.onSaveImageCallBack(task.result)
            null
        }, Task.UI_THREAD_EXECUTOR)
    }

    override val addressList: List<String>
        get() = model.getAddressList()
}