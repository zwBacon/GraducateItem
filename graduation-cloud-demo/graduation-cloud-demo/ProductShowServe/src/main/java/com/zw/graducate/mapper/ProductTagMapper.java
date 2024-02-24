package com.zw.graducate.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zw.graducate.entity.ProductConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
@DS("configds")
public interface ProductTagMapper extends BaseMapper<ProductConfig> {

    @Select("select TAG_NAME from tag_data where DATA_ID in (${dataId}) and TAG_NAME IS NOT NULL")
    public List<String> selectProductTagByDataId(@Param("dataId") String dataId);


}
