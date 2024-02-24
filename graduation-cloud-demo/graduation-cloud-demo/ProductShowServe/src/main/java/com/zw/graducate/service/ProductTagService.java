package com.zw.graducate.service;

import com.zw.graducate.entity.ProductConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductTagService {

    public List<String> selectProductTagByDataId(String dataId);

    public List<ProductConfig> selectAllTags();

}
