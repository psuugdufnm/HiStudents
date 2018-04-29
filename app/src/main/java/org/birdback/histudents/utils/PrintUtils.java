package org.birdback.histudents.utils;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;

import org.birdback.histudents.entity.OrderListEntity;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.UUID;

/**
 * 打印格式工具类
 * Created by meixin.song on 2018/4/10.
 */

public class PrintUtils {

    public static UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static BluetoothAdapter defaultAdapter;

    public static BluetoothAdapter getBluetoothAdapter(){
        if (defaultAdapter == null) {
            defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        return defaultAdapter;
    }

    private static OutputStream mOutputStream;

    /**
     * 复位打印机
     */
    public static final byte[] RESET = {0x1b, 0x40};

    /**
     * 左对齐
     */
    public static final byte[] ALIGN_LEFT = {0x1b, 0x61, 0x00};

    /**
     * 中间对齐
     */
    public static final byte[] ALIGN_CENTER = {0x1b, 0x61, 0x01};

    /**
     * 右对齐
     */
    public static final byte[] ALIGN_RIGHT = {0x1b, 0x61, 0x02};

    /**
     * 选择加粗模式
     */
    public static final byte[] BOLD = {0x1b, 0x45, 0x01};

    /**
     * 取消加粗模式
     */
    public static final byte[] BOLD_CANCEL = {0x1b, 0x45, 0x00};

    /**
     * 宽高加倍
     */
    public static final byte[] DOUBLE_HEIGHT_WIDTH = {0x1d, 0x21, 0x11};

    /**
     * 宽加倍
     */
    public static final byte[] DOUBLE_WIDTH = {0x1d, 0x21, 0x10};

    /**
     * 高加倍
     */
    public static final byte[] DOUBLE_HEIGHT = {0x1d, 0x21, 0x01};

    /**
     * 字体不放大
     */
    public static final byte[] NORMAL = {0x1d, 0x21, 0x00};

    /**
     * 设置默认行间距
     */
    public static final byte[] LINE_SPACING_DEFAULT = {0x1b, 0x32};


    /**
     * 打印纸一行最大的字节
     */
    private static final int LINE_BYTE_SIZE = 28;

    /**
     * 打印三列时，中间一列的中心线距离打印纸左侧的距离
     */
    private static final int LEFT_LENGTH = 18;

    /**
     * 打印三列时，中间一列的中心线距离打印纸右侧的距离
     */
    private static final int RIGHT_LENGTH = 10;

    /**
     * 打印三列时，第一列汉字最多显示几个文字
     */
    private static final int LEFT_TEXT_MAX_LENGTH = 8;

    public static void setOutputStream(OutputStream outputStream){
        mOutputStream = outputStream;
    }

    /**
     * 设置打印格式
     * @param command 格式指令
     */
    public static void selectCommand(byte[] command) {
        try {
            mOutputStream.write(command);
            mOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印文字
     * @param text 要打印的文字
     */
    public static void printText(String text) {
        try {
            byte[] data = text.getBytes("gbk");
            mOutputStream.write(data, 0, data.length);
            mOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据长度
     * @param msg
     * @return
     */
    @SuppressLint("NewApi")
    private static int getBytesLength(String msg) {
        return msg.getBytes(Charset.forName("GB2312")).length;
    }

    /**
     * 打印两列
     *
     * @param leftText  左侧文字
     * @param rightText 右侧文字
     * @return
     */
    @SuppressLint("NewApi")
    public static String printTwoData(String leftText, String rightText) {
        StringBuilder sb = new StringBuilder();
        int leftTextLength = getBytesLength(leftText);
        int rightTextLength = getBytesLength(rightText);
        sb.append(leftText);

        // 计算两侧文字中间的空格
        int marginBetweenMiddleAndRight = LINE_BYTE_SIZE - leftTextLength - rightTextLength;

        for (int i = 0; i < marginBetweenMiddleAndRight; i++) {
            sb.append(" ");
        }
        sb.append(rightText);
        return sb.toString();
    }

    /**
     * 打印三列
     * @param leftText   左侧文字
     * @param middleText 中间文字
     * @param rightText  右侧文字
     * @return
     */
    @SuppressLint("NewApi")
    public static String printThreeData(String leftText, String middleText, String rightText) {
        StringBuilder sb = new StringBuilder();


        if (leftText.length() > LEFT_TEXT_MAX_LENGTH) {

            leftText = leftText + "\n";

            sb.append(leftText);

            for (int i = 0; i < LEFT_LENGTH - 1; i++) {
                sb.append(" ");
            }
            sb.append(middleText);

            int middleTextLength = getBytesLength(middleText);
            int rightTextLength = getBytesLength(rightText);
            int marginBetweenMiddleAndRight = RIGHT_LENGTH - middleTextLength / 2 - rightTextLength;

            for (int i = 0; i < marginBetweenMiddleAndRight; i++) {
                sb.append(" ");
            }

            sb.delete(sb.length() - 1, sb.length()).append(rightText);
        }else {
            int leftTextLength = getBytesLength(leftText);
            int middleTextLength = getBytesLength(middleText);
            int rightTextLength = getBytesLength(rightText);

            sb.append(leftText);
            // 计算左侧文字和中间文字的空格长度
            int marginBetweenLeftAndMiddle = LEFT_LENGTH - leftTextLength - middleTextLength / 2;

            for (int i = 0; i < marginBetweenLeftAndMiddle; i++) {
                sb.append(" ");
            }
            sb.append(middleText);

            // 计算右侧文字和中间文字的空格长度
            int marginBetweenMiddleAndRight = RIGHT_LENGTH - middleTextLength / 2 - rightTextLength;

            for (int i = 0; i < marginBetweenMiddleAndRight; i++) {
                sb.append(" ");
            }

            // 打印的时候发现，最右边的文字总是偏右一个字符，所以需要删除一个空格
            sb.delete(sb.length() - 1, sb.length()).append(rightText);
        }

        return sb.toString();

    }

    /**
     * 发送数据
     */
    public static synchronized void send(OutputStream outputStream, String shopName, OrderListEntity.GrabListBean bean) {
        PrintUtils.setOutputStream(outputStream);

        PrintUtils.selectCommand(PrintUtils.RESET);
        PrintUtils.selectCommand(PrintUtils.LINE_SPACING_DEFAULT);
        PrintUtils.selectCommand(PrintUtils.DOUBLE_HEIGHT);
        PrintUtils.selectCommand(PrintUtils.ALIGN_CENTER);
        PrintUtils.printText("****#" + bean.getOrder_num() + "同学快跑订单****\n\n");

        PrintUtils.selectCommand(PrintUtils.NORMAL);
        PrintUtils.printText(shopName + "\n\n");

        PrintUtils.printText("--已在线支付--\n\n");
        PrintUtils.selectCommand(PrintUtils.ALIGN_LEFT);
        PrintUtils.printText("--------------------------------\n");
        PrintUtils.printText("时间:"+bean.getPay_time() + "\n");
        PrintUtils.printText("餐具数量:" + bean.getTableware_num()+ "\n");
        PrintUtils.printText("--------------------------------\n");

        PrintUtils.selectCommand(PrintUtils.BOLD_CANCEL);

        PrintUtils.selectCommand(PrintUtils.DOUBLE_HEIGHT);
        int size = bean.getGoods_list().size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                List<OrderListEntity.GrabListBean.GoodsListBean> goods_list = bean.getGoods_list();
                OrderListEntity.GrabListBean.GoodsListBean goodsListBean = goods_list.get(i);

                PrintUtils.printText(PrintUtils.printThreeData("" + goodsListBean.getName(),
                        "x" + goodsListBean.getNum(),
                        goodsListBean.getPrice() + "\n"));

                PrintUtils.printText(goodsListBean.getShowDesc() +"\n\n");
            }
        }
        PrintUtils.selectCommand(PrintUtils.NORMAL);
        PrintUtils.printText("--------------------------------\n");
        PrintUtils.printText(PrintUtils.printTwoData("合计", bean.getReal_price() + "\n"));
        PrintUtils.printText(PrintUtils.printTwoData("折扣", bean.getRebate() + "\n"));
        PrintUtils.printText(PrintUtils.printTwoData("实付", bean.getPay_price() + "\n"));
        PrintUtils.printText("--------------------------------\n");

        PrintUtils.selectCommand(PrintUtils.ALIGN_LEFT);
        PrintUtils.printText("备注:" + bean.getRemark() + "\n");
        PrintUtils.printText("-------------------------------\n");

        PrintUtils.selectCommand(PrintUtils.DOUBLE_HEIGHT_WIDTH);



        String address = bean.getAddress();


        PrintUtils.selectCommand(PrintUtils.DOUBLE_HEIGHT_WIDTH);

        PrintUtils.printText(address + "\n");
        PrintUtils.printText(bean.getAddr_name() + "("+bean.getAddr_sex()+")" + "\n\n");
        PrintUtils.printText(bean.getAddr_phone() + "\n");

        PrintUtils.selectCommand(PrintUtils.NORMAL);
        PrintUtils.printText("订单号:"+ bean.getOrder_no() + "\n\n");
        PrintUtils.selectCommand(PrintUtils.DOUBLE_HEIGHT);
        PrintUtils.selectCommand(PrintUtils.ALIGN_CENTER);
        PrintUtils.printText("*********#" + bean.getOrder_num() + "完*********");
        PrintUtils.printText("\n\n\n\n");
    }
}
