package com.zenas.acivitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zenas.main.Config;
import com.zenas.main.R;
import com.zenas.net_request.GetSmsCode;
import com.zenas.net_request.Login;
import com.zenas.tools.MD5Tools;
import com.zenas.tools.RandomSmsCheck;
import com.zenas.tools.SqltieTools;

import java.lang.annotation.Target;

/**
 * 登陆界面
 * Created by Administrator on 2015/3/16.
 */
public class AtyLogin extends Activity {

    private ImageView server_status;
    //验证码
    private TextView result_code;
    /*是否上传通讯录数据*/
    //private boolean updata_is=true;
    private EditText phone_number = null;
    private EditText input_smscode = null;
    /*
    * 调用本地数据的参数
    * */
    //是否启动本地演示
    private Boolean IsLocal = false;
    //获取数据库操作工具类实例
    private SqltieTools sqLiteDatabase = null;
    //token
    private int ltoken = 0;
    //code
    private int lcode = 0;
    //status
    private int lstatus = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_login);
        phone_number = (EditText) findViewById(R.id.phonenumber);
        server_status = (ImageView) findViewById(R.id.statuscode);
        result_code = (TextView) findViewById(R.id.resultcode);
        input_smscode = (EditText) findViewById(R.id.inputcode);

//    判断是否启用本地SQLite演示
        final AlertDialog.Builder builder = new AlertDialog.Builder(AtyLogin.this).setTitle("演示提醒").setMessage("是否启用本地SQL数据库演示？");

        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                IsLocal = true;

