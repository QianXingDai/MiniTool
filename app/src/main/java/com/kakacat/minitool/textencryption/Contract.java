package com.kakacat.minitool.textencryption;

import com.kakacat.minitool.common.base.IPresenter;
import com.kakacat.minitool.common.base.IView;

public interface Contract {

    interface Presenter extends IPresenter{
        String[] getEncryptionMethods();
        void encryptText(String content,CharSequence method);
    }

    interface View extends IView<Presenter>{
        void onEncryptResult(String decode);
    }
}
