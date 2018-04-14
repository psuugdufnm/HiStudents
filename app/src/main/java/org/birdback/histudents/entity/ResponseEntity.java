package org.birdback.histudents.entity;

import org.birdback.histudents.net.HttpServer;

import java.io.Serializable;



public class ResponseEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private T data;
    private int ok;
    private String msg;

    public int getOk() {
        return ok;
    }

    public void setOk(int ok) {
        this.ok = ok;
    }



    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public String getMessage() {
        return msg;
    }

    public void setMessage(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return ok == HttpServer.SUCCESS;
    }

    @Override
    public String toString() {
        return "ResponseEntity{" +
                "data=" + data +
                ", ok=" + ok +
                ", msg='" + msg + '\'' +
                '}';
    }
}
