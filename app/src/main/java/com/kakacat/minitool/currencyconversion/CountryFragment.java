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
import com.kakacat.minitool.common.ui.view.MyPopupWindow;
import com.kakacat.minitool.currencyconversion.model.CountryBean;

import java.util.List;

public class CountryFragment extends Fragment {

    private Contract.View mainView;
    private List<CountryBean> countryBeanList;

    public EditText et;
    public CountryBean countryBean;
    private TextView tvCountryName;
    private TextView tvMoneyUnit;
    private SimpleDraweeView sdv;
    private MyPopupWindow popupWindow;
    private Context context;
    private int flag;

    public CountryFragment(Contract.View mainView, List<CountryBean> countryBeanList, int flag) {
        this.mainView = mainView;
        this.countryBeanList = countryBeanList;
        this.flag = flag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (this.context == null) {
            this.context = getContext();
        }
        View view = inflater.inflate(R.layout.country_view_layout, container, false);

        if (flag == 1) {
            countryBean = countryBeanList.get(0);
        } else if (flag == 2) {
            countryBean = countryBeanList.get(1);
        }

        tvCountryName = view.findViewById(R.id.tv_country_name);
        tvCountryName.setText(countryBean.getNameId());

        tvMoneyUnit = view.findViewById(R.id.tv_money_unit);
        tvMoneyUnit.setText(countryBean.getUnitId());

        et = view.findViewById(R.id.et);
        et.addTextChangedListener(getTextWatcher());

        sdv = view.findViewById(R.id.sdv);
        sdv.setActualImageResource(countryBean.getIconId());
        sdv.setOnClickListener(view1 -> showDialog(view));

        return view;
    }

    private void showDialog(View view) {
        if (popupWindow == null) {
            View contentView = View.inflate(context, R.layout.dialog_select_country, null);
            popupWindow = new MyPopupWindow(context, contentView, ViewGroup.LayoutParams.WRAP_CONTENT, 1000);

            CountryAdapter countryAdapter = new CountryAdapter(countryBeanList);
            RecyclerView rv = contentView.findViewById(R.id.rv_country);

            rv.setAdapter(countryAdapter);
            rv.setLayoutManager(new GridLayoutManager(context, 3));
            countryAdapter.setOnClickListener((v, position) -> {
                countryBean = countryBeanList.get(position);
                sdv.setActualImageResource(countryBean.getIconId());
                tvCountryName.setText(countryBean.getNameId());
                tvMoneyUnit.setText(countryBean.getUnitId());
                popupWindow.dismiss();
            });
        }
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 100);
    }

    private TextWatcher getTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (et.isFocused()) {
                    mainView.onTextChanged(charSequence, flag);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
    }

}
