package com.zenas.acivitys;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zenas.net_request.*;

import com.zenas.entity.CommentInfo;
import com.zenas.main.CommentListAdapter;
import com.zenas.main.Config;
import com.zenas.main.R;
import com.zenas.net_request.GetComment;

import java.util.List;

/**
 * 查看并发布评论
 * Created by Administrator on 2015/3/16.
 */
public class AtyCommentDetails extends ListActivity {

    private CommentListAdapter adapter;
    private String phone_md5, token;
    private int msgid;
    private EditText pubomment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_comment_show);
        phone_md5 = getIntent().getExtras().getString(Config.MD5_KEY);
        token = getIntent().getExtras().getString(Config.TOKEN_KEY);
        msgid = getIntent().getExtras().getInt(Config.MSG_ID_KEY);
        //将传过来的消息内容呈现
        TextView msv = (TextView) findViewById(R.id.tvmessage);
        msv.setText(getIntent().getExtras().getString(Config.MSG_KEY));
        //时间
        TextView mstv = (TextView) findViewById(R.id.comment_time_detail);
        mstv.setText(getIntent().getExtras().getString(Config.PUB_TIME_KEY));
        //评论编辑框
        pubomment = (EditText) findViewById(R.id.edit_comment);

        //实例化适配器
        adapter = new CommentListAdapter(this);
        //将内容传入适配器
        setListAdapter(adapter);

        //发布评论监听
        findViewById(R.id.pubcomment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PUBComment(phone_md5, token, pubomment.getText().toString(), msgid, new PUBComment.SuccessCallback() {
                    @Override
                    public void onSuccess(int status) {
                        switch (status) {
                            case Config.RESULT_STATUS_SUCCESS:
                                Toast.makeText(AtyCommentDetails.this, "PUBLISH COMMENT Successful!", Toast.LENGTH_LONG).show();
                            case Config.RESULT_STATUS_FAIL:
                                Toast.makeText(AtyCommentDetails.this, "PUBLISH COMMENT  Fail!", Toast.LENGTH_LONG).show();
                            case Config.RESULT_STATUS_INVALID_TOKEN:
                                Toast.makeText(AtyCommentDetails.this, "PUBLISH COMMENT TOKEN error!", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new PUBComment.FailCallback() {
                    @Override
                    public void onFail(int error) {
                        Toast.makeText(AtyCommentDetails.this, "PUBLISH COMMENT TOKEN error!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        final ProgressDialog dialog = ProgressDialog.show(AtyCommentDetails.this, getResources().getString(R.string.login_progress_title), getResources().getString(R.string.login_progress_text));

        new GetComment(phone_md5, token, new GetComment.SuccessCallback() {
            @Override
            public void onSuccess(int page, int perpage, List<CommentInfo> items, int msgid) {
                adapter.addAll(items);
                dialog.dismiss();
            }
        }, new GetComment.FailCallback() {
            @Override
            public void onFail() {
                Toast.makeText(AtyCommentDetails.this, "获取评论失败！", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }, 1, 20, msgid);

    }
}
