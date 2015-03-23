package com.zenas.entity;

/**
 * Created by Administrator on 2015/3/19.
 */
public class MessageEntity {
    private int page;
    private int perpage;
    private String item;

    public MessageEntity(int page, int perpage, String item) {
        this.page = page;
        this.perpage = perpage;
        this.item = item;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerpage() {
        return perpage;
    }

    public void setPerpage(int perpage) {
        this.perpage = perpage;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
