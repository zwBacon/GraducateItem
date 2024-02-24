package com.zw.graducate.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2024/2/19 12:52
 */
@Data
public class FirstHeadProductImgeBean {

        private MultipartFile file;
        private String productId;


}
