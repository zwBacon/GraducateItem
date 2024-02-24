package com.zw.graducate.controller;

import com.alibaba.fastjson.JSON;
import com.zw.graducate.consts.GetHttpSessionConfigurator;
import com.zw.graducate.entity.TalkRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2024/1/28 18:14
 */

@ServerEndpoint(value = "/webSocket",configurator = GetHttpSessionConfigurator.class)
@Component
@CrossOrigin(origins = {"*", "null"})
public class ChatEndPoint {

    private static final Map<String,Session> onlineUsers = new ConcurrentHashMap<>();

    private HttpSession httpSession;

    @OnOpen
//    建立连接时调用
    public void onOpen(Session session, EndpointConfig config){
//        1.将session保存
        this.httpSession = (HttpSession)config.getUserProperties().get(HttpSession.class.getName());
        String username = (String) this.httpSession.getAttribute("username");
        onlineUsers.put(username,session);
    }

    @OnMessage
//    接收到客户端发送的数据时被调用
    public void onmessage(String message){
        try {
//        将消息推送给指定的用户
            TalkRecord talkRecord = JSON.parseObject(message, TalkRecord.class);

//        消息接收方的用户名和用户编号
            String toName = talkRecord.getToName();
            String toUserId = talkRecord.getToUserId();
//        消息信息
            String info = talkRecord.getMessage();

//        获取消息接收方用户对象的session对象
            Session session = onlineUsers.get(toName);
            String user = (String) this.httpSession.getAttribute("username");
            TalkRecord sendMessage = new TalkRecord();
            sendMessage.setName(user);
            sendMessage.setMessage(talkRecord.getMessage());
            session.getBasicRemote().sendText(JSON.toJSONString(sendMessage));
        } catch (IOException e) {

        }
    }

    @OnClose
//    连接关闭时调用
    public void onClose(Session session){
//        1.从集合中剔除当前用户的session
        String username = (String)this.httpSession.getAttribute("username");
        onlineUsers.remove(username);
    }

}
