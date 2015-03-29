package com.zenas.entity;

import android.media.MediaPlayer;

/**
 * Message实体
 * Created by Administrator on 2015/3/19.
 */
public class Message {
    private  String msg=null, phone_md5 = null, msg_time = null;
    private int msgid=0;
    public Message(int msgid, String msg, String phone_md5,String msg_time) {
        this.msgid = msgid;
        this.msg = msg;
        this.phone_md5 = phone_md5;
        this.msg_time = msg_time;
    }
    public String getMsg_time() {
        return msg_time;
    }

    public void setMsg_time(String msg_time) {
        this.msg_time = msg_time;
    }

    public int getMsgid() {
        return msgid;
    }

    public void setMsgid(int msgid) {
        this.msgid = msgid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPhone_md5() {
        return phone_md5;
    }

    public void setPhone_md5(String phone_md5) {
        this.phone_md5 = phone_md5;
    }
}
