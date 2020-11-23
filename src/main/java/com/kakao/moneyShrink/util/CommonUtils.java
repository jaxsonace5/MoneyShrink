package com.kakao.moneyShrink.util;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by sangheon on 2020-11-21
 */
public class CommonUtils {
    public static Date addDateMinute(Date date, Integer minute) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minute);

        return cal.getTime();
    }

    public static String getToken() {
        char[] tmp = new char[3];
        for(int i=0; i<tmp.length; i++) {
            tmp[i] = (char) (Math.random() * 26 + 'A') ;
        }
        return new String(tmp);

    }

}
