package com.zw.graducate.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zw.graducate.entity.FirstHeadProductDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface FirstHeadProductService {

    public IPage<FirstHeadProductDTO> selectAllProductByPage(Integer beginNum, Integer size,String city);

    public IPage<FirstHeadProductDTO> selectAllProductByPageNew(Integer beginNum, Integer size,String city);

    public IPage<FirstHeadProductDTO> selectAllHousesByCondition(Integer beginNum, Integer size,String city,String type);

    public List<FirstHeadProductDTO> firstHeadProductResponseDataHandel(List<FirstHeadProductDTO> records);

    public Integer selectAllProductCountByCity(String city);

    public Integer selectAllProductCountByCityAndCondition(String city,String type);

    public FirstHeadProductDTO selectOneProductByID(String productId);

    public List<FirstHeadProductDTO> selectAllProductIsSubscribed(String userId,String type);

    public List<FirstHeadProductDTO> selectAllProductIsSubscribedNew(String userId,String type);

    public Integer updateProductState(String productId,String state);

    public Integer addNewHeadProduct(FirstHeadProductDTO firstHeadProductDTO);

    public void ImageInsert(byte[] images,String ID);

    public IPage<FirstHeadProductDTO> selectAllConfimingProduct(Integer beginNum, Integer size);

    Map<String,Object> selectAllMyPubshingByCondition(Integer currentPage,Integer pageSize,String productName,String type,String state,String userId);

    public Integer againstProductPublish(String productId,String againstReason);

    public String lookUpResaon(String productId);

    public Integer updateTagAndState(String productId,String tag);

    public Integer reverseApplication(String productId);

    public Integer offShellHandel(String productId);

}
