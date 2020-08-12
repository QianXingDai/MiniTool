package com.kakacat.minitool.cleanfile

import bolts.Continuation
import bolts.Task
import com.kakacat.minitool.cleanfile.model.FileItem
import com.kakacat.minitool.cleanfile.model.Model
import com.kakacat.minitool.common.util.StringUtil
import com.kakacat.minitool.common.util.ThreadUtil.callInBackground
import com.kakacat.minitool.common.util.ThreadUtil.callInUiThread

class Presenter(private val view: Contract.View) : Contract.Presenter {

    private val model: Model = Model()

    override fun initData() {
        Task.callInBackground {
            model.initData()
            val taskList = model.taskList
            Task.whenAll(taskList).continueWith(Continuation<Void?, Any?> {
                view.onUpdateDataCallBack()
                null
            }, Task.UI_THREAD_EXECUTOR)
            null
        }
    }

    override fun selectAll(currentPagePosition: Int, isSelectedAll: Boolean) {
        callInBackground(Runnable {
            model.selectAll(currentPagePosition, isSelectedAll)
            callInUiThread(Runnable { view.onSelectedAllCallBack() })
        })
    }

    override fun deleteSelectedFile() {
        Task.callInBackground { model.deleteSelectedFile() }.continueWith(Continuation<LongArray, Void?> { task: Task<LongArray> ->
            val results = task.result
            val s = "一共清理了" + results[0] + "个文件,释放空间" + StringUtil.byteToMegabyte(results[1])
            view.onFileDeletedCallBack(s)
            null
        }, Task.UI_THREAD_EXECUTOR)
    }

    override val fileListList: List<List<FileItem>>
        get() = model.fileListList
}