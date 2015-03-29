package com.zenas.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zenas.entity.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * MessageListView适配器
 * Created by Administrator on 2015/3/19.
 */
public class MessageListItemAdpater extends BaseAdapter{
    private Context context = null;

    public Context getContext() {
        return context;
    }

    private List<Message> messages = new ArrayList<Message>();

    public MessageListItemAdpater(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Message getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 将数据添加进列表
     *
     * @param data
     */
    public void addAll(List<Message> data) {
        messages.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 清空列表
     */
    public void clearAll() {
        messages.clear();
        notifyDataSetChanged();
    }

    @Override
//  映射列表
    public View getView(int position, View convertView, ViewGroup parent) {
        /*加载数据优化*/
        if (convertView == null) {
            //获取布局实例，进行布局填充
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.aty_timeline_item, null);
            //设置标签存入布局中的控件数据
            convertView.setTag(new ListCached((TextView) convertView.findViewById(R.id.list_item), (TextView) convertView.findViewById(R.id.time)));
        }
        //不为null直接取出标签中存入的控件实例进行加载
        ListCached listCached = (ListCached) convertView.getTag();
        //取出当前页面上加载的数据
        Message msg = getItem(position);
        //设置列表项
        listCached.getList_item().setText(msg.getMsg());
        listCached.getTime().setText(msg.getMsg_time());
        return convertView;
    }


    private static class ListCached {
        private TextView list_item;
        private TextView time;

        public ListCached(TextView list_item, TextView time) {
            this.list_item = list_item;
            this.time = time;
        }

        public TextView getList_item() {
            return list_item;
        }

        public TextView getTime() {
            return time;
        }

    }
}
