package org.birdback.histudents.entity;

/**
 * Created by Administrator on 2018/4/30.
 */

public class DialogEntity {

    /**
     * msg : 消息
     * title : 标题
     * enterLabel : 确认
     * enterFunc : Callback.run(2)
     * cancelLabel : 取消
     * cancelFunc : Callback.run(3)
     */

    private String msg;
    private String title;
    private String enterLabel;
    private String enterFunc;
    private String cancelLabel;
    private String cancelFunc;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEnterLabel() {
        return enterLabel;
    }

    public void setEnterLabel(String enterLabel) {
        this.enterLabel = enterLabel;
    }

    public String getEnterFunc() {
        return enterFunc;
    }

    public void setEnterFunc(String enterFunc) {
        this.enterFunc = enterFunc;
    }

    public String getCancelLabel() {
        return cancelLabel;
    }

    public void setCancelLabel(String cancelLabel) {
        this.cancelLabel = cancelLabel;
    }

    public String getCancelFunc() {
        return cancelFunc;
    }

    public void setCancelFunc(String cancelFunc) {
        this.cancelFunc = cancelFunc;
    }
}