//                Config.deleteCachedToken(AtyLogin.this);
//                Config.deleteCachedcode(AtyLogin.this);
                sqLiteDatabase = new SqltieTools(AtyLogin.this, "LocalBase.db", null, 1);
                sqLiteDatabase.getWritableDatabase();//实例化后需要获取到写入方法才能创建表
                //判断是否有缓存
                if (Config.getsmscachedcode(AtyLogin.this) == null && Config.getCachedToken(AtyLogin.this) == null) {
                    Config.saveCachedToken(AtyLogin.this, String.valueOf(new RandomSmsCheck().RandomSmsCheck()));
                    Config.savesmscachedcode(AtyLogin.this, String.valueOf(new RandomSmsCheck().RandomSmsCheck()));
                    sqLiteDatabase.getWritableDatabase().execSQL("insert into Token(token,code,status)VALUES(" + Integer.parseInt(Config.getCachedToken(AtyLogin.this)) + "," + Integer.parseInt(Config.getsmscachedcode(AtyLogin.this)) + ",1)");
                } else {//否则不重新生成
                    Log.d("test", "is code cached!");
                }
            }
        });

        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();

    /*
    *
    *
    * 获取当前连接状态以及短信验证码
    * */
        findViewById(R.id.getcode).setOnClickListener(new View.OnClickListener() {
                                                          @Override
                                                          public void onClick(View v) { //直接显示进度弹窗，注意final是不允许修改的，所以不能new
                                                              final ProgressDialog progressdialog = ProgressDialog.show(AtyLogin.this, getResources().getString(R.string.progress_sms_title), getResources().getString(R.string.progress_sms_test));
                                                              if (TextUtils.isEmpty(phone_number.getText()) & !(Config.getsmscachedcode(AtyLogin.this) == null)) {
                                                                  Toast.makeText(AtyLogin.this, R.string.phone_number_not_null, Toast.LENGTH_LONG).show();
                                                                  progressdialog.dismiss();
                                                                  return;
                                                              }
                                                              if (IsLocal) {//获取数据库中之前写入的TOKEN值
                                                                  try {
                                                                      Cursor cursor = sqLiteDatabase.getReadableDatabase().rawQuery("select * from Token", null);
                                                                      Cursor md5 = sqLiteDatabase.getReadableDatabase().rawQuery("select * from User where phone=?", new String[]{MD5Tools.getMD5String(phone_number.getText().toString())});
                                                                      if (cursor.moveToLast()) {
                                                                          ltoken = cursor.getInt(cursor.getColumnIndex("token"));
                                                                          lcode = cursor.getInt(cursor.getColumnIndex("code"));
                                                                          lstatus = cursor.getInt(cursor.getColumnIndex("status"));

                                                                          switch (lstatus) {
                                                                              case Config.RESULT_STATUS_SUCCESS:
                                                                                  server_status.setImageResource(R.drawable.on);
                                                                                  result_code.setText(lcode + "");
                                                                                  progressdialog.dismiss();
                                                                                  //这里做个校验避免重复插入数据库
                                                                                  try {
                                                                                      if (!(md5.moveToLast())) {
                                                                                              //注意这里存入的MD5加密需要转义"后才能存入数据库
                                                                                              sqLiteDatabase.getWritableDatabase().execSQL("insert into User(phone,code,status)VALUES(\"" + MD5Tools.getMD5String(phone_number.getText().toString()) + "\"," + lcode + ",1)");
                                                                                              progressdialog.dismiss();
                                                                                          } else {
                                                                                              Log.d("test", "is Save");
                                                                                              server_status.setImageResource(R.drawable.on);
                                                                                              result_code.setText(lcode + "");
                                                                                              progressdialog.dismiss();
                                                                                          }
                                                                                  } catch
                                                                                          (SQLException e) {
                                                                                      e.printStackTrace();
                                                                                  }
                                                                                  break;
                                                                              default:
                                                                                  server_status.setImageResource(R.drawable.off);
                                                                                  progressdialog.dismiss();
                                                                                  break;
                                                                          }
                                                                          cursor.close();
                                                                      }
                                                                  } catch (Exception e) {
                                                                      e.printStackTrace();
                                                                  }
                                                              } else {
//                判断当前电话号码输入框是否为null
                                                                  //传入电话号码及成功和失败的回调方法
                                                                  new GetSmsCode(phone_number.getText().toString(), new GetSmsCode.SuccessCallBack() {
                                                                      @Override
                                                                      public void onSuccess(int code) {
                                                                          //将从服务上获取到验证码缓存
                                                                          Config.savesmscachedcode(AtyLogin.this, String.valueOf(code));
                                                                          //显示服务器返回的短信验证吗
                                                                          result_code.setText(code + "");
                                                                          progressdialog.dismiss();//关闭弹窗进度
                                                                          Toast.makeText(AtyLogin.this, R.string.result_sms_successful, Toast.LENGTH_LONG).show();
                                                                      }
                                                                  }, new GetSmsCode.FailCallBack() {
                                                                      @Override
                                                                      public void onFail() {
                                                                          //返回数据失败
                                                                          server_status.setImageResource(R.drawable.off);
                                                                          //屏蔽掉短信接收码
                                                                          result_code.setText("");
                                                                          progressdialog.dismiss();//关闭弹窗进度
                                                                          Toast.makeText(AtyLogin.this, R.string.result_sms_fail, Toast.LENGTH_LONG).show();

                                                                      }
                                                                  });
                                                              }
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
                                                            //判断当前短信输入框和号码输入框是否为空
                                                            if (TextUtils.isEmpty(input_smscode.getText()) && TextUtils.isEmpty(phone_number.getText()) || !(input_smscode.getText().toString().equals(result_code.getText().toString()))) {
                                                                Toast.makeText(AtyLogin.this, "Please Check Your Get short code and PhoneNum!", Toast.LENGTH_LONG).show();
                                                                return;
                                                            }
                                                            final ProgressDialog progressDialog = ProgressDialog.show(AtyLogin.this, getResources().getString(R.string.login_progress_title), getResources().getString(R.string.login_progress_text));
                                                            if (IsLocal) {//是否本地
                                                                Cursor cursor = sqLiteDatabase.getReadableDatabase().rawQuery("select * from User", null);
                                                                if (cursor.moveToLast()) {
                                                                    if (Integer.parseInt(input_smscode.getText().toString()) == cursor.getInt(cursor.getColumnIndex("code")) && MD5Tools.getMD5String(phone_number.getText().toString()).equals(cursor.getString(cursor.getColumnIndex("phone")))) {
                                                                        Intent intent = new Intent(AtyLogin.this, AtyTimeLine.class);
                                                                        intent.putExtra(Config.IS_LOCAL, IsLocal);//传入是否启用本地演示标识
                                                                        intent.putExtra(Config.TOKEN_KEY, String.valueOf(ltoken));//将TOKEN及MD5电话号码传入下一个页面
                                                                        intent.putExtra(Config.MD5_KEY, new MD5Tools().MD5Tools(phone_number.getText().toString()));
                                                                        progressDialog.dismiss();
                                                                        startActivity(intent);
                                                                        sqLiteDatabase.close();
                                                                        finish();
                                                                    } else {
                                                                        Toast.makeText(AtyLogin.this, "No query to the relevant information!", Toast.LENGTH_LONG).show();
                                                                        progressDialog.dismiss();
                                                                    }
                                                                }

                                                            } else {
                                                                new Login(new MD5Tools().MD5Tools(phone_number.getText().toString()), input_smscode.getText().toString(), new Login.Successcallback() {
                                                                    @Override
                                                                    public void onSuccess(String token) {
//                        将从服务器上获取到Token保存到本地
                                                                        Config.saveCachedToken(AtyLogin.this, token);
                                                                        Intent intent = new Intent(AtyLogin.this, AtyTimeLine.class);
                                                                        intent.putExtra(Config.TOKEN_KEY, token);//将TOKEN及MD5电话号码传入下一个页面
                                                                        intent.putExtra(Config.MD5_KEY, new MD5Tools().MD5Tools(phone_number.getText().toString()));
                                                                        progressDialog.dismiss();
                                                                        startActivity(intent);
                                                                        finish();
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
                                                    }

        );

    }

}
