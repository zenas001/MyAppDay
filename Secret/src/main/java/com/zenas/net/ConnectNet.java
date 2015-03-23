package com.zenas.net;

import android.os.AsyncTask;
import android.util.Log;

import com.zenas.main.Config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Http通信工具类
 * Created by Administrator on 2015/3/16.
 */
public class ConnectNet {
    /**
     * @param url:通信地址
     * @param method：通信方式
     * @param successCallback：成功回掉
     * @param failCallback：失败
     * @param kvs：通信参数，参数对
     */
    public ConnectNet(final String url, final HttpMethod method, final SuccessCallback successCallback, final FailCallback failCallback, final String... kvs) {
//异步任务完成网络访问,ANR即程序没有的响应，所以耗时操作都放在异步任务（AysncTask）执行，它会单独开辟一个线程
        new AsyncTask<Void, Void, String>() {
            protected String doInBackground(Void... params) {
//                配置上传参数对，StringBuffer可变字符串常量
                StringBuffer reuqstparams = new StringBuffer();
//                生成参数
                for (int i = 0; i < kvs.length; i += 2) {//？后开始计算请求参数
                    reuqstparams.append(kvs[i]).append("=").append(kvs[i + 1]).append("&");//拼接字符串流通信请求参数
                }
                try {
//                    获取URL连接
                    URLConnection uc;
                    /*判断使用哪种通信方式*/
                    switch (method) {
                        case POST:
                            uc = new URL(url).openConnection();
                            uc.setDoOutput(true);//往服务器端写入，因为POST是以流的方式写入
//                            获取到对服务端字节输出流，并制定编码格式，打开字符写入转换为字节输出流
                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(uc.getOutputStream(), Config.CHARSET));
//                            将请求参数对写入服务器
                            writer.write(reuqstparams.toString());
//                           写入
                            writer.flush();
                            Log.d("test", "connect,thread---------" + Thread.currentThread().getName());//在DoInBackground里面单独开辟一个线程
                            break;

                        default:
//                            采用GET方式是将请求地址及请求参数拼接后，打开连接获取到URLConnection()连接对象
                            uc = new URL(url + "?" + reuqstparams.toString()).openConnection();
                            break;
                    }
                    Log.d("test", "Request url:" + uc.getURL().toString());//打印出具体通信的地址调试用
                    Log.d("test", "Request data" + reuqstparams.toString());//打印出请求参数调试用

//                    读取服务端返回的数据，将使用InputStreamReader,将字节流读取转换为字符流，并传入编码方式
                    BufferedReader reader = new BufferedReader(new InputStreamReader(uc.getInputStream(), Config.CHARSET));
//                   使用String赋值
                    String line = null;
//                    用StringBuffer装读取到的数据，StringBuffer是可变长的字符串，因为在实时增加
                    StringBuffer result = new StringBuffer();
//                   循环读取
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    Log.d("test", "Result:" + result.toString());
//然后将解析后的数据进行返回给onPostExecute()进行判断服务器返回的通信状态码
                    return result.toString();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            /**
             * 判断dobackground()方法返回值
             * @param result
             */
            @Override
            protected void onPostExecute(String result) {
//                判断是否通信成功
                if (result != null) {
//                    Log.d("test", "connectthread---------" + Thread.currentThread().getName());
                    if (successCallback != null) {
                        successCallback.onSuccess(result);
                    }
                } else {
                    if (failCallback != null) {
                        failCallback.onfail();
                    }
                }
            }
        }.execute();
    }

    /**
     * 成功回调
     */
    public static interface SuccessCallback {
        void onSuccess(String result);
    }

    /**
     * 回调失败
     */
    public static interface FailCallback {
        void onfail();
    }

}
