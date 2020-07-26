package com.kakacat.minitool.cleanfile;

import android.os.Message;

import com.kakacat.minitool.cleanfile.model.FileItem;

import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;

public class ScannerThread implements Callable<Void> {

    private int threadNum;
    private static int doneCount;
    private File[] files;
    private int startIndex;
    private int endIndex;
    private CleanFileActivity activity;
    private List<FileItem> bigFileList;
    private List<FileItem> emptyFileList;
    private List<FileItem> emptyDirList;
    private List<FileItem> apkList;

    public ScannerThread(CleanFileActivity activity,File[] files, int threadNum,int startIndex, int endIndex, List<FileItem> bigFileList, List<FileItem> emptyFileList, List<FileItem> emptyDirList, List<FileItem> apkList) {
        this.activity = activity;
        this.files = files;
        this.threadNum = threadNum;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.bigFileList = bigFileList;
        this.emptyFileList = emptyFileList;
        this.emptyDirList = emptyDirList;
        this.apkList = apkList;
    }

    private synchronized void done(){
        doneCount++;
    }


    private void getFileList(File file){
        if(file.isFile()){
            if(file.length() == 0)
                emptyFileList.add(new FileItem(file,false));
            else{
                if(file.getName().endsWith("apk")) apkList.add(new FileItem(file,false));
                if(file.length() > 30 * 1024 * 1024) bigFileList.add(new FileItem(file,false));
            }
        }else if(file.isDirectory()){
            File[] files = file.listFiles();
            if(files == null || files.length == 0)
                emptyDirList.add(new FileItem(file,false));
            else
                for(File file1 : files)
                    getFileList(file1);
        }
    }

    @Override
    public Void call() {
        for(int i = startIndex; i >= endIndex; i--){
            getFileList(files[i]);
        }
        done();
        if(doneCount == threadNum){
            Message msg = Message.obtain();
            activity.handle.sendMessage(msg);
            doneCount = 0;
        }
        return null;
    }
}
