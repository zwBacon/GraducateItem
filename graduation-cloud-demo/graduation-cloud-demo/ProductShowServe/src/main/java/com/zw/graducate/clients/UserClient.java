package com.zw.graducate.clients;

import com.zw.graducate.entity.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2024/1/22 14:39
 */
@FeignClient("userclient")
public interface UserClient {

    @PostMapping("/baseUserController/selectUserInfoByUserId")
    UserInfo selectUserInfoByUserId(Map<String, String> request);

}
