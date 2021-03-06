package com.kakacat.minitool.modifydpi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.kakacat.minitool.R;
import com.kakacat.minitool.common.ui.view.MyPopupWindow;
import com.kakacat.minitool.common.util.SystemUtil;

public class ModifyDpiView extends MyPopupWindow {

    @SuppressLint("StaticFieldLeak")
    private static ModifyDpiView modifyDpiView;
    private View parentView;
    private View contentView;

    private ModifyDpiView(Activity activity, View contentView, int width, int height) {
        super(activity, contentView, width, height);
        this.contentView = contentView;
    }

    public static ModifyDpiView getInstance(Activity activity, View parentView, View contentView, int width, int height) {
        if (modifyDpiView == null) {
            modifyDpiView = new ModifyDpiView(activity, contentView, width, height);
            modifyDpiView.parentView = parentView;
            modifyDpiView.initView();
        }

        return modifyDpiView;
    }

    private void initView() {
        Button btClear = contentView.findViewById(R.id.iv_clear);
        Button btModifyDpi = contentView.findViewById(R.id.bt_modify_dpi);
        EditText etDpi = contentView.findViewById(R.id.edit_text);
        btClear.setOnClickListener(v -> dismiss());
        btModifyDpi.setOnClickListener(v -> {
            String val = etDpi.getText().toString();
            if (TextUtils.isEmpty(val))
                Snackbar.make(parentView, "输入错误", Snackbar.LENGTH_SHORT).show();
            else
                SystemUtil.modifyDpi(val);
        });
    }

}
