package com.zw.graducate.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.zw.graducate.clients.UserClient;
import com.zw.graducate.entity.OrderInfo;
import com.zw.graducate.mapper.OrderInfoMapper;
import com.zw.graducate.service.OrderInfoService;
import com.zw.graducate.util.AESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2024/2/3 15:24
 */

@Service
@DS("order")
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private OrderInfoMapper mapper;

    @Autowired
    private UserClient userClient;

    @Override
    public Integer addNewOrderOfPage(OrderInfo orderInfo) {
        return mapper.insert(orderInfo);
    }

    @Override
    public List<OrderInfo> selectAllAliveOrder(String ownerId, String buyerId) {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("OWNER_ID",ownerId);
        queryWrapper.eq("BUYER_ID",buyerId);
        queryWrapper.notIn("STATE","2");
        queryWrapper.orderByDesc("CREATE_TIME");
        return mapper.selectList(queryWrapper);
    }

    @Override
    public Map<String,Object> selectAllPreparePayOrder(String ownerId, Integer size, Integer currentPage, String orderId, String nameOrPhone, String createtime, String orderSta, Integer minPrice, Integer maxPrice, String expireDay,String type,String timeNow) {
        Integer _currentPage = (currentPage - 1) * size;

        Map<String,Object> result = new HashMap<>();

        String _orderId = "";
        String _createtime = "";
        if(StringUtils.isNotBlank(orderId)) {
            _orderId = "ORDER-DEPOSIT-FOR-" + orderId;
        }
        if(StringUtils.isNotBlank(createtime)) {
            _createtime = createtime + "%";
        }

        String userId = "";
        if(StringUtils.isNotBlank(nameOrPhone)) {
            String name = "";
            String phone = "";
            Pattern formStyleModify = Pattern.compile("^[-\\+]?[\\d]*$");
            if (formStyleModify.matcher(nameOrPhone).matches()) {
                phone = AESUtil.encrpt(nameOrPhone);
            } else {
                name = nameOrPhone;
            }
             userId = userClient.selectUserIdByNameOrPhone(name, phone);
        }

        List<OrderInfo> orderInfos = mapper.selectAllPreparePayOrder(ownerId, size, _currentPage, _orderId, userId, _createtime, orderSta, minPrice, maxPrice, expireDay, timeNow,type);
        Integer count = mapper.selectCountAllPreparePayOrder(ownerId, _orderId, userId, _createtime, orderSta, minPrice, maxPrice, expireDay, timeNow,type);

        result.put("orderInfos",orderInfos);
        result.put("count",count);
        return result;
    }

    @Override
    public Integer UpdateOrderState(String orderId, String type) {
        String _orderId = "ORDER-DEPOSIT-FOR-" + orderId;
        return mapper.UpdateOrderState(_orderId, type);
    }

    @Override
    public Integer UpdateOrderStateAndTypeAndEndtime(String orderId) {
//        获取当前时间
        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime _endTime = LocalDateTime.now();
        String endTime = _endTime.format(formatter);

        String _orderId = "ORDER-DEPOSIT-FOR-" + orderId;

        return mapper.UpdateOrderStateAndTypeAndEndtime(_orderId,"1","2",endTime);
    }

    @Override
    public Map<String, Object> selectAllPayedOrder(String ownerId, Integer size, Integer currentPage, String orderId, String nameOrPhone, String createtime, String orderSta, Integer minPrice, Integer maxPrice, String expireDay, String type) {
        Integer _currentPage = (currentPage - 1) * size;

        Map<String,Object> result = new HashMap<>();

        String _orderId = "";
        String _createtime = "";
        String _expireDay = "";
        if(StringUtils.isNotBlank(orderId)) {
            _orderId = "ORDER-DEPOSIT-FOR-" + orderId;
        }
        if(StringUtils.isNotBlank(createtime)) {
            _createtime = createtime + "%";
            _expireDay = expireDay + "%";
        }

        if(StringUtils.isNotBlank(expireDay)) {
            _expireDay = expireDay + "%";
        }

        String userId = "";
        if(StringUtils.isNotBlank(nameOrPhone)) {
            String name = "";
            String phone = "";
            Pattern formStyleModify = Pattern.compile("^[-\\+]?[\\d]*$");
            if (formStyleModify.matcher(nameOrPhone).matches()) {
                phone = AESUtil.encrpt(nameOrPhone);
            } else {
                name = nameOrPhone;
            }
            userId = userClient.selectUserIdByNameOrPhone(name, phone);
        }

        List<OrderInfo> orderInfos = mapper.selectAllPayedOrder(ownerId, size, _currentPage, _orderId, userId, _createtime, orderSta, minPrice, maxPrice, _expireDay,type);
        Integer count = mapper.selectCountAllPayedOrder(ownerId, _orderId, userId, _createtime, orderSta, minPrice, maxPrice, _expireDay,type);

        result.put("orderInfos",orderInfos);
        result.put("count",count);
        return result;
    }



}
