package com.kakacat.minitool.cleanfile.model

import java.io.File
import java.util.function.Consumer

class ScanTask(private val targetScanFileList: MutableList<File>, private val fileListList: MutableList<MutableList<FileItem>>){

    fun start() {
        targetScanFileList.forEach(Consumer { file: File -> scan(file) })
    }

    private fun scan(file: File) {
        if (file.isFile) {
            if (file.length() == 0L) {
                fileListList[1].add(FileItem(file, false))
            } else {
                if (file.length() > 30 * 1024 * 1024) {
                    fileListList[0].add(FileItem(file, false))
                }
                val fileName = file.name
                when {
                    fileName.endsWith(".apk") -> {
                        fileListList[2].add(FileItem(file, false))
                    }
                    isVideo(fileName) -> {
                        fileListList[3].add(FileItem(file, false))
                    }
                    isAudio(fileName) -> {
                        fileListList[4].add(FileItem(file, false))
                    }
                }
            }
        } else if (file.isDirectory) {
            val files = file.listFiles()
            if (files == null || files.isEmpty()) {
                fileListList[1].add(FileItem(file, false))
            } else {
                for (file1 in files) {
                    scan(file1)
                }
            }
        }
    }

    private fun isVideo(fileName: String): Boolean {
        for (suffix in videoSuffixes) {
            if (fileName.endsWith(suffix)) {
                return true
            }
        }
        return false
    }

    private fun isAudio(fileName: String): Boolean {
        for (suffix in audioSuffixes) {
            if (fileName.endsWith(suffix)) {
                return true
            }
        }
        return false
    }

    companion object {
        private val videoSuffixes = arrayOf(".mp4", ".mkv", ".flv")
        private val audioSuffixes = arrayOf(".mp3", ".flac", ".aac")
    }
}