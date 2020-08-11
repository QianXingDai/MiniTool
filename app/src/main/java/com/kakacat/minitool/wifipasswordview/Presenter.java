package com.kakacat.minitool.wifipasswordview;

import android.content.Context;

import com.kakacat.minitool.common.util.SystemUtil;
import com.kakacat.minitool.common.util.ThreadUtil;
import com.kakacat.minitool.wifipasswordview.model.Model;
import com.kakacat.minitool.wifipasswordview.model.Wifi;

import java.util.List;

public class Presenter implements Contract.Presenter {

    private Contract.View view;
    private Model model;
    private Context context;

    public Presenter(Contract.View view) {
        this.view = view;
        this.model = new Model();
        this.context = view.getContext();
    }

    @Override
    public void initData() {
        ThreadUtil.callInBackground(() -> {
            String result = "获取wifi配置信息失败,请检查是否有ROOT权限";
            if (model.copyWifiConfigToCache(context)) {
                if (model.handleWifiConfig(context)) {
                    result = "解析成功";
                } else {
                    result = "获取wifi配置文件成功,但是处理失败";
                }
            }
            String finalResult = result;
            ThreadUtil.callInUiThread(() -> view.onGetWifiDataCallBack(finalResult));
        });
    }

    @Override
    public void copyToClipboard(int position) {
        Wifi wifi = getWifiList().get(position);
        SystemUtil.copyToClipboard(context, "wifiPwd", wifi.getWifiPwd());
        view.onCopyCallback("\"" + wifi.getWifiName() + "\"的wifi密码已复制");
    }

    @Override
    public List<Wifi> getWifiList() {
        return model.getWifiList();
    }
}
