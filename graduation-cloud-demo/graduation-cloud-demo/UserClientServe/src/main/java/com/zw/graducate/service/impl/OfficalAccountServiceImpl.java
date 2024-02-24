package com.zw.graducate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zw.graducate.entity.OfficalAccount;
import com.zw.graducate.entity.SubscribedAccount;
import com.zw.graducate.mapper.OfficalAccountMapper;
import com.zw.graducate.service.OfficalAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2023/12/10 22:15
 */
@Service
public class OfficalAccountServiceImpl implements OfficalAccountService {

    @Autowired
    private OfficalAccountMapper accountMapper;

    @Override
    public List<OfficalAccount> selectAllListAccount() {
        QueryWrapper<OfficalAccount> wrapper = new QueryWrapper<>();
        return accountMapper.selectList(wrapper);
    }

    @Override
    public List<SubscribedAccount> selectSubscriedAccount(String username) {
        return accountMapper.selectSubscriedAccount(username);
    }

    @Override
    public OfficalAccount selectOneOfficalAccountByAccountName(String accountName) {
        QueryWrapper<OfficalAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ACCOUNT_NAME", accountName);
        return accountMapper.selectOne(queryWrapper);
    }

    @Override
    public List<OfficalAccount> selectSubScribedAccByUserId(String userId) {
        return accountMapper.selectSubScribedAccByUserId(userId);
    }

    @Override
    public Integer deleteSubscribed(String userId,String accountIndex) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime now = LocalDateTime.now();
        String format = now.format(formatter);
        return accountMapper.deleteSubscribed(userId,accountIndex,format);
    }
}
