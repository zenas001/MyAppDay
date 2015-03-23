package com.zenas.acivitys;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.zenas.main.Config;
import com.zenas.main.R;

/**
 * 消息列表界面
 * Created by Administrator on 2015/3/16.
 */
public class AtyTimeLine extends Activity {
    private String token = null;
    private String md5num = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_timeline);
        token =getIntent().getExtras().getString(Config.TOKEN_KEY);
        md5num = (String) getIntent().getExtras().getString(Config.MD5_KEY);
        Log.d("test", "POST:::::::::::" + token);
        Log.d("test", "POST:::::::::::" + md5num);
    }
}
