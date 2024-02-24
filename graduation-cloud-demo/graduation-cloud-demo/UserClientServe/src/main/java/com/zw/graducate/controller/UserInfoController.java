package com.zw.graducate.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.zw.graducate.entity.UserInfo;
import com.zw.graducate.interfaceAop.CustomerAopNote;
import com.zw.graducate.service.UserInfoService;
import com.zw.graducate.util.AESUtil;
import com.zw.graducate.util.BeanToMapUtil;
import com.zw.graducate.util.RSAUtil;
import com.zw.graducate.consts.RedisConst;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2023/12/3 13:23
 */
@RestController
@RequestMapping("/baseUserController")
@CrossOrigin(origins = {"*", "null"})
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    //    用户登录
    @PostMapping("/userLoad")
    @CustomerAopNote
    public Map<String, Object> userLoad(@RequestBody Map<String, String> request , HttpSession session) {
        Map<String, Object> result = new HashMap<String, Object>();

        String username = request.get("username");
        String password = request.get("code");

        String redisKey = "username:" + DigestUtil.sha256Hex(username);

        Boolean exist = stringRedisTemplate.hasKey(redisKey);

        if (exist) {
            Map<Object, Object> resultFromRedis = stringRedisTemplate.opsForHash().entries(redisKey);
            String userId = (String)resultFromRedis.get("userId");
            String encodePassword = (String) resultFromRedis.get("passWord");
            String dePassword = RSAUtil.decryptData(encodePassword, RSAUtil.generatePrivateKey());
            result.put("result", dePassword != null && dePassword.equals(password) ? true : "密码错误，请重新输入！");
            result.put("userId",userId);
            if(dePassword != null && dePassword.equals(password)) {
                session.setAttribute("username", username);
            }
            return result;
        }

        UserInfo userInfo = userInfoService.selectUserNameAndPasswordByName(username);
        if (ObjectUtils.isEmpty(userInfo)) {
            result.put("result", "用户不存在！");
            return result;
        }

        Map<String, Object> userInfoMap = new HashMap<>();
        userInfoMap.put("userName", userInfo.getUserName());
        userInfoMap.put("passWord", userInfo.getPassword());
        userInfoMap.put("userId",userInfo.getUserID());
//          2.写入缓存
        stringRedisTemplate.opsForHash().putAll(redisKey, userInfoMap);
//          2.1设置过期时间
        stringRedisTemplate.expire(redisKey, RedisConst.Login_User_Expire_Time, TimeUnit.MINUTES);
        String encodePassword = (String) userInfoMap.get("passWord");
        String dePassword = RSAUtil.decryptData(encodePassword, RSAUtil.generatePrivateKey());
        result.put("result", dePassword != null && dePassword.equals(password) ? true : "密码错误，请重新输入！");
        result.put("userId",userInfo.getUserID());
        if(dePassword != null && dePassword.equals(password)) {
            session.setAttribute("username", username);
        }
        return result;
    }

    //      查询用户全部信息
    @GetMapping("/selectUserInfoByName")
    @CustomerAopNote
    public Map<String, Object> selectUserInfoByName(String username) {

        Map<String, Object> result = new HashMap<String, Object>();

        String redisKey = "usernameInfo:" + DigestUtil.sha256Hex(username);

        Boolean exist = stringRedisTemplate.hasKey(redisKey);

        if (exist) {
            Map<Object, Object> resultFromRedis = stringRedisTemplate.opsForHash().entries(redisKey);
            result.put("result", resultFromRedis);
            return result;
        }

        UserInfo userInfo = userInfoService.selectUserInfoByName(username);
        userInfo.setEmail(AESUtil.decrpt(userInfo.getEmail()));
        Map<String, Object> userInfoMap = BeanToMapUtil.beanToMap(userInfo);

//          2.写入缓存
//        查询用户头像
        String title = selectUserTitle(username);
        if (StringUtils.isNotBlank(title)) {
            userInfoMap.put("headimg", title);
        }
//        更改用户头像字段
        stringRedisTemplate.opsForHash().putAll(redisKey, userInfoMap);

//          2.1设置过期时间
        stringRedisTemplate.expire(redisKey, RedisConst.Login_User_Expire_Time, TimeUnit.MINUTES);
        result.put("result", userInfoMap);
        return result;
    }

    //      查询用户头像
    private String selectUserTitle(String username) {

        String redisKey = "usernameTitle:" + DigestUtil.sha256Hex(username);

        Boolean exist = stringRedisTemplate.hasKey(redisKey);
        if (exist) {
            return stringRedisTemplate.opsForValue().get(redisKey);
        }
        UserInfo userTitle = userInfoService.selectUserTitle(username);

        if (userTitle==null){
            return null;
        }

//          2.写入缓存
        stringRedisTemplate.opsForValue().set(redisKey, Base64.getEncoder().encodeToString(userTitle.getHeadimg()));
//          2.1设置过期时间
        stringRedisTemplate.expire(redisKey, RedisConst.Login_User_Expire_Time, TimeUnit.MINUTES);
        return Base64.getEncoder().encodeToString(userTitle.getHeadimg());
    }


    //       用户注册
    @PostMapping("/userRegister")
    @CustomerAopNote
    public Map<String, Object> UserRegister(@RequestBody Map<String, String> request,HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        String body = registerModify(request);
        if (!body.equals("true")) {
            result.put("body", body);
            return result;
        }

        UserInfo userInfo = BeanToMapUtil.mapToBean(request, UserInfo.class);

        userInfo.setPassword(RSAUtil.encryptData(userInfo.getPassword(), RSAUtil.generatePublicKey()));
        userInfo.setPhone(AESUtil.encrpt(userInfo.getPhone()));
        userInfo.setEmail(AESUtil.encrpt(userInfo.getEmail()));
        userInfo.setCreateTime(LocalDateTime.now());
        userInfo.setIsdelete('0');
        String userId = "GCCI-U-" + RandomStringUtils.randomAlphanumeric(18);
        userInfo.setUserID(userId);

        int reister = userInfoService.userReister(userInfo);

        session.setAttribute("username",request.get("username"));
        result.put("body", reister);
        result.put("userId", userId);

//        存入redis
        return result;
    }

    private String registerModify(Map<String, String> request) {
        //        1.用户名
        String username = request.get("username");
        if (StringUtils.isBlank(username)) {
            return "请输入用户名";
        }

        if (username.length() > 15) {
            return "用户名过长";
        }
//        1.1用户名是否存在
        int count = userInfoService.selectUserNameExists(username);

        if (count > 0) {
            return "用户名已存在";
        }
//        2.密码
        String password = request.get("password");

        if (StringUtils.isBlank(password)) {
            return "请输入密码";
        }
//        2.1密码长度校验
        if (password.length() < 8) {
            return "密码要在八位以上";
        } else if (password.length() > 16) {
            return "密码长度过长";
        }
//        2.2密码格式校验
        if (!password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+={}\\[\\]:;,<.>/?]).{8,}$")) {
            return "密码格式错误";
        }

