package com.zw.graducate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zw.graducate.entity.UserInfo;
import com.zw.graducate.mapper.UserInfoMapper;
import com.zw.graducate.service.UserInfoService;
import com.zw.graducate.util.AESUtil;
import com.zw.graducate.util.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2023/12/3 13:22
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo selectUserNameAndPasswordByName(String username) {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<UserInfo>();
        wrapper.select("USERNAME", "PASSWORD","USERID").eq("USERNAME", username).ne("ISDELETE","1");
        return userInfoMapper.selectOne(wrapper);
    }

    @Override
    public UserInfo selectUserInfoByName(String username) {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<UserInfo>();
        wrapper.eq("USERNAME", username);
        return userInfoMapper.selectOne(wrapper);
    }

    @Override
    public UserInfo selectUserTitle(String username) {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<UserInfo>();
        wrapper.select("HEADIMG").eq("USERNAME", username);
        return userInfoMapper.selectOne(wrapper);
    }

    @Override
    public int selectUserNameExists(String username) {
        return userInfoMapper.selectUserNameExists(username);
    }

    @Override
    public List<String> selectUserPhoneAll() {
        return userInfoMapper.selectUserPhoneAll();
    }

    @Override
    public List<String> selectUserEmailAll() {
        return userInfoMapper.selectUserEmailAll();
    }

    @Override
    public int userReister(UserInfo userInfo) {
        return userInfoMapper.insert(userInfo);
    }

    @Override
    public String userPwdByPhone(String phone) {
        return userInfoMapper.userPwdByPhone(AESUtil.encrpt(phone));
    }


    @Override
    public void updatePasswordByPhone(String phone, String newPassWord) {
        String encrptPhone = AESUtil.encrpt(phone);
        String encrptPwd = RSAUtil.encryptData(newPassWord, RSAUtil.generatePublicKey());
        userInfoMapper.updatePasswordByPhone(encrptPhone, encrptPwd);
    }


    @Override
    public String userNameByPhone(String phone) {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<UserInfo>();
        wrapper.select("USERNAME").eq("PHONE", AESUtil.encrpt(phone));
        return userInfoMapper.selectOne(wrapper).getUserName();
    }

    @Override
    public String selectUserInfoByUserId(String userId) {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<UserInfo>();
        wrapper.select("USERNAME").eq("USERID",userId);
        return userInfoMapper.selectOne(wrapper).getUserName();
    }

    @Override
    public UserInfo selectUserInfoByUserIdNew(String userId) {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<UserInfo>();
        wrapper.select("HEADIMG").eq("USERID",userId);
        return userInfoMapper.selectOne(wrapper);
    }

    @Override
    public List<UserInfo> selectConditionUserInfoByUserId(List<String> userIds, String username) {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<UserInfo>();
        wrapper.like("USERNAME",username).in("USERID",userIds);
        return userInfoMapper.selectList(wrapper);
    }

    @Override
    public String selectUserIdByNameOrPhone(String name, String phone) {
        return userInfoMapper.selectUserIdByNameOrPhone(name, phone);
    }

    @Override
    public Integer updateUserPassword(String userId, String password) {
        //只更新一个属性，把名字为rhb的用户年龄更新为18，其他属性不变
        UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<>();
        String encryptPassword = RSAUtil.encryptData(password, RSAUtil.generatePublicKey());
        updateWrapper.eq("USERID",userId).set("PASSWORD", encryptPassword);
        return userInfoMapper.update(null, updateWrapper);
    }

    @Override
    public Integer updatePersonalInfo(String userId, String userName, String introduce, String address, Integer age, char sex) {
        UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("USERID",userId).set("USERNAME",userName).set("INTRODUCE",introduce).set("ADDRESS",address).set("AGE",age).set("SEX",sex);
        return userInfoMapper.update(null,updateWrapper);
    }

    @Override
    public Integer updatePhoneAndEmail(String userId, String newPhone, String newEmail) {
        UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<>();
        String encrptPhone = AESUtil.encrpt(newPhone);
        String encrptEmail = AESUtil.encrpt(newEmail);
        updateWrapper.eq("USERID",userId).set("EMAIL", encrptEmail).set("PHONE",encrptPhone);
        return userInfoMapper.update(null,updateWrapper);
    }

    @Override
    public Integer updateUserState(String userId) {
        UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("USERID",userId).set("ISDELETE", "1");
        return userInfoMapper.update(null,updateWrapper);
    }


}
