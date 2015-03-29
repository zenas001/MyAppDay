package com.zenas.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.zenas.acivitys.AtyLogin;
import com.zenas.acivitys.AtyTimeLine;
import com.zenas.localdata.Contacts;
import com.zenas.net_request.GetNewMessage;


/**
 * 入口判断TOKEN是否为null
 */
public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Config.deleteCachedToken(this);//默认将TOKEN删除以便测试





//        判断本地Token是否为null,以便跳转校验
        String token = Config.getCachedToken(this);
        if (token != null) {
            Log.d("test", "Token in validity!");
//            启动登陆后的数据呈现页
            Intent intent = new Intent(this, AtyTimeLine.class);
//           下面存入token,需要访问服务器数据使用
            intent.putExtra(Config.TOKEN_KEY, token);
            startActivity(intent);
            finish();
        } else {
//            启动登录界面
            Log.d("test", "Token not in validity!");
            startActivity(new Intent(this, AtyLogin.class));
            finish();
        }
    }
}
