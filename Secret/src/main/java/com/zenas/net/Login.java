package com.zenas.net;

import android.util.Log;

import com.zenas.main.Config;
import com.zenas.tools.MD5Tools;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 登陆
 * Created by Administrator on 2015/3/18.
 */
public class Login {
    /**
     * 传入手机加密MD5及短信验证码
     *
     * @param phonemd5
     * @param code
     * @param successcallback
     * @param failcallback
     */
    public Login(String phonemd5, String code, final Successcallback successcallback, final Failcallback failcallback) {

        new ConnectNet(Config.URL, HttpMethod.POST, new ConnectNet.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    Log.d("test","login"+json.toString());
                    switch (json.getInt(Config.STATUS_KEY)) {
                        case Config.RESULT_STATUS_SUCCESS:
                            if (successcallback != null) {
                                successcallback.onSuccess(json.getString(Config.TOKEN_KEY));
                            }
                            break;
                        default:
                            if (failcallback != null) {
                                failcallback.onFail();
                            }
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (failcallback != null) {
                        failcallback.onFail();
                    }
                }
            }
        }, new ConnectNet.FailCallback() {
            @Override
            public void onfail() {
                if (failcallback != null) {
                    onfail();
                }

            }
        }, Config.ACTION_KEY, Config.ACTION_LOGIN, Config.MD5_KEY, phonemd5, Config.CODE_KEY, code);//传入M5手机号和短信验证码


    }




    public static interface Successcallback {
        void onSuccess(String token);
    }

    public static interface Failcallback {
        void onFail();
    }
}
