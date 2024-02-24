package com.zw.graducate.service;

import com.zw.graducate.entity.TalkRecord;
import com.zw.graducate.entity.UserInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


public interface TalkRecordService {

//    查询所有聊天记录
    public List<TalkRecord> selAllRecordOfOne(String userId,String toUserId);

//    查询所有已经有聊天记录的人信息
    public List<String> selAllChatedInfo(String userId);

//    添加聊天记录
    public TalkRecord addNewChatInfo(String name,String toName,String userId,String toUserId,String message);

}
