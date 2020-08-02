package com.kakacat.minitool.bingpic;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

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
        loadMore();
    }

    @Override
    public void loadMore() {
        model.loadMore();
        view.onUpdateImagesCallBack();
    }

    @Override
    public void saveImage(SimpleDraweeView imageView) {
        Task.callInBackground(() -> model.saveImage(imageView)).onSuccess((Continuation<String, Void>) task -> {
            view.onSaveImageCallBack(task.getResult());
            return null;
        }, Task.UI_THREAD_EXECUTOR);
    }

    @Override
    public List<String> getAddressList() {
        return model.getAddressList();
    }
}
