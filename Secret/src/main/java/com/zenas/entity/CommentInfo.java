package com.zenas.entity;

/**
 * Created by Administrator on 2015/3/24.
 */
public class CommentInfo {

    private String content;
    private String phone_md5;
    private String comment_time;

    public CommentInfo(String content, String phone_md5, String comment_time) {
        this.content = content;
        this.phone_md5 = phone_md5;
        this.comment_time = comment_time;
    }

    public String getComment_time() {
        return comment_time;
    }

    public void setComment_time(String comment_time) {
        this.comment_time = comment_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhone_md5() {
        return phone_md5;
    }

    public void setPhone_md5(String phone_md5) {
        this.phone_md5 = phone_md5;
    }
}
