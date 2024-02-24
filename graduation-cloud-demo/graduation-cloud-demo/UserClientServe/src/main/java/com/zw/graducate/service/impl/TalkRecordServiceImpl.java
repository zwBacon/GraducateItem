package com.zw.graducate.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.zw.graducate.entity.TalkRecord;
import com.zw.graducate.entity.UserInfo;
import com.zw.graducate.mapper.TalkRecordMapper;
import com.zw.graducate.service.TalkRecordService;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2024/1/26 12:34
 */
@Service
public class TalkRecordServiceImpl implements TalkRecordService {

    @Resource
    private TalkRecordMapper mapper;

    @Resource
    private MongoTemplate template;
    @Override
    public List<TalkRecord> selAllRecordOfOne(String userId, String toUserId) {

        //用来封装所有条件的对象
        Query query = new Query();
        //用来构建条件
        Criteria criteria = new Criteria();
        Criteria criteria2 = new Criteria();

        criteria2.and("userId").is(toUserId).and("toUserId").is(userId);
        criteria.and("userId").is(userId).and("toUserId").is(toUserId);

        Criteria rescriteria = new Criteria().orOperator(criteria,criteria2);
        query.addCriteria(rescriteria);

        List<TalkRecord> talkRecords = template.find(query, TalkRecord.class);
        Comparator<TalkRecord> c = new Comparator<TalkRecord>() {
            @Override
            public int compare(TalkRecord o1, TalkRecord o2) {
                return o1.getDatetime().compareTo(o2.getDatetime());
            }
        };

        Collections.sort(talkRecords,c);
        return talkRecords;
    }

    @Override
    public List<String> selAllChatedInfo(String userId) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("userId").is(userId);

        query.fields().include("toUserId");
        query.addCriteria(criteria);

        List<String> toUserIds = template.findDistinct(query, "toUserId",TalkRecord.class,String.class);
        return toUserIds;
    }

    @Override
    public TalkRecord addNewChatInfo(String name, String toName, String userId, String toUserId, String message) {
//        时间生成
        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime now = LocalDateTime.now();
        String format = now.format(formatter);

        TalkRecord newTalkRecord = new TalkRecord(RandomStringUtils.randomAlphanumeric(25).toLowerCase(),name,toName,message,userId,toUserId,format);
        return template.insert(newTalkRecord, "talkRecord");
    }
}
