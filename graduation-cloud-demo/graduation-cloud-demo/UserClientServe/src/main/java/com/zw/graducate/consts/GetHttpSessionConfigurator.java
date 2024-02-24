package com.zw.graducate.consts;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2024/1/28 18:39
 */

public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession = (HttpSession)request.getHttpSession();
//        将httpSession对象存储到配置对象中
        sec.getUserProperties().put(HttpSession.class.getName(),httpSession);
    }
}
