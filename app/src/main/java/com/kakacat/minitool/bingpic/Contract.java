package com.kakacat.minitool.bingpic;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kakacat.minitool.common.base.IPresenter;
import com.kakacat.minitool.common.base.IView;

import java.util.List;

public interface Contract {

    interface View extends IView<Presenter> {
        void showBigImage(android.view.View view);

        void showOptionDialog(android.view.View view);

        void saveImage();

        void onUpdateImagesCallBack();

        void onSaveImageCallBack(String result);
    }

    interface Presenter extends IPresenter {
        void initData();

        void loadMore();

        void saveImage(SimpleDraweeView imageView);

        List<String> getAddressList();
    }
}
