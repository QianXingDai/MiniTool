package com.kakacat.minitool.common.base;

import android.content.Context;

public interface IView<T extends IPresenter> {
    void initData();

    void initView();

    T getPresenter();

    Context getContext();
}
