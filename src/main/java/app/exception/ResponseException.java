package app.exception;



public class ResponseException extends RuntimeException {
    /**
     * Attend响应结果
     */
    private AttendResponse attendResponse;

    private String content;

    /**
     * 重载构造函数
     *
     * @param errConst 错误枚举对象
     */
    public ResponseException(ErrorConst errConst) {
        super();
        attendResponse = new AttendResponse(errConst);
    }

    /**
     * 重载构造函数
     *
     * @param errConst 错误枚举对象
     */
    public ResponseException(ErrorConst errConst, String content) {
        super();
        attendResponse = new AttendResponse(errConst);
        this.content = content;
    }


    /**
     * 重载构造函数
     *
     * @param errConst  错误枚举对象
     * @param requestId 请求唯一标识
     */
    public ResponseException(ErrorConst errConst, Long requestId, String content) {
        super();
        attendResponse = new AttendResponse(errConst, requestId);
        this.content = content;
    }

    /**
     * 重载构造函数
     *
     * @param errCode 错误码
     */
    public ResponseException(int errCode, Long requestId, String content) {
        super();
        attendResponse = new AttendResponse(errCode, requestId);
        this.content = content;
    }

    /**
     * 重载构造函数
     *
     * @param errConst  错误枚举对象
     * @param requestId 请求唯一标识
     */
    public ResponseException(ErrorConst errConst, Long requestId) {
        super();
        attendResponse = new AttendResponse(errConst, requestId);
    }

    /**
     * 重载构造函数
     *
     * @param errCode 错误码
     */
    public ResponseException(int errCode, Long requestId) {
        super();
        attendResponse = new AttendResponse(errCode, requestId);
    }



    /**
     * 重载构造函数
     *
     * @param attendResponse 响应结果
     */
    public ResponseException(AttendResponse attendResponse) {
        super();
        this.attendResponse = attendResponse;
    }

    /**
     * 重载构造函数
     *
     * @param attendResponse 响应结果
     * @param message      异常消息
     */
    public ResponseException(AttendResponse attendResponse, String message) {
        super(message);
        this.attendResponse = attendResponse;
    }

    /**
     * 重载构造函数
     *
     * @param attendResponse 响应结果
     * @param cause        异常原因
     */
    public ResponseException(AttendResponse attendResponse, Throwable cause) {
        super(cause);
        this.attendResponse = attendResponse;
    }

    /**
     * 重载构造函数
     *
     * @param attendResponse 响应结果
     * @param message      异常消息
     * @param cause        异常原因
     */
    public ResponseException(AttendResponse attendResponse, String message, Throwable cause) {
        super(message, cause);
        this.attendResponse = attendResponse;
    }

    /**
     * 获取响应结果
     *
     * @return 响应结果
     */
    public AttendResponse getAttendResponse() {
        return attendResponse;
    }

    /**
     * 设置响应结果
     *
     * @param attendResponse 响应结果
     */
    public void setAttendResponse(AttendResponse attendResponse) {
        this.attendResponse = attendResponse;
    }



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
