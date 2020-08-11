package com.kakacat.minitool.common.util

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.kakacat.minitool.R
import com.kakacat.minitool.common.ui.view.MyPopupWindow
import java.io.ByteArrayOutputStream

object UiUtil {
    private var loadingPopupWindow: MyPopupWindow? = null

    @JvmStatic
    fun showKeyboard(context: Context, v: View?) {
        val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.showSoftInput(v, 0)
    }

    @JvmStatic
    fun closeKeyboard(context: Context, view: View) {
        val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    @JvmStatic
    @JvmOverloads
    fun showSnackBar(view: View?, hint: CharSequence?, btmNav: BottomNavigationView? = null) {
        SystemUtil.log((btmNav == null).toString())
        if (btmNav != null) {
            val lp = btmNav.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = (lp.behavior as HideBottomViewOnScrollBehavior<BottomNavigationView>?)!!
            behavior.slideDown(btmNav)
        }
        Snackbar.make(view!!, hint!!, Snackbar.LENGTH_SHORT).show()
    }

    @JvmStatic
    fun showToast(context: Context?, s: CharSequence?) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
    }

    @JvmStatic
    fun showLoading(context: Context, parent: View?) {
        SystemUtil.log("show loading")
        if (loadingPopupWindow == null) {
            val view = LayoutInflater.from(context).inflate(R.layout.loading_layout, parent as ViewGroup?, false)
            loadingPopupWindow = MyPopupWindow(context, view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
        loadingPopupWindow!!.showAtLocation(parent!!, Gravity.CENTER, 0, 0)
    }

    @JvmStatic
    fun dismissLoadingWindow() {
        if (loadingPopupWindow != null && loadingPopupWindow!!.isShowing) {
            loadingPopupWindow!!.dismiss()
            SystemUtil.log("dismiss")
        }
    }

    @JvmStatic
    fun drawable2Bitmap(drawable: Drawable): Bitmap {
        val width = drawable.intrinsicWidth
        val height = drawable.intrinsicHeight
        val config = if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
        //创建对应的bitmap
        val bitmap = Bitmap.createBitmap(width, height, config)
        //创建对应的bitmap的画布
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, width, height)
        //把drawable内容画到画布中
        drawable.draw(canvas)
        return bitmap
    }

    @JvmStatic
    fun bitmap2Bytes(bm: Bitmap): ByteArray {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos)
        return baos.toByteArray()
    }

    @JvmStatic
    fun slideUpToTop(nestedScrollView: NestedScrollView) {
        nestedScrollView.fullScroll(View.FOCUS_UP)
    }

    @JvmStatic
    fun initToolbar(activity: AppCompatActivity, showTitle: Boolean) {
        initToolbar(activity, showTitle, -1)
    }

    @JvmStatic
    fun initToolbar(activity: AppCompatActivity, showTitle: Boolean, @DrawableRes iconId: Int) {
        activity.setSupportActionBar(activity.findViewById(R.id.toolbar))
        val actionBar = activity.supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            if (iconId != -1) {
                actionBar.setHomeAsUpIndicator(iconId)
            } else {
                actionBar.setHomeAsUpIndicator(R.drawable.ic_action_back)
            }
            actionBar.setDisplayShowTitleEnabled(showTitle)
        }
    }

    //状态栏字体黑色
    @JvmStatic
    fun setTranslucentStatusBarBlack(activity: Activity) {
        val window = activity.window
        if (null != window && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            var vis = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vis = vis or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            }
            window.decorView.systemUiVisibility = vis
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    //状态栏字体白色
    @JvmStatic
    fun setTranslucentStatusBarWhite(activity: Activity) {
        val window = activity.window
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT
    }

    @JvmStatic
    fun setLightNavigationBar(window: Window?, light: Boolean) {
        if (null == window || Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return
        }
        var vis = window.decorView.systemUiVisibility
        vis = if (light) {
            vis or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        } else {
            vis and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
        }
        window.decorView.systemUiVisibility = vis
    }
}