package com.kakacat.minitool.cleanfile;

import com.kakacat.minitool.cleanfile.model.FileItem;
import com.kakacat.minitool.cleanfile.model.Model;
import com.kakacat.minitool.common.util.StringUtil;
import com.kakacat.minitool.common.util.ThreadUtil;

import java.util.List;
import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;

public class Presenter implements Contract.Presenter {

    private Contract.View view;
    private Model model;

    public Presenter(Contract.View view) {
        this.view = view;
        this.model = Model.getInstance();
    }

    @Override
    public void initData() {
        Task.callInBackground((Callable<Void>) () -> {
            model.initData();
            List<Task<Void>> taskList = model.getTaskList();
            Task.whenAll(taskList).continueWith(task -> {
                view.onUpdateDataCallBack();
                return null;
            }, Task.UI_THREAD_EXECUTOR);
            return null;
        });
    }

    @Override
    public void selectAll(int currentPagePosition, boolean isSelectedAll) {
        ThreadUtil.callInBackground(() -> {
            model.selectAll(currentPagePosition, isSelectedAll);
            ThreadUtil.callInUiThread(() -> view.onSelectedAllCallBack());
        });
    }

    @Override
    public void deleteSelectedFile() {
        Task.callInBackground(() -> model.deleteSelectedFile()).continueWith((Continuation<long[], Void>) task -> {
            long[] results = task.getResult();
            String s = "一共清理了" + results[0] + "个文件,释放空间" + StringUtil.byteToMegabyte(results[1]);
            view.onFileDeletedCallBack(s);
            return null;
        }, Task.UI_THREAD_EXECUTOR);
    }

    @Override
    public List<List<FileItem>> getFileListList() {
        return model.getFileListList();
    }
}
