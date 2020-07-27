package com.kakacat.minitool.common.base;

import android.content.Context;

public interface IView<T extends IPresenter> {
    void initData();
    void initView();
    void setPresenter(T presenter);
    Context getContext();
}
