<template>
<HeadLineViewForMain/>
  <el-alert v-model:title="this.errorMessage" type="error" center id="errorMessage" v-show="this.errorDisper"/>
  <el-alert v-model:title="this.errorMessage" type="success" center id="successMessage" v-show="this.successDisper"/>
  <div id="AllDiv" style="position: absolute;width: 100%;height: 648px;top:80px;background-color: #F1F1F1">
    <img :src="this.stringplus + this.userTitle" style="position: absolute;width: 180px;height: 170px;border-radius: 25px;border-bottom-right-radius:80px;left: 210px;top: 20px;z-index: 1">
    <div id="PersonCard">
     <span style="position: absolute;font-size: 35px;font-weight: bold;font-family: 华文新魏;left: 60px;top: 10px">{{this.userName}}</span>
      <img src="../../../assets/首页资源/vip.png" id="vipImg">
      <span style="position: absolute;top: 30px;left: 207px;font-size: 13px;color: #1cc0ec">寄居蟹普通用户</span>
      <span class="infosTitle" style="left: 65px;top: 70px">个人介绍：<span class="infos">{{this.introduce}}</span></span>
      <span class="infosTitle" style="left: 65px;top: 115px">地&emsp;&emsp;址：<span class="infos">{{this.address}}</span></span>

      <span class="infosTitle" style="left: 415px;top: 70px">手&nbsp;机&nbsp;号：<span class="infos">{{this.phone}}</span></span>
      <span class="infosTitle" style="left: 415px;top: 115px">邮&emsp;&emsp;箱：<span class="infos">{{this.email}}</span></span>

      <span class="infosTitle" style="left: 695px;top: 70px">注册时间：<span class="infos">{{this.createTime.replace("T"," ")}}</span></span>
      <span class="infosTitle" style="left: 695px;top: 115px">年&emsp;&emsp;龄：<span class="infos">{{this.age}}</span></span>
      <span class="infosTitle" style="left: 845px;top: 115px">性&emsp;&emsp;别：<span class="infos">{{this.sex === 1 ? '女':'男'}}</span></span>
    </div>

    <div id="LeftCard">
       <div style="position: absolute;background-image:-webkit-linear-gradient(right,#c3e1b5,#e5a83a,#7896da);-webkit-background-clip:text;-webkit-text-fill-color:transparent;font-size: 23px;left: 55px;top: 10px;">个人中心</div>
       <div class="itemClass" style="left: 0;top: 70px;width: 100%;height: 50px" id="firstItem"><span class="titleInfo"  @click="changeClick('firstItem')"><img v-bind:src="imgSrc1" class="titleIcon">&nbsp;&nbsp;修改密码</span></div>
       <div class="itemClass" style="left: 0;top: 140px;width: 100%;height: 50px"  id="secondItem"><span class="titleInfo" @click="changeClick('secondItem')"><img v-bind:src="imgSrc2" class="titleIcon">&nbsp;&nbsp;修改个人信息</span></div>
       <div class="itemClass" style="left: 0;top: 210px;width: 100%;height: 50px"  id="thirdItem"><span class="titleInfo" @click="changeClick('thirdItem')"><img v-bind:src="imgSrc3" class="titleIcon">&nbsp;&nbsp;隐私设置</span></div>
       <div class="itemClass" style="left: 0;top: 280px;width: 100%;height: 50px" id="fourthItem"><span class="titleInfo"  @click="changeClick('fourthItem')"><img v-bind:src="imgSrc4" class="titleIcon">&nbsp;&nbsp;注销账号</span></div>
    </div>