//        3.手机号
        String phone = request.get("phone");

        if (StringUtils.isBlank(phone)) {
            return "请输入手机号";
        }

//        3.1手机号长度校验
        if (phone.length() != 11) {
            return "手机号必须为11位";
        }
//        3.2手机号格式校验
        if (!phone.matches("1(3[0-9]|4[01456879]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[0-35-9])\\d{8}")) {
            return "请输入正确格式的手机号";
        }
//        3.3手机号存在校验
        if (userInfoService.selectUserPhoneAll().contains(phone)) {
            return "手机号已存在";
        }

//        4.验证码
        String modifyCode = request.get("modifyCode");

        if (StringUtils.isBlank(modifyCode)) {
            return "请输入验证码";
        }
//        4.1获取图片验证码值
        if (!stringRedisTemplate.hasKey("modifyCode")) {
            return "验证码已失效";
        }
        if (!stringRedisTemplate.opsForValue().get("modifyCode").equals(modifyCode)) {
            return "验证码错误，请重新输入";
        }
        return "true";
    }

    //    获取图片验证码
    @RequestMapping("/getImage")
    public void ImgCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String rediskey = "modifyCode";
        // 定义图形验证码的长、宽、验证码字符数、干扰元素个数
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 30);
        // 图形验证码写出，可以写出到文件，也可以写出到流
        captcha.write("D:/Graduation-Design/graduation-design-demo/src/assets/登录页资源/captcha.png");
        String code = captcha.getCode();
