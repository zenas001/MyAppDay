package com.zenas.tools;

/**
 * 建表语句
 * Created by Administrator on 2015/3/26.
 */
public class SQLCreateTable {
    /*此表中的数据，在选择是否为本地演示时，调用随机数生成存入*/
    public final static String TOKEN_TEBLE = "Create Table Token(cid integer primary key,token integer,code integer,status integer)";
    /*此表用于存入用户登陆电话号码，在存入号码之前先查询上表的code与请求的code是否一致*/
    public final static String USER_TEBLE = "Create Table User(uid integer primary key,phone text,code integer,status integer)";
    /*上传本地电话号码，存入之前查询TOKEN_TEBLE的TOKEN与USER_TABLE的phone是否一致，然后再插入*/
    public final static String CONTACT_NUMBER_TABLE = "Create Table Contact(ct_id integer primary key,contact text status integer)";
    /*朋友圈消息*/
    public final static String MSG_CONTENT = "Create Table Friend_Msg(msg_id integer primary key,content text,status integer)";
    /*获取评论*/
    public final static String GET_COMMENT="Create Table Comment(mo_id integer primary key,comment text,status integer)";
    /*发送消息*/
    public final static String PUB_MESSAGE="Create Table Pub_message(pub_msg_id integer primary key,msg_content text)";
    /*发表评论*/
    public final static String PUB_COMMENT="Create Table Pub_comment(pub_cm_id integer primary key,content text,msg_id integer,status integer)";

}

