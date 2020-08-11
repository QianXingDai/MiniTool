package com.kakacat.minitool.fakebattery

import android.app.Activity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.kakacat.minitool.R
import com.kakacat.minitool.common.ui.view.MyPopupWindow
import com.kakacat.minitool.common.util.SystemUtil.getElectricity
import com.kakacat.minitool.common.util.SystemUtil.resetBattery
import com.kakacat.minitool.common.util.SystemUtil.setBatteryLevel
import com.kakacat.minitool.common.util.UiUtil

class FakeBatteryView private constructor(private val activity: Activity, view: View, width: Int, height: Int) : MyPopupWindow(activity, view, width, height) {

    private lateinit var etBattery: EditText
    private lateinit var seekBarBattery: SeekBar

    private fun initView() {
        etBattery = contentView.findViewById(R.id.et_current_battery)
        seekBarBattery = contentView.findViewById(R.id.seek_bar_battery)
        val btFakeBattery = contentView.findViewById<Button>(R.id.bt_fake_battery)
        val btResetBattery = contentView.findViewById<Button>(R.id.bt_reset_battery)
        seekBarBattery.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) { etBattery.setText(progress.toString()) }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
        btResetBattery.setOnClickListener {
            resetBattery()
            val `val` = getElectricity(activity)
            seekBarBattery.progress = `val`
        }
        btFakeBattery.setOnClickListener {
            val `val` = etBattery.text.toString().toInt()
            if (`val` < 0 || `val` > 100) UiUtil.showToast(activity.applicationContext, "你正常点好吗???") else {
                setBatteryLevel(`val`.toString() + "")
                seekBarBattery.progress = `val`
            }
        }
    }

    override fun showAtLocation(parent: View, gravity: Int, x: Int, y: Int) {
        val batteryLevel = getElectricity(activity)
        etBattery.setText(batteryLevel.toString())
        seekBarBattery.progress = batteryLevel
        super.showAtLocation(parent, gravity, x, y)
    }

    companion object {

        private lateinit var fakeBatteryView : FakeBatteryView
        
        fun getInstance(activity: Activity, contentView: View, width: Int, height: Int): FakeBatteryView {
            if (!this::fakeBatteryView.isInitialized) {
                fakeBatteryView = FakeBatteryView(activity, contentView, width, height)
                fakeBatteryView.initView()
            }
            return fakeBatteryView
        }
    }
}