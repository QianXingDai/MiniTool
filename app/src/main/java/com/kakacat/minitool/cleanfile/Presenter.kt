package com.kakacat.minitool.cleanfile

import com.kakacat.minitool.cleanfile.model.FileItem
import com.kakacat.minitool.cleanfile.model.Model
import com.kakacat.minitool.common.util.StringUtil
import com.kakacat.minitool.common.util.SystemUtil
import kotlinx.coroutines.*


class Presenter(private val view: Contract.View) : Contract.Presenter {

    private val model by lazy { Model() }

    override fun initData() {
        GlobalScope.launch {
            model.initData()
            val taskList = model.taskList
            val job1 = async(Dispatchers.IO) { taskList[0].start() }
            val job2 = async(Dispatchers.Default) { taskList[1].start() }
            job1.await()
            job2.await()
            view.onUpdateDataCallBack()
        }
    }

    override fun selectAll(currentPagePosition: Int, isSelectedAll: Boolean) {
        GlobalScope.launch {
            model.selectAll(currentPagePosition, isSelectedAll)
            view.onSelectedAllCallBack()
        }
    }

    override fun deleteSelectedFile() {
        GlobalScope.launch {
            val results = model.deleteSelectedFile()
            val s = "一共清理了" + results[0] + "个文件,释放空间" + StringUtil.byteToMegabyte(results[1])
            view.onFileDeletedCallBack(s)
        }
    }

    override val fileListList: MutableList<MutableList<FileItem>>
        get() = model.fileListList
}