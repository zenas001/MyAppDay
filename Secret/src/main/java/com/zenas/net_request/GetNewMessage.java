package com.zenas.net_request;

import android.util.Log;

import com.zenas.entity.Message;
import com.zenas.main.Config;
import com.zenas.net_tools.ConnectNet;
import com.zenas.net_tools.HttpMethod;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取朋友圈消息
 * Created by Administrator on 2015/3/23.
 */
public class GetNewMessage {


    public GetNewMessage(String phonemd5, String token, final SuccessCallback succesCallback, final FailCallback failCallback, int page, int perpage) {
        new ConnectNet(Config.URL, HttpMethod.POST, new ConnectNet.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    switch (obj.getInt(Config.STATUS_KEY)) {
                        case Config.RESULT_STATUS_SUCCESS:
                            if (succesCallback != null) {
//                                直接遍历出来放到集合中
                                List<Message> messages = new ArrayList<>();
                                JSONArray jsonArray = obj.getJSONArray(Config.ITEMLINE_KEY);
                                JSONObject object;
                                for (int i = 0; i < jsonArray.length(); i++) {
//                                   遍历出JSON对象
                                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    String time = format.format(obj.getLong(Config.PUB_TIME_KEY));
                                    Log.d("test", "time::" + time);
                                    object = jsonArray.getJSONObject(i);
                                    /*构建实体对象，并传入集合中*/
                                    messages.add(new Message(Integer.parseInt(object.getString(Config.MSG_ID_KEY)), object.getString(Config.MSG_KEY), object.getString(Config.MD5_KEY), time));
                                    //转换时间
                                    succesCallback.onSuccess(obj.getInt(Config.PAGE_KEY), obj.getInt(Config.PERPAGE_KEY), messages);
                                }
                            }
                            break;
                        default:
                            if (failCallback != null) {
                                failCallback.onFail();
                                Log.d("test", "e");
                            }
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (failCallback != null) {
                        failCallback.onFail();
                        Log.d("test", "e2");
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
        }, Config.ACTION, Config.ACTION_GET_MESSAGE, Config.MD5_KEY, phonemd5, Config.TOKEN_KEY, token, Config.PAGE_KEY, page + "", Config.PERPAGE_KEY, perpage + "");
    }

    public static interface SuccessCallback {
        void onSuccess(int page, int perpage, List<Message> messageList);
    }

    public static interface FailCallback {
        void onFail();
    }
}
