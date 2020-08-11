package com.kakacat.minitool.audiocapture

import android.app.IntentService
import android.content.Intent
import android.media.MediaExtractor
import android.media.MediaFormat
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import com.kakacat.minitool.BuildConfig
import com.kakacat.minitool.common.util.SystemUtil
import com.kakacat.minitool.common.util.UiUtil.showToast
import java.io.File
import java.io.FileOutputStream
import java.nio.ByteBuffer

/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 *
 *
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
class GetAudioService : IntentService("GetAudioService2") {
    override fun onHandleIntent(intent: Intent?) {
        val uri = intent!!.getParcelableExtra<Uri>("uri")
        val context = baseContext
        val projections = arrayOf(MediaStore.Video.Media.DATA) //  列名
        if (BuildConfig.DEBUG && uri == null) {
            error("Assertion failed")
        }
        val cursor = context.contentResolver.query(uri!!, projections, null, null, null)!!
        cursor.moveToFirst()
        val filePath = cursor.getString(0)
        cursor.close()
        val result = separateAudioFromVideo(filePath)
        showToast(this, result)
        SystemUtil.vibrate(context, 200)
    }

    private fun addADTStoPacket(packet: ByteArray, packetLen: Int) {
        /*
        标识使用AAC级别 当前选择的是LC
        一共有1: AAC Main 2:AAC LC (Low Complexity) 3:AAC SSR (Scalable Sample Rate) 4:AAC LTP (Long Term Prediction)
        */
        val profile = 2
        val frequencyIndex = 0x04 //设置采样率
        val channelConfiguration = 2 //设置频道,其实就是声道

        // fill in ADTS data
        packet[0] = 0xFF.toByte()
        packet[1] = 0xF9.toByte()
        packet[2] = ((profile - 1 shl 6) + (frequencyIndex shl 2) + (channelConfiguration shr 2)).toByte()
        packet[3] = ((channelConfiguration and 3 shl 6) + (packetLen shr 11)).toByte()
        packet[4] = (packetLen and 0x7FF shr 3).toByte()
        packet[5] = ((packetLen and 7 shl 5) + 0x1F).toByte()
        packet[6] = 0xFC.toByte()
    }

    private fun separateAudioFromVideo(filePath: String): String {
        try {
            val mediaExtractor = MediaExtractor()
            mediaExtractor.setDataSource(filePath) //设置视频路径
            var audioIndex = 0
            val trackCount = mediaExtractor.trackCount
            var audioFormat: MediaFormat? = null
            for (i in 0 until trackCount) {    //得到音轨
                val mediaFormat = mediaExtractor.getTrackFormat(i)
                val mime = mediaFormat.getString(MediaFormat.KEY_MIME)
                if (mime.startsWith("audio")) {
                    audioIndex = i
                    audioFormat = mediaFormat
                    break
                }
            }
            val audioFile = File(Environment.getExternalStorageDirectory().toString() + "/MiniTool/" + filePath.substring(filePath.lastIndexOf('/') + 1, filePath.length - 1) + "3")
            SystemUtil.createFile(audioFile)
            val fos = FileOutputStream(audioFile)
            val maxAudioBufferCount = audioFormat!!.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE)
            val audioByteBuffer = ByteBuffer.allocate(maxAudioBufferCount)
            mediaExtractor.selectTrack(audioIndex)
            var len: Int
            while (mediaExtractor.readSampleData(audioByteBuffer, 0).also { len = it } != -1) {
                val bytes = ByteArray(len)
                audioByteBuffer[bytes]
                val adtsData = ByteArray(len + 7)
                addADTStoPacket(adtsData, len + 7)
                System.arraycopy(bytes, 0, adtsData, 7, len)
                fos.write(adtsData)
                audioByteBuffer.clear()
                mediaExtractor.advance()
            }
            fos.flush()
            fos.close()
            mediaExtractor.release()
            return "提取完成,保存在目录" + audioFile.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "提取失败..."
    }
}