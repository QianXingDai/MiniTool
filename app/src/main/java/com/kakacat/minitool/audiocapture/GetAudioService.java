package com.kakacat.minitool.audiocapture;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.kakacat.minitool.common.util.UiUtil;
import com.kakacat.minitool.common.util.SystemUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GetAudioService extends IntentService {

    public GetAudioService() {
        super("GetAudioService2");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Uri uri = intent.getParcelableExtra("uri");
        Context context = getBaseContext();
        String[] projections = {MediaStore.Video.Media.DATA};  //  列名
        assert uri != null;
        Cursor cursor = context.getContentResolver().query(uri,projections, null, null, null);
        assert cursor != null;
        cursor.moveToFirst();
        String filePath = cursor.getString(0);
        cursor.close();
        String result = separateAudioFromVideo(filePath);
        UiUtil.showToast(this,result);
        SystemUtil.vibrate(context,200);
    }

    private void addADTStoPacket(byte[] packet, int packetLen) {
        /*
        标识使用AAC级别 当前选择的是LC
        一共有1: AAC Main 2:AAC LC (Low Complexity) 3:AAC SSR (Scalable Sample Rate) 4:AAC LTP (Long Term Prediction)
        */
        int profile = 2;
        int frequencyIndex = 0x04; //设置采样率
        int channelConfiguration = 2; //设置频道,其实就是声道

        // fill in ADTS data
        packet[0] = (byte) 0xFF;
        packet[1] = (byte) 0xF9;
        packet[2] = (byte) (((profile - 1) << 6) + (frequencyIndex << 2) + (channelConfiguration >> 2));
        packet[3] = (byte) (((channelConfiguration & 3) << 6) + (packetLen >> 11));
        packet[4] = (byte) ((packetLen & 0x7FF) >> 3);
        packet[5] = (byte) (((packetLen & 7) << 5) + 0x1F);
        packet[6] = (byte) 0xFC;
    }

    private String separateAudioFromVideo(String filePath){
        try{
            MediaExtractor mediaExtractor = new MediaExtractor();
            mediaExtractor.setDataSource(filePath);      //设置视频路径
            int audioIndex = 0;
            int trackCount = mediaExtractor.getTrackCount();
            MediaFormat audioFormat = null;
            for(int i = 0; i < trackCount; i++){    //得到音轨
                MediaFormat mediaFormat = mediaExtractor.getTrackFormat(i);
                String mime = mediaFormat.getString(MediaFormat.KEY_MIME);
                if(mime.startsWith("audio")){
                    audioIndex = i;
                    audioFormat = mediaFormat;
                    break;
                }
            }

            File audioFile = new File(Environment.getExternalStorageDirectory() + "/MiniTool/" + filePath.substring(filePath.lastIndexOf('/') + 1,filePath.length() - 1) + "3");
            if(audioFile.exists()){
                audioFile.delete();
            }else{
                File parentFile = audioFile.getParentFile();
                parentFile.mkdirs();
                audioFile.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(audioFile);
            int maxAudioBufferCount = audioFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);
            ByteBuffer audioByteBuffer = ByteBuffer.allocate(maxAudioBufferCount);
            mediaExtractor.selectTrack(audioIndex);
            int len;

            while((len = mediaExtractor.readSampleData(audioByteBuffer,0)) != -1){
                byte[] bytes = new byte[len];
                audioByteBuffer.get(bytes);
                byte[] adtsData = new byte[len + 7];
                addADTStoPacket(adtsData, len + 7);
                System.arraycopy(bytes,0,adtsData,7,len);
                fos.write(adtsData);
                audioByteBuffer.clear();
                mediaExtractor.advance();
            }
            fos.flush();
            fos.close();
            mediaExtractor.release();
            return "提取完成,保存在目录" + audioFile.getAbsolutePath();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "提取失败...";
    }

}
