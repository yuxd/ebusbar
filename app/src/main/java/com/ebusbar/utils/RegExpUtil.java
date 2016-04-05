package com.ebusbar.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具
 * Created by Jelly on 2016/3/3.
 */
public class RegExpUtil {
    /**
     * TAG
     */
    private static String TAG = "RegExpUtil";

    public static final String RPHONE="(^(13\\d|14[57]|15[^4,\\D]|17[678]|18\\d)\\d{8}|170[059]\\d{7})$";

    private static final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
            'B', 'C', 'D', 'E', 'F' };
    /**
     * 校验手机号码
     * @return
     */
    public static boolean regPhone(String phone){
        return phone.matches(RPHONE);
    }

    /**
     * 校验是否匹配字符串
     * @param condition
     * @param character
     * @return
     */
    public static boolean regChar(String condition,String character){
        if(TextUtils.isEmpty(character)){
            return false;
        }
        Pattern pattern = Pattern.compile(character);
        Matcher matcher = pattern.matcher(condition);
        if(matcher.find()){
            return true;
        }else{
            return false;
        }
    }

    public static String toEncodedUnicode(String theString, boolean escapeSpace) {

        int len = theString.length();

        int bufLen = len * 2;

        if (bufLen < 0) {

            bufLen = Integer.MAX_VALUE;

        }

        StringBuffer outBuffer = new StringBuffer(bufLen);




        for (int x = 0; x < len; x++) {

            char aChar = theString.charAt(x);

            // Handle common case first, selecting largest block that

            // avoids the specials below

            if ((aChar > 61) && (aChar < 127)) {

                if (aChar == '\\') {

                    outBuffer.append('\\');

                    outBuffer.append('\\');

                    continue;

                }

                outBuffer.append(aChar);

                continue;

            }



            switch (aChar) {

                case ' ':

                    if (x == 0 || escapeSpace) outBuffer.append('\\');

                    outBuffer.append(' ');

                    break;

                case '\t':

                    outBuffer.append('\\');

                    outBuffer.append('t');

                    break;

                case '\n':

                    outBuffer.append('\\');

                    outBuffer.append('n');

                    break;

                case '\r':

                    outBuffer.append('\\');

                    outBuffer.append('r');

                    break;

                case '\f':

                    outBuffer.append('\\');

                    outBuffer.append('f');

                    break;

                case '=': // Fall through

                case ':': // Fall through

                case '#': // Fall through

                case '!':

                    outBuffer.append('\\');

                    outBuffer.append(aChar);

                    break;

                default:

                    if ((aChar < 0x0020) || (aChar > 0x007e)) {

                        // 每个unicode有16位，每四位对应的16进制从高位保存到低位

                        outBuffer.append('\\');

                        outBuffer.append('u');

                        outBuffer.append(toHex((aChar >> 12) & 0xF));

                        outBuffer.append(toHex((aChar >> 8) & 0xF));

                        outBuffer.append(toHex((aChar >> 4) & 0xF));

                        outBuffer.append(toHex(aChar & 0xF));

                    } else {

                        outBuffer.append(aChar);

                    }

            }

        }

        return outBuffer.toString();

    }

    private static char toHex(int nibble) {

        return hexDigit[(nibble & 0xF)];

    }

}
