package com.kakacat.minitool.cleanfile;

import com.kakacat.minitool.cleanfile.model.FileItem;
import com.kakacat.minitool.common.base.IPresenter;
import com.kakacat.minitool.common.base.IView;

import java.util.List;

public interface Contract {

    interface Presenter extends IPresenter {
        void deleteSelectedFile();

        void selectAll(int currentPagePosition, boolean isSelectedAll);

        List<List<FileItem>> getFileListList();
    }

    interface View extends IView<Presenter> {
        void requestPermission();

        void onUpdateDataCallBack();

        void onSelectedAllCallBack();

        void onFileDeletedCallBack(String result);

        void selectAll();

        void showDialogWindow();
    }
}
