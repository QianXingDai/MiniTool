package com.kakacat.minitool.epidemicinquiry

import com.kakacat.minitool.common.base.IPresenter
import com.kakacat.minitool.common.base.IView
import com.kakacat.minitool.epidemicinquiry.model.GroupBean

interface Contract {
    interface Presenter : IPresenter {
        fun requestData()
        val groupList: MutableList<GroupBean>?
    }

    interface View : IView<Presenter> {
        fun onUpdateViewSuccessful()
        fun onUpdateViewError(error: String)
    }
}