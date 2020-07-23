package com.kakacat.minitool.currencyconversion;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kakacat.minitool.R;
import com.kakacat.minitool.common.ui.MyPopupWindow;
import com.kakacat.minitool.currencyconversion.model.Country;

import java.util.List;

public class CountryFragment extends Fragment{

    private static MainContract.View mainView;
    private static List<Country> countryList;

    private TextView tvCountryName;
    private TextView tvMoneyUnit;
    private SimpleDraweeView sdv;
    protected EditText et;
    private MyPopupWindow popupWindow;

    private Context context;

    protected Country country;
    private int flag;

    public CountryFragment(MainContract.View mainView,List<Country> countryList,int flag) {
        if(CountryFragment.mainView == null){
            CountryFragment.mainView = mainView;
        }
        if(CountryFragment.countryList == null){
            CountryFragment.countryList = countryList;
        }
        this.flag = flag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(this.context == null){
            this.context = getContext();
        }
        View view = inflater.inflate(R.layout.country_view_layout,container,false);

        if(flag == 1){
            country = countryList.get(0);
        }else if(flag == 2){
            country = countryList.get(1);
        }

        tvCountryName = view.findViewById(R.id.tv_country_name);
        tvCountryName.setText(country.getNameId());

        tvMoneyUnit = view.findViewById(R.id.tv_money_unit);
        tvMoneyUnit.setText(country.getUnitId());

        et = view.findViewById(R.id.et);
        et.addTextChangedListener(getTextWatcher());

        sdv = view.findViewById(R.id.sdv);
        sdv.setActualImageResource(country.getIconId());
        sdv.setOnClickListener(view1 -> showDialog(view));

        return view;
    }

    private void showDialog(View view){
        if(popupWindow == null){
            View contentView = View.inflate(context,R.layout.dialog_select_country,null);
            popupWindow = new MyPopupWindow(context,contentView, ViewGroup.LayoutParams.WRAP_CONTENT, 1000);

            CountryAdapter countryAdapter = new CountryAdapter(countryList);
            RecyclerView rv = contentView.findViewById(R.id.rv_country);

            rv.setAdapter(countryAdapter);
            rv.setLayoutManager(new GridLayoutManager(context,3));
            countryAdapter.setOnClickListener((v, position) -> {
                country = countryList.get(position);
                sdv.setActualImageResource(country.getIconId());
                tvCountryName.setText(country.getNameId());
                tvMoneyUnit.setText(country.getUnitId());
                popupWindow.dismiss();
            });
        }
        popupWindow.showAtLocation(view, Gravity.CENTER, 0,100);
    }

    private TextWatcher getTextWatcher(){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(et.isFocused()){
                    mainView.onTextChanged(charSequence,flag);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        };
    }

}
