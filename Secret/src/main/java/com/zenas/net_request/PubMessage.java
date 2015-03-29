package com.zenas.net_request;

import com.zenas.main.Config;
import com.zenas.net_tools.ConnectNet;
import com.zenas.net_tools.HttpMethod;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 发送评论
 * Created by Administrator on 2015/3/24.
 */
public class PubMessage {
    public PubMessage(String phone_md5, String token, String msg, final SuccessCallback successCallback, final FailCallback failCallback) {
        new ConnectNet(Config.URL, HttpMethod.POST, new ConnectNet.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                if (successCallback != null) {
                    try {
                        JSONObject obj = new JSONObject(result);
                        switch (obj.getInt(Config.STATUS_KEY)) {
                            case Config.RESULT_STATUS_SUCCESS:
                                successCallback.onSuccess(obj.getInt(Config.STATUS_KEY));
                                break;
                            default:
                                if (failCallback != null) {
                                    failCallback.onFail();
                                }
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (failCallback != null) {
                            failCallback.onFail();
                        }
                    }
                }
            }
        }, new ConnectNet.FailCallback() {
            @Override
            public void onfail() {
                if (failCallback != null) {
                    failCallback.onFail();
                }

            }
        }, Config.ACTION, Config.ACTION_PUBULISH_MESSAGE, Config.MD5_KEY, phone_md5, Config.TOKEN_KEY, token, Config.MSG_KEY, msg);
    }

    public static interface SuccessCallback {
        void onSuccess(int status);
    }

    public static interface FailCallback {
        void onFail();
    }
}
