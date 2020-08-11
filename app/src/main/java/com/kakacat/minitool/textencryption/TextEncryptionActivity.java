package com.kakacat.minitool.textencryption;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.kakacat.minitool.R;
import com.kakacat.minitool.common.base.BaseActivity;
import com.kakacat.minitool.common.util.SystemUtil;
import com.kakacat.minitool.common.util.UiUtil;

import org.jetbrains.annotations.NotNull;

public class TextEncryptionActivity extends BaseActivity implements Contract.View {

    private Contract.Presenter presenter;

    private Toolbar toolbar;
    private TextView tvOutput;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_encryption);

        initData();
        initView();
    }

    @Override
    public void initData() {
        presenter = new Presenter(this);
        presenter.initData();
    }

    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_back);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ChipGroup chipGroup = findViewById(R.id.chip_group);
        for (String s : presenter.getEncryptionMethods()) {
            Chip chip = new Chip(getContext());
            chip.setCheckable(true);
            chip.setChipIconVisible(false);
            chip.setCloseIconVisible(false);
            chip.setCheckedIconResource(R.drawable.ic_mtrl_chip_checked_black);
            chip.setOnCheckedChangeListener((compoundButton, b) -> {
                if (b) {
                    toolbar.setSubtitle(s);
                    toolbar.setSubtitleTextColor(getColor(android.R.color.white));
                }
            });
            chip.setText(s);
            chipGroup.addView(chip);
        }
        ((Chip) chipGroup.getChildAt(0)).setChecked(true);

        tvOutput = findViewById(R.id.tv_output);
        editText = findViewById(R.id.edit_text);
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                UiUtil.closeKeyboard(getContext(), v);
            }
        });
    }

    public void onClick(@NotNull View v) {
        switch (v.getId()) {
            case R.id.bt_code: {
                presenter.encryptText(editText.getText().toString(), toolbar.getSubtitle());
                break;
            }
            case R.id.bt_decode: {
                //TODO:还没实现，看看网上有没有合适的接口
                break;
            }

            case R.id.bt_delete_input: {
                editText.setText("");
                break;
            }
            case R.id.bt_copy: {
                SystemUtil.copyToClipboard(getContext(), "codeContent", tvOutput.getText());
                UiUtil.showToast(getContext(), "复制成功");
                break;
            }
        }
    }

    @Override
    public void onEncryptResult(String decode) {
        if (!TextUtils.isEmpty(decode)) {
            tvOutput.setText(decode);
            UiUtil.showToast(getContext(), "加密成功");
        } else {
            UiUtil.showToast(getContext(), "加密失败");
        }
    }

}
