package com.kakacat.minitool.cleanfile.model;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import bolts.Task;

public class Model {

    private static final int THREAD_NUM = 5;

    private static Model model;

    private List<List<FileItem>> fileListList;

    private Model() {

    }

    public static Model getInstance() {
        if (model == null) {
            return new Model();
        }
        return model;
    }

    public void initData() {
        fileListList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            fileListList.add(new CopyOnWriteArrayList<>());
        }
    }

    public List<Task<Void>> getTaskList() {
        List<Task<Void>> taskList = new ArrayList<>();

        File[] files = Environment.getExternalStorageDirectory().listFiles();
        assert files != null;
        int startIndex = files.length - 1;
        int endIndex;
        for (int i = 0; i < THREAD_NUM; i++) {
            endIndex = i != THREAD_NUM - 1 ? startIndex - files.length / THREAD_NUM : 0;
            List<File> fileList = new ArrayList<>(startIndex - endIndex + 1);
            for (int index = startIndex; index >= endIndex; index--) {
                fileList.add(files[index]);
            }
            taskList.add(Task.call(new ScanTask(fileList, getFileListList())));
            startIndex = endIndex;
        }
        return taskList;
    }

    public long[] deleteSelectedFile() {
        final long[] results = {0, 0};
        getFileListList().forEach(list -> {
            for (int i = list.size() - 1; i >= 0; i--) {
                FileItem fileItem = list.get(i);
                if (fileItem.getChecked()) {
                    File file = fileItem.getFile();
                    long fileSize = file.length();
                    if (fileItem.getFile().delete()) {
                        results[0]++;
                        results[1] += fileSize;
                    }
                    list.remove(i);
                }
            }
        });
        return results;
    }

    public void selectAll(int currentPagePosition, boolean isSelectedAll) {
        getFileListList().get(currentPagePosition).forEach(item -> item.setChecked(isSelectedAll));
    }

    public List<List<FileItem>> getFileListList() {
        if (fileListList == null) {
            fileListList = new ArrayList<>();
        }
        return fileListList;
    }
}
