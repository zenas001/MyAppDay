package com.zenas.acivitys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zenas.localdata.Contacts;
import com.zenas.localdata.GetLocalNumber;
import com.zenas.main.Config;
import com.zenas.main.R;
import com.zenas.net.GetSmsCode;
import com.zenas.net.Login;
import com.zenas.net.UpLoadContacts;
import com.zenas.tools.MD5Tools;

/**
 * 登陆界面
 * Created by Administrator on 2015/3/16.
 */
public class AtyLogin extends Activity {

    private ImageView serverstatus;
    private TextView resultcode;
    /*是否上传通讯录数据*/
//    private boolean updata_is=true;
    private EditText shortmessagecode = null;
    private EditText inputsmscode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_login);
        shortmessagecode = (EditText) findViewById(R.id.phonenumber);
        serverstatus = (ImageView) findViewById(R.id.statuscode);
        resultcode = (TextView) findViewById(R.id.resultcode);
        inputsmscode = (EditText) findViewById(R.id.inputcode);

/*
*
* 启动到登陆界面首先验证是否扫描上传了通信录数据
* */
        final ProgressDialog progressDialog = ProgressDialog.show(this, getResources().getString(R.string.contacts_title), getResources().getString(R.string.contacts_ms));
        Toast.makeText(this, "Welcome1!", Toast.LENGTH_LONG).show();
//        if (updata_is) {
            Toast.makeText(this, "Welcome2!", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
//        } else {
            Toast.makeText(this, "Welcome3!", Toast.LENGTH_LONG).show();
            //获取本机通信录数据
            String contacts = new Contacts().getLocalContactsJson(this);
            //获取本机号码
            String localphonenum = new GetLocalNumber().GetLocalNumber(AtyLogin.this);
            if (contacts.isEmpty()) {
                Toast.makeText(this, "No Found Contacts!", Toast.LENGTH_LONG).show();
            }
            new UpLoadContacts(MD5Tools.getMD5String(localphonenum), Config.getCachedToken(AtyLogin.this), contacts, new UpLoadContacts.SuccessfulCallBack() {
                @Override
                public void onSuccess() {
                    //上传成功
                    serverstatus.setImageResource(R.drawable.on);
//                    updata_is = false;
                    Toast.makeText(AtyLogin.this, "UpLoad Successful Contacts!", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }, new UpLoadContacts.FailCallBack() {
                @Override
                public void onFail() {
                    serverstatus.setImageResource(R.drawable.off);
                    Toast.makeText(AtyLogin.this, "No Found Contacts!", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            });
//        }

    /*
    *
    *
    * 获取当前连接状态以及短信验证码
    * */
        findViewById(R.id.getcode).setOnClickListener(new View.OnClickListener() {
                                                          @Override
                                                          public void onClick(View v) {
//                判断当前电话号码输入框是否为null
                                                              if (TextUtils.isEmpty(shortmessagecode.getText())) {
                                                                  Toast.makeText(AtyLogin.this, R.string.phone_number_not_null, Toast.LENGTH_LONG).show();
                                                                  return;
                                                              } else if (!(Config.getsmscachedcode(AtyLogin.this) == null)) {
                                                                  resultcode.setText("");
                                                                  resultcode.setText(Config.getsmscachedcode(AtyLogin.this));
                                                                  Toast.makeText(AtyLogin.this, "SMScode is have!", Toast.LENGTH_LONG).show();
                                                                  return;
                                                              }
                                                              //直接显示进度弹窗，注意final是不允许修改的，所以不能new
                                                              final ProgressDialog progressdialog = ProgressDialog.show(AtyLogin.this, getResources().getString(R.string.progress_sms_title), getResources().getString(R.string.progress_sms_test));
                                                              //传入电话号码及成功和失败的回调方法
                                                              new GetSmsCode(shortmessagecode.getText().toString(), new GetSmsCode.SuccessCallBack() {
                                                                  @Override
                                                                  public void onSuccess(int code) {
                                                                      //将从服务上获取到验证码缓存
                                                                      Config.savesmscachedcode(AtyLogin.this, String.valueOf(code));
                                                                      //显示服务器返回的短信验证吗
                                                                      resultcode.setText(code + "");
                                                                      progressdialog.dismiss();//关闭弹窗进度
                                                                      Toast.makeText(AtyLogin.this, R.string.result_sms_successful, Toast.LENGTH_LONG).show();
                                                                  }
                                                              }, new GetSmsCode.FailCallBack() {
                                                                  @Override
                                                                  public void onFail() {
                                                                      //返回数据失败
                                                                      serverstatus.setImageResource(R.drawable.off);
                                                                      //屏蔽掉短信接收码
                                                                      resultcode.setText("");
                                                                      progressdialog.dismiss();//关闭弹窗进度
                                                                      Toast.makeText(AtyLogin.this, R.string.result_sms_fail, Toast.LENGTH_LONG).show();

                                                                  }
                                                              });
                                                          }
                                                      }

        );

    /*
    *
    *
    *
    * 登陆界面的
    * */
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            Log.d("test", "input" + inputsmscode.getText().toString());
                                                            Log.d("test", "result" + resultcode.getText().toString());
                                                            Log.d("test", "is" + !(inputsmscode.getText().toString().equals(resultcode.getText().toString())));
                                                            //判断当前短信输入框和号码输入框是否为空
                                                            if (TextUtils.isEmpty(inputsmscode.getText()) && TextUtils.isEmpty(shortmessagecode.getText()) || !(inputsmscode.getText().toString().equals(resultcode.getText().toString()))) {
                                                                Toast.makeText(AtyLogin.this, "Please Check Your Get short code and PhoneNum!", Toast.LENGTH_LONG).show();
                                                                return;
                                                            }
                                                            final ProgressDialog progressDialog = ProgressDialog.show(AtyLogin.this, getResources().getString(R.string.login_progress_title), getResources().getString(R.string.login_progress_text));
                                                            new Login(new MD5Tools().MD5Tools(shortmessagecode.getText().toString()), inputsmscode.getText().toString(), new Login.Successcallback() {
                                                                @Override
                                                                public void onSuccess(String token) {
//                        将从服务器上获取到Token保存到本地
                                                                    Config.saveCachedToken(AtyLogin.this, token);
                                                                    Intent intent = new Intent(AtyLogin.this, AtyTimeLine.class);
                                                                    intent.putExtra(Config.TOKEN_KEY, token);//将TOKEN及MD5电话号码传入下一个页面
                                                                    intent.putExtra(Config.MD5_KEY, new MD5Tools().MD5Tools(shortmessagecode.getText().toString()));
                                                                    progressDialog.dismiss();
                                                                    startActivity(intent);
                                                                }
                                                            }, new Login.Failcallback() {
                                                                @Override
                                                                public void onFail() {
                                                                    progressDialog.dismiss();
                                                                    Toast.makeText(AtyLogin.this, "Login Fail Please Cheack!", Toast.LENGTH_LONG).show();
                                                                }
                                                            });
                                                        }
                                                    }

        );

    }

}
