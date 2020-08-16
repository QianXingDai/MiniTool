package com.kakacat.minitool.cleanfile

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.kakacat.minitool.R
import com.kakacat.minitool.cleanfile.adapter.FragmentAdapter
import com.kakacat.minitool.cleanfile.model.FileItem
import com.kakacat.minitool.common.base.BaseActivity
import com.kakacat.minitool.common.ui.view.MyPopupWindow
import com.kakacat.minitool.common.util.SystemUtil
import com.kakacat.minitool.common.util.UiUtil.initToolbar
import com.kakacat.minitool.common.util.UiUtil.setTranslucentStatusBarWhite
import com.kakacat.minitool.common.util.UiUtil.showSnackBar
import com.kakacat.minitool.common.util.UiUtil.showToast
import kotlinx.android.synthetic.main.activity_clean_file.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.function.Consumer

class CleanFileActivity : BaseActivity(), Contract.View {

    private var presenter: Contract.Presenter? = null

    private val viewPager2 by lazy { findViewById<ViewPager2>(R.id.fragment_container) }
    private val btmNav by lazy { findViewById<BottomNavigationView>(R.id.btm_nav) }
    private val btSelectAll by lazy { findViewById<MaterialButton>(R.id.bt_select_all) }
    private val popupWindow by lazy {
        val view = LayoutInflater.from(this).inflate(R.layout.dialog, viewPager2, false)
        MyPopupWindow(this, view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private val myFragmentList: MutableList<MyFragment> by lazy { ArrayList<MyFragment>() }

    private var currentPagePosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clean_file)

        presenter = Presenter(this)
        requestPermission()
        initView()
    }

    override fun onDestroy() {
        presenter = null
        super.onDestroy()
    }

    override fun initData() {
        presenter!!.initData()
    }

    override fun requestPermission() {
        val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
        val requestPermissionList: MutableList<String> = ArrayList()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionList.add(permission)
            }
        }
        if (requestPermissionList.size > 0) {
            ActivityCompat.requestPermissions(this, requestPermissionList.toTypedArray(), REQUEST_CODE)
        } else {
            initData()
        }
    }

    override fun initView() {
        setTranslucentStatusBarWhite(this)
        initToolbar(this, true)

        presenter!!.fileListList.forEach(Consumer { list: MutableList<FileItem> -> myFragmentList.add(MyFragment(list)) })
        btmNav.setOnNavigationItemSelectedListener(navigationItemSelectedListener)
        viewPager2.adapter = FragmentAdapter(this, myFragmentList)
        viewPager2.registerOnPageChangeCallback(packChangeCallBack)
    }

    private val packChangeCallBack: OnPageChangeCallback
        get() = object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentPagePosition = position
                when (position) {
                    0 -> {
                        myFragmentList[0].setSelectedAll(myFragmentList[0].isSelectedAll,btSelectAll)
                        btmNav.selectedItemId = R.id.big_file
                    }
                    1 -> {
                        myFragmentList[1].setSelectedAll(myFragmentList[1].isSelectedAll,btSelectAll)
                        btmNav.selectedItemId = R.id.empty_file
                    }
                    2 -> {
                        myFragmentList[2].setSelectedAll(myFragmentList[2].isSelectedAll,btSelectAll)
                        btmNav.selectedItemId = R.id.apk
                    }
                    3 -> {
                        myFragmentList[3].setSelectedAll(myFragmentList[3].isSelectedAll,btSelectAll)
                        btmNav.selectedItemId = R.id.video
                    }
                    4 -> {
                        myFragmentList[4].setSelectedAll(myFragmentList[4].isSelectedAll,btSelectAll)
                        btmNav.selectedItemId = R.id.audio
                    }
                    else -> {
                    }
                }
            }
        }

    private val navigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener
        get() = BottomNavigationView.OnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.big_file -> {
                    viewPager2.setCurrentItem(0, true)
                }
                R.id.empty_file -> {
                    viewPager2.setCurrentItem(1, true)
                }
                R.id.apk -> {
                    viewPager2.setCurrentItem(2, true)
                }
                R.id.video -> {
                    viewPager2.setCurrentItem(3, true)
                }
                R.id.audio -> {
                    viewPager2.setCurrentItem(4, true)
                }
                else -> {
                }
            }
            true
        }

    override fun onUpdateDataCallBack() {
        GlobalScope.launch(Dispatchers.Main) {
            progress_bar.visibility = View.INVISIBLE
            viewPager2.visibility = View.VISIBLE
            myFragmentList.forEach(Consumer { myFragment: MyFragment -> myFragment.adapter.notifyDataSetChanged() })
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            var count = 0
            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    count++
                    break
                }
            }
            if (count == 0) {
                initData()
            } else {
                showToast(this, "获取存储权限失败,请手动打开存储权限哟")
                SystemUtil.openAppDetailInSetting(this)
            }
        }
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.bt_select_all -> selectAll()
            R.id.fab -> showDialogWindow()
            R.id.bt_cancel -> popupWindow.dismiss()
            R.id.bt_delete_file -> {
                popupWindow.dismiss()
                presenter!!.deleteSelectedFile()
            }
        }
    }

    override fun selectAll() {
        val myFragment = myFragmentList[currentPagePosition]
        myFragment.setSelectedAll(!myFragment.isSelectedAll, btSelectAll)
        presenter!!.selectAll(currentPagePosition, myFragment.isSelectedAll)
    }

    override fun onSelectedAllCallBack() {
        GlobalScope.launch(Dispatchers.Main) {
            myFragmentList[currentPagePosition].adapter.notifyDataSetChanged()
        }
    }

    override fun onFileDeletedCallBack(result: String) {
        GlobalScope.launch(Dispatchers.Main) {
            myFragmentList.forEach(Consumer { myFragment: MyFragment -> myFragment.adapter.notifyDataSetChanged() })
            showSnackBar(coordinator_layout, result, btmNav)
        }
    }

    override fun showDialogWindow() {
        popupWindow.showAtLocation(viewPager2, Gravity.CENTER, 0, 0)
    }

    companion object {
        private const val REQUEST_CODE = 1
    }
}