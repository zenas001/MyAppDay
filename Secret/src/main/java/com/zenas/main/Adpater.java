package com.zenas.main;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Administrator on 2015/3/19.
 */
public class Adpater extends ArrayAdapter {
    private int resourceid;

    public Adpater(Context context, int resource, int textViewResourceId, List objects) {
        super(context, resource, textViewResourceId, objects);
        this.resourceid = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        MessageEntity message = getItem(position);
        View view;
        if (convertView == null) {
//            view=
        }


        return null;
    }

    class hoiler {

    }
}
