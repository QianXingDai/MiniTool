package com.kakacat.minitool.cleanfile;

import android.os.Environment;

import com.kakacat.minitool.cleanfile.model.FileItem;
import com.kakacat.minitool.cleanfile.model.ScanTask;
import com.kakacat.minitool.common.util.StringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;

import bolts.Continuation;
import bolts.Task;

public class Presenter implements Contract.Presenter {

    private static final int THREAD_NUM = 3;

    private Contract.View view;
    private List<List<FileItem>> fileListList;

    public Presenter(Contract.View view) {
        this.view = view;
    }

    @Override
    public void initData() {
        Task.callInBackground((Callable<Void>) () -> {
            fileListList = new ArrayList<>();
            for(int i = 0; i < 5; i++){
                fileListList.add(new CopyOnWriteArrayList<>());
            }

            List<Task<Void>> taskList = getTaskList();
            Task.whenAll(taskList).continueWith(task -> {
                view.onUpdateDataCallBack();
                return null;
            },Task.UI_THREAD_EXECUTOR);
            return null;
        });

    }

    private List<Task<Void>> getTaskList(){
        List<Task<Void>> taskList = new ArrayList<>();

        File[] files = Environment.getExternalStorageDirectory().listFiles();
        assert files != null;
        int startIndex = files.length - 1;
        int endIndex;
        for(int i = 0; i < THREAD_NUM; i++){
            endIndex = i != THREAD_NUM - 1 ? startIndex - files.length / THREAD_NUM : 0;
            List<File> fileList = new ArrayList<>(startIndex - endIndex + 1);
            for(int index = startIndex; index >= endIndex; index--){
                fileList.add(files[index]);
            }
            taskList.add(Task.call(new ScanTask(fileList,getFileListList())));
            startIndex = endIndex;
        }
        return taskList;
    }

    @Override
    public void selectAll(int currentPagePosition,boolean isSelectedAll){
        getFileListList().get(currentPagePosition).forEach(item->item.setChecked(isSelectedAll));
        view.onSelectedAllCallBack();
    }

    @Override
    public void deleteSelectedFile() {
        Task.callInBackground((Callable<long[]>) () -> {
            final long[] results = {0,0};
            getFileListList().forEach(list->{
                for(int i = list.size() - 1; i >= 0; i--){
                    FileItem fileItem = list.get(i);
                    if (fileItem.getChecked()) {
                        File file = fileItem.getFile();
                        long fileSize = file.length();
                        if(fileItem.getFile().delete()){
                            results[0]++;
                            results[0] += fileSize;
                        }
                        list.remove(i);
                    }
                }
            });
            return results;
        }).continueWith((Continuation<long[], Void>) task -> {
            long[] results = task.getResult();
            String s = "一共清理了" + results[0] + "个文件,释放空间" + StringUtil.byteToMegabyte(results[1]);
            view.onFileDeletedCallBack(s);
            return null;
        },Task.UI_THREAD_EXECUTOR);
    }

    @Override
    public List<List<FileItem>> getFileListList() {
        return fileListList;
    }
}
