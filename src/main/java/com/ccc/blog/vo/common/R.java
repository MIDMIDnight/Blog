package com.ccc.blog.vo.common;


public class R {

    private boolean isSuccess;

    private Integer code;

    private String msg;

    private Object data;


    public R(boolean isSuccess, Integer code, String msg, Object data) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public R() {
    }

    @Override
    public String toString() {
        return "R{" +
                "isSuccess=" + isSuccess +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static R success(Object data){
        R success1 = new R(true, 200, "success", data);
        System.out.println("测试000000000000000000000000000000000000000000000");
        System.out.println(success1);
        return success1;
    }

    public static R fail(Integer code,String msg){
        return new R(false,code,msg,false);
    }




    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
