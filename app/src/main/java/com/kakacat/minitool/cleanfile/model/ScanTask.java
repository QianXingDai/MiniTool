package com.kakacat.minitool.cleanfile.model;

import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;

public class ScanTask implements Callable<Void> {

    private List<File> targetScanFileList;
    private List<List<FileItem>> fileListList;

    public ScanTask(List<File> targetScanFileList, List<List<FileItem>> fileListList) {
        this.targetScanFileList = targetScanFileList;
        this.fileListList = fileListList;
    }

    @Override
    public Void call() {
        targetScanFileList.forEach(this::start);
        return null;
    }

    private void start(File file) {
        if (file.isFile()) {
            if (file.length() == 0) {
                fileListList.get(1).add(new FileItem(file, false));
            } else {
                String fileName = file.getName();
                if (file.length() > 30 * 1024 * 1024) {
                    fileListList.get(0).add(new FileItem(file, false));
                }
                if (fileName.endsWith(".apk")) {
                    fileListList.get(2).add(new FileItem(file, false));
                } else if (isVideo(fileName)) {
                    fileListList.get(3).add(new FileItem(file, false));
                } else if (isAudio(fileName)) {
                    fileListList.get(4).add(new FileItem(file, false));
                }
            }
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null || files.length == 0) {
                fileListList.get(1).add(new FileItem(file, false));
            } else {
                for (File file1 : files) {
                    start(file1);
                }
            }
        }
    }

    private boolean isVideo(String fileName) {
        String[] videoSuffixes = {".mp4", ".mkv", ".flv"};

        for (String suffix : videoSuffixes) {
            if (fileName.endsWith(suffix)) {
                return true;
            }
        }

        return false;
    }

    private boolean isAudio(String fileName) {
        String[] audioSuffixes = {".mp3", ".flac", ".aac"};

        for (String suffix : audioSuffixes) {
            if (fileName.endsWith(suffix)) {
                return true;
            }
        }

        return false;
    }


}
