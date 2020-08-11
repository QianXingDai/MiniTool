package com.kakacat.minitool.cleanfile

import com.kakacat.minitool.cleanfile.model.FileItem
import com.kakacat.minitool.common.base.IPresenter
import com.kakacat.minitool.common.base.IView

interface Contract {
    interface Presenter : IPresenter {
        fun deleteSelectedFile()
        fun selectAll(currentPagePosition: Int, isSelectedAll: Boolean)
        val fileListList: List<List<FileItem>>
    }

    interface View : IView<Presenter?> {
        fun requestPermission()
        fun onUpdateDataCallBack()
        fun onSelectedAllCallBack()
        fun onFileDeletedCallBack(result: String)
        fun selectAll()
        fun showDialogWindow()
    }
}