package org.birdback.histudents.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/4/24.
 */

public class MyMenuEntity {

    /**
     * day_stat : {"view_num":"1202","pay_num":"46","goods_num":"100","pay_money":"894.00"}
     * month_stat : {"view_num":"1252","pay_num":"47","goods_num":"101","pay_money":"894.10"}
     * order_stat : {"wait_grab":1,"wait_send":1,"wait_succ":30}
     * menu : [{"name":"店铺管理","url":"/store/index","icon":"http://juhuijia2.birdback.org/mapi/i/home2/shop.png?gv=16_9"},{"name":"商品管理","url":"/store/index","icon":"http://juhuijia2.birdback.org/mapi/i/home2/shop.png?gv=16_9"},{"name":"分类管理","url":"/store/index","icon":"http://juhuijia2.birdback.org/mapi/i/home2/shop.png?gv=16_9"},{"name":"属性管理","url":"/store/index","icon":"http://juhuijia2.birdback.org/mapi/i/home2/shop.png?gv=16_9"}]
     */

    private DayStatBean day_stat;
    private MonthStatBean month_stat;
    private OrderStatBean order_stat;
    private List<MenuBean> menu;

    public DayStatBean getDay_stat() {
        return day_stat;
    }

    public void setDay_stat(DayStatBean day_stat) {
        this.day_stat = day_stat;
    }

    public MonthStatBean getMonth_stat() {
        return month_stat;
    }

    public void setMonth_stat(MonthStatBean month_stat) {
        this.month_stat = month_stat;
    }

    public OrderStatBean getOrder_stat() {
        return order_stat;
    }

    public void setOrder_stat(OrderStatBean order_stat) {
        this.order_stat = order_stat;
    }

    public List<MenuBean> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuBean> menu) {
        this.menu = menu;
    }

    public static class DayStatBean {
        /**
         * view_num : 1202
         * pay_num : 46
         * goods_num : 100
         * pay_money : 894.00
         */

        private String view_num;
        private String pay_num;
        private String goods_num;
        private String pay_money;

        public String getView_num() {
            return view_num;
        }

        public void setView_num(String view_num) {
            this.view_num = view_num;
        }

        public String getPay_num() {
            return pay_num;
        }

        public void setPay_num(String pay_num) {
            this.pay_num = pay_num;
        }

        public String getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(String goods_num) {
            this.goods_num = goods_num;
        }

        public String getPay_money() {
            return pay_money;
        }

        public void setPay_money(String pay_money) {
            this.pay_money = pay_money;
        }
    }

    public static class MonthStatBean {
        /**
         * view_num : 1252
         * pay_num : 47
         * goods_num : 101
         * pay_money : 894.10
         */

        private String view_num;
        private String pay_num;
        private String goods_num;
        private String pay_money;

        public String getView_num() {
            return view_num;
        }

        public void setView_num(String view_num) {
            this.view_num = view_num;
        }

        public String getPay_num() {
            return pay_num;
        }

        public void setPay_num(String pay_num) {
            this.pay_num = pay_num;
        }

        public String getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(String goods_num) {
            this.goods_num = goods_num;
        }

        public String getPay_money() {
            return pay_money;
        }

        public void setPay_money(String pay_money) {
            this.pay_money = pay_money;
        }
    }

    public static class OrderStatBean {
        /**
         * wait_grab : 1
         * wait_send : 1
         * wait_succ : 30
         */

        private int wait_grab;
        private int wait_send;
        private int wait_succ;

        public int getWait_grab() {
            return wait_grab;
        }

        public void setWait_grab(int wait_grab) {
            this.wait_grab = wait_grab;
        }

        public int getWait_send() {
            return wait_send;
        }

        public void setWait_send(int wait_send) {
            this.wait_send = wait_send;
        }

        public int getWait_succ() {
            return wait_succ;
        }

        public void setWait_succ(int wait_succ) {
            this.wait_succ = wait_succ;
        }
    }

    public static class MenuBean {
        /**
         * name : 店铺管理
         * url : /store/index
         * icon : http://juhuijia2.birdback.org/mapi/i/home2/shop.png?gv=16_9
         */

        private String name;
        private String url;
        private String icon;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
