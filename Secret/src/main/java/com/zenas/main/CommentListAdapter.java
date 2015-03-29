package com.zenas.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zenas.entity.CommentInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * CommentAdapter
 * Created by Administrator on 2015/3/24.
 */
public class CommentListAdapter extends BaseAdapter {
    /*实体数据集合*/
    private List<CommentInfo> commentInfos = new ArrayList<CommentInfo>();
    /*传入上下文*/
    private Context context = null;

    /*获取上下文*/
    public Context getContext() {
        return context;
    }

    /*适配器构造方法*/
    public CommentListAdapter(Context context) {
        this.context = context;
    }

    /*获取总项数*/
    @Override
    public int getCount() {
        return commentInfos.size();
    }

    /*获取单项*/
    @Override
    public CommentInfo getItem(int position) {
        return commentInfos.get(position);
    }

    /*获取单项ID*/
    @Override
    public long getItemId(int position) {
        return position;
    }

    /*加载数据*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            /*获取布局实例*/
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.aty_comment_item, null);
            convertView.setTag(new ListCached((TextView) convertView.findViewById(R.id.comment_item), (TextView) convertView.findViewById(R.id.comment_time)));
        }

        ListCached listCached = (ListCached) convertView.getTag();
        CommentInfo commentInfo = getItem(position);
        listCached.getComment_item().setText(commentInfo.getContent());
        listCached.getComment_time().setText(commentInfo.getComment_time());

        return convertView;
    }


    /**
     * 布局数据实体缓存
     */
    private static class ListCached {
        private TextView comment_item;
        private TextView comment_time;

        private ListCached(TextView comment_item, TextView comment_time) {
            this.comment_item = comment_item;
            this.comment_time = comment_time;
        }

        public TextView getComment_item() {
            return comment_item;
        }

        public TextView getComment_time() {
            return comment_time;
        }
    }

    public void addAll (List<CommentInfo> data){
        commentInfos.addAll(data);
        notifyDataSetChanged();
    }
}
