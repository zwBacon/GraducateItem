package com.zw.graducate.mapper;

import com.zw.graducate.entity.TalkRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

@Mapper
public interface TalkRecordMapper extends MongoRepository<TalkRecord,String> {


}
