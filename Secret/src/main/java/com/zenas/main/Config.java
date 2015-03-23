package com.zenas.main;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 配置
 * Created by Administrator on 2015/3/16.
 */
public class Config {
    /**
     * 以下是请求参数及配置参数
     */
    /*tokenKEY*/
    public final static String TOKEN_KEY = "token";
    /*动作标识*/
    public final static String ACTION_KEY = "action";
    /*上传数据KEY*/
    public static final String UPLOAD_DATA_KEY ="contacts";
    /*MD5手机号KEY*/
    public static final String MD5_KEY = "phone_md5";
    /*电话号码KEY*/
    public static final String PHONE_NUM_KEY = "phone";
    /*短信验证码标识KEY*/
    public static final String CODE_KEY = "code";
    /*上次数据标识*/
    public static final String ACTION_UPLOAD_KEY="upload_contacts";
    /*状态码标识*/
    public static final String STATUS_KEY = "status";
    /*服务器地址*/
//    public static final String URL = "http://demo.eoeschool.com/api/v1/nimings/io";
    /*本地测试地址*/
    public static final String URL = "http://192.168.1.101:8080/TestServer/api.jsp";
    /*APP_ID*/
    public final static String APP_ID = "com.zenas.secret";
    /*编码*/
    public static final String CHARSET = "utf-8";
    /*获取短信验证码标识*/
    public static final String ACTION_GET_CODE = "send_pass";
    /*登陆*/
    public static final String ACTION_LOGIN = "login";
    /*服务器返回的状态码，1：成功，0：失败，2：失效*/
    public static final int RESULT_STATUS_SUCCESS = 1;
    public static final int RESULT_STATUS_FAIL = 0;
    public static final int RESULT_STATUS_INVALID_TOKEN = 2;
    public static final int RESULT_MS = 1;

    /**
     * 获取本地缓存TOKEN
     *
     * @param context
     * @return
     */
    public static String getCachedToken(Context context) {
//        根据TOKEN_KEY获取到缓存到本地的TOKEN值
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(TOKEN_KEY, null);
    }

    /**
     * 存入本地通过网络获取的TOKEN
     *
     * @param context
     * @param token
     */
    public static void saveCachedToken(Context context, String token) {
        SharedPreferences.Editor edit = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        edit.putString(TOKEN_KEY, token);//根据传入进来的token值存入SharedPreference
        edit.commit();
    }

    /**
     * 删除存入的Token
     *
     * @param context
     */
    public static void deleteCachedToken(Context context) {
        context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit().remove(Config.TOKEN_KEY).commit();
    }

    /**
     * 保存从服务器上获得的短信验证码
     *
     * @param code
     */
    public static void savesmscachedcode(Context context, String code) {
        context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit().putString(Config.CODE_KEY, code).commit();

    }

    public static String getsmscachedcode(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(Config.CODE_KEY, null);

    }
}
