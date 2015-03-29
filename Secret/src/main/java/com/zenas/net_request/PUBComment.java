package com.zenas.net_request;

import com.zenas.main.Config;
import com.zenas.net_tools.ConnectNet;
import com.zenas.net_tools.HttpMethod;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 发布评论
 * Created by Administrator on 2015/3/25.
 */
public class PUBComment {

    public PUBComment(String phone_md5, String token, String content, int msgId, final SuccessCallback successCallback, final FailCallback failCallback) {
        new ConnectNet(Config.URL, HttpMethod.POST, new ConnectNet.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                if (successCallback != null) {
                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(result);
                        successCallback.onSuccess(obj.getInt(Config.STATUS_KEY));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (failCallback != null) {
                            try {
                                failCallback.onFail(obj.getInt(Config.STATUS_KEY));
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
                , new ConnectNet.FailCallback()

        {
            @Override
            public void onfail() {
                if (failCallback != null) {
                    failCallback.onFail(Config.RESULT_STATUS_FAIL);
                }
            }
        }
                , Config.ACTION, Config.ACTION_PUBLISH_COMMENT, Config.MD5_KEY, phone_md5, Config.TOKEN_KEY, token, Config.PUB_COMMENT_CONTENT_KEY, content, Config.MSG_ID_KEY, msgId + "");

    }


    public static interface SuccessCallback {
        void onSuccess(int status);

    }

    public static interface FailCallback {
        void onFail(int error);

    }
}
