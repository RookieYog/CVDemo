package com.byl.qrobotor.http.exception;


/**
 * author: lw
 * date: 2016/9/1
 * 统一处理网络异常errorCode
 */
public class MediaException extends RuntimeException {
    public MediaException(int code) {
        this(getErrorMessage(code));
    }

    public MediaException(String msg) {
        super(msg);
    }

    private static String getErrorMessage(int code) {
        String msg;
        switch (code) {
            case -2:
                msg = "Invalid URL !";
                break;
            case -875574520:
                msg = "404 resource not found !";
                break;
            case -111:
                msg = "Connection refused !";
                break;
            case -110:
                msg = "Connection timeout !";
                break;
            case -541478725:
                msg = "Empty playlist !";
                break;
            case -11:
                msg = "Stream disconnected !";
                break;
            case -5:
                msg = "Network IO Error !";
                break;
            case -825242872:
                msg = "Unauthorized Error !";
                break;
            case -2001:
                msg = "Prepare timeout !";
                break;
            case -2002:
                msg = "Read frame timeout !";
                break;
            case -1:
            default:
                msg = "unknown error !";
                break;
        }
        return msg;
    }

}
