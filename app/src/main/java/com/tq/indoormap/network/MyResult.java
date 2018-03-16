package com.tq.indoormap.network;

/**
 * Created by niantuo on 2017/4/4.
 */

public class MyResult<T> {

    private int status;
    private String msg;

    private int pageIndex;
    private int pageCount;

    private T data;

    public MyResult() {
        super();
    }


    public boolean hasMore() {
        return pageIndex < pageCount;
    }

    public boolean isSuccess() {
        return status > 0;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
