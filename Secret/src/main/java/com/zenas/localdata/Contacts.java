package com.zenas.localdata;


import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.zenas.main.Config;
import com.zenas.tools.MD5Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取本地手机联系人，获取到封装到JSONOBJ存入JSONArray
 * Created by Administrator on 2015/3/16.
 */
public class Contacts {

    public String getLocalContactsJson(Context context) {

        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        String phonenum;
        JSONArray jsonarry = new JSONArray();
        JSONObject jsonobj;
        while (cursor.moveToNext()) {
            phonenum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            //下面对获取到手机号筛选了两次，第一次判断是否含有”+86“，第二次判断是否大于11和小于11
            if (phonenum.charAt(0) == '+' && phonenum.charAt(1) == '8' && phonenum.charAt(2) == '6') {
                phonenum = phonenum.substring(3);
                /*13568923203 15928077079 15198213542 18200110831 15228828696*/
            }
            if (!(phonenum.length() < 11) && !(phonenum.length() > 11)) {
                jsonobj = new JSONObject();
                try {
                    Log.d("test", "numa3:" + phonenum.toString());
                    jsonobj.put(Config.MD5_KEY, new MD5Tools().MD5Tools(phonenum));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonarry.put(jsonobj);
            }

        }

        return jsonarry.toString();
    }


}
