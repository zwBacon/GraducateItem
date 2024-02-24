package com.zw.graducate.service;

import com.zw.graducate.entity.DoubelRentProductDetails;

public interface SecPlusRentProductDetailService {

    //    根据productId查询信息
    public DoubelRentProductDetails selOneDoubelRentProductDetailByProductId(String productId);

    //     根据userId修改关注状态
    public int updateSubscribeById(String productId,Integer opertion,String userId);

    public Integer updateNewProductDetails(DoubelRentProductDetails details);

    public Integer updateNewProductDetailImgs(String key,byte[] images,String productId);

}
