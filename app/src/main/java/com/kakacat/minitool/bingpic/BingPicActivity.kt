package com.kakacat.minitool.bingpic

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
import android.view.*
import android.widget.ImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import com.kakacat.minitool.R
import com.kakacat.minitool.common.base.BaseActivity
import com.kakacat.minitool.common.ui.RecycleViewListener
import com.kakacat.minitool.common.ui.RecycleViewScrollListener
import com.kakacat.minitool.common.ui.RecycleViewScrollListener.OnSwipeUpRefresh
import com.kakacat.minitool.common.ui.view.MyPopupWindow
import com.kakacat.minitool.common.util.SystemUtil
import com.kakacat.minitool.common.util.UiUtil
import com.kakacat.minitool.common.util.UiUtil.showToast
import kotlinx.android.synthetic.main.activity_app_info.*


class BingPicActivity : BaseActivity() , Contract.View{

    private var presenter: Contract.Presenter? = Presenter(this)

    private var coordinatorLayout: CoordinatorLayout? = null
    private var adapter: ImageAdapter? = null
    private var bigImageDialog: MyPopupWindow? = null
    private var optionDialog: MyPopupWindow? = null
    private var bigImageView: ImageView? = null
    private var currentImageView: SimpleDraweeView? = null

    private var currentX: Int = 0
    private var currentY: Int = 0

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_bing_pic)

        initData()
        initView()
    }

    override fun onDestroy() {
        presenter = null
        super.onDestroy()
    }

    override fun showOptionDialog(view: View?) {
        currentImageView = view!!.findViewById(R.id.image_view)
        if(optionDialog != null){
            val contentView = LayoutInflater.from(this)!!.inflate(R.layout.option_layout, coordinatorLayout, false)
            optionDialog = MyPopupWindow(this,contentView,ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
            optionDialog!!.setAlpha(1.0f)
        }
        optionDialog!!.showAtLocation(coordinatorLayout!!,Gravity.NO_GRAVITY,0,0)
    }

    override fun initData() {
        presenter!!.initData()
    }

    override fun saveImage() {
        val permissions = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if(checkSelfPermission(permissions[0]) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(permissions, REQUEST_PERMISSION_CODE)
        }else{
            presenter!!.saveImage(currentImageView)
        }
    }

    override fun onSaveImageCallBack(result: String?) {
        optionDialog!!.dismiss()
        showToast(this,result)
        SystemUtil.vibrate(this,50)
    }

    override fun initView() {
        UiUtil.setTranslucentStatusBarWhite(this)
        UiUtil.initToolbar(this,true)

        coordinatorLayout= findViewById(R.id.coordinator_layout)
        val recycleView = findViewById<RecyclerView>(R.id.recycler_view)
        adapter = ImageAdapter(presenter!!.addressList!!)
        recycleView.adapter = adapter
        recycleView.layoutManager = GridLayoutManager(this,2)
        recycleView.addOnScrollListener(RecycleViewScrollListener(object : OnSwipeUpRefresh  { override fun loadMore() { presenter!!.loadMore() }}))
        adapter!!.setOnClickListener(object : RecycleViewListener.OnItemClick{
            override fun onClick(v: View?, position: Int) {
                showBigImage(v)
            }
        })
        adapter!!.setOnLongClickListener(object : RecycleViewListener.OnItemLongClick{
            override fun onLongClick(v: View?, position: Int) {
                showOptionDialog(v)
            }
        })
        adapter!!.setOnTouchListener(object : RecycleViewListener.OnTouch {
            override fun onTouch(v: View?, event: MotionEvent?) {
                currentX = event!!.rawX.toInt()
                currentY = event.rawY.toInt()
            }
        })
    }

    override fun onUpdateImagesCallBack() {
        if (adapter != null){
            adapter!!.notifyDataSetChanged()
            UiUtil.dismissLoadingWindow()
        }
    }

    override fun showBigImage(view: View?) {
        currentImageView = view!!.findViewById(R.id.image_view)
        val contentDrawable = currentImageView!!.drawable
        if(bigImageDialog == null){
            val contentView = LayoutInflater.from(this)!!.inflate(R.layout.big_image_layout,coordinator_layout,false)
            bigImageDialog = MyPopupWindow(this,contentView,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            bigImageView = contentView.findViewById(R.id.image_view)
        }
        bigImageView!!.setImageDrawable(contentDrawable)
        bigImageDialog!!.showAtLocation(coordinator_layout,Gravity.CENTER,0,0)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                showToast(this, "权限获取失败,后期会导致保存图片失败,其他功能不受影响")
            } else {
                presenter!!.saveImage(currentImageView)
            }
        }
    }

    fun onClick(view: View){
        when(view.id){
            R.id.fab -> {}
            R.id.tv_save_image -> saveImage()
        }
    }

    companion object{
        private const val REQUEST_PERMISSION_CODE = 2
    }

}