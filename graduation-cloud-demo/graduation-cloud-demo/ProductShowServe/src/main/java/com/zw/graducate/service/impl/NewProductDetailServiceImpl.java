package com.zw.graducate.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zw.graducate.entity.DoubelRentProductDetails;
import com.zw.graducate.entity.NewProductDetails;
import com.zw.graducate.mapper.NewProductDetailMapper;
import com.zw.graducate.service.NewProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2024/1/22 11:11
 */

@Service
@DS("master")
public class NewProductDetailServiceImpl implements NewProductDetailService {

    @Autowired
    private NewProductDetailMapper mapper;


    @Override
    public NewProductDetails selOneNewProductDetailByProductId(String productId) {
        return mapper.selectOneNewProductDetail(productId);
    }

    @Override
    public int updateSubscribeById(String productId,Integer opertion,String userId) {
        int res = 0;
//        关注
        if(opertion == 0){
            res = mapper.updateSubscribeById(productId,userId);
//            取关
        }else if(opertion == 1){
            res = mapper.updateSubscribeByIdBack(productId,userId);
        }
       return res;
    }
}
