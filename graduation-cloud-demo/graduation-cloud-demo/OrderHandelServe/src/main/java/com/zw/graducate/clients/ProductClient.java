package com.zw.graducate.clients;

import com.zw.graducate.entity.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2024/2/4 20:42
 */

@FeignClient("productshow")
public interface ProductClient {

    @PostMapping("/productDetail/selectOneProductDetailById")
    Map<String,Object> selectOneProductDetailById(@RequestBody Map<String, String> request);

    @GetMapping("/productServe/selectOneProductByID")
    Map<String,Object> selectOneProductByID(@RequestParam("productId")String productId);

    @PostMapping("/productServe/dataHandel")
    public String EsAndMysqlDataHandelAfterPay(@RequestBody Map<String,Object> request);

}
