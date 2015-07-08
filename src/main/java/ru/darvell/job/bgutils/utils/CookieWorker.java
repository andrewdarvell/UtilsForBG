package ru.darvell.job.bgutils.utils;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;

public class CookieWorker {
    public static Map<String, String> cookieToMap(Cookie[] cookies){
        Map<String, String> result = new HashMap<>();
        if (cookies != null){
            for(int i=0; i<cookies.length; i++){
                result.put(cookies[i].getName(), cookies[i].getValue());
            }
        }
        return result;
    }

    public static String getSessionKey(Cookie[] cookies){
        String result = "";
        if (cookies != null){
            for(int i=0; i<cookies.length; i++){
                if (cookies[i].getName().equals("sessionKey")){
                    result = cookies[i].getValue();
                }
            }
        }
        return result;
    }
}
