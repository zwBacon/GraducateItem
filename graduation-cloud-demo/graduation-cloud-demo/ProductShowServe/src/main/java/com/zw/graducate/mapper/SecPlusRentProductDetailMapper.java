package com.zw.graducate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zw.graducate.entity.DoubelRentProductDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SecPlusRentProductDetailMapper extends BaseMapper<DoubelRentProductDetails> {

    @Update("update doubel_rent_product_details set IS_SUBSCRIB = '1' ,SUBSCRIBED_USERID = #{userId} where Product_ID = #{productId}")
    public int updateSubscribeById(@Param("productId") String productId,@Param("userId") String userId);

    @Update("update doubel_rent_product_details set IS_SUBSCRIB = '0' ,SUBSCRIBED_USERID = null where Product_ID = #{productId}")
    public int updateSubscribeByIdBack(@Param("productId") String productId,@Param("userId") String userId);

}
