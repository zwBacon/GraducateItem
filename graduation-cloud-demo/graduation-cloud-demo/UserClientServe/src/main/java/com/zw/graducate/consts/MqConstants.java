package com.zw.graducate.consts;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2024/2/4 20:12
 */

public class MqConstants {

    /**
     * 声明订单查询房源信息交换机
     */
    public final static String ORDER_EXCHANGE = "order.info";

    /**
     * 监听消息的队列
     */
    public final static String ORDER_SELINFO_QUEUE = "order.info.queue";


    /**
     * 查询的RoutingKey
     */
    public final static String ORDER_SELINFO_KEY = "order.info.key";

}
