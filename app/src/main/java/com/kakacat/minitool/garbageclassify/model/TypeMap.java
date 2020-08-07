package com.kakacat.minitool.garbageclassify.model;

import com.kakacat.minitool.R;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class TypeMap {

    public static int getIcon(int type) {
        switch (type) {
            case 0:
                return R.drawable.ic_recycleable;
            case 1:
                return R.drawable.ic_harmful;
            case 2:
                return R.drawable.ic_wet;
            case 3:
                return R.drawable.ic_dry;
        }
        return 0;
    }


    @NotNull
    @Contract(pure = true)
    public static String getTypeName(int type) {
        switch (type) {
            case 0:
                return "可回收垃圾";
            case 1:
                return "有害垃圾";
            case 2:
                return "厨余(湿)垃圾";
            case 3:
                return "其他(干)垃圾";
        }
        return "";
    }

}
