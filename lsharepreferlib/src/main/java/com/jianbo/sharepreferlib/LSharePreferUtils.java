package com.jianbo.sharepreferlib;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;
import android.text.TextUtils;

import com.jianbo.sharepreferlib.utils.DESUtils;
import com.jianbo.sharepreferlib.utils.MD5Utils;

import java.util.Map;

/**
 * Created by jianbo on 2016/8/9.
 */
public class LSharePreferUtils {
    private static int MODE = Context.MODE_PRIVATE;
    private static String DEF_FILE_NAME = "cache_share";
    private static Context sContext;

    private LSharePreferUtils() {
    }

    public static void init(Context context) {
        init(context, DEF_FILE_NAME);
    }

    public static void init(Context context, String defFileName) {
        if (context == null) {
            throw new NullPointerException("context is null !");
        }
        sContext = context;
        DEF_FILE_NAME = defFileName;
    }

    /**
     * get sharepreferences
     *
     * @param fileName sharepre file name
     * @return sharePreferences
     */
    private static SharedPreferences getSharePrefer(String fileName) {
        return sContext.getSharedPreferences(fileName, MODE);
    }

    /**
     * 使用默认文件名存储
     *
     * @param key    存储内容 key
     * @param object 存储的内容
     */
    public static void put(String key, Object object) {
        put(key, object, false);
    }

    public static void put(String key, Object object, boolean valueEncrypt) {
        put(DEF_FILE_NAME, key, object, valueEncrypt);
    }

    public static void put(String fileName, String key, Object object) {
        put(fileName, key, object, false);
    }

    /**
     * 保存数据，通过文件名，key。通过对象的类型来存储
     *
     * @param fileName 文件名
     * @param key      存储内容 key
     * @param object   存储的内容
     */
    public static void put(String fileName, String key, Object object, boolean valueEncrypt) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        String dataKey = convertKey(key);
        SharedPreferences sp = getSharePrefer(fileName);
        SharedPreferences.Editor editor = sp.edit();
        if (valueEncrypt) {
            try {
                String encryptValue = encryptValue(String.valueOf(object));
                editor.putString(dataKey, encryptValue);
            } catch (Exception e) {
                editor.putString(dataKey, (String) object);
            }
        } else {
            if (object instanceof String) {
                editor.putString(dataKey, (String) object);
            } else if (object instanceof Integer) {
                editor.putInt(dataKey, (Integer) object);
            } else if (object instanceof Boolean) {
                editor.putBoolean(dataKey, (Boolean) object);
            } else if (object instanceof Float) {
                editor.putFloat(dataKey, (Float) object);
            } else if (object instanceof Long) {
                editor.putLong(dataKey, (Long) object);
            } else {
                editor.putString(dataKey, object.toString());
            }
        }
        apply(editor);
    }


    /**
     * 加密key
     *
     * @param key 需要加密的key
     * @return 加密后的key
     */
    private static String convertKey(String key) {
        return MD5Utils.MD5(key);
    }

    /**
     * 加密value
     *
     * @param value value
     * @return 加密后的 value
     */
    private static String encryptValue(String value) throws Exception {
        if (TextUtils.isEmpty(value)) {
            return value;
        }
        return new DESUtils().encrypt(value);
    }

    /**
     * 解密value
     *
     * @param value value
     * @return 解密后的 value
     */
    private static String decryptValue(String value) throws Exception {
        if (TextUtils.isEmpty(value)) {
            return value;
        }
        return new DESUtils().decrypt(value);
    }

    /**
     * 获取默认文件下，key 的值
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(String key, Object defaultObject) {
        return get(DEF_FILE_NAME, key, defaultObject);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     */
    public static Object get(String fileName, String key, Object defaultObject) {
        return get(fileName, key, defaultObject, false);
    }

    public static Object get(String key, Object defaultObject, boolean valueDecrypt) {
        return get(DEF_FILE_NAME, key, defaultObject, valueDecrypt);
    }

    public static Object get(String fileName, String key, Object defaultObject, boolean valueDecrept) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        String dataKey = convertKey(key);
        SharedPreferences sp = getSharePrefer(fileName);
        if (valueDecrept) {
            String dataValue = sp.getString(dataKey, "");
            if (TextUtils.isEmpty(dataValue)) {
                return defaultObject;
            }
            try {
                String decryptValue = decryptValue(dataValue);
                if (defaultObject instanceof Integer) {
                    return Integer.parseInt(decryptValue);
                } else if (defaultObject instanceof Boolean) {
                    return Boolean.parseBoolean(decryptValue);
                } else if (defaultObject instanceof Float) {
                    return Float.parseFloat(decryptValue);
                } else if (defaultObject instanceof Long) {
                    return Long.parseLong(decryptValue);
                } else {
                    return decryptValue;
                }
            } catch (Exception e) {
                return dataValue;
            }
        } else {
            if (defaultObject instanceof String) {
                return sp.getString(dataKey, (String) defaultObject);
            } else if (defaultObject instanceof Integer) {
                return sp.getInt(dataKey, (Integer) defaultObject);
            } else if (defaultObject instanceof Boolean) {
                return sp.getBoolean(dataKey, (Boolean) defaultObject);
            } else if (defaultObject instanceof Float) {
                return sp.getFloat(dataKey, (Float) defaultObject);
            } else if (defaultObject instanceof Long) {
                return sp.getLong(dataKey, (Long) defaultObject);
            }
        }
        return null;
    }


    /**
     * 移除某个key值已经对应的值
     */
    public static void remove(String key) {
        remove(DEF_FILE_NAME, key);
    }

    /**
     * 移除某个key值已经对应的值
     */
    public static void remove(String fileName, String key) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        String dataKey = convertKey(key);
        SharedPreferences sp = getSharePrefer(fileName);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(dataKey);
        apply(editor);
    }

    public static void clear() {
        clear(DEF_FILE_NAME);
    }

    /**
     * 清除所有数据
     */
    public static void clear(String fileName) {
        SharedPreferences sp = getSharePrefer(fileName);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        apply(editor);
    }

    public static boolean contains(String key) {
        return contains(DEF_FILE_NAME, key);
    }

    /**
     * 查询某个key是否已经存在
     */
    public static boolean contains(String fileName, String key) {
        if (TextUtils.isEmpty(key)) {
            return false;
        }
        String dataKey = convertKey(key);
        SharedPreferences sp = getSharePrefer(fileName);
        return sp.contains(dataKey);
    }

    public static Map<String, ?> getAll() {
        return getAll(DEF_FILE_NAME);
    }

    /**
     * 返回所有的键值对
     */
    public static Map<String, ?> getAll(String fileName) {
        SharedPreferences sp = getSharePrefer(fileName);
        return sp.getAll();
    }

    private static void apply(SharedPreferences.Editor editor) {
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }
}
