package com.zenas.tools;
import android.util.Log;

import java.util.Random;

public class RandomSmsCheck {
	public int RandomSmsCheck() {
		Random random = new Random();
		int x = random.nextInt(899999);
		int sms = x + 100000;
        Log.d("test", String.valueOf(sms));
		return sms;
	}
}