<!--    修改密码-->
     <div id="updatePassWord" class="mainCard" v-show="updatePassWordDisabel">
         <span style="font-family: 华文中宋;position: absolute;left: 220px;top: 50px">新&nbsp;密&nbsp;码&nbsp;：<el-input
             style="position: absolute;left: 85px;top: -2px;width: 250px"
             v-model="updatePasswordForm.newPassWord"
             type="password"
             placeholder="请输入新密码"
             show-password
         /></span>
         <span style="font-family: 华文中宋;position: absolute;left: 220px;top: 120px">确认密码 ：<el-input
             style="position: absolute;left: 85px;top: -2px;width: 250px"
             v-model="updatePasswordForm.confirmPassword"
             type="password"
             placeholder="请确认新密码"
             show-password
         /></span>
         <span style="font-family: 华文中宋;position: absolute;left: 220px;top: 190px">手&nbsp;机&nbsp;号&nbsp;：<el-input style="position: absolute;left: 80px;top: -2px;width: 150px" v-model="this.phone" disabled /></span>
         <button id="getVertifyCodeBtn" @click="SendSmsCapthcModify" v-show="isgetCode">点击获取</button>
         <div
             v-show="!isgetCode"
             @click="reacquire()"
             id="verifyPhone-button"
         >
           <span v-show="isActive">重新获取</span>
           <span v-show="!isActive" disabled="true">{{ count }}s</span>
         </div>
         <span style="font-family: 华文中宋;position: absolute;left: 220px;top: 260px">验&nbsp;证&nbsp;码&nbsp;：<el-input style="position: absolute;left: 80px;top: -2px;width: 100px" v-model="updatePasswordForm.modifyCode" placeholder="输入验证码" /></span>
         <button class="confirmUpdate" @click="confirmUpdatePassword">确认修改</button>
     </div>

<!--    修改个人信息-->
    <div id="updatePersonalInfo" class="mainCard" v-show="updatePersonalInfoDisabel">
      <span style="font-family: 华文中宋;position: absolute;left: 220px;top: 50px">注册名称：<el-input style="position: absolute;left: 80px;top: -2px;width: 240px" v-model="updatePersonInfo.userName" /></span>
      <span style="font-family: 华文中宋;position: absolute;left: 220px;top: 120px">个人介绍：<el-input style="position: absolute;left: 80px;top: -2px;width: 240px" v-model="updatePersonInfo.introduce" /></span>
      <span style="font-family: 华文中宋;position: absolute;left: 220px;top: 190px">地&emsp;&emsp;址：<el-input style="position: absolute;left: 80px;top: -2px;width: 240px" v-model="updatePersonInfo.address" /></span>
      <span style="font-family: 华文中宋;position: absolute;left: 220px;top: 260px">年&emsp;&emsp;龄：<el-input style="position: absolute;left: 80px;top: -2px;width: 100px" v-model="updatePersonInfo.age"/></span>
      <span style="font-family: 华文中宋;position: absolute;left: 220px;top: 330px">性&emsp;&emsp;别：<el-select v-model="updatePersonInfo.sex" style="width: 150px">
        <el-option label="男" value="1" />
        <el-option label="女" value="0" />
      </el-select>
      </span>
      <button class="confirmUpdate" @click="confirmUpdatePersonInfo">确认修改</button>
    </div>

<!--    隐私设置-->
    <div id="updatePhoneAndEmail" class="mainCard" v-show="updatePhoneAndEmailDisabel">
      <span style="font-family: 华文中宋;position: absolute;left: 220px;top: 50px">新手机号&nbsp;：<el-input style="position: absolute;left: 80px;top: -2px;width: 200px" v-model="updatePhoneAndEmail.newPhone"/></span>
      <span style="font-family: 华文中宋;position: absolute;left: 220px;top: 120px">新&nbsp;邮&nbsp;箱&nbsp;：<el-input style="position: absolute;left: 80px;top: -2px;width: 200px" v-model="updatePhoneAndEmail.newEmail"/></span>
      <span style="font-family: 华文中宋;position: absolute;left: 220px;top: 190px">手&nbsp;机&nbsp;号&nbsp;：<el-input style="position: absolute;left: 80px;top: -2px;width: 150px" v-model="this.phone" disabled /></span>
      <button id="getVertifyCodeBtn" @click="SendSmsCapthcModify" v-show="isgetCode">点击获取</button>
      <div
          v-show="!isgetCode"
          @click="reacquire()"
          id="verifyPhone-button"
      >
        <span v-show="isActive">重新获取</span>
        <span v-show="!isActive" disabled="true">{{ count }}s</span>
      </div>
      <span style="font-family: 华文中宋;position: absolute;left: 220px;top: 260px">验&nbsp;证&nbsp;码&nbsp;：<el-input style="position: absolute;left: 80px;top: -2px;width: 100px" v-model="updatePhoneAndEmail.modifyCode" placeholder="输入验证码" /></span>
      <button class="confirmUpdate" @click="confirmUpdatePhoneAndEmail">确认修改</button>
    </div>


