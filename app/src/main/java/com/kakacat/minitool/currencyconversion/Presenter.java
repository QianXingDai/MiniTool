package com.kakacat.minitool.currencyconversion;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.kakacat.minitool.R;
import com.kakacat.minitool.common.constant.AppKey;
import com.kakacat.minitool.common.constant.Host;
import com.kakacat.minitool.common.constant.Result;
import com.kakacat.minitool.common.myinterface.HttpCallback;
import com.kakacat.minitool.common.util.HttpUtil;
import com.kakacat.minitool.currencyconversion.model.Country;
import com.kakacat.minitool.currencyconversion.model.Rate;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class Presenter implements Contract.Presenter {

    private static final String requestAddress = Host.EXCHANGE_RATE_HOST + AppKey.EXCHANGE_RATE_KEY;



    private Contract.View view;
    private Context context;

    private List<Country> countryList;

    public Presenter(Contract.View view) {
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
        HttpUtil.sendOkHttpRequest(requestAddress,new HttpCallback() {
            int resultFlag = Result.REQUEST_ERROR;
            @Override
            public void onSuccess(Response response) {
                if(!Rate.handleResponse(response)){
                    resultFlag = Result.HANDLE_FAIL;
                }else{
                    resultFlag = Result.HANDLE_SUCCESS;
                }
                view.onRefreshExchangeRate(resultFlag);
            }
            @Override
            public void onError() {
                view.onRefreshExchangeRate(Result.REQUEST_ERROR);
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
