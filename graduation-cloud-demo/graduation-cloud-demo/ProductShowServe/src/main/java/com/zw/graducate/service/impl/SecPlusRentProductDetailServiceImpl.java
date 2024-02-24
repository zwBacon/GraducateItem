package com.zw.graducate.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zw.graducate.entity.DoubelRentProductDetails;
import com.zw.graducate.mapper.SecPlusRentProductDetailMapper;
import com.zw.graducate.service.SecPlusRentProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2024/1/22 11:11
 */

@Service
@DS("master")
public class SecPlusRentProductDetailServiceImpl implements SecPlusRentProductDetailService {

    @Autowired
    private SecPlusRentProductDetailMapper mapper;


    @Override
    public DoubelRentProductDetails selOneDoubelRentProductDetailByProductId(String productId) {
        QueryWrapper<DoubelRentProductDetails> wrapper = new QueryWrapper<>();
        wrapper.eq("Product_ID",productId);
        return mapper.selectOne(wrapper);
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

    @Override
    public Integer updateNewProductDetails(DoubelRentProductDetails details) {
        return mapper.insert(details);
    }

    @Override
    public Integer updateNewProductDetailImgs(String key, byte[] images, String productId) {
        UpdateWrapper<DoubelRentProductDetails> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("Product_ID",productId).set(key,images);
        return mapper.update(null,updateWrapper);
    }
}
