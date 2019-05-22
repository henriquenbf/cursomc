package com.hf.cursomc.resources.utils;

import static java.util.Objects.nonNull;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ParamsConverter {

    public static List<Integer> convertIdsList(String s) {

        if (nonNull(s)) {
            String[] ids = s.split(",");

            List<Integer> idsList = Arrays.stream(ids).map(id -> Integer.parseInt(id)).collect(Collectors.toList());
            return idsList;
        }

        return Collections.emptyList();
    }

    public static String decodeString(String s) {
        try {
            return URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

}
