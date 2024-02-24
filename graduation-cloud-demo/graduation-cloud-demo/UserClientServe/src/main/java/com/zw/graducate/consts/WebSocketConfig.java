package com.zw.graducate.consts;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.server.ServerEndpoint;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2024/1/28 18:36
 */
@Configuration
public class WebSocketConfig {

    @Bean
//    注入ServerEndpointExporter,自动注册使用@ServerEndpoint注解的
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

}
