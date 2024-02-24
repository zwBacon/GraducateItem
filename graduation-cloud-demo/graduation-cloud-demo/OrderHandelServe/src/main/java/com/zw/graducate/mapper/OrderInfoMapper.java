package com.zw.graducate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zw.graducate.entity.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2024/2/3 13:15
 */

@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

    @Select("<script>" +
            "select * from all_order_info where 1=1" +
            "<if test=\"orderId != null and orderId != ''\"> " +
            "and ORDER_ID = #{orderId} " +
            "</if>" +
            "<if test=\"userId != null and userId != ''\"> " +
            "and OWNER_ID = #{userId} " +
            "</if>" +
            "<if test=\"createtime != null and createtime != ''\">" +
            " and CREATE_TIME like #{createtime}" +
            "</if> " +
            "<if test=\"orderSta != null and orderSta != ''\">" +
            " and STATE = #{orderSta} " +
            "</if> " +
            "<if test=\"minPrice != 0 and minPrice != '' and maxPrice != 0 and maxPrice != ''\"> " +
            " and BARGAIN between ${minPrice} and ${maxPrice} " +
            "</if>" +
            "<if test=\"expireDay != null and expireDay != ''\">" +
            " and DATEDIFF(END_TIME,#{localDay}) ${expireDay} " +
            "</if>" +
            "and BUYER_ID = #{ownerId}" +
            "and TYPE = #{type}" +
            "and STATE != '2'" +
            "order by CREATE_TIME desc " +
            " limit #{currentPage},#{size}" +
            "</script>")
    public List<OrderInfo> selectAllPreparePayOrder(@Param("ownerId") String ownerId, @Param("size")Integer size, @Param("currentPage")Integer currentPage,
                                                    @Param("orderId")String orderId, @Param("userId")String userId,@Param("createtime")String createtime,
                                                    @Param("orderSta")String orderSta, @Param("minPrice")Integer minPrice, @Param("maxPrice")Integer maxPrice, @Param("expireDay")String expireDay,@Param("localDay")String localDay,@Param("type") String type);

    @Select("<script>" +
            "select count(*) from all_order_info where 1=1" +
            "<if test=\"orderId != null and orderId != ''\"> " +
            "and ORDER_ID = #{orderId} " +
            "</if>" +
            "<if test=\"userId != null and userId != ''\"> " +
            "and OWNER_ID = #{userId} " +
            "</if>" +
            "<if test=\"createtime != null and createtime != ''\">" +
            " and CREATE_TIME like #{createtime}" +
            "</if> " +
            "<if test=\"orderSta != null and orderSta != ''\">" +
            " and STATE = #{orderSta} " +
            "</if> " +
            "<if test=\"minPrice != 0 and minPrice != '' and maxPrice != 0 and maxPrice != ''\"> " +
            " and BARGAIN between ${minPrice} and ${maxPrice} " +
            "</if>" +
            "<if test=\"expireDay != null and expireDay != ''\">" +
            " and DATEDIFF(END_TIME,#{localDay}) ${expireDay} " +
            "</if>" +
            "and BUYER_ID = #{ownerId}" +
            "and TYPE = #{type}" +
            "and STATE != '2'" +
            "</script>")
    public Integer selectCountAllPreparePayOrder(@Param("ownerId") String ownerId, @Param("orderId")String orderId, @Param("userId")String userId,@Param("createtime")String createtime,
                                                    @Param("orderSta")String orderSta, @Param("minPrice")Integer minPrice, @Param("maxPrice")Integer maxPrice, @Param("expireDay")String expireDay,@Param("localDay")String localDay,@Param("type") String type);

    @Update("update all_order_info set STATE = #{state} where ORDER_ID = #{orderId}")
    public Integer UpdateOrderState(@Param("orderId")String orderId, @Param("state")String state);

    @Update("update all_order_info set STATE = #{state},TYPE = #{type},END_TIME = #{endTime} where ORDER_ID = #{orderId}")
    public Integer UpdateOrderStateAndTypeAndEndtime(@Param("orderId")String orderId,@Param("state")String state,@Param("type")String type,@Param("endTime")String endTime);

    @Select("<script>" +
            "select * from all_order_info where 1=1" +
            "<if test=\"orderId != null and orderId != ''\"> " +
            "and ORDER_ID = #{orderId} " +
            "</if>" +
            "<if test=\"userId != null and userId != ''\"> " +
            "and OWNER_ID = #{userId} " +
            "</if>" +
            "<if test=\"createtime != null and createtime != ''\">" +
            " and CREATE_TIME like #{createtime}" +
            "</if> " +
            "<if test=\"orderSta != null and orderSta != ''\">" +
            " and STATE = #{orderSta} " +
            "</if> " +
            "<if test=\"minPrice != 0 and minPrice != '' and maxPrice != 0 and maxPrice != ''\"> " +
            " and FINAL_PAYMENT between ${minPrice} and ${maxPrice} " +
            "</if>" +
            "<if test=\"expireDay != null and expireDay != ''\">" +
            " and END_TIME like #{expireDay} " +
            "</if>" +
            "and BUYER_ID = #{ownerId}" +
            "and TYPE = #{type}" +
            "order by END_TIME desc " +
            " limit #{currentPage},#{size}" +
            "</script>")
    public List<OrderInfo> selectAllPayedOrder(@Param("ownerId") String ownerId, @Param("size")Integer size, @Param("currentPage")Integer currentPage,
                                                    @Param("orderId")String orderId, @Param("userId")String userId,@Param("createtime")String createtime,
                                                    @Param("orderSta")String orderSta, @Param("minPrice")Integer minPrice, @Param("maxPrice")Integer maxPrice, @Param("expireDay")String expireDay,@Param("type") String type);

    @Select("<script>" +
            "select count(*) from all_order_info where 1=1" +
            "<if test=\"orderId != null and orderId != ''\"> " +
            "and ORDER_ID = #{orderId} " +
            "</if>" +
            "<if test=\"userId != null and userId != ''\"> " +
            "and OWNER_ID = #{userId} " +
            "</if>" +
            "<if test=\"createtime != null and createtime != ''\">" +
            " and CREATE_TIME like #{createtime}" +
            "</if> " +
            "<if test=\"orderSta != null and orderSta != ''\">" +
            " and STATE = #{orderSta} " +
            "</if> " +
            "<if test=\"minPrice != 0 and minPrice != '' and maxPrice != 0 and maxPrice != ''\"> " +
            " and FINAL_PAYMENT between ${minPrice} and ${maxPrice} " +
            "</if>" +
            "<if test=\"expireDay != null and expireDay != ''\">" +
            " and END_TIME like #{expireDay} " +
            "</if>" +
            "and BUYER_ID = #{ownerId}" +
            "and TYPE = #{type}" +
            "order by END_TIME desc " +
            "</script>")
    public Integer selectCountAllPayedOrder(@Param("ownerId") String ownerId, @Param("orderId")String orderId, @Param("userId")String userId,@Param("createtime")String createtime,
                                                 @Param("orderSta")String orderSta, @Param("minPrice")Integer minPrice, @Param("maxPrice")Integer maxPrice, @Param("expireDay")String expireDay,@Param("type") String type);
}
