package com.zenas.net_request;

import com.zenas.main.Config;
import com.zenas.net_tools.ConnectNet;
import com.zenas.net_tools.HttpMethod;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * 上传联系人信息
 * Created by Administrator on 2015/3/20.
 */
public class UpLoadContacts {

    public UpLoadContacts(String phonemd5, String token, String contacts, final SuccessfulCallBack successfulCallBack, final FailCallBack failCallBack) {
        new ConnectNet(Config.URL, HttpMethod.POST, new ConnectNet.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonobj = new JSONObject(result);
                    switch (jsonobj.getInt(Config.STATUS_KEY)) {
                        case Config.RESULT_STATUS_SUCCESS:
                            if (successfulCallBack != null) {
                                successfulCallBack.onSuccess();
                            }
                            break;
                        case Config.RESULT_STATUS_INVALID_TOKEN:
                            if (failCallBack != null) {//如果Token失效
                                failCallBack.onFail(Config.RESULT_STATUS_INVALID_TOKEN);
                            }
                            break;
                        default:
                            if (failCallBack != null) {
                                failCallBack.onFail(Config.RESULT_STATUS_FAIL);
                            }
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (failCallBack != null) {
                        failCallBack.onFail(Config.RESULT_STATUS_FAIL);
                    }
                }
            }
        }, new ConnectNet.FailCallback() {
            @Override
            public void onfail() {
                if (failCallBack != null) {
                    failCallBack.onFail(Config.RESULT_STATUS_FAIL);
                }
            }
        }, Config.ACTION, Config.ACTION_UPLOAD, Config.MD5_KEY, phonemd5, Config.TOKEN_KEY, token, Config.UPLOAD_DATA_KEY, contacts);

    }

    public static interface SuccessfulCallBack {
        void onSuccess();
    }

    public static interface FailCallBack {
        void onFail(int error);

    }

}
