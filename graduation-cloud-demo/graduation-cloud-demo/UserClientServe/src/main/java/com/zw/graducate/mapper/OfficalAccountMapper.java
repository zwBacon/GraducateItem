package com.zw.graducate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zw.graducate.entity.OfficalAccount;
import com.zw.graducate.entity.SubscribedAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface OfficalAccountMapper extends BaseMapper<OfficalAccount> {

//    查询已关注的名单
    @Select("select * from offical_account_subsribe where SUBSCRIBED_USERNAME = #{username} and IS_SUBSCRIBED != '0'")
    public List<SubscribedAccount> selectSubscriedAccount(String username);

    @Select("select * from offical_general_accounts where ACCOUNT_INDEX in (select SUBSCRIBED_ACCOUNT_INDEX from offical_account_subsribe where SUBSCRIBED_USERID = #{userId} and IS_SUBSCRIBED != '0')")
    public List<OfficalAccount> selectSubScribedAccByUserId(@Param("userId") String userId);

    @Update("update offical_account_subsribe set IS_SUBSCRIBED = '0',DESUBCRIBED_TIME = #{desubscribedTime} where SUBSCRIBED_USERID = #{userId} and SUBSCRIBED_ACCOUNT_INDEX = #{accountIndex}")
    public Integer deleteSubscribed(@Param("userId")String userId, @Param("accountIndex")String accountIndex,@Param("desubscribedTime") String desubscribedTime);
}
