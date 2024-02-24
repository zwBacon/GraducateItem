package com.zw.graducate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zw.graducate.entity.NewProductDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface NewProductDetailMapper extends BaseMapper<NewProductDetails> {

    @Select("select * from new_product_details where Product_ID = #{productId}")
    NewProductDetails selectOneNewProductDetail(@Param("productId") String productId);

    @Update("update new_product_details set IS_SUBSCRIB = '1' ,SUBSCRIBED_USERID = #{userId} where Product_ID = #{productId}")
    public int updateSubscribeById(@Param("productId") String productId,@Param("userId") String userId);

    @Update("update new_product_details set IS_SUBSCRIB = '0' ,SUBSCRIBED_USERID = null where Product_ID = #{productId}")
    public int updateSubscribeByIdBack(@Param("productId") String productId,@Param("userId") String userId);

}
