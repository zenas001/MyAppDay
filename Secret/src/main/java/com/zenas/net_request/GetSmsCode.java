package com.zenas.net_request;

import com.zenas.main.Config;
import com.zenas.net_tools.ConnectNet;
import com.zenas.net_tools.HttpMethod;

import org.json.JSONObject;

/**
 * Created by Administrator on 2015/3/16.
 */
public class GetSmsCode {

    /**
     * 传入手机号，验证通信的是否成功，并返回短信验证码
     *
     * @param phone
     */
    public GetSmsCode(String phone, final SuccessCallBack successCallback, final FailCallBack failCallBack) {

        new ConnectNet(Config.URL, HttpMethod.POST, new ConnectNet.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
//                成功需要解码，服务端返回的是一个Json字符串
                try {
                    JSONObject jsonobj = new JSONObject(result);
                    //将返回的数据放回到一个的JSON字符数组里面
//                    JSONArray jsonarray = new JSONArray(result);
                    //JSON对象集合
//                    List<JSONObject> jslist = new ArrayList<JSONObject>();
                    //JSON对象申明
//                    JSONObject jsonobj = null;
                    //解析JOSON对象，并将JSON对象放于集合用于保存
//                    for (int i = 0; i < jsonarray.length(); i++) {
//                        jsonobj = (JSONObject) jsonarray.get(i);
//                        jslist.add(jsonobj);
//                        if (jsonarray.length() == 0) {
//                            //当解析完成后跳出次循环
//                            break;
//                        }
//                    }
//                    Iterator list = jslist.iterator();
//                    //遍历出JSON对象集合
//                    while (list.hasNext()) {
//                        JSONObject js1 = (JSONObject) list.next();
//                        Log.d("test", js1.toString());
//                        JSONObject js2 = (JSONObject) list.next();
//                        Log.d("test", js2.toString());
                    /*判断当前状态是否正确*/
                    switch (jsonobj.getInt(Config.STATUS_KEY)) {
                        case Config.RESULT_STATUS_SUCCESS:
                            //如果状态正确并传入获取到短信验证码
                            successCallback.onSuccess(jsonobj.getInt(Config.CODE_KEY));
                            break;
                        default:
                            //返回其他状态码都表示操作失败
                            if (failCallBack != null) {
                                failCallBack.onFail();
                            }
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
//                    捕捉到异常表示失败
                    if (failCallBack != null) {
                        failCallBack.onFail();
                    }
                } finally {

                }
            }
        }, new ConnectNet.FailCallback() {
            @Override
            public void onfail() {
                if (failCallBack != null) {//判断是否访问失败
                    failCallBack.onFail();
                }
            }
        }, Config.ACTION, Config.ACTION_GET_CODE, Config.PHONE_NUM_KEY, phone
        );
    }


    /**
     * 以下留的接口用于回调验证是否成功
     */
    public static interface SuccessCallBack {
        void onSuccess(int code);
    }

    public static interface FailCallBack {
        void onFail();
    }
}

