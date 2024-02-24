package com.zw.graducate.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zw.graducate.entity.FirstHeadProductDTO;
import com.zw.graducate.mapper.FirstHeadProductMapper;
import com.zw.graducate.mapper.ProductTagMapper;
import com.zw.graducate.service.FirstHeadProductService;
import com.zw.graducate.service.ProductTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2024/1/1 13:39
 */

@Service
public class FirstHeadProductServiceImpl implements FirstHeadProductService {

    @Autowired
    private FirstHeadProductMapper mapper;

    @Autowired
    private  ProductTagMapper tagMapper;

    @Override
    public IPage<FirstHeadProductDTO> selectAllProductByPage(Integer beginNum, Integer size,String city) {
        Page<FirstHeadProductDTO> page = new Page<>(beginNum, size);
        LambdaQueryWrapper<FirstHeadProductDTO> queryWrapper = Wrappers.<FirstHeadProductDTO>lambdaQuery();
        queryWrapper.eq(FirstHeadProductDTO::getCity,city);
        queryWrapper.eq(FirstHeadProductDTO::getState,'1');
        queryWrapper.orderByDesc(FirstHeadProductDTO::getCreateTime);
        IPage<FirstHeadProductDTO> firstHeadProductDTOIPage = mapper.selectPage(page,queryWrapper);

//        面积字段处理
        List<FirstHeadProductDTO> records = firstHeadProductDTOIPage.getRecords();
        List<FirstHeadProductDTO> productDTOS = this.firstHeadProductResponseDataHandel(records);
        firstHeadProductDTOIPage.setRecords(productDTOS);
        return firstHeadProductDTOIPage;
    }

    @Override
    public List<FirstHeadProductDTO> firstHeadProductResponseDataHandel(List<FirstHeadProductDTO> records){
        for(FirstHeadProductDTO dto : records){
            if(dto.getArea().contains("-")){
                dto.setArea("建面 " + dto.getArea() + "m²");
            }else {
                dto.setArea(dto.getArea() + "m²");
            }
            //        标签处理
            if(StringUtils.isNotBlank(dto.getTagList())) {
                String dataIds = dto.getTagList().replace("，", ",");
                String newTagList = tagMapper.selectProductTagByDataId(dataIds).toString().replace("[", "").replace("]", "").replace(" ", "");
                dto.setTagList(newTagList);
            }
            //       价格处理
            if(dto.getType()=='1'){
                int price = dto.getPrice() / 10000;
                int area = Integer.parseInt(dto.getArea().replace("m²",""));
                String priceDescription = dto.getPrice() / area + "元/平";
                dto.setPricePerMeter(priceDescription);
                dto.setPriceDescription("万");
                dto.setPrice(price);
            }else if(dto.getType()=='0'){
                dto.setPriceDescription("元/平");
            }else if(dto.getType()=='2'){
                dto.setPriceDescription("元/月");
            }
        }
        return records;
    }

    @Override
    public IPage<FirstHeadProductDTO> selectAllProductByPageNew(Integer beginNum, Integer size,String city) {
        Page<FirstHeadProductDTO> page = new Page<>(beginNum, size);
        LambdaQueryWrapper<FirstHeadProductDTO> queryWrapper = Wrappers.<FirstHeadProductDTO>lambdaQuery();
        queryWrapper.eq(FirstHeadProductDTO::getCity,city);
        queryWrapper.eq(FirstHeadProductDTO::getState,'1');
        queryWrapper.orderByDesc(FirstHeadProductDTO::getCreateTime);
        IPage<FirstHeadProductDTO> firstHeadProductDTOIPage = mapper.selectPage(page,queryWrapper);

        return firstHeadProductDTOIPage;
    }

    @Override
    public IPage<FirstHeadProductDTO> selectAllHousesByCondition(Integer beginNum, Integer size, String city, String type) {
        Page<FirstHeadProductDTO> page = new Page<>(beginNum, size);
        LambdaQueryWrapper<FirstHeadProductDTO> queryWrapper = Wrappers.<FirstHeadProductDTO>lambdaQuery();
        queryWrapper.eq(FirstHeadProductDTO::getCity,city);
        queryWrapper.eq(FirstHeadProductDTO::getType,type);
        queryWrapper.eq(FirstHeadProductDTO::getState,'1');
        queryWrapper.orderByDesc(FirstHeadProductDTO::getCreateTime);
        IPage<FirstHeadProductDTO> firstHeadProductDTOIPage = mapper.selectPage(page,queryWrapper);

//        面积字段处理
        List<FirstHeadProductDTO> records = firstHeadProductDTOIPage.getRecords();
        List<FirstHeadProductDTO> productDTOS = this.firstHeadProductResponseDataHandel(records);
        firstHeadProductDTOIPage.setRecords(productDTOS);
        return firstHeadProductDTOIPage;
    }

    @Override
    public Integer selectAllProductCountByCity(String city) {
        return mapper.selectAllProductCountByCity(city);
    }

