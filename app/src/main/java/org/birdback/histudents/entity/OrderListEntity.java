package org.birdback.histudents.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/4/21.
 */

public class OrderListEntity {

    /**
     * grab_list : [{"order_no":"1523887175136322221","order_num":"0","goods_list":[{"name":"鱼香肉丝","num":1,"showDesc":""}],"pay_time":"2018-04-16 21:59:39","addr_name":"hehe","addr_phone":"13888888888","addr_sex":"男同学","address":"xxxx xxxx xxxx","tableware_num":"0","remark":"","real_price":"0.10","pay_price":"0.10","goods_price":"0.10","rebate":0},{"order_no":"1523895655961956244","order_num":"0","goods_list":[{"name":"鱼香肉丝","num":1,"showDesc":""}],"pay_time":"2018-04-17 00:21:00","addr_name":"hehe","addr_phone":"13888888888","addr_sex":"男同学","address":"xxxx xxxx xxxx","tableware_num":"0","remark":"","real_price":"0.10","pay_price":"0.10","goods_price":"0.10","rebate":0},{"order_no":"1523895753573405694","order_num":"0","goods_list":[{"name":"鱼香肉丝","num":1,"showDesc":""}],"pay_time":"2018-04-17 00:22:44","addr_name":"hehe","addr_phone":"13888888888","addr_sex":"男同学","address":"xxxx xxxx xxxx","tableware_num":"0","remark":"","real_price":"0.10","pay_price":"0.10","goods_price":"0.10","rebate":0},{"order_no":"1523895833469938321","order_num":"0","goods_list":[{"name":"鱼香肉丝","num":1,"showDesc":""}],"pay_time":"2018-04-17 00:23:57","addr_name":"hehe","addr_phone":"13888888888","addr_sex":"男同学","address":"xxxx xxxx xxxx","tableware_num":"0","remark":"","real_price":"0.10","pay_price":"0.10","goods_price":"0.10","rebate":0},{"order_no":"1523895916316349549","order_num":"0","goods_list":[{"name":"鱼香肉丝","num":1,"showDesc":""}],"pay_time":"2018-04-17 00:25:19","addr_name":"hehe","addr_phone":"13888888888","addr_sex":"男同学","address":"xxxx xxxx xxxx","tableware_num":"0","remark":"","real_price":"0.10","pay_price":"0.10","goods_price":"0.10","rebate":0},{"order_no":"1523895983568377176","order_num":"0","goods_list":[{"name":"鱼香肉丝","num":1,"showDesc":""}],"pay_time":"2018-04-17 00:26:26","addr_name":"hehe","addr_phone":"13888888888","addr_sex":"男同学","address":"xxxx xxxx xxxx","tableware_num":"0","remark":"","real_price":"0.10","pay_price":"0.10","goods_price":"0.10","rebate":0},{"order_no":"1523896228651117942","order_num":"0","goods_list":[{"name":"鱼香肉丝","num":1,"showDesc":""}],"pay_time":"2018-04-17 00:30:32","addr_name":"hehe","addr_phone":"13888888888","addr_sex":"男同学","address":"xxxx xxxx xxxx","tableware_num":"0","remark":"","real_price":"0.10","pay_price":"0.10","goods_price":"0.10","rebate":0},{"order_no":"1523945389338645124","order_num":"0","goods_list":[{"name":"鱼香肉丝","num":1,"showDesc":""}],"pay_time":"2018-04-17 14:09:55","addr_name":"Jack","addr_phone":"18616360416","addr_sex":"男同学","address":"上海 123 123","tableware_num":"0","remark":"","real_price":"0.10","pay_price":"0.10","goods_price":"0.10","rebate":0},{"order_no":"1523981850060948619","order_num":"0","goods_list":[{"name":"鱼香肉丝","num":3,"showDesc":""}],"pay_time":"2018-04-18 00:17:41","addr_name":"hehe","addr_phone":"13888888888","addr_sex":"男同学","address":"xxxx xxxx xxxx","tableware_num":"0","remark":"","real_price":"0.30","pay_price":"0.30","goods_price":"0.30","rebate":0},{"order_no":"1524237634415337667","order_num":"0","goods_list":[{"name":"鱼香肉丝","num":4,"showDesc":""}],"pay_time":"2018-04-20 23:20:58","addr_name":"hehe","addr_phone":"13888888888","addr_sex":"男同学","address":"xxxx xxxx xxxx","tableware_num":"0","remark":"","real_price":"0.40","pay_price":"0.40","goods_price":"0.40","rebate":0}]
     * store_name : 测试店2
     */

    private String store_name = "";
    private List<GrabListBean> grab_list;

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public List<GrabListBean> getGrab_list() {
        return grab_list;
    }

    public void setGrab_list(List<GrabListBean> grab_list) {
        this.grab_list = grab_list;
    }

    public static class GrabListBean {
        /**
         * order_no : 1523887175136322221
         * order_num : 0
         * goods_list : [{"name":"鱼香肉丝","num":1,"showDesc":""}]
         * pay_time : 2018-04-16 21:59:39
         * addr_name : hehe
         * addr_phone : 13888888888
         * addr_sex : 男同学
         * address : xxxx xxxx xxxx
         * tableware_num : 0
         * remark :
         * real_price : 0.10
         * pay_price : 0.10
         * goods_price : 0.10
         * rebate : 0
         */

        private String order_no = "";
        private String order_num = "";
        private String pay_time = "";
        private String addr_name = "";
        private String addr_phone = "";
        private String addr_sex = "";
        private String address = "";
        private String tableware_num = "";
        private String remark = "";
        private String real_price = "";
        private String pay_price = "";
        private String goods_price = "";
        private float rebate;
        private List<GoodsListBean> goods_list;

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getOrder_num() {
            return order_num;
        }

        public void setOrder_num(String order_num) {
            this.order_num = order_num;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getAddr_name() {
            return addr_name;
        }

        public void setAddr_name(String addr_name) {
            this.addr_name = addr_name;
        }

        public String getAddr_phone() {
            return addr_phone;
        }

        public void setAddr_phone(String addr_phone) {
            this.addr_phone = addr_phone;
        }

        public String getAddr_sex() {
            return addr_sex;
        }

        public void setAddr_sex(String addr_sex) {
            this.addr_sex = addr_sex;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTableware_num() {
            return tableware_num;
        }

        public void setTableware_num(String tableware_num) {
            this.tableware_num = tableware_num;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getReal_price() {
            return real_price;
        }

        public void setReal_price(String real_price) {
            this.real_price = real_price;
        }

        public String getPay_price() {
            return pay_price;
        }

        public void setPay_price(String pay_price) {
            this.pay_price = pay_price;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public float getRebate() {
            return rebate;
        }

        public void setRebate(float rebate) {
            this.rebate = rebate;
        }

        public List<GoodsListBean> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List<GoodsListBean> goods_list) {
            this.goods_list = goods_list;
        }

        public static class GoodsListBean {
            /**
             * name : 鱼香肉丝
             * num : 1
             * showDesc :
             */

            private String name = "";
            private int num;
            private String showDesc = "";

            private float price;

            public float getPrice() {
                return price;
            }

            public void setPrice(float price) {
                this.price = price;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getShowDesc() {
                return showDesc;
            }

            public void setShowDesc(String showDesc) {
                this.showDesc = showDesc;
            }
        }
    }
}
