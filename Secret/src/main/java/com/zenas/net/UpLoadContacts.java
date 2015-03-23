package com.zenas.net;

import android.util.Log;

import com.zenas.main.Config;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Administrator on 2015/3/20.
 */
public class UpLoadContacts {

    public UpLoadContacts(String phonemd5, String token, String contacts, final SuccessfulCallBack successfulCallBack, final FailCallBack failCallBack) {
        new ConnectNet(Config.URL, HttpMethod.POST, new ConnectNet.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonobj = new JSONObject(result);
                    Log.d("test:", "UP1:" + jsonobj.getInt(Config.STATUS_KEY));
                    switch (jsonobj.getInt(Config.STATUS_KEY)) {
                        case Config.RESULT_STATUS_SUCCESS:
                            Log.d("test:", "UP2:" + jsonobj.getInt(Config.STATUS_KEY));
                            if (successfulCallBack != null) {
                                Log.d("test:", "UP2:" + jsonobj.getInt(Config.STATUS_KEY));
                                successfulCallBack.onSuccess();
                            }
                            break;
                        default:
                            if (failCallBack != null) {
                                failCallBack.onFail();
                            }
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (failCallBack != null) {
                        failCallBack.onFail();
                    }
                }
            }
        }, new ConnectNet.FailCallback() {
            @Override
            public void onfail() {
                if (failCallBack != null) {
                    failCallBack.onFail();
                }
            }
        }, Config.ACTION_KEY, Config.ACTION_UPLOAD_KEY, Config.MD5_KEY, phonemd5, Config.TOKEN_KEY, token, Config.UPLOAD_DATA_KEY, contacts);

    }

    public static interface SuccessfulCallBack {
        void onSuccess();
    }

    public static interface FailCallBack {
        void onFail();

    }

}
