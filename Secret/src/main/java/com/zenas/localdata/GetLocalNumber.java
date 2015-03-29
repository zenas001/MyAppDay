package com.zenas.localdata;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * 获取本机电话号码
 * Created by Administrator on 2015/3/20.
 */
public class GetLocalNumber {
    public String GetLocalNumber(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getLine1Number();
    }
}
