package com.plusend.diycode.util;

import android.util.Log;

public class HtmlUtil {
    private static final String TAG = "HtmlUtil";

    public static String removeP(String html) {
        String result = html;
        Log.d(TAG, "html: " + html);
        if (result.contains("<p>") && result.contains("</p>")) {
            result = result.replace("<p>", "");
            result = result.replace("</p>", "<br>");
            Log.d(TAG, "result: " + result);
            if (result.endsWith("<br>")) {
                result = result.substring(0, result.length() - 4);
                Log.d(TAG, "final: " + result);
            }
        }
        return result;
    }
}
