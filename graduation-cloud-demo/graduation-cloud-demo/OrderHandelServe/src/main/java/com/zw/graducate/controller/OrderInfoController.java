package com.zw.graducate.controller;

import com.alibaba.fastjson.JSON;
import com.zw.graducate.clients.ProductClient;
import com.zw.graducate.clients.UserClient;
import com.zw.graducate.entity.DoubelRentProductDetails;
import com.zw.graducate.entity.FirstHeadProductDTO;
import com.zw.graducate.entity.OrderInfo;
import com.zw.graducate.entity.UserInfo;
import com.zw.graducate.service.OrderInfoService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2024/2/3 15:24
 */

@RestController
@RequestMapping("/orderInfoController")
@CrossOrigin(origins = {"*", "null"})
public class OrderInfoController {

    @Autowired
    private OrderInfoService service;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UserClient userClient;

    @Autowired
    private ProductClient productClient;


//    查询房源信息（生成订单第一项）
    @PostMapping("/selectAllInfoOfNewOrder")
    public Map<String,Object> selectAllInfoOfNewOrder(@RequestBody Map<String,Object> request){
        Map<String,String> userClientRequest = new HashMap<>();
        Map<String,String> productClientRequest = new HashMap<>();

        Map<String,Object> result = new HashMap<>();
//        查询房源详细信息（图片集合，信息等）
        Map<String, Object> productFirstPageInfo = productClient.selectOneProductByID(request.get("productId").toString());
        String json = JSON.toJSONString(productFirstPageInfo.get("body"));
        FirstHeadProductDTO firstHeadProductDTO = JSON.parseObject(json,FirstHeadProductDTO.class);

        productClientRequest.put("type",request.get("type").toString());
        productClientRequest.put("productId",request.get("productId").toString());
        productClientRequest.put("userId",request.get("userId").toString());
        Map<String, Object> productDetailsInfo = productClient.selectOneProductDetailById(productClientRequest);

//        查询户主信息
        userClientRequest.put("userId",request.get("userId").toString());
        UserInfo userInfo = userClient.selectUserInfoByUserId(userClientRequest);

//        结果处理
        List<byte[]> imgList = (List<byte[]>) productDetailsInfo.get("imgList");
        imgList.add(firstHeadProductDTO.getFirstPage());
        firstHeadProductDTO.setFirstPage(null);

        result.put("imgList",imgList);
        result.put("userInfo",userInfo);
        result.put("productHeadInfo",firstHeadProductDTO);
        result.put("productDeatilInfo",productDetailsInfo.get("body"));

        return result;
    }

//    入库（已支付定金订单数据）
    @PostMapping("/InsertDepositOrderData")
    public Integer InsertDepositOrderData(@RequestBody OrderInfo orderInfo){
        String orderId = "ORDER-DEPOSIT-FOR-" + RandomStringUtils.random(20,false,true);

        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime _createTime = LocalDateTime.now();
        LocalDateTime _endTime = LocalDateTime.now().plusDays(7L);
        String createTime = _createTime.format(formatter);
        String endTime = _endTime.format(formatter);

        orderInfo.setOrderId(orderId);
        orderInfo.setCreateTime(createTime);
        orderInfo.setEndTime(endTime);
        orderInfo.setState("0");
        orderInfo.setType('0');
        return service.addNewOrderOfPage(orderInfo);
    }

//    查询所有订单(代支付尾款)(分页，条件)
    @PostMapping("/selectAllPreparePayOrder")
    public Map<String,Object> selectAllPreparePayOrder(@RequestBody Map<String,Object> request){

        Map<String,String> userClientRequest = new HashMap<>();
        Map<String,String> productClientRequest = new HashMap<>();

//        获取基本信息
        String ownerId = request.get("ownerId").toString();
        Integer size = Integer.valueOf(request.get("pageSize").toString());
        Integer currentPage = Integer.valueOf(request.get("currentPage").toString());
//        查询条件
        String orderId = request.get("orderId").toString();
        String nameOrPhone = request.get("ownerNameOrOhone").toString();
        String createtime = request.get("createDate").toString();
        String orderSta = request.get("orderSta").toString();

        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime now = LocalDateTime.now();
        String timeNow = now.format(formatter);

        String _minPrice = request.get("minPrice").toString();
        Integer minPrice = 0;
        if(StringUtils.isNotBlank(_minPrice)){
            minPrice = Integer.valueOf(_minPrice);
        }
        String _maxPrice = request.get("maxPrice").toString();
        Integer maxPrice = 0;
        if(StringUtils.isNotBlank(_maxPrice)){
            maxPrice = Integer.valueOf(_maxPrice);
        }
        String expireDay = request.get("expireDate").toString();
        String type = request.get("type").toString();

        Map<String, Object> result = service.selectAllPreparePayOrder(ownerId, size, currentPage, orderId, nameOrPhone, createtime, orderSta, minPrice, maxPrice, expireDay, type,timeNow);
        List<OrderInfo> orderInfos = (List<OrderInfo>)result.get("orderInfos");
        //        结果集处理
        for(OrderInfo orderInfo : orderInfos){
            orderInfo.setOrderId(orderInfo.getOrderId().substring(18));
            //            首页信息
            Map<String, Object> productFirstPageInfo = productClient.selectOneProductByID(orderInfo.getProductId());
            String productFirstPageInfoJson = JSON.toJSONString(productFirstPageInfo.get("body"));
            orderInfo.setFirstHeadProductDTO(JSON.parseObject(productFirstPageInfoJson,FirstHeadProductDTO.class));
//          判断时间
//          创建一个DateTimeFormatter对象，定义字符串的日期时间格式
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            // 使用parse方法将字符串解析为LocalDateTime对象
            LocalDateTime nowTime = LocalDateTime.parse(timeNow, format);
            LocalDateTime endTime = LocalDateTime.parse(orderInfo.getEndTime(), format);
            if(nowTime.isAfter(endTime)){
                orderInfo.setState("3");
//                修改订单与产品状态
                Map<String,Object> updateRequest = new HashMap<>();
                updateRequest.put("orderId",orderInfo.getOrderId());
                updateRequest.put("productId",orderInfo.getProductId());
                updateRequest.put("productState","1");
                updateRequest.put("id",orderInfo.getFirstHeadProductDTO().getID());
//                Map<String, Object> updateOrderAndProductState = this.UpdateOrderAndProductState(updateRequest);
                Map<String, Object> map = this.UpdateOrderStateForCanceled(orderInfo.getOrderId(), "3");
            }
            orderInfo.setState(orderInfo.getState().equals("0") ? "在途": orderInfo.getState().equals("1") ? "完工" : orderInfo.getState().equals("2") ? "删除" : "已取消");

//            详细信息(购买人信息)
            productClientRequest.put("type","2");
            productClientRequest.put("productId",orderInfo.getProductId());
            productClientRequest.put("userId",orderInfo.getBuyerId());
            Map<String, Object> productDetailsInfo = productClient.selectOneProductDetailById(productClientRequest);
            String productDetailsInfoJson = JSON.toJSONString(productDetailsInfo.get("body"));
            orderInfo.setDoubelRentProductDetails(JSON.parseObject(productDetailsInfoJson, DoubelRentProductDetails.class));
//            用户信息
            userClientRequest.put("userId",orderInfo.getOwnerId());
            orderInfo.setUserInfo(userClient.selectUserInfoByUserId(userClientRequest));
        }

        return result;
    }

