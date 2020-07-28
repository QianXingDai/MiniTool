package com.kakacat.minitool.translation;

public class LanguageMap {

    private static String[] languages = {
            "自动检测","中文","英语","粤语","文言文","日语",
            "韩语","法语","西班牙语","泰语","阿拉伯语","俄语",
            "葡萄牙语","德语","意大利语","希腊语","荷兰语","波兰语",
            "保加利亚语","爱沙尼亚语","丹麦语","芬兰语","捷克语","罗马尼亚语",
            "斯洛文尼亚语","瑞典语","匈牙利语","繁体中文","越南语"
    };

    private static CharSequence[] shortCodes = {
            "auto","zh","en","yue","wyw","jp",
            "kor","fra","spa","th","ara","ru",
            "pt","de","it","el","nl","pl",
            "bul","est","dan","fin","cs","rom",
            "slo","swe","hu","cht","vie"
    };

    public static String[] getLanguages(){
        return languages;
    }

    public static CharSequence getShortCode(CharSequence language){
        for (int i = 0; i < languages.length; i++) {
            if(language.equals(languages[i])){
                return shortCodes[i];
            }
        }
        return null;
    }
}
