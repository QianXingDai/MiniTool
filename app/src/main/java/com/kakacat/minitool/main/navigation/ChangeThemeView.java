package com.kakacat.minitool.main.navigation;

import android.content.Context;
import android.view.View;
import android.widget.RadioGroup;

import com.kakacat.minitool.R;
import com.kakacat.minitool.common.ui.MyPopupWindow;

public class ChangeThemeView extends MyPopupWindow {

    private static ChangeThemeView themeView;
    private View contentView;

    private ChangeThemeView(Context context,View contentView, int width, int height) {
        super(context, contentView,width, height);
        this.contentView = contentView;
    }


    public static ChangeThemeView getInstance(Context context, View contentView, int width, int height){
        if(themeView == null){
            themeView = new ChangeThemeView(context, contentView,width, height);
            themeView.initView();
        }

        return themeView;
    }


    private void initView(){
        RadioGroup radioGroup = contentView.findViewById(R.id.radio_group_theme);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId){
                case R.id.theme_blue:{
                    //TODO
                    break;
                }
                case R.id.theme_purple:{
                    //TODO
                    break;
                }
                case R.id.theme_pink:{
                    //TODO
                    break;
                }
                default:
                    break;
            }
        });
    }

}
