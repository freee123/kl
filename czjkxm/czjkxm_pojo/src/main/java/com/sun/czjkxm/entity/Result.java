package com.sun.czjkxm.entity;

import java.io.Serializable;

/**
 * 处理响应结果集
 */

public class Result implements Serializable {
    private boolean flag;   //执行结果 true|false
    private String message; //返回结果的文字说明
    private Object data;    //返回数据本体

    //查询回显
    public Result(boolean flag, String message, Object data) {
        this.flag = flag;
        this.message = message;
        this.data = data;
    }

    //保存、修改
    public Result(boolean flag, String message) {
        /*？
            这个super的意义是什么？
            为什么满参构造不用
         */
        super();
        this.flag = flag;
        this.message = message;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
