package com.kakacat.minitool.main.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.kakacat.minitool.R;
import com.kakacat.minitool.audiocapture.GetAudioService;
import com.kakacat.minitool.common.ui.RecycleViewListener;
import com.kakacat.minitool.inquireip.InquireIpActivity;
import com.kakacat.minitool.main.MainContract;
import com.kakacat.minitool.modifydpi.ModifyDpiView;
import com.kakacat.minitool.textencryption.TextEncryptionActivity;

import static android.app.Activity.RESULT_OK;

public class GeekFragment extends MyFragment implements RecycleViewListener.OnItemClick {

    private Activity activity;
    private View parentView;

    public GeekFragment() {

    }

    public GeekFragment(MainContract.Presenter presenter) {
        super(presenter.getGeekList());
        super.setOnClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = super.onCreateView(inflater, container, savedInstanceState);
        activity = getActivity();
        return parentView;
    }

    @Override
    public void onClick(View v, int position) {
        Intent intent = null;
        switch (position) {
            case 0: {
                intent = new Intent(activity, TextEncryptionActivity.class);
                break;
            }
            case 1: {
                ModifyDpiView modifyDpiView = ModifyDpiView.getInstance(activity, parentView, View.inflate(getContext(), R.layout.modify_dpi_layout, null), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                modifyDpiView.showAtLocation(parentView, Gravity.CENTER, 0, 0);
                break;
            }
            case 2: {
        /*        FakeBatteryView fakeBatteryView = FakeBatteryView.getInstance(activity, parentView, View.inflate(getContext(), R.layout.fake_battery_layout, null), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                fakeBatteryView.showAtLocation(parentView, Gravity.CENTER, 0, 0);*/
                break;
            }
            case 3: {
                audioCapture();
                break;
            }
            case 4: {
                intent = new Intent(activity, InquireIpActivity.class);
                break;
            }
        }

        if (intent != null) startActivity(intent);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startSelectVideo();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int REQUEST_WRITE = 1;
        if (requestCode == REQUEST_WRITE && resultCode == RESULT_OK && data != null) {
            Intent intent = new Intent(activity, GetAudioService.class);
            intent.putExtra("uri", data.getData());
            activity.startService(intent);
        }
    }

    private void audioCapture() {
        String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            startSelectVideo();
        }
    }

    private void startSelectVideo() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("video/*");
        startActivityForResult(intent, 1);
    }

}
