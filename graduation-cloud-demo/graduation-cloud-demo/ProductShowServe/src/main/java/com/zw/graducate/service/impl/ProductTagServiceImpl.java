package com.zw.graducate.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zw.graducate.entity.ProductConfig;
import com.zw.graducate.mapper.ProductTagMapper;
import com.zw.graducate.service.ProductTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2024/1/16 11:54
 */
@DS("master")
@Service
public class ProductTagServiceImpl implements ProductTagService {

    @Autowired
    private ProductTagMapper mapper;

    @Override
    @DS("configds")
    public List<String> selectProductTagByDataId(String dataId) {
        return mapper.selectProductTagByDataId(dataId);
    }

    @Override
    @DS("configds")
    public List<ProductConfig> selectAllTags() {
        QueryWrapper<ProductConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("VALID_FLAG","0").eq("REMARK","房源标签").eq("TAG_TYPE_ID","HOUSING_STOCK_TAG");
        return mapper.selectList(queryWrapper);
    }
}
