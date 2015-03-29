package com.zenas.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Button;

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
    /*上传数据KEY*/
    public static final String UPLOAD_DATA_KEY = "contacts";
    /*MD5手机号KEY*/
    public static final String MD5_KEY = "phone_md5";
    /*页面码*/
    public static final String PAGE_KEY = "page";
    /*当前页面显示数量*/
    public static final String PERPAGE_KEY = "perpage";
    /*联系人姓名KEY*/
    //   public static final String PERSON_NAME_KEY="person_name";
    /*电话号码KEY*/
    public static final String PHONE_NUM_KEY = "phone";
    /*返回的数据KEY*/
    public static final String ITEMLINE_KEY = "items";
    /*短信验证码标识KEY*/
    public static final String CODE_KEY = "code";
    /*状态码标识*/
    public static final String STATUS_KEY = "status";
    /*返回的消息id*/
    public static final String MSG_ID_KEY = "msgId";
    /*获取评论KEY*/
    public static final String COMMENT_ITEM = "items";
    /*朋友圈消息发布时间*/
    public static final String PUB_TIME_KEY = "pub_time";
    /*发布评论的时间*/
    public static final String PUB_COMMENT_TIME_KEY = "comment_pub_time";
    /*发布评论内容*/
    public static final String PUB_COMMENT_CONTENT_KEY = "content";
    /*返回的消息内容*/
    public static final String MSG_KEY = "msg";
    /**
     * 以下是action
     */
    /*动作标识*/
    public final static String ACTION = "action";
    /*上次数据标识*/
    public static final String ACTION_UPLOAD = "upload_contacts";
    /*获取朋友消息*/
    public static final String ACTION_GET_MESSAGE = "timeline";
    /*登陆*/
    public static final String ACTION_LOGIN = "login";
    /*获取短信验证码标识*/
    public static final String ACTION_GET_CODE = "send_pass";
    /*获取评论标识*/
    public static final String ACTION_GET_COMMENT = "get_comment";
    /*发布消息*/
    public static final String ACTION_PUBULISH_MESSAGE = "publish";
    /*发布评论*/
    public static final String ACTION_PUBLISH_COMMENT = "pub_comment";
    /**
     * NET通信
     */
    /*服务器地址*/
//    public static final String URL = "http://demo.eoeschool.com/api/v1/nimings/io";
    /*本地测试地址*/
    public static final String URL = "http://192.168.1.101:8080/TestServer/api.jsp";
    /*APP_ID*/
    public final static String APP_ID = "com.zenas.secret";
    /*编码*/
    public static final String CHARSET = "utf-8";
    /*服务器返回的状态码，1：成功，0：失败，2：失效*/
    public static final int RESULT_STATUS_SUCCESS = 1;
    public static final int RESULT_STATUS_FAIL = 0;
    public static final int RESULT_STATUS_INVALID_TOKEN = 2;
    public static final int RESULT_MS = 1;
    /**
     * 本地SQLite配置
     */
    /*是否启用标识*/
    public static final String IS_LOCAL = "islocal";


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

    /**
     * 获取本地短信验证码
     *
     * @param context
     * @return
     */
    public static String getsmscachedcode(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(Config.CODE_KEY, null);

    }

    /**
     * 删除存入的本地短信验证码
     *
     * @param context
     */
    public static void deleteCachedcode(Context context) {
        context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit().remove(Config.CODE_KEY).commit();
    }

}