//    存储进redis
        stringRedisTemplate.opsForValue().set(rediskey, code);
        stringRedisTemplate.expire(rediskey, RedisConst.Pic_Smactha_Expire_Time, TimeUnit.MINUTES);
        // 关闭流
        response.getOutputStream().close();
    }


//    用户上传头像


    //    忘记密码
    @PostMapping("/forgetPassword")
    @CustomerAopNote
    public Map<String, Object> forgetPassword(@RequestBody Map<String, String> request) {
        String modifyResult = forgetPasswordModify(request);
        Map<String, Object> result = new HashMap<>();
        if (!modifyResult.equals("true")) {
            result.put("body", modifyResult);
            return result;
        }

        String newPhone = request.get("newPhone");
        String newPassword = request.get("newPassword");

//        更新
        userInfoService.updatePasswordByPhone(newPhone, newPassword);

        //        刷新redis
        String username = userInfoService.userNameByPhone(newPhone);
        UserInfo userInfo = userInfoService.selectUserNameAndPasswordByName(username);

        Map<String, Object> userInfoMap = new HashMap<>();
        userInfoMap.put("userName", userInfo.getUserName());
        userInfoMap.put("passWord", userInfo.getPassword());
        String redisKey = "username:" + DigestUtil.sha256Hex(username);
        stringRedisTemplate.opsForHash().putAll(redisKey, userInfoMap);
        result.put("body", "true");
        return result;
    }

    private String forgetPasswordModify(Map<String, String> request) {
        //        获取手机号
        String newPhone = request.get("newPhone");

        if (StringUtils.isBlank(newPhone)) {
            return "请输入手机号";
        }
//        手机号长度
        if (newPhone.length() != 11) {
            return "手机号必须为11位";
        }
//        手机号校验
        if (!newPhone.matches("1(3[0-9]|4[01456879]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[0-35-9])\\d{8}")) {
            return "请输入正确格式的手机号";
        }
        String newPassword = request.get("newPassword");
//        新密码不能与旧密码一致
        if (RSAUtil.decryptData(userInfoService.userPwdByPhone(newPhone), RSAUtil.generatePrivateKey()).equals(newPassword)) {
            return "新密码不能与旧密码一致";
        }

//        验证码校验
        String smscaptha = request.get("smscaptha");
        if (StringUtils.isBlank(smscaptha)) {
            return "请输入验证码";
        }

        if (!stringRedisTemplate.hasKey("phone:" + newPhone)) {
            return "验证码已失效";
        }
        if (!stringRedisTemplate.opsForValue().get("phone:" + newPhone).equals(smscaptha)) {
            return "验证码错误，请重新输入";
        }
        return "true";
    }


    //    发送验证码
    @PostMapping("/sendSmscaptha")
    @CustomerAopNote
    public Map<String, Object> sendSmscaptha(@RequestBody Map<String, String> request) throws SQLException {
        String phone = request.get("newPhone");
        Map<String, Object> result = new HashMap<>();
        if (StringUtils.isBlank(phone)) {
            result.put("body", "请输入手机号！");
            return result;
        }
        if (!phone.matches("1(3[0-9]|4[01456879]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[0-35-9])\\d{8}") || !(phone.length() == 11)) {
            result.put("body", "请正确格式的手机号！");
            return result;
        }
        String rediskey = "phone:" + phone;
        Random random = new Random();
        String smsCaptha = String.valueOf(random.nextInt(9999 - 1000 + 1) + 1000);

        stringRedisTemplate.opsForValue().set(rediskey, smsCaptha);
        stringRedisTemplate.expire(rediskey, RedisConst.Phone_Smactha_Expire_Time, TimeUnit.SECONDS);
        result.put("body", "验证码发送成功！");
        return result;
    }

