package com.zw.graducate.mapper;

import com.zw.graducate.entity.Log;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.annotation.sql.DataSourceDefinition;

@Mapper
@DataSourceDefinition(url = "jdbc:mysql://localhost:3306/log?useSSL=false&serverTimezone=Asia/Shanghai",user = "root",password = "264017",databaseName = "log",className = "com.mysql.cj.jdbc.Driver", name = "root")
public interface LogHandlerMapper {

    @Insert("insert into log.${table_name} values (null,#{log.callType},#{log.method},#{log.followId},#{log.params},#{log.url},#{log.ip},#{log.callTime},#{log.response})")
    public void save(@Param("table_name") String table_name, @Param("log") Log log);

}
