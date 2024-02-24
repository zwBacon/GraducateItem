package com.zw.graducate.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2024/2/22 17:38
 */
@Data
public class ProductDetailsImgeBean {

    private MultipartFile file;
    private String productId;
    private String sign;

}