<!--    注销账号-->
    <div id="deleteAccount" class="mainCard" v-show="deleteAccountDisabel">
      <el-result
          icon="warning"
          title="别冲动啊亲"
          sub-title="请确定您将要注销此账号"
          v-show="warningDisabel"
      >
        <template #extra>
          <el-button type="danger" style="color: whitesmoke;font-weight: bold" @click="deleteAccount">注销</el-button>
        </template>
      </el-result>

        <div v-show="successDisabel">
          <el-icon color="#67c23a" :size="100" style="position: absolute;left: 370px;top: 100px"><SuccessFilled /></el-icon>
           <span style="color: #000109;position: absolute;left: 280px;top: 250px;font-weight: bold">注销成功，将于&emsp;
             <nut-countdown style="position:absolute;z-index:3;color: orangered;top: 1px;left: 120px" :start-time="start" :end-time="end" format="ss 秒"></nut-countdown><span>
             &emsp;&emsp;后跳转至首页</span></span>
        </div>
    </div>

  </div>
</template>

<script>
import HeadLineViewForMain from "@/components/HeadLineViewForMain.vue";
import axios from "axios";
import router from "@/router";
import store from "core-js/internals/shared-store";
const TIME_COUNT = 60;
export default {
  name: "PersonalCenterPage",
  components:{
    HeadLineViewForMain
  },
  mounted() {
    this.requestForm.userId = store.state.LoadUserId
    // this.requestForm.userId = "GCCI-U-Scuqw8q9h398f2ccdd"
    this.selectUserInfo()
    this.imgSrc1 = require("../../../assets/个人中心页面资源/修改密码 (1).png")
    var font = document.getElementById("firstItem");
    font.style.backgroundColor = "#e8f2fc";
    this.befontName = "firstItem";
    this.updatePhoneAndEmail.userId = this.requestForm.userId
    this.updatePersonInfo.userId = this.requestForm.userId
    this.updatePasswordForm.userId = this.requestForm.userId
  },
  data(){
    return{
      // 注销
      start:Date.now(),
      end:Date.now(),
      warningDisabel:true,
      successDisabel:false,
      // 公共消息提示
      errorDisper: false,
      successDisper: false,
      errorMessage: "",
      // 修改手机号和邮箱
      updatePhoneAndEmail:{
        newPhone:"",
        newEmail:"",
        userId:"",
        oldPhone:"",
        modifyCode:""
      },
      // 修改个人信息
      updatePersonInfo:{
        userName:"",
        introduce:"",
        address:"",
        age:"",
        sex:"",
        userId:""
      },
      // 修改密码
      updatePasswordForm:{
        newPassWord:"",
        confirmPassword:"",
        newPhone:"",
        modifyCode:"",
        userId:""
      },
      isgetCode: true,
      isActive: false,
      count: 0,
      timer: null,

      // 个人信息
      userTitle:"",
      userName:"",
      introduce:"",
      address:"",
      age:"",
      createTime:"",
      email:"",
      phone:"",
      sex:"",
      userID:"",
      stringplus: "data:image/png;base64,",
      requestForm:{
        userId:""
      },
      // 侧边栏
      imgSrc1: require('../../../assets/个人中心页面资源/修改密码.png'),
      imgSrc2: require('../../../assets/个人中心页面资源/修改信息.png'),
      imgSrc3: require('../../../assets/个人中心页面资源/隐私.png'),
      imgSrc4: require('../../../assets/个人中心页面资源/注销.png'),
      befontName:"",
      updatePassWordDisabel: true,
      updatePersonalInfoDisabel: false,
      updatePhoneAndEmailDisabel: false,
      deleteAccountDisabel: false,
    }
  },
  methods:{
    // 注销账号
    deleteAccount(){
      this.warningDisabel = false
      this.successDisabel = true
      axios({
        method: "post",
        url: "http://localhost:2000/baseUserController/deleteAcount",
        data: this.requestForm
      }).then(function () {
      })
      this.end = Date.now()+ 3 * 1000
      setTimeout(() => {
        router.push("/WelcomPage")
      }, 4000);
    },
    // 修改信息
    confirmUpdatePersonInfo(){
      let _this = this;
      axios({
        method: "post",
        url: "http://localhost:2000/baseUserController/updatePersonalInfo",
        data: this.updatePersonInfo
      }).then(function (resp) {
        if (resp.data["body"] === 'true'){
          _this.errorMessage = '修改成功';
          _this.SuccessMessageApperance();
          _this.selectUserInfo();
        }else {
          _this.errorMessage = resp.data["body"];
          _this.ErrorMessageApperance();
        }
      })
    },
    // 修改隐私
    confirmUpdatePhoneAndEmail(){
      let _this = this;
      axios({
        method: "post",
        url: "http://localhost:2000/baseUserController/updatePhoneAndEmail",
        data: this.updatePhoneAndEmail
      }).then(function (resp) {
        if (resp.data["body"] === 'true'){
          _this.errorMessage = '修改成功';
          _this.SuccessMessageApperance();
          _this.selectUserInfo();
          _this.updatePhoneAndEmail.modifyCode = ""
          _this.isgetCode = true
          _this.isActive= false
          _this.count = 0
          _this.timer = null
        }else {
          _this.errorMessage = resp.data["body"];
          _this.ErrorMessageApperance();
        }
      })
    },
    // 修改密码
    confirmUpdatePassword(){
      let _this = this;
      axios({
        method: "post",
        url: "http://localhost:2000/baseUserController/updatePassword",
        data: this.updatePasswordForm
      }).then(function (resp) {
         if (resp.data["body"] === 'true'){
           _this.errorMessage = '修改成功';
           _this.SuccessMessageApperance();
           _this.updatePasswordForm.newPassWord = "";
           _this.updatePasswordForm.confirmPassword = ""
           _this.updatePasswordForm.modifyCode = ""
           _this.isgetCode = true
           _this.isActive= false
           _this.count = 0
           _this.timer = null
         }else {
           _this.errorMessage = resp.data["body"];
           _this.ErrorMessageApperance();
         }
      })
    },
    SendSmsCapthcModify() {
      let _this = this;
      axios({
        method: "post",
        url: "http://localhost:2000/baseUserController/sendSmscaptha",
        data: this.updatePasswordForm
      }).then(function (resp) {
        _this.errorMessage = resp.data["body"];
        if (resp.data["body"] === "验证码发送成功！") {
          _this.SuccessMessageApperance();
          _this.getCode();
        } else {
          _this.ErrorMessageApperance();
        }
      })
    },
    getCode() {
      var element = document.getElementById("verifyPhone-button").style;
      this.isgetCode = false;
      if (!this.timer) {
        this.count = TIME_COUNT;
        this.isActive = false;
        element.backgroundColor = "#b9b9b9";
        element.color = "white";
        element.lineHeight = "28px";
        element.borderWidth = "1px";
        element.borderColor = "#b9b9b9";
        this.timer = setInterval(() => {
          if (this.count > 0 && this.count <= TIME_COUNT) {
            this.count--;
          } else {
            element.backgroundColor = "white";
            element.color = "#009cfd";
            element.borderWidth = "1px";
            element.borderColor = "#009cfd";
            this.isActive = true;
            clearInterval(this.timer);
            this.timer = null;
          }
        }, 1000);
      }
    },
    //重新获取验证码
    reacquire() {
      this.SendSmsCapthcModify();
    },
    // 公共消息提示
    ErrorMessageApperance() {
      this.errorDisper = true;
      setTimeout(() => {
        this.errorDisper = false
      }, 4000);
    },
    SuccessMessageApperance() {
      this.successDisper = true;
      setTimeout(() => {
        this.successDisper = false
      }, 4000);
    },
    // 查询用户信息
    selectUserInfo(){
      let _this = this
      axios({
        method: "post",
        url: "http://localhost:2000/baseUserController/selectUserInfoByUserId",
        data: this.requestForm
      }).then(function (resp) {
        _this.userTitle = resp.data._headImg
        _this.userName = resp.data.userName
        _this.updatePersonInfo.userName = resp.data.userName
        _this.introduce = resp.data.introduce
        _this.updatePersonInfo.introduce = resp.data.introduce
        _this.address = resp.data.address
        _this.updatePersonInfo.address = resp.data.address

        _this.updatePhoneAndEmail.oldPhone = resp.data.phone
        _this.updatePasswordForm.newPhone = resp.data.phone
        _this.updatePhoneAndEmail.newPhone = resp.data.phone
        _this.updatePhoneAndEmail.newEmail = resp.data.email
        _this.phone = resp.data.phone.replace(resp.data.phone.substring(3, 9), "****")
        _this.email = resp.data.email.replace(resp.data.email.substring(3, 12), "*********")
        _this.createTime = resp.data.createTime
        _this.age = resp.data.age
        _this.updatePersonInfo.age = resp.data.age
        _this.sex = resp.data.sex
        _this.updatePersonInfo.sex = resp.data.sex
      })
    },
    // 侧边栏
    changeClick(fontName){
      this.resetChangeClick(this.befontName);
      var font = document.getElementById(fontName);
      font.style.backgroundColor = "#e8f2fc"
      if(fontName === 'firstItem'){
         this.imgSrc1 = require("../../../assets/个人中心页面资源/修改密码 (1).png")
         this.updatePassWordDisabel = true
      }else if(fontName === 'secondItem'){
        this.imgSrc2 = require("../../../assets/个人中心页面资源/修改信息 (1).png")
        this.updatePersonalInfoDisabel = true
      }else if(fontName === 'thirdItem'){
        this.imgSrc3 = require("../../../assets/个人中心页面资源/隐私 (1).png")
        this.updatePhoneAndEmailDisabel = true
      }else if(fontName === 'fourthItem'){
        this.imgSrc4 = require("../../../assets/个人中心页面资源/注销 (1).png")
        this.deleteAccountDisabel = true
      }
      this.befontName = fontName
    },
    resetChangeClick(beFontName){
      var font = document.getElementById(beFontName);
      font.style.backgroundColor = "#f5f5f5";
      if(beFontName === 'firstItem'){
        this.imgSrc1 = require("../../../assets/个人中心页面资源/修改密码.png")
        this.updatePassWordDisabel = false
      }else if(beFontName === 'secondItem'){
        this.imgSrc2 = require("../../../assets/个人中心页面资源/修改信息.png")
        this.updatePersonalInfoDisabel = false
      }else if(beFontName === 'thirdItem'){
        this.imgSrc3 = require("../../../assets/个人中心页面资源/隐私.png")
        this.updatePhoneAndEmailDisabel = false
      }else if(beFontName === 'fourthItem'){
        this.imgSrc4 = require("../../../assets/个人中心页面资源/注销.png")
        this.deleteAccountDisabel = false
      }
    }
  }
}
</script>

