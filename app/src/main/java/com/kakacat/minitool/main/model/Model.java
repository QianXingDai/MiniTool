package com.kakacat.minitool.main.model;

import com.kakacat.minitool.R;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private static Model model;

    private List<MainItem> dailyList;
    private List<MainItem> geekList;

    private Model() {

    }

    public static Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public void initData() {
        dailyList = getDailyList();
        geekList = getGeekList();
    }

    public List<MainItem> getDailyList() {
        if (dailyList == null) {
            dailyList = new ArrayList<>();
            dailyList.add(new MainItem(R.string.title_currency_conversion, R.drawable.ic_money, R.string.note_currency_conversion));
            dailyList.add(new MainItem(R.string.title_phone_attribution, R.drawable.ic_phone, R.string.note_phone_attribution));
            dailyList.add(new MainItem(R.string.title_today_in_history, R.drawable.ic_today_in_history, R.string.note_today_in_history));
            dailyList.add(new MainItem(R.string.title_wifi_pwd_view, R.drawable.ic_wifi, R.string.note_wifi_pwd_view));
            dailyList.add(new MainItem(R.string.title_app_info, R.drawable.ic_app_info, R.string.note_app_info));
            dailyList.add(new MainItem(R.string.title_clean_file, R.drawable.ic_clean_file, R.string.note_clean_info));
            dailyList.add(new MainItem(R.string.title_garbage_classification, R.drawable.ic_garbage, R.string.note_garbage_classification));
            dailyList.add(new MainItem(R.string.title_global_outbreak, R.drawable.ic_global_outbreak, R.string.note_global_outbreak));
            dailyList.add(new MainItem(R.string.title_translation, R.drawable.ic_dictionary, R.string.note_translation));
            dailyList.add(new MainItem(R.string.title_bing_pic, R.drawable.ic_bing, R.string.note_bing_pic));
            dailyList.add(new MainItem(R.string.title_delivery, R.drawable.ic_delivery, R.string.note_delivery));
        }

        return dailyList;
    }

    public List<MainItem> getGeekList() {
        if (geekList == null) {
            geekList = new ArrayList<>();
            geekList.add(new MainItem(R.string.title_text_encryption, R.drawable.ic_lock, R.string.note_text_encryption));
            geekList.add(new MainItem(R.string.title_modify_dpi, R.drawable.ic_dpi, R.string.note_modify_dpi));
            geekList.add(new MainItem(R.string.title_battery_info, R.drawable.ic_battery, R.string.note_battery_info));
            geekList.add(new MainItem(R.string.title_audio_capture, R.drawable.ic_audio_capture, R.string.note_audio_capture));
            geekList.add(new MainItem(R.string.title_inquire_ip, R.drawable.ic_internet, R.string.note_inquire_ip));

        }
        return geekList;
    }
}