//    根据UserId查询用户信息
    @PostMapping("/selectUserInfoByUserId")
    @CustomerAopNote
    public UserInfo selectUserInfoByUserId(@RequestBody Map<String, String> request){

//        获取userId入参
        String userId = request.get("userId");

        String userName = userInfoService.selectUserInfoByUserId(userId);

        Map<String, Object> stringObjectMap = (Map<String, Object>)selectUserInfoByName(userName).get("result");

        UserInfo userInfo = BeanToMapUtil.mapToBean(stringObjectMap, UserInfo.class);

//        出参预处理
        userInfo.setPhone(AESUtil.decrpt(userInfo.getPhone()));
        userInfo.setPassword(null);
        userInfo.set_headImg(Base64.getEncoder().encodeToString(userInfo.getHeadimg()));
        userInfo.setHeadimg(null);

        return userInfo;
    }

    @GetMapping("/selectUserInfoByUserIdNew")
    @CustomerAopNote
    public Map<String,Object> selectUserInfoByUserIdNew(String userId){

        Map<String,Object> result = new HashMap<>();

        byte[] headimg = userInfoService.selectUserInfoByUserIdNew(userId).getHeadimg();
        String headImg = Base64.getEncoder().encodeToString(headimg);

        String userName = userInfoService.selectUserInfoByUserIdNew(userId).getUserName();

        result.put("headImg",headImg);
        result.put("userName",userName);
        return result;
    }

    @GetMapping("/selectUserIdByNameOrPhone")
    @CustomerAopNote
    public String selectUserIdByNameOrPhone(String name, String phone) {
        return userInfoService.selectUserIdByNameOrPhone(name, phone);
    }

//    修改密码
    @PostMapping("/updatePassword")
    public Map<String,Object> updatePassword(@RequestBody Map<String, Object> request){
        Map<String,Object> result = new HashMap<>();

        String newPassword = request.get("newPassWord").toString();
        String confirmPassword = request.get("confirmPassword").toString();
        String modifyCode = request.get("modifyCode").toString();
        String userId = request.get("userId").toString();
        String body = modifyRequestParams(newPassword, confirmPassword, modifyCode);
        if(!body.equals("pass")){
            result.put("body",body);
            return result;
        }
        String modifyCodeFromRedis = stringRedisTemplate.opsForValue().get("phone:" + request.get("newPhone").toString());
        if (!modifyCode.equals(modifyCodeFromRedis)){
            result.put("body","验证码错误");
            return result;
        }
        Integer updated = userInfoService.updateUserPassword(userId, newPassword);
        result.put("body","true");
        System.out.println(stringRedisTemplate.keys("usernameInfo*").isEmpty());
        if(!stringRedisTemplate.keys("usernameInfo*").isEmpty()) {
            stringRedisTemplate.delete(stringRedisTemplate.keys("usernameInfo*"));
        }
        stringRedisTemplate.delete(stringRedisTemplate.keys("username*"));
        return result;
    }


    private String modifyRequestParams(String newPassword,String confirmPassword,String modifyCode){
        if (StringUtils.isBlank(newPassword)) {
            return "请输入密码";
        }
//        2.1密码长度校验
        if (newPassword.length() < 8) {
            return "密码要在八位以上";
        } else if (newPassword.length() > 16) {
            return "密码长度过长";
        }
//        2.2密码格式校验
        if (!newPassword.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+={}\\[\\]:;,<.>/?]).{8,}$")) {
            return "密码格式错误";
        }

        if (!confirmPassword.equals(newPassword)){
            return "两次输入密码不一致";
        }

        if (StringUtils.isBlank(modifyCode)){
            return "请输入验证码";
        }
        return "pass";
    }

