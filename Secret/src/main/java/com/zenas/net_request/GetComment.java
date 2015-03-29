package com.zenas.net_request;

import com.zenas.entity.CommentInfo;
import com.zenas.main.Config;
import com.zenas.net_tools.ConnectNet;
import com.zenas.net_tools.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取评论
 * Created by Administrator on 2015/3/24.
 */
public class GetComment {
    public GetComment(String phone_md5, String token, final SuccessCallback successCallback, final FailCallback failCallback, int page, int perpage, int msgid) {
        new ConnectNet(Config.URL, HttpMethod.POST, new ConnectNet.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    switch (obj.getInt(Config.STATUS_KEY)) {
                        case Config.RESULT_STATUS_SUCCESS:
                            if (successCallback != null) {
                                List<CommentInfo> items = new ArrayList<CommentInfo>();
                                JSONArray jsonArray = obj.getJSONArray(Config.COMMENT_ITEM);
                                JSONObject object;
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    object = jsonArray.getJSONObject(i);
                                    //格式化时间戳
                                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    String pub_time = format.format(object.getLong(Config.PUB_COMMENT_TIME_KEY));
                                    //构建数据实体，传入集合
                                    items.add(new CommentInfo(object.getString(Config.PUB_COMMENT_CONTENT_KEY), object.getString(Config.MD5_KEY), pub_time));
                                }
                                successCallback.onSuccess(obj.getInt(Config.PAGE_KEY), obj.getInt(Config.PERPAGE_KEY), items, obj.getInt(Config.MSG_ID_KEY));
                            }
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
        }, new ConnectNet.FailCallback() {
            @Override
            public void onfail() {
                if (failCallback != null) {
                    failCallback.onFail();
                }

            }
        }, Config.ACTION, Config.ACTION_GET_COMMENT, Config.MD5_KEY, phone_md5, Config.TOKEN_KEY, token, Config.PAGE_KEY, page + "", Config.PERPAGE_KEY, perpage + "", Config.MSG_ID_KEY, msgid + "");


    }

    public static interface SuccessCallback {
        void onSuccess(int page, int perpage, List<CommentInfo> items, int msgid);
    }

    public static interface FailCallback {
        void onFail();
    }
}
