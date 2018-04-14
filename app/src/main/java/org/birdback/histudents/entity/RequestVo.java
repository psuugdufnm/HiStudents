package org.birdback.histudents.entity;

import android.app.Activity;
import android.util.SparseArray;

import java.util.Map;

import io.reactivex.Observable;


public class RequestVo<T> {
    private Object tag;
    private SparseArray<Object> mKeyedTags;

    public enum RequestMode {
        GET, POST, FORM, DELETE, PUT, PATCH
    }

    public Activity activity;

    public RequestMode mode = RequestMode.POST;

    /**
     * 域
     */
    public String host = "";

    /**
     * 接口
     */
    public String requestUrl = "";

    /**
     * 参数
     */
    public Map<String, Object> params;

    /**
     * 结果
     */
    public Object result;

    /**
     * 结果 解析类型
     */
    public Object resultType;

    /**
     * 是否出现等待对话框  true:显示等待对话框，false:不显示
     */
    public boolean hasDialog = true;

    public Observable<ResponseEntity<T>> observable;

    /**
     * 是否需要验签
     *
     * @return
     */
    public boolean hasSign = true;

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public Object getTag(int key) {
        if (mKeyedTags == null)
            return null;
        return mKeyedTags.get(key);
    }

    public void setTags(int key, Object tag) {
        if (mKeyedTags == null)
            mKeyedTags = new SparseArray<>(2);
        mKeyedTags.put(key, tag);
    }
}