//    修改个人信息
    @PostMapping("/updatePersonalInfo")
    public Map<String,Object> updatePersonalInfo(@RequestBody Map<String, Object> request){
        Map<String,Object> result = new HashMap<>();
        Map<String,String> userRequest = new HashMap<>();
        String userName = request.get("userName").toString();
        String userId = request.get("userId").toString();
        userRequest.put("userId",userId);
        if (StringUtils.isBlank(userName)){
            result.put("body","注册名称不能为空");
            return result;
        }
        UserInfo userInfo = this.selectUserInfoByUserId(userRequest);
        if (!userInfo.getUserName().equals(userName)) {
            int count = userInfoService.selectUserNameExists(userName);

            if (count > 0) {
                result.put("body", "用户名已存在");
                return result;
            }
        }

        String introduce = request.get("introduce").toString();
        String address = request.get("address").toString();
        Integer age = Integer.parseInt(request.get("age").toString());
        String sex = request.get("sex").toString();

        Integer updated = userInfoService.updatePersonalInfo(userId, userName, introduce, address, age, sex.charAt(0));
        result.put("body","true");
        if(!stringRedisTemplate.keys("usernameInfo*").isEmpty()) {
            stringRedisTemplate.delete(stringRedisTemplate.keys("usernameInfo*"));
        }
        return result;
    }


//    修改手机号和邮箱
    @PostMapping("/updatePhoneAndEmail")
    public Map<String,Object> updatePhoneAndEmail(@RequestBody Map<String, Object> request){
        Map<String,Object> result = new HashMap<>();

        String newPhone = request.get("newPhone").toString();
        String newEmail = request.get("newEmail").toString();
        String userId = request.get("userId").toString();
        String modifyCode = request.get("modifyCode").toString();
        String body = modifyPhoneAndEmailParams(newPhone, newEmail, userId,modifyCode);
        if(!body.equals("pass")){
            result.put("body",body);
            return result;
        }
        String modifyCodeFromRedis = stringRedisTemplate.opsForValue().get("phone:" + request.get("oldPhone").toString());
        if (!modifyCode.equals(modifyCodeFromRedis)){
            result.put("body","验证码错误");
            return result;
        }
        Integer updated = userInfoService.updatePhoneAndEmail(userId,newPhone,newEmail);
        result.put("body","true");
        System.out.println(stringRedisTemplate.keys("usernameInfo*").isEmpty());
        if(!stringRedisTemplate.keys("usernameInfo*").isEmpty()) {
            stringRedisTemplate.delete(stringRedisTemplate.keys("usernameInfo*"));
        }
        return result;
    }

    private String modifyPhoneAndEmailParams(String phone,String email,String userId,String modifyCode){

        if (StringUtils.isBlank(phone)) {
            return "请输入手机号";
        }

//        3.1手机号长度校验
        if (phone.length() != 11) {
            return "手机号必须为11位";
        }
//        3.2手机号格式校验
        if (!phone.matches("1(3[0-9]|4[01456879]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[0-35-9])\\d{8}")) {
            return "请输入正确格式的手机号";
        }

        if(StringUtils.isBlank(modifyCode)){
            return "请输入验证码";
        }

        Map<String,String> userRequest = new HashMap<>();
        userRequest.put("userId",userId);
        UserInfo userInfo = this.selectUserInfoByUserId(userRequest);
        if (!userInfo.getPhone().equals(phone)) {
//        3.3手机号存在校验
            if (userInfoService.selectUserPhoneAll().contains(phone)) {
                return "手机号已存在";
            }
        }
        if (!userInfo.getEmail().equals(email)) {
//        3.3手机号存在校验
            if (userInfoService.selectUserEmailAll().contains(email)) {
                return "邮箱已存在";
            }
        }
        return "pass";
    }

//    用户注销
    @PostMapping("/deleteAcount")
    public Map<String,Object> deleteAcount(@RequestBody Map<String, Object> request){
        Map<String,Object> result = new HashMap<>();
        String userId = request.get("userId").toString();
        Integer updated = userInfoService.updateUserState(userId);
        return result;
    }


}
