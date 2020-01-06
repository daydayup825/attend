package app.exception;


import java.io.Serializable;

/**
 * @author: fanbopeng
 * @Date: 2019/11/6 20:56
 * @Description:attend 响应结果封装
 */
public class AttendResponse implements Serializable {
    /**
     * 错误码
     */
    private Integer errCode;
    /**
     * 错误信息
     */
    private String errMsg;


    /**
     * 默认构造函数
     */
    public AttendResponse() {
        this.errCode = ErrorConst.SUCCESS.getErrorCode();
        this.errMsg = ErrorConst.SUCCESS.getResult();

    }

    /**
     * 重载构造函数
     */
    public AttendResponse(ErrorConst errConst) {
        this.errCode = errConst.getErrorCode();
        this.errMsg = errConst.getResult();

    }

    /**
     * 重载构造函数
     */
    public AttendResponse(ErrorConst errConst, Long requestId) {
        this.errCode = errConst.getErrorCode();
        this.errMsg = errConst.getResult();

    }

    /**
     * 重载构造函数
     */
    public AttendResponse(Integer errCode) {
        this.errCode = errCode;
        this.errMsg = ErrorConst.getResult(errCode);

    }

    /**
     * 重载构造函数
     */
    public AttendResponse(Long requestId) {
        this.errCode = ErrorConst.SUCCESS.getErrorCode();
        this.errMsg = ErrorConst.SUCCESS.getResult();

    }

    /**
     * 重载构造函数
     */
    public AttendResponse(Integer errCode, Long requestId) {
        this.errCode = errCode;
        this.errMsg = ErrorConst.getResult(errCode);

    }

    /**
     * 重载构造函数
     */
    public AttendResponse(Integer errCode, String result, Long requestId) {
        this.errCode = errCode;
        this.errMsg = result;

    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    /**
     * 获取错误信息
     */
    public String getErrMsg() {
        return errMsg;
    }

    /**
     * 设置错误信息
     */
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg == null ? null : errMsg.trim();
    }

    /**
     * 设置错误信息
     */
    public void setResult(String result) {
        this.errMsg = result == null ? null : result.trim();
    }

}
