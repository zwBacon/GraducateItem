package com.zw.graducate.service;

import com.zw.graducate.entity.OrderInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface OrderInfoService {

    public Integer addNewOrderOfPage(OrderInfo orderInfo);

    public List<OrderInfo> selectAllAliveOrder(String ownerId, String buyerId);

    public Map<String,Object> selectAllPreparePayOrder(String ownerId,Integer size,Integer currentPage,String orderId,String nameOrPhone,String createtime,
                                                    String orderSta,Integer minPrice,Integer maxPrice,String expireDay,String type,String timeNow);

    public Integer UpdateOrderState(String orderId,String state);

    public Integer UpdateOrderStateAndTypeAndEndtime(String orderId);

    public Map<String,Object> selectAllPayedOrder(String ownerId,Integer size,Integer currentPage,String orderId,String nameOrPhone,String createtime,
                                                       String orderSta,Integer minPrice,Integer maxPrice,String expireDay,String type);


}
