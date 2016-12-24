package com.ljb.shareprefers.helper;

import com.jianbo.sharepreferlib.LSharePreferUtils;

/**
 * Created by jianbo on 2016/12/24.
 */

public class MainHelper {
    private static final String FILE_USER = "user";
    private static final String KEY_USER_NAME = "key_user_name";
    private static final String KEY_USER_AGE = "key_user_age";
    public static void saveUserName(String name) {
        LSharePreferUtils.put(FILE_USER,KEY_USER_NAME,name,true);
    }

    public static String getUserName() {
        return (String) LSharePreferUtils.get(FILE_USER, KEY_USER_NAME, "",true);
    }

    public static void saveUserAge(float age) {
        LSharePreferUtils.put(FILE_USER,KEY_USER_AGE,age,true);
    }

    public static float getUserAge() {
        return (Float) LSharePreferUtils.get(FILE_USER, KEY_USER_AGE, 0f, true);
    }
}
