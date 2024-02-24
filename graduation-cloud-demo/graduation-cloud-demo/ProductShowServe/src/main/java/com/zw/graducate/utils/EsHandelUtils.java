package com.zw.graducate.utils;

import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2024/2/7 20:04
 */
public class  EsHandelUtils {

    @Bean
    public static UpdateRequest HandelEsUpdateMethod(String indexName,String index,String key1,String param1) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(indexName,index);

//        准备请求参数
        updateRequest.doc(key1,param1);

        return updateRequest;
    }

}
