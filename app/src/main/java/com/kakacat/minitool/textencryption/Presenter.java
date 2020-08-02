package com.kakacat.minitool.textencryption;

import android.text.TextUtils;

public class Presenter implements Contract.Presenter {

    private Contract.View view;
    private Model model;

    public Presenter(Contract.View view) {
        this.view = view;
        this.model = Model.getInstance();
    }

    @Override
    public void initData() {
    }

    @Override
    public String[] getEncryptionMethods() {
        return model.getEncryptionMethods();
    }

    @Override
    public void encryptText(String content, CharSequence method) {
        if (TextUtils.isEmpty(method)) {
            view.onEncryptResult(null);
        } else {
            view.onEncryptResult(model.encryptText(content, method));
        }
    }
}