<style scoped>
.itemClass{
  position: absolute;
  text-align: center;
  line-height: 50px;
  font-size: 14px;
  font-weight: lighter;
  font-family: "Kristen ITC";
}
.mainCard{
  position: absolute;
  left: 450px;
  top: 210px;
  width: 880px;
  height: 420px;
  border-radius: 10px;
  background-color: #f5f5f5;
  box-shadow: 1px 1px 2px #dce8fc, -1px -1px 2px #e1e1fc;
}
.titleIcon{
  position: relative;
  top: 2px;
  width: 16px;
  height: 16px;
}
#vipImg{
  position: absolute;
  width: 22px;
  height: 22px;
  left: 180px;
  top: 27px;
}
#PersonCard{
  position: absolute;
  left: 350px;
  top: 30px;
  width: 980px;
  height: 150px;
  border-radius: 10px;
  background-color: #f5f5f5;
  box-shadow: 5px 5px 10px #dedada, -5px -5px 10px #e5e1e1;
}
#LeftCard{
  position: absolute;
  left: 210px;
  top: 210px;
  width: 200px;
  height: 420px;
  border-radius: 5px;
  background-color: #f5f5f5;
  box-shadow: 1px 1px 2px #dedada, -1px -1px 2px #e5e1e1;
}
.infosTitle{
  position: absolute;
  font-family: 华文中宋;
  color: #c2c2c2;
}
.infos{
  color: black;
}
#getVertifyCodeBtn {
  position: absolute;
  top: 190px;
  left: 485px;
  width: 70px;
  height: 30px;
  background-color: #009cfd;
  border-style: solid;
  font-size: 13px;
  color: white;
  border-radius: 2px;
  border-color: #009cfd;
}
#verifyPhone-button {
  position: absolute;
  top: 190px;
  left: 485px;
  width: 70px;
  height: 30px;
  background-color: #b9b9b9;
  border-style: solid;
  text-align: center;
  font-size: 14px;
  color: white;
  border-radius: 2px;
  border-color: #b9b9b9;
}
.confirmUpdate{
  position: absolute;
  top: 370px;
  left: 705px;
  width: 70px;
  height: 35px;
  background-color: #eef9ff;
  border-style: solid;
  font-size: 13px;
  color: #009cfd;
  border-radius: 2px;
  border-color: #d9e7fa;
}
.confirmUpdate:hover{
  background-color: #009cfd;
  border-style: solid;
  color: white;
  border-color: #009cfd;
}
#errorMessage {
  position: absolute;
  width: 300px;
  top: 60px;
  z-index: 2;
  left: 600px;
}

#successMessage {
  position: absolute;
  width: 300px;
  top: 60px;
  z-index: 2;
  left: 600px;
}
</style>