    @PostMapping("/UpdateOrderAndProductState")
    public Map<String,Object> UpdateOrderAndProductState(@RequestBody Map<String,Object> request){
        Map<String,Object> result = new HashMap<>();
        String orderId = request.get("orderId").toString();
        String productId = request.get("productId").toString();
        String productState = request.get("productState").toString();
        String orderState = request.get("orderState").toString();
        String id = request.get("id").toString();

        Map<String,Object> productClientRequest = new HashMap<>();
        productClientRequest.put("id",id);
        productClientRequest.put("state",productState);
        productClientRequest.put("productId",productId);

        Integer updateOrderState = service.UpdateOrderState(orderId, orderState);
        String message = productClient.EsAndMysqlDataHandelAfterPay(productClientRequest);
        result.put("updateOrderState",updateOrderState);
        result.put("message",message);
        return result;
    }

    @GetMapping("/UpdateOrderStateForCanceled")
    public Map<String,Object> UpdateOrderStateForCanceled(String orderId,String orderState){
        Map<String,Object> result = new HashMap<>();
        Integer updateOrderState = service.UpdateOrderState(orderId, orderState);
        result.put("updateOrderState",updateOrderState);
        return result;
    }

    @GetMapping("/UpdateOrderStateAndTypeAndEndtime")
    public Map<String,Object> UpdateOrderStateAndTypeAndEndtime(String orderId){
        Map<String,Object> result = new HashMap<>();
        Integer updateOrderState = service.UpdateOrderStateAndTypeAndEndtime(orderId);
        result.put("updateOrderState",updateOrderState);
        return result;
    }


