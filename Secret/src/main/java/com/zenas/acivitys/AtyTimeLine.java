package com.zenas.acivitys;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.zenas.entity.Message;
import com.zenas.localdata.Contacts;
import com.zenas.main.Config;
import com.zenas.main.MessageListItemAdpater;
import com.zenas.main.R;
import com.zenas.net_request.GetNewMessage;
import com.zenas.net_request.UpLoadContacts;

import java.util.List;

/**
 * 消息列表界面
 * Created by Administrator on 2015/3/16.
 */
public class AtyTimeLine extends ListActivity {
    private String token, md5num;
    private Boolean IsLocal;//是否本地演示标识
    private MessageListItemAdpater adpater = null;//数据适配器

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_timeline);
        token = getIntent().getExtras().getString(Config.TOKEN_KEY);
        md5num = getIntent().getExtras().getString(Config.MD5_KEY);
        IsLocal = getIntent().getExtras().getBoolean(Config.IS_LOCAL);
        adpater = new MessageListItemAdpater(this);//实例化适配器
        setListAdapter(adpater);
        ListView listView = getListView();

         /*
         * Item项监听器
         * */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AtyTimeLine.this, AtyCommentDetails.class);
                //传入需要的参数
                intent.putExtra(Config.MD5_KEY, adpater.getItem(position).getPhone_md5());
                intent.putExtra(Config.TOKEN_KEY, Config.getCachedToken(AtyTimeLine.this));
                intent.putExtra(Config.MSG_ID_KEY, adpater.getItem(position).getMsgid());
                intent.putExtra(Config.PUB_TIME_KEY, adpater.getItem(position).getMsg_time());
                intent.putExtra(Config.MSG_KEY, adpater.getItem(position).getMsg());
                startActivity(intent);
            }
        });
         /*
         *
         * 发布消息按钮监听器
         * */
        findViewById(R.id.sendmeassge).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AtyTimeLine.this, AtyPubMessage.class);
                intent.putExtra(Config.MD5_KEY, md5num);
                intent.putExtra(Config.TOKEN_KEY, token);
                startActivity(intent);
            }
        });


//        final ProgressDialog progressDialog = ProgressDialog.show(AtyTimeLine.this, getResources().getString(R.string.connect_title), getString(R.string.connect_ms));
        if (IsLocal) {
            Log.d("test", "sucees");
        } else {
//上传手机联系人数据
            new UpLoadContacts(md5num, token, new Contacts().getLocalContactsJson(AtyTimeLine.this), new UpLoadContacts.SuccessfulCallBack() {
                @Override
                public void onSuccess() {
//                progressDialog.dismiss();
                    //成功上传通讯录联系人后，调用加载朋友圈消息
                    LoadMessage();
                }
            }, new UpLoadContacts.FailCallBack() {
                @Override
                public void onFail(int error) {
//                progressDialog.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(AtyTimeLine.this).setTitle("提示");
                    switch (error) {
                        //如果Token过期失效或通信失败，重新登陆
                        case Config.RESULT_STATUS_INVALID_TOKEN:
                            builder.setMessage("TOKEN失效，请重新登陆！");
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(AtyTimeLine.this, AtyLogin.class));
                                    finish();
                                }
                            }).show();
                            break;
                        case Config.RESULT_STATUS_FAIL:
                            builder.setMessage("与服务器通信中断！请重新登陆！");
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(AtyTimeLine.this, AtyLogin.class));
                                    finish();
                                }
                            }).show();
                        default:
                            finish();
                            break;

                    }
                }
            });
        }
    }

    /**
     * 朋友圈消息加载
     */
    private void LoadMessage() {
        final ProgressDialog progressDialog = ProgressDialog.show(AtyTimeLine.this, getResources().getString(R.string.connect_title), getString(R.string.connect_ms));
        if (IsLocal) {
//            adpater.addAll();
        } else {
            new GetNewMessage(md5num, token, new GetNewMessage.SuccessCallback() {
                @Override
                public void onSuccess(int page, int perpage, List<Message> messageList) {
                    //将获取到数据，传入从服务器上获取到的数据
                    adpater.addAll(messageList);
                    progressDialog.dismiss();
                }
            }, new GetNewMessage.FailCallback() {
                @Override
                public void onFail() {
                    Toast.makeText(AtyTimeLine.this, "朋友圈数据加载失败！", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }, 1, 20);
        }

    }
}
