package com.kakacat.minitool.cleanfile.model

import android.os.Environment.getExternalStorageDirectory
import java.io.File
import java.util.concurrent.CopyOnWriteArrayList
import java.util.function.Consumer

class Model {

    val fileListList: MutableList<MutableList<FileItem>> by lazy { ArrayList() }
    val taskList: List<ScanTask> by lazy {
        val taskList: MutableList<ScanTask> = ArrayList()
        val files = getExternalStorageDirectory().listFiles()!!
        var startIndex = files.size - 1
        var endIndex: Int
        for (i in 0 until THREAD_NUM) {
            endIndex = if (i != THREAD_NUM - 1) startIndex - files.size / THREAD_NUM else 0
            val fileList: MutableList<File> = ArrayList(startIndex - endIndex + 1)
            for (index in startIndex downTo endIndex) {
                fileList.add(files[index])
            }
            taskList.add(ScanTask(fileList, fileListList))
            startIndex = endIndex
        }
        taskList
    }

    fun initData() {
        for (i in 0 until 5) {
            fileListList.add(CopyOnWriteArrayList())
        }
    }

    fun deleteSelectedFile(): LongArray {
        val results = longArrayOf(0, 0)

        fileListList.forEach { list ->
            for (index in list.size - 1 downTo 0){
                val fileItem = list[index]
                if(fileItem.checked && fileItem.file != null && fileItem.file!!.exists()){
                    val fileSize = fileItem.file!!.length()
                    if (fileItem.file!!.delete()) {
                        results[0]++
                        results[1] += fileSize
                    }
                    list.removeAt(index)
                }
            }
        }
        return results
    }

    fun selectAll(currentPagePosition: Int, isSelectedAll: Boolean) {
        fileListList[currentPagePosition].forEach(Consumer { item: FileItem -> item.checked = isSelectedAll })
    }

    companion object {
        private const val THREAD_NUM = 2
    }
}