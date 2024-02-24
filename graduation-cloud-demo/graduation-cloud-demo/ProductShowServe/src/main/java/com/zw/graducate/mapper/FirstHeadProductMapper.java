package com.zw.graducate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zw.graducate.entity.FirstHeadProductDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface FirstHeadProductMapper extends BaseMapper<FirstHeadProductDTO>{

//    首页查询商品
    @Select("select * from all_product_infos where CITY=#{city} and STATE = '1' order by CREATE_TIME desc limit #{beginNum},#{size};")
    public List<FirstHeadProductDTO> selectAllProductByPage(@Param("beginNum") Integer beginNum, @Param("size") Integer size,@Param("city")String city);

    @Select("select count(*) from all_product_infos where CITY=#{city} and STATE = '1';")
    public Integer selectAllProductCountByCity(@Param("city") String city);

    @Select("select count(*) from all_product_infos where CITY=#{city} and STATE = '1' and TYPE = #{type};")
    public Integer selectAllProductCountByCityAndCondition(@Param("city") String city,@Param("type") String type);

    @Select("select * from all_product_infos where PRODUCT_ID in (select PRODUCT_ID from doubel_rent_product_details where IS_SUBSCRIB = '1' and SUBSCRIBED_USERID = #{userId}) and TYPE = #{type}")
    public List<FirstHeadProductDTO> selectAllProductIsSubscribed(@Param("userId") String userId,@Param("type") String type);

    @Select("select * from all_product_infos where PRODUCT_ID in (select PRODUCT_ID from new_product_details where IS_SUBSCRIB = '1' and SUBSCRIBED_USERID = #{userId}) and TYPE = #{type}")
    public List<FirstHeadProductDTO> selectAllProductIsSubscribedNew(@Param("userId") String userId,@Param("type") String type);

    @Update("update all_product_infos set STATE = #{state} where PRODUCT_ID = #{productId}")
    public Integer updateProductState(@Param("productId") String productId, @Param("state")String state);

    @Update("update all_product_infos set FIRSETPAGE = #{images} , PROVINCE='320000' where PRODUCT_ID = #{productId}")
    public void ImageInsert(@Param("images")byte[] images, @Param("productId")String productId);

    @Select("<script>" +
            "select * from all_product_infos where 1=1" +
            "<if test=\"productName != null and productName != ''\"> " +
            "and PRODUCT_NAME like #{productName} " +
            "</if>" +
            "<if test=\"state != null and state != ''\"> " +
            "and STATE = #{state} " +
            "</if>" +
            "<if test=\"type != null and type != ''\">" +
            " and TYPE = #{type}" +
            "</if> " +
            "and USERID = #{userId}" +
            "and TYPE != '0'" +
            "order by CREATE_TIME desc,STATE desc" +
            " limit #{currentPage},#{pageSize}" +
            "</script>")
    public List<FirstHeadProductDTO> selectAllMyPubshingByCondition(@Param("currentPage") Integer currentPage, @Param("pageSize")Integer pageSize, @Param("productName")String productName,
                                                                    @Param("type")String type, @Param("state")String state, @Param("userId")String userId);

    @Select("<script>" +
            "select count(*) from all_product_infos where 1=1" +
            "<if test=\"productName != null and productName != ''\"> " +
            "and PRODUCT_NAME like #{productName} " +
            "</if>" +
            "<if test=\"state != null and state != ''\"> " +
            "and STATE = #{state} " +
            "</if>" +
            "<if test=\"type != null and type != ''\">" +
            " and TYPE = #{type}" +
            "</if> " +
            "and USERID = #{userId}" +
            "and TYPE != '0'" +
            "</script>")
    public Integer selectCountMyPubshingByCondition(@Param("productName")String productName,@Param("type")String type, @Param("state")String state, @Param("userId")String userId);
}
