package com.zw.graducate.clients;

import com.zw.graducate.entity.UserInfo;
import com.zw.graducate.interfaceAop.CustomerAopNote;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2024/2/4 20:42
 */

@FeignClient("userclient")
public interface UserClient {

    @PostMapping("/baseUserController/selectUserInfoByUserId")
    UserInfo selectUserInfoByUserId(Map<String, String> request);

    @GetMapping("/baseUserController/selectUserIdByNameOrPhone")
    String selectUserIdByNameOrPhone(@RequestParam(value = "name")String name,@RequestParam(value = "phone")String phone);

}
