package com.zw.graducate.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Repository;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2024/1/26 11:04
 */
@Document(collection = "talkRecord")
@Data
@Repository
public class TalkRecord implements Comparable<TalkRecord> {

    @Id
    @Field("_id")
    private String _id;


    @Field("name")
    @Indexed
    private String name;

    @Field("toName")
    @Indexed
    private String toName;

    @Field("message")
    private String message;

    @Field("userId")
    @Indexed
    private String userId;

    @Field("toUserId")
    @Indexed
    private String toUserId;

    @Field("datetime")
    private String datetime;

    public TalkRecord() {
    }

    @Override
    public int compareTo(TalkRecord o) {
        return getDatetime().compareTo(o.getDatetime());
    }

    public TalkRecord(String _id, String name, String toName, String message, String userId, String toUserId, String datetime) {
        this._id = _id;
        this.name = name;
        this.toName = toName;
        this.message = message;
        this.userId = userId;
        this.toUserId = toUserId;
        this.datetime = datetime;
    }
}
