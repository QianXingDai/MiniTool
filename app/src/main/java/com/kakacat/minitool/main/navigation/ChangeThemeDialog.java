package com.kakacat.minitool.main.navigation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.RadioGroup;

import com.kakacat.minitool.R;
import com.kakacat.minitool.common.ui.view.MyPopupWindow;

public class ChangeThemeDialog extends MyPopupWindow {

    @SuppressLint("StaticFieldLeak")
    private static ChangeThemeDialog themeView;
    private View contentView;

    private ChangeThemeDialog(Context context, View contentView, int width, int height) {
        super(context, contentView, width, height);
        this.contentView = contentView;
    }


    public static ChangeThemeDialog getInstance(Context context, View contentView, int width, int height) {
        if (themeView == null) {
            themeView = new ChangeThemeDialog(context, contentView, width, height);
            themeView.initView();
        }

        return themeView;
    }


    private void initView() {
        RadioGroup radioGroup = contentView.findViewById(R.id.radio_group_theme);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.theme_blue: {
                    //TODO:1
                    break;
                }
                case R.id.theme_purple: {
                    //TODO:2
                    break;
                }
                case R.id.theme_pink: {
                    //TODO:3
                    break;
                }
                default:
                    break;
            }
        });
    }

}
