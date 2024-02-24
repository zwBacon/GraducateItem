package com.zw.graducate.service;

import com.zw.graducate.entity.NewProductDetails;

public interface NewProductDetailService {

    //    根据productId查询信息
    public NewProductDetails selOneNewProductDetailByProductId(String productId);

   //     根据userId修改关注状态
   public int updateSubscribeById(String productId,Integer opertion,String userId);

}