    @Override
    public Integer selectAllProductCountByCityAndCondition(String city, String type) {
        return mapper.selectAllProductCountByCityAndCondition(city,type);
    }

    @Override
    public FirstHeadProductDTO selectOneProductByID(String productId) {
        QueryWrapper<FirstHeadProductDTO> wrapper = new QueryWrapper<>();
        wrapper.eq("Product_ID",productId);
        return mapper.selectOne(wrapper);
    }

    @Override
    public List<FirstHeadProductDTO> selectAllProductIsSubscribed(String userId,String type) {
        return mapper.selectAllProductIsSubscribed(userId,type);
    }

    @Override
    public List<FirstHeadProductDTO> selectAllProductIsSubscribedNew(String userId, String type) {
        return mapper.selectAllProductIsSubscribedNew(userId, type);
    }

    @Override
    @DS("master")
    public Integer updateProductState(String productId, String state) {
        return mapper.updateProductState(productId, state);
    }

    @Override
    public Integer addNewHeadProduct(FirstHeadProductDTO firstHeadProductDTO) {
        return mapper.insert(firstHeadProductDTO);
    }

    @Override
    public void ImageInsert(byte[] images, String ID) {
         mapper.ImageInsert(images, ID);
    }

    @Override
    public IPage<FirstHeadProductDTO> selectAllConfimingProduct(Integer beginNum, Integer size) {
        Page<FirstHeadProductDTO> page = new Page<>(beginNum, size);
        LambdaQueryWrapper<FirstHeadProductDTO> queryWrapper = Wrappers.<FirstHeadProductDTO>lambdaQuery();
        queryWrapper.ne(FirstHeadProductDTO::getState,'3');
        queryWrapper.ne(FirstHeadProductDTO::getState,'4');
        queryWrapper.ne(FirstHeadProductDTO::getType,'0');
        queryWrapper.orderByDesc(FirstHeadProductDTO::getCreateTime);
        queryWrapper.orderByDesc(FirstHeadProductDTO::getState);
        IPage<FirstHeadProductDTO> firstHeadProductDTOIPage = mapper.selectPage(page,queryWrapper);

//        面积字段处理
        List<FirstHeadProductDTO> records = firstHeadProductDTOIPage.getRecords();
        List<FirstHeadProductDTO> productDTOS = this.firstHeadProductResponseDataHandel(records);
        firstHeadProductDTOIPage.setRecords(productDTOS);
        return firstHeadProductDTOIPage;
    }

    @Override
    public Map<String, Object> selectAllMyPubshingByCondition(Integer currentPage, Integer pageSize, String productName, String type, String state, String userId) {
        Map<String, Object> result = new HashMap<>();

        Page<FirstHeadProductDTO> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<FirstHeadProductDTO> queryWrapper = Wrappers.<FirstHeadProductDTO>lambdaQuery();
        if(StringUtils.isNotBlank(productName)){
            queryWrapper.like(FirstHeadProductDTO::getProductName,productName);
        }
        if(StringUtils.isNotBlank(type)){
            queryWrapper.eq(FirstHeadProductDTO::getType,type);
        }
        if(StringUtils.isNotBlank(state)){
            queryWrapper.eq(FirstHeadProductDTO::getState,state);
        }
        queryWrapper.eq(FirstHeadProductDTO::getUserID,userId);
        queryWrapper.ne(FirstHeadProductDTO::getType,'0');
        queryWrapper.orderByDesc(FirstHeadProductDTO::getCreateTime);
        queryWrapper.orderByDesc(FirstHeadProductDTO::getState);
        IPage<FirstHeadProductDTO> dtoPage = mapper.selectPage(page,queryWrapper);


        List<FirstHeadProductDTO> productDTOS = this.firstHeadProductResponseDataHandel(dtoPage.getRecords());

        result.put("records",productDTOS);
        result.put("totalCount",dtoPage.getTotal());
        return result;
    }

    @Override
    public Integer againstProductPublish(String productId, String againstReason) {
        UpdateWrapper<FirstHeadProductDTO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("PRODUCT_ID", productId).set("STATE", "0").set("AGAINST_REASON", againstReason);
        return mapper.update(null,updateWrapper);
    }

    @Override
    public String lookUpResaon(String productId) {
        QueryWrapper<FirstHeadProductDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("PRODUCT_ID",productId);
        return mapper.selectOne(queryWrapper).getAgainstReason();
    }

    @Override
    public Integer updateTagAndState(String productId, String tag) {
        UpdateWrapper<FirstHeadProductDTO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("PRODUCT_ID", productId).set("STATE", "1").set("TAGLIST", tag);
        return mapper.update(null,updateWrapper);
    }

    @Override
    public Integer reverseApplication(String productId) {
        UpdateWrapper<FirstHeadProductDTO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("PRODUCT_ID", productId).set("STATE", "4");
        return mapper.update(null,updateWrapper);
    }

    @Override
    public Integer offShellHandel(String productId) {
        UpdateWrapper<FirstHeadProductDTO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("PRODUCT_ID", productId).set("STATE", "5");
        return mapper.update(null,updateWrapper);
    }
}
