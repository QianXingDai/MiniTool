package com.kakacat.minitool.common.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.snackbar.Snackbar;
import com.kakacat.minitool.R;
import com.kakacat.minitool.common.ui.view.MyPopupWindow;

import org.jetbrains.annotations.NotNull;

public class UiUtil {

    private static MyPopupWindow loadingPopupWindow;

    public static void showKeyboard(@NotNull Context context, View v) {
        InputMethodManager manager = ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE));
        if (manager != null) {
            manager.showSoftInput(v, 0);
        }
    }

    public static void closeKeyboard(@NotNull Context context, View view) {
        InputMethodManager manager = ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE));
        if (manager != null){
            manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void showSnackBar(View view, CharSequence hint) {
        Snackbar.make(view, hint, Snackbar.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, CharSequence s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    public static void showLoading(Context context,View parent){
        if(loadingPopupWindow == null){
            View view = LayoutInflater.from(context).inflate(R.layout.loading_layout,null,false);
            loadingPopupWindow = new MyPopupWindow(context,view, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        }
        loadingPopupWindow.showAtLocation(parent, Gravity.CENTER,0,0);
    }

    public static void dismissLoadingWindow(){
        if(loadingPopupWindow != null && loadingPopupWindow.isShowing()){
            loadingPopupWindow.dismiss();
        }
    }

    public static Bitmap drawableToBitmap(@NotNull Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE
                ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        //创建对应的bitmap
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        //创建对应的bitmap的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        //把drawable内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    public static void slideUpToTop(@NotNull NestedScrollView nestedScrollView) {
        nestedScrollView.fullScroll(View.FOCUS_UP);
    }

    public static void initToolbar(AppCompatActivity activity, boolean showTitle) {
        initToolbar(activity,showTitle,-1);
    }

    public static void initToolbar(@NotNull AppCompatActivity activity, boolean showTitle, @DrawableRes int iconId) {
        activity.setSupportActionBar(activity.findViewById(R.id.toolbar));
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            if(iconId != -1){
                actionBar.setHomeAsUpIndicator(iconId);
            }else{
                actionBar.setHomeAsUpIndicator(R.drawable.ic_action_back);
            }
            actionBar.setDisplayShowTitleEnabled(showTitle);
        }
    }


    //状态栏字体黑色
    public static void setTranslucentStatusBarBlack(@NotNull Activity activity) {
        Window window = activity.getWindow();
        if (null != window && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            int vis = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vis = vis | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            }
            window.getDecorView().setSystemUiVisibility(vis);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    //状态栏字体白色
    public static void setTranslucentStatusBarWhite(@NotNull Activity activity){
        Window window = activity.getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    public static void setLightNavigationBar(Window window, boolean light) {
        if (null == window || Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }
        int vis = window.getDecorView().getSystemUiVisibility();
        if (light) {
            vis |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
        } else {
            vis &= ~View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
        }
        window.getDecorView().setSystemUiVisibility(vis);
    }

}
