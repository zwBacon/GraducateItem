package com.zw.graducate.controller;

import com.zw.graducate.entity.TalkRecord;
import com.zw.graducate.entity.UserInfo;
import com.zw.graducate.interfaceAop.CustomerAopNote;
import com.zw.graducate.service.TalkRecordService;
import com.zw.graducate.service.UserInfoService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2024/1/26 12:07
 */
@RestController
@RequestMapping("/userTalkController")
@CrossOrigin(origins = {"*", "null"})
public class TalkRecordController {

     @Autowired
     private TalkRecordService service;
     
     @Autowired
     private UserInfoService userInfoService;

     @GetMapping("/selAllRecordOfOne")
     public List<TalkRecord> selAllRecordOfOne(String userId, String toUserId) {
         return service.selAllRecordOfOne(userId, toUserId);
     }
     
     @GetMapping("/selAllChatedInfo")
     public Map<String,Object> selAllChatedInfo(String userId){
          Map<String,Object> result = new HashMap<>();

          List<String> toUserIds = service.selAllChatedInfo(userId);
          List<UserInfo> userInfoList = new ArrayList<>();
          for (int i = 0; i < toUserIds.size(); i++) {
               String username = userInfoService.selectUserInfoByUserId(toUserIds.get(i));
               UserInfo userInfoDetail = userInfoService.selectUserInfoByName(username);
               UserInfo userInfoTitle = userInfoService.selectUserTitle(username);
               userInfoDetail.set_headImg(Base64.getEncoder().encodeToString(userInfoTitle.getHeadimg()));
               userInfoList.add(userInfoDetail);
          }
          result.put("body",userInfoList);
          return result;
     }

     @GetMapping("/selConditionChatedInfo")
     public Map<String,Object> selConditionChatedInfo(String userId,String inputUserName){
          Map<String,Object> result = new HashMap<>();

          List<String> toUserIds = service.selAllChatedInfo(userId);
          List<UserInfo> userInfoDetail = userInfoService.selectConditionUserInfoByUserId(toUserIds, inputUserName);
          result.put("body",userInfoDetail);
          return result;
     }

     @PostMapping("/addNewChatInfo")
     public Map<String,Object> addNewChatInfo(@RequestBody Map<String, String> request){
          Map<String,Object> result = new HashMap<>();

          String name = request.get("name");
          String toName = request.get("toName");
          String userId = request.get("userId");
          String toUserId = request.get("toUserId");
          String message = request.get("message");
          TalkRecord talkRecord = service.addNewChatInfo(name, toName, userId, toUserId, message);
          result.put("body",talkRecord);

          return result;
     }

}
