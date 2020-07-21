package com.kakacat.minitool.common;

import android.content.Context;

public interface IView<T extends IPresenter> {
    void setPresenter(T presenter);
    void initView();
    Context getContext();
}
