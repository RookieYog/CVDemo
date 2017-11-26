package com.byl.qrobotor.common;


/**
 * Created by acer on 2017/6/6.
 */

public class UnicodeConvert {

    public static String convertToChinese(String unicode){
        if (unicode == null) {
            return null;
        }
        StringBuffer retBuf = new StringBuffer();
        int maxLoop = unicode.length();
        for (int i = 0; i < maxLoop; i++) {
            if (unicode.charAt(i) == '\\') {
                if ((i < maxLoop - 5) && ((unicode.charAt(i + 1) == 'u') || (unicode.charAt(i + 1) == 'U')))
                    try {
                        retBuf.append((char) Integer.parseInt(unicode.substring(i + 2, i + 6), 16));
                        i += 5;
                    } catch (NumberFormatException localNumberFormatException) {
                        retBuf.append(unicode.charAt(i));
                    }
                else
                    retBuf.append(unicode.charAt(i));
            } else {
                retBuf.append(unicode.charAt(i));
            }
        }
        return retBuf.toString();
    }
}
