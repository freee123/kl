package com.sun.czjkxm.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 处理分页响应结果集
 */
public class PageResult<T> implements Serializable {
    private Long total;     //总记录数
    private List<T> rows;   //当前页结果

    public PageResult(Long total, List<T> rows) {
        /*？
            这个super的意义是什么？
         */
        super();
        this.total = total;
        this.rows = rows;
    }

    public PageResult(Long total) {
        this.total = total;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
