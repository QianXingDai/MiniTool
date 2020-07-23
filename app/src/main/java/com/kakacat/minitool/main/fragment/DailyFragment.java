package com.kakacat.minitool.main.fragment;

import android.content.Intent;
import android.view.View;

import com.kakacat.minitool.R;
import com.kakacat.minitool.appInfo.AppInfoActivity;
import com.kakacat.minitool.bingPic.BingPicActivity;
import com.kakacat.minitool.cleanFile.CleanFileActivity;
import com.kakacat.minitool.common.myinterface.RecycleViewItemOnClickListener;
import com.kakacat.minitool.currencyconversion.MainActivity;
import com.kakacat.minitool.garbageClassification.GarbageClassificationActivity;
import com.kakacat.minitool.globalOutbreak.GlobalOutbreakActivity;
import com.kakacat.minitool.main.MainContract;
import com.kakacat.minitool.phoneartribution.PhoneAttributionActivity;
import com.kakacat.minitool.todayInHistory.TodayInHistoryActivity;
import com.kakacat.minitool.translation.TranslationActivity;
import com.kakacat.minitool.wifipasswordview.WifiPwdViewActivity;

public class DailyFragment extends MyFragment implements RecycleViewItemOnClickListener {

    public DailyFragment(MainContract.Presenter presenter) {
        super(presenter.getDailyList());
        super.setOnClickListener(this);
    }

    @Override
    public void onClick(View v, int position) {
        Intent intent = null;
        switch (position){
            case 0 :{
                intent = new Intent(super.context, MainActivity.class);
                break;
            }
            case 1:{
                intent = new Intent(super.context, PhoneAttributionActivity.class);
                break;
            }
            case 2:{
                intent = new Intent(super.context, TodayInHistoryActivity.class);
                break;
            }
            case 3:{
                intent = new Intent(super.context, WifiPwdViewActivity.class);
                break;
            }
            case 4:{
                intent = new Intent(super.context, AppInfoActivity.class);
                break;
            }
            case 5:{
                intent = new Intent(super.context, CleanFileActivity.class);
                break;
            }
            case 6:{
                intent = new Intent(super.context, GarbageClassificationActivity.class);
                break;
            }
            case 7:{
                intent = new Intent(super.context, GlobalOutbreakActivity.class);
                break;
            }
            case 8:{
                intent = new Intent(super.context, TranslationActivity.class);
                break;
            }
            case 9:{
                intent = new Intent(super.context, BingPicActivity.class);
                break;
            }
        }

        if(intent != null) {
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.activity_open,R.anim.activity_enter_anim);
        }
    }
}