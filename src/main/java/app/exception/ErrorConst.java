package app.exception;


public enum  ErrorConst {

    SUCCESS(0, 200, "call Success"),
    FAILED(0, 408, "参数效验错误");


    /**
     * 错误码
     */
    private int errorCode;
    /**
     * 错误信息
     */
    private String errMsg;
    /**
     * Http状态码
     */
    private int httpCode;

    /**
     * 重载构造函数
     */
    ErrorConst(int errorCode, int httpCode, String errMsg) {
        this.errorCode = errorCode;
        this.errMsg = errMsg;
        this.httpCode = httpCode;
    }

    /**
     * 获取响应结果
     */
    public static String getResult(int errorCode) {
        for (ErrorConst e : ErrorConst.values()) {
            if (e.getErrorCode() == errorCode) {
                return e.errMsg;
            }
        }
        return null;
    }

    /**
     * 获取HTTP状态码
     */
    public static int getHttpCode(int errorCode) {
        for (ErrorConst e : ErrorConst.values()) {
            if (e.getErrorCode() == errorCode) {
                return e.httpCode;
            }
        }
        return 200;
    }



    /**
     * 获取Http状态码
     */
    public int getHttpCode() {
        return httpCode;
    }

    /**
     * 设置Http状态码
     */
    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    /**
     * 获取错误码
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * 设置错误码
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * 设置错误信息
     */
    public String getResult() {
        return errMsg;
    }

    /**
     * 获取错误信息
     */
    public void setResult(String result) {
        this.errMsg = result;
    }

}

