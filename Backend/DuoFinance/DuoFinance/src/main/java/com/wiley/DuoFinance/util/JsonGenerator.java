package com.wiley.DuoFinance.util;

import java.util.HashMap;
import java.util.Map;

public class JsonGenerator {

    public static Map<String, Map<String, String>> formatSingleError(String key, String value) {

        Map<String, String> inner = new HashMap<>();
        Map<String, Map<String, String>> outter = new HashMap<>();

        inner.put(key, value);
        outter.put("errors", inner);

        return outter;
    }
}
