package com.iti.mealmate.data.filter.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class CountryFlagProvider {

    private static final String FLAG_BASE_URL = "https://flagsapi.com/%s/flat/64.png";

    private static final Map<String, String> AREA_TO_CODE = new HashMap<>();

    static {
        AREA_TO_CODE.put("Algerian", "DZ");
        AREA_TO_CODE.put("American", "US");
        AREA_TO_CODE.put("Argentinian", "AR");
        AREA_TO_CODE.put("Australian", "AU");
        AREA_TO_CODE.put("British", "GB");
        AREA_TO_CODE.put("Canadian", "CA");
        AREA_TO_CODE.put("Chinese", "CN");
        AREA_TO_CODE.put("Croatian", "HR");
        AREA_TO_CODE.put("Dutch", "NL");
        AREA_TO_CODE.put("Egyptian", "EG");
        AREA_TO_CODE.put("Filipino", "PH");
        AREA_TO_CODE.put("French", "FR");
        AREA_TO_CODE.put("Greek", "GR");
        AREA_TO_CODE.put("Indian", "IN");
        AREA_TO_CODE.put("Irish", "IE");
        AREA_TO_CODE.put("Italian", "IT");
        AREA_TO_CODE.put("Jamaican", "JM");
        AREA_TO_CODE.put("Japanese", "JP");
        AREA_TO_CODE.put("Kenyan", "KE");
        AREA_TO_CODE.put("Malaysian", "MY");
        AREA_TO_CODE.put("Mexican", "MX");
        AREA_TO_CODE.put("Moroccan", "MA");
        AREA_TO_CODE.put("Norwegian", "NO");
        AREA_TO_CODE.put("Polish", "PL");
        AREA_TO_CODE.put("Portuguese", "PT");
        AREA_TO_CODE.put("Russian", "RU");
        AREA_TO_CODE.put("Saudi Arabian", "SA");
        AREA_TO_CODE.put("Slovakian", "SK");
        AREA_TO_CODE.put("Spanish", "ES");
        AREA_TO_CODE.put("Syrian", "SY");
        AREA_TO_CODE.put("Thai", "TH");
        AREA_TO_CODE.put("Tunisian", "TN");
        AREA_TO_CODE.put("Turkish", "TR");
        AREA_TO_CODE.put("Ukrainian", "UA");
        AREA_TO_CODE.put("Uruguayan", "UY");
        AREA_TO_CODE.put("Venezulan", "VE");
        AREA_TO_CODE.put("Vietnamese", "VN");
    }

    private CountryFlagProvider() {
    }

    public static String getFlagUrl(String area) {
        if (area == null) return null;
        String code = AREA_TO_CODE.get(area);
        if (code == null) {
            return null;
        }
        return String.format(Locale.US, FLAG_BASE_URL, code);
    }
}


