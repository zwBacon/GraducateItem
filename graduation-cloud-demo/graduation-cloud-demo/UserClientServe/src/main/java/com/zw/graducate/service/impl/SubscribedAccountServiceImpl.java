package com.zw.graducate.service.impl;

import com.zw.graducate.entity.SubscribedAccount;
import com.zw.graducate.mapper.SubscribedAccountMapper;
import com.zw.graducate.service.SubscribedAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2023/12/16 22:45
 */

@Service
public class SubscribedAccountServiceImpl implements SubscribedAccountService {

    @Autowired
    private SubscribedAccountMapper subscribedAccountMapper;

    @Override
    public int addAccount(SubscribedAccount subscribedAccount) {
        return subscribedAccountMapper.insert(subscribedAccount);
    }
}
