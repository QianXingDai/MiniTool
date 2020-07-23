package com.kakacat.minitool.currencyconversion;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.kakacat.minitool.R;
import com.kakacat.minitool.common.myinterface.HttpCallbackListener;
import com.kakacat.minitool.common.util.HttpUtil;
import com.kakacat.minitool.currencyconversion.model.Country;
import com.kakacat.minitool.currencyconversion.model.Rate;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Response;

public class MainPresenter implements MainContract.Presenter {

    private static final String key = "6103d9a9aeca9c09fc8f6bd4734be680";
    private static final String host = "http://web.juhe.cn:8080/finance/exchange/rmbquot?key=";
    private static final String requestAddress = host + key;

    public static final int HANDLE_SUCCESS = 0;
    public static final int HANDLE_FAIL = 1;
    public static final int REQUEST_SUCCESS = 2;
    public static final int REQUEST_FAIL = 3;

    private MainContract.View view;
    private Context context;

    private List<Country> countryList;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        this.context = view.getContext();
    }

    @Override
    public void initData(){
        Rate.readRateFromLocal(context);
        fillCountryList();
    }

    @Override
    public void refreshExchangeRate() {
        final int[] flag = {REQUEST_FAIL};
        HttpUtil.sendOkHttpRequest(requestAddress,new HttpCallbackListener() {
            @Override
            public void onSuccess(Response response) {
                flag[0] = REQUEST_SUCCESS;
                String s = null;
                try {
                    s = Objects.requireNonNull(response.body()).string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(handleRateResponse(s)){
                    for(int i = 0; i < 22; i++){
                        countryList.get(i).setRate(Rate.getRate(i + 1));
                    }
                    flag[0] = HANDLE_SUCCESS;
                }else{
                    flag[0] = HANDLE_FAIL;
                }
                view.onRefreshExchangeRate(flag[0]);
            }
            @Override
            public void onError() {
                view.onRefreshExchangeRate(REQUEST_FAIL);
            }
        });
    }

    @Override
    public boolean handleRateResponse(String response){
        try{
            if(!TextUtils.isEmpty(response)){
                JSONObject jsonObject = new JSONObject(response);
                String resultCode = jsonObject.getString("resultcode");

                if(!resultCode.equals("200")){
                    return false;
                }

                JSONObject result = jsonObject.getJSONArray("result").getJSONObject(0);
                for(int i = 1; i <= 22;i ++){
                    JSONObject data = result.getJSONObject("data" + i);
                    double rate = Double.parseDouble(data.getString("bankConversionPri")) / 100;
                    Rate.setRate(rate,i);
                }
                Rate.writeRateToLocal(context);
                return true;
            }else{
                Log.d("hhh","汇率数据空");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getResult(CharSequence val,double rate1,double rate2){
        if(TextUtils.isEmpty(val)){
            return "";
        }
        double s = Double.parseDouble(val.toString());
        s = s * rate1 / rate2;
        return String.valueOf(s);
    }

    @Override
    public List<Country> getCountryList() {
        return countryList;
    }

    private void fillCountryList(){
        countryList = new ArrayList<>();

        countryList.add(new Country(R.drawable.ic_us,R.string.name_us,R.string.unit_us, Rate.us));
        countryList.add(new Country(R.drawable.ic_eu,R.string.name_eu,R.string.unit_eu, Rate.eu));
        countryList.add(new Country(R.drawable.ic_hk,R.string.name_hk,R.string.unit_hk, Rate.hk));
        countryList.add(new Country(R.drawable.ic_jp,R.string.name_jp,R.string.unit_jp, Rate.jp));
        countryList.add(new Country(R.drawable.ic_uk,R.string.name_uk,R.string.unit_uk, Rate.uk));
        countryList.add(new Country(R.drawable.ic_au,R.string.name_au,R.string.unit_au, Rate.au));
        countryList.add(new Country(R.drawable.ic_ca,R.string.name_ca,R.string.unit_ca, Rate.ca));
        countryList.add(new Country(R.drawable.ic_th,R.string.name_th,R.string.unit_th, Rate.th));
        countryList.add(new Country(R.drawable.ic_sg,R.string.name_sg,R.string.unit_sg, Rate.sg));
        countryList.add(new Country(R.drawable.ic_ch,R.string.name_ch,R.string.unit_ch, Rate.ch));
        countryList.add(new Country(R.drawable.ic_dk,R.string.name_dk,R.string.unit_dk, Rate.dk));
        countryList.add(new Country(R.drawable.ic_ma,R.string.name_ma,R.string.unit_ma, Rate.ma));
        countryList.add(new Country(R.drawable.ic_my,R.string.name_my,R.string.unit_my, Rate.my));
        countryList.add(new Country(R.drawable.ic_no,R.string.name_no,R.string.unit_no, Rate.no));
        countryList.add(new Country(R.drawable.ic_nz,R.string.name_nz,R.string.unit_nz, Rate.nz));
        countryList.add(new Country(R.drawable.ic_ph,R.string.name_ph,R.string.unit_ph, Rate.ph));
        countryList.add(new Country(R.drawable.ic_ru,R.string.name_ru,R.string.unit_ru, Rate.ru));
        countryList.add(new Country(R.drawable.ic_se,R.string.name_se,R.string.unit_se, Rate.se));
        countryList.add(new Country(R.drawable.ic_tw,R.string.name_tw,R.string.unit_tw, Rate.tw));
        countryList.add(new Country(R.drawable.ic_br,R.string.name_br,R.string.unit_br, Rate.br));
        countryList.add(new Country(R.drawable.ic_kr,R.string.name_kr,R.string.unit_kr, Rate.kr));
        countryList.add(new Country(R.drawable.ic_za,R.string.name_za,R.string.unit_za, Rate.za));
        countryList.add(new Country(R.drawable.ic_cn,R.string.name_cn,R.string.unit_cn, Rate.cn));
    }


}
