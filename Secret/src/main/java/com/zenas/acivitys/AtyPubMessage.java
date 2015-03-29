package com.zenas.acivitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zenas.net_request.*;

import com.zenas.main.Config;
import com.zenas.main.R;

/**
 * 消息发布界面
 * Created by Administrator on 2015/3/16.
 */
public class AtyPubMessage extends Activity {
    private String phone_md5;
    private String token;
    private TextView pubmsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_pubmessage);
        phone_md5 = getIntent().getExtras().getString(Config.MD5_KEY);
        token = getIntent().getExtras().getString(Config.TOKEN_KEY);
        pubmsg = (TextView) findViewById(R.id.pubmessage);

        findViewById(R.id.publish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PubMessage(phone_md5, token, pubmsg.getText().toString(), new PubMessage.SuccessCallback() {
                    @Override
                    public void onSuccess(int status) {
                        switch (status) {
                            case Config.RESULT_STATUS_SUCCESS:
                                Toast.makeText(AtyPubMessage.this
                                        , "Publish Message Successful!", Toast.LENGTH_LONG).show();
                                break;
                            case Config.RESULT_STATUS_FAIL:
                                Toast.makeText(AtyPubMessage.this, "Please Checkout network!", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new PubMessage.FailCallback() {
                    @Override
                    public void onFail() {
                        Toast.makeText(AtyPubMessage.this, "Please Checkout network!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }


}
