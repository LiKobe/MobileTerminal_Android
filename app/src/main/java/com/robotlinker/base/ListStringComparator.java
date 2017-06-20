package com.robotlinker.base;

import java.util.Comparator;

/**
 * Created by gaowubin on 2017/6/20.
 */

public class ListStringComparator implements Comparator<String> {

    @Override
    public int compare(String str1, String str2) {

        return str1.compareTo(str2);
    }
}