    //    查询所有订单(代支付尾款)(分页，条件)
    @PostMapping("/selectAllPayedOrder")
    public Map<String,Object> selectAllPayedOrder(@RequestBody Map<String,Object> request){

        Map<String,String> userClientRequest = new HashMap<>();
        Map<String,String> productClientRequest = new HashMap<>();

//        获取基本信息
        String ownerId = request.get("ownerId").toString();
        Integer size = Integer.valueOf(request.get("pageSize").toString());
        Integer currentPage = Integer.valueOf(request.get("currentPage").toString());
//        查询条件
        String orderId = request.get("orderId").toString();
        String nameOrPhone = request.get("ownerNameOrOhone").toString();
        String createtime = request.get("createDate").toString();
        String orderSta = request.get("orderSta").toString();

        String _minPrice = request.get("minPrice").toString();
        Integer minPrice = 0;
        if(StringUtils.isNotBlank(_minPrice)){
            minPrice = Integer.valueOf(_minPrice);
        }
        String _maxPrice = request.get("maxPrice").toString();
        Integer maxPrice = 0;
        if(StringUtils.isNotBlank(_maxPrice)){
            maxPrice = Integer.valueOf(_maxPrice);
        }
        String expireDay = request.get("expireDate").toString();
        String type = request.get("type").toString();

        Map<String, Object> result = service.selectAllPayedOrder(ownerId, size, currentPage, orderId, nameOrPhone, createtime, orderSta, minPrice, maxPrice, expireDay, type);
        List<OrderInfo> orderInfos = (List<OrderInfo>)result.get("orderInfos");
        //        结果集处理
        for(OrderInfo orderInfo : orderInfos){
            orderInfo.setOrderId(orderInfo.getOrderId().substring(18));
            //            首页信息
            Map<String, Object> productFirstPageInfo = productClient.selectOneProductByID(orderInfo.getProductId());
            String productFirstPageInfoJson = JSON.toJSONString(productFirstPageInfo.get("body"));
            orderInfo.setFirstHeadProductDTO(JSON.parseObject(productFirstPageInfoJson,FirstHeadProductDTO.class));

            orderInfo.setState(orderInfo.getState().equals("1") ? "已完工": "已删除");

//            详细信息(购买人信息)
            productClientRequest.put("type","2");
            productClientRequest.put("productId",orderInfo.getProductId());
            productClientRequest.put("userId",orderInfo.getBuyerId());
            Map<String, Object> productDetailsInfo = productClient.selectOneProductDetailById(productClientRequest);
            String productDetailsInfoJson = JSON.toJSONString(productDetailsInfo.get("body"));
            orderInfo.setDoubelRentProductDetails(JSON.parseObject(productDetailsInfoJson, DoubelRentProductDetails.class));
//            用户信息
            userClientRequest.put("userId",orderInfo.getOwnerId());
            orderInfo.setUserInfo(userClient.selectUserInfoByUserId(userClientRequest));
        }

        return result;
    }


}
