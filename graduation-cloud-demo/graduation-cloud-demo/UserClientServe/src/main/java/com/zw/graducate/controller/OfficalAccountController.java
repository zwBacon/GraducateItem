package com.zw.graducate.controller;

import cn.hutool.crypto.digest.DigestUtil;
import com.zw.graducate.entity.SubscribedAccount;
import com.zw.graducate.interfaceAop.CustomerAopNote;
import com.zw.graducate.service.OfficalAccountService;
import com.zw.graducate.entity.OfficalAccount;
import com.zw.graducate.mapper.SubscribedAccountMapper;
import com.zw.graducate.service.SubscribedAccountService;
import com.zw.graducate.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2023/12/10 22:16
 */
@RestController
@RequestMapping("/officalAccountController")
@CrossOrigin(origins = {"*", "null"})
public class OfficalAccountController {

    @Autowired
    private OfficalAccountService service;

    @Autowired
    private SubscribedAccountService accountService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserInfoService userInfoService;



    //   查询未关注的公众号
    @PostMapping("/headPageSubsribeShow")
    public List<OfficalAccount> headPageSubsribeShow(@RequestBody Map<String, String> request) {

        String userName = request.get("userName");

//       查询所有的公众号列表
        List<OfficalAccount> officalAccounts = service.selectAllListAccount();

//       查询已关注列表
        List<SubscribedAccount> subscribedAccounts = service.selectSubscriedAccount(userName);

        if (subscribedAccounts.isEmpty()) {
            return officalAccounts;
        }

        Predicate<OfficalAccount> filterCondition = officialAccount -> subscribedAccounts.stream().
                map(SubscribedAccount::getSubscribedAccountIndex).collect(Collectors.toList()).contains(officialAccount.getAccountIndex());

        List<OfficalAccount> filteredUsers = officalAccounts.stream()
                .filter(filterCondition.negate())
                .collect(Collectors.toList());

        return filteredUsers;

    }

    //   关注公众号
    @PostMapping("/addSubscribeAccount")
    public Map<String, Object>  addSubscribeAccount(@RequestBody Map<String, String> request) {
        Map<String, Object> result = new HashMap<>();
//        获取UserName
        String userName = request.get("userName");
//        获取USERID
        String userID = "";
        String redisKey = "usernameInfo:" + DigestUtil.sha256Hex(userName);

        Boolean exist = stringRedisTemplate.hasKey(redisKey);

        if (exist) {
            userID = stringRedisTemplate.opsForHash().entries(redisKey).get("userID").toString();
        }else {
            userID = userInfoService.selectUserInfoByName(userName).getUserID();
        }
//        获取公众号名
        String accountName = request.get("accountName");
//        获取公众号索引
        Integer accountIndex = service.selectOneOfficalAccountByAccountName(accountName).getAccountIndex();

//        入表
        SubscribedAccount subscribedAccount = SubscribedAccount.builder().subscribedUserName(userName)
                .subscribedAccountName(accountName).subscribedUserID(userID).subscribedAccountIndex(accountIndex)
                .subscribedTime(LocalDateTime.now()).isSubscribed(1).build();
        accountService.addAccount(subscribedAccount);

        result.put("result","关注成功！！");

        return result;
    }


    @GetMapping("/selectSubScribedAccByUserId")
    public Map<String, Object> selectSubScribedAccByUserId(String userId) {
        Map<String, Object> result = new HashMap<>();
        List<OfficalAccount> officalAccounts = service.selectSubScribedAccByUserId(userId);
        result.put("body",officalAccounts);
        return result;
    }

    @GetMapping("/deletSubscribed")
    public Map<String, Object> deletSubscribed(String userId,String accountIndex) {
        Map<String, Object> result = new HashMap<>();
        Integer res = service.deleteSubscribed(userId, accountIndex);
        result.put("body",res);
        return result;
    }

}
