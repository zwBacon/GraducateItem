<template>
  <img src="../../assets/登录页资源/登录背景.jpg" id="backFrowards">

  <el-alert v-model:title="this.errorMessage" type="error" center id="errorMessage" v-show="this.errorDisper"/>
  <el-alert v-model:title="this.errorMessage" type="success" center id="successMessage" v-show="this.successDisper"/>
  <!--  背景卡片-->
  <div id="LoadCard">
    <!--    介绍区域-->
    <div id="introduce">
      <!--     主题1   -->
      <div id="topic1">
        About Hermit
      </div>
      <!--      细节1-->
      <div id="detail1">
        Welcome to our product! Our innovative solution is designed to simplify your daily<br>
        tasks. With its user-friendly interface and powerful features, you can effortlessly <br>
        manage your data and streamline your workflow. Whether you're a professional or a <br>
        beginner, our product provides the tools you need to succeed. Experience efficiency<br>
        and convenience like never before!
      </div>
      <!--      主题2-->
      <div id="topic2">
        Features
      </div>
      <!--      细节（1）-->
      <div id="detailF">
        <span class="point">·</span>&ensp;The news reportors inform that the war located in the middle east has extended
        to..more<br>
        area
      </div>
      <!--      细节（2）-->
      <div id="detailS">
        <span class="point">·</span>&ensp;The news reportors inform that the war located in the middle east has extended
        to..more<br>
        area
      </div>
      <!--      细节（3）-->
      <div id="detailT">
        <span class="point">·</span>&ensp;The news reportors inform that the war located in the middle east has extended
        to..more<br>
        area
      </div>
      <!--      图标-->
      <div id=svg1>
        <img src="../../assets/登录页资源/房子.svg">
      </div>
      <img src="../../assets/登录页资源/树木.png" id=svg2>
      <img src="../../assets/登录页资源/树木.png" id=svg3>
      <img src="../../assets/登录页资源/树木.png" id=svg4>
      <img src="../../assets/登录页资源/栅栏.svg" id=svg5>
      <img src="../../assets/登录页资源/栅栏.svg" id=svg6>
      <img src="../../assets/登录页资源/小狗.png" id=svg7>
      <img src="../../assets/登录页资源/女生.png" id=svg8>
    </div>
    <!--    登录细节-->
    <div id="LoadDetail">
      <!--      网站头像-->
      <div id="websiteHead">
        <img src="../../assets/首页资源/logo.png" id="websiteHeadPic">
      </div>
      <!--      网站名称-->
      <div id="websiteName">
        hermit
      </div>
      <!--      欢迎语-->
      <div id="Welcom">
        Welcome to
      </div>
      <div id="LargeWebName">
        hermit
      </div>
      <!--      用户名输入框-->
      <div id="Username">Username</div>
      <div><img src="../../assets/登录页资源/登录人.png" id="LoadPerson"></div>
      <input id="UsernameInput" class="ee_input" maxlength="15" @blur="NameModify(this.LoadFormData.username)"
             v-model="LoadFormData.username">
      <p id="errorSignName" v-show="NameBackdisable">请输入用户名!!!</p>
      <!--      密码输入框-->
      <div id="Password">Password</div>
      <div><img src="../../assets/登录页资源/密码.png" id="LoadPassWord"></div>
      <input id="PasswordInput" class="ee_input" type="password" maxlength="15"
             @blur="PwdModify(this.LoadFormData.code)" v-model="LoadFormData.code"/><img v-bind:src="imgSrc" alt=""
                                                                                         id="showText"
                                                                                         @click="hideShowPsw">
      <img src="../../assets/登录页资源/对号.png" id="rightSign" v-show="disable">
      <p id="errorSign" v-show="Backdisable">请输入至少八位由大小写字符、数字，特殊符号组成的密码!!!</p>
      <!--      登录按钮-->
      <button id="GoRush" @click="load(this.NameBackdisable,this.Backdisable)">GoRush</button>
      <!--      忘记密码链接-->
      <p id="Forgot" @click="apperanceForgotTabel">Forgot Your Password?</p>
      <!--      Remember Me-->
      <input type="checkbox" id="Remember">
      <p id="RememberText">Remember Me</p>
      <!--      注册按钮-->
      <button id="Create" @click="createAccount">Create A Account</button>
      <!--      末尾标签-->
    </div>
  </div>

  <!--  忘记密码弹窗-->
  <div id="ForgotPwdMessage" v-show="showForgotPwdTabel">
    <img src="../../assets/登录页资源/忘记密码.png" id="forgotPwdPic">
    <p id="ConnectPhone" class="forgotFontStyle">手机号</p><input type="text" id="ConnectPhoneInput" class="InputStyle"
                                                                  v-model="ConfirmData.newPhone">
    <p id="NewPwd" class="forgotFontStyle">新密码</p><input type="password" id="NewPwdInput" class="InputStyle"
                                                            maxlength="15"
                                                            @blur="NewPwdModify(this.ConfirmData.newPassword)"
                                                            v-model="ConfirmData.newPassword">
    <img src="../../assets/登录页资源/对号.png" id="NewRightSign" v-show="Newdisable">
    <p id="NewErrorSign" v-show="NewBackdisable">请输入至少八位由大小写字符、数字，特殊符号组成的密码!!!</p>
    <p id="ConfirmNewPwd" class="forgotFontStyle">确认密码</p><input type="text" id="ConfirmNewPwd" class="InputStyle"
                                                                     v-model="ConfirmData.ConfirmnewCode"
                                                                     @blur="ConfirmTwoPwd(this.ConfirmData.newPassword,this.ConfirmData.ConfirmnewCode)">
    <p id="ConfirmShow" v-show="ConfirmShowdisable">两次输入密码不一致!!!</p>
    <p id="VertifyCode" class="forgotFontStyle">验证码</p><input type="text" id="VertifyCodeInput" class="InputStyle"
                                                                 v-model="ConfirmData.smscaptha">
    <button id="getVertifyCodeBtn" @click="SendSmsCapthcModify" v-show="isgetCode">点击获取</button>
    <div
        v-show="!isgetCode"
        @click="reacquire()"
        id="verifyPhone-button"
    >
      <span v-show="isActive">重新获取</span>
      <span v-show="!isActive" disabled="true">{{ count }}s</span>
    </div>
    <button id="cancel" @click="cancelTabel">取消</button>
    <button id="confirm" @click="confirmTabel">确认</button>
  </div>

</template>

<script>
import router from "@/router";
import axios from "axios";
import store from "core-js/internals/shared-store";

const TIME_COUNT = 60;
export default {
  name: "SignOrLoadPage",
  methods: {
    hideShowPsw() {
      let _this = this;
      var PasswordInput = document.getElementById("PasswordInput");
      if (PasswordInput.type == "password") {
        PasswordInput.type = "text";
        _this.imgSrc = require('../../assets/登录页资源/睁眼.png');
      } else {
        PasswordInput.type = "password";
        _this.imgSrc = require('../../assets/登录页资源/闭眼.png');
      }
    },
    load(NameFlag, PwdFlag) {
      let _this = this;
      if (NameFlag || PwdFlag) {
      } else {
        axios({
          method: "post",
          url: "http://localhost:2000/baseUserController/userLoad",
          data: this.LoadFormData
        }).then(function (resp) {
          var result = resp.data["result"];
          if (result === true) {
            router.push('/MainMeunShowPage/'+ "main")
            store.state.LoadUsername = _this.LoadFormData.username;
            store.state.LoadUserId = resp.data["userId"];
          } else {
            _this.errorMessage = result
            _this.ErrorMessageApperance();
          }
        })
      }
    },

    createAccount() {
      setTimeout(() => {
        router.push('/CreateAccountPage')
      }, 350)
      axios({
        method: "get",
        url: "http://localhost:2000/baseUserController/getImage",
      }).then(function () {

      })
    },
    cancelTabel() {
      this.showForgotPwdTabel = false;
    },
    confirmTabel() {
      if (this.NewBackdisable || this.ConfirmShowdisable) {
      } else {
        let _this = this
        axios({
          method: "post",
          url: "http://localhost:2000/baseUserController/forgetPassword",
          data: this.ConfirmData
        }).then(function (res) {
          if (res.data["body"] === "true") {
            _this.errorMessage = "修改成功";
            _this.SuccessMessageApperance();
            setTimeout(() => {
              _this.showForgotPwdTabel = false;
            }, 1500)
            _this.ConfirmData = '';
          } else {
            _this.errorMessage = res.data["body"];
            _this.ErrorMessageApperance();
          }
        })
      }
    },
    apperanceForgotTabel() {
      this.showForgotPwdTabel = true;
    },
    ConfirmTwoPwd(code, ConfirmCode) {
      if (code != ConfirmCode) {
        this.ConfirmShowdisable = true
      } else {
        this.ConfirmShowdisable = false
      }
    },
    NameModify(name) {
      if (name === "") {
        this.NameBackdisable = true;
      } else {
        this.NameBackdisable = false;
      }
    },
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
    PwdModify(code) {
      if (code.match('^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+={}\\[\\]:;,<.>/?]).{8,}$')) {
        setTimeout(() => {
          this.Backdisable = false
          this.disable = true
        }, 800);
      } else {
        setTimeout(() => {
          this.disable = false
          this.Backdisable = true
        }, 400);
      }
    },
    NewPwdModify(code) {
      if (code.match('^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+={}\\[\\]:;,<.>/?]).{8,}$')) {
        setTimeout(() => {
          this.NewBackdisable = false
          this.Newdisable = true
        }, 800);
      } else {
        setTimeout(() => {
          this.Newdisable = false
          this.NewBackdisable = true
        }, 400);
      }
    },
    SendSmsCapthcModify() {
      let _this = this;
      axios({
        method: "post",
        url: "http://localhost:2000/baseUserController/sendSmscaptha",
        data: this.ConfirmData
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
    }
  },
  data() {
    return {
      errorDisper: false,
      successDisper: false,
      errorMessage: "",
      // 登录标识
      LoadFormData: {
        code: "",
        username: ""
      },
      NameBackdisable: false,
      Backdisable: false,
      disable: false,
      imgSrc: require('../../assets/登录页资源/闭眼.png'),
      // 忘记密码标识
      ConfirmData: {
        newPhone: "",
        newPassword: "",
        ConfirmnewCode: "",
        smscaptha: "",
      },
      NewBackdisable: false,
      Newdisable: false,
      ConfirmShowdisable: false,
      isgetCode: true,
      isActive: false,
      count: 0,
      timer: null,
      showForgotPwdTabel: false
    }
  }
}

</script>

<style scoped>
@keyframes borderChange {
  0% {
    border-bottom-color: #6a735c;
    border-style: solid;
  }
  100% {
    border-color: #80ad32;
    border-style: solid;
  }
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

#cancel {
  position: absolute;
  top: 310px;
  left: 250px;
  width: 70px;
  height: 30px;
  border-style: solid;
  border-color: rgb(245, 108, 108);
  color: white;
  background-color: rgb(245, 108, 108);
}

#confirm {
  position: absolute;
  top: 310px;
  left: 370px;
  width: 70px;
  height: 30px;
  border-style: solid;
  color: white;
  border-color: rgb(64, 158, 255);
  background-color: rgb(64, 158, 255);
}

#getVertifyCodeBtn {
  position: absolute;
  top: 255px;
  left: 395px;
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
  top: 255px;
  left: 395px;
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

#ConfirmShow {
  position: absolute;
  top: 230px;
  left: 215px;
  font-size: 10px;
  color: #fa0101;
}

#NewErrorSign {
  position: absolute;
  top: 160px;
  left: 215px;
  font-size: 10px;
  color: #fa0101;
}

#NewRightSign {
  position: absolute;
  top: 137px;
  left: 475px;
  width: 20px;
  height: 20px;
}

.InputStyle {
  position: absolute;
  left: 215px;
  border-style: solid;
  height: 30px;
  width: 250px;
  border-radius: 5px;
  border-color: #bbb7b7;
}

.InputStyle:focus {
  outline: none;
  border-color: #9ad4f3;
}

.forgotFontStyle {
  position: absolute;
  left: 125px;
  color: #4d4a4a;
  font-size: 16px;
}

#ConnectPhone {
  top: 70px;
}

#ConnectPhoneInput {
  top: 70px;
}

#NewPwd {
  top: 130px;
}

#NewPwdInput {
  top: 130px;
}

#ConfirmNewPwd {
  top: 195px;
}

#VertifyCode {
  top: 255px;
}

#VertifyCodeInput {
  top: 255px;
  width: 140px;
}

#forgotPwdPic {
  position: absolute;
  left: 285px;
  top: -43px;
  width: 85px;
  height: 85px;
}

#ForgotPwdMessage {
  position: absolute;
  left: 445px;
  top: 150px;
  width: 650px;
  height: 350px;
  z-index: 2;
  border-radius: 10px; /* 设置圆角半径为50px */
  background: white; /* 设置背景颜色为灰色 */
  /* 设置两个颜色的阴影效果，形成凸起和凹陷的效果 */
  padding: 40px;
  box-shadow: 2px 2px 6px #bebebe, /* 右下方的深灰色阴影 */ -2px -2px 6px #ffffff; /* 左上方的白色阴影 */
}

#Create {
  position: absolute;
  z-index: 1;
  border-style: solid;
  border-color: #b0abab;
  left: 60px;
  width: 160px;
  height: 37px;
  border-radius: 7px;
  font-weight: bold;
  top: 515px;
  color: #7171cc;
  font-size: 15px;
}

#Create:hover {
  background-color: #3d280a;
  border-style: none;
  color: white;
}

#GoRush {
  position: absolute;
  z-index: 1;
  left: 272px;
  width: 180px;
  height: 40px;
  color: white;
  background-image: linear-gradient(to bottom right, #ffcc00, #f68baf);
  border-radius: 20px;
  font-weight: bold;
  top: 511px;
  font-size: 17px;
}

#GoRush:hover {
  top: 517px;
  left: 280px;
  width: 170px;
  height: 37px;
}

#Forgot {
  position: absolute;
  z-index: 1;
  color: cornflowerblue;
  left: 232px;
  letter-spacing: 0.5px;
  font-weight: bold;
  top: 461px;
  font-size: 10px;
}

#Forgot:hover {
  color: #0033ff;
}

#Remember {
  position: absolute;
  z-index: 1;
  left: 90px;
  top: 463px;
}

#RememberText {
  position: absolute;
  z-index: 1;
  color: #aba3a3;
  left: 108px;
  letter-spacing: 0.5px;
  font-weight: bold;
  top: 461px;
  font-size: 10px;
}

#errorSign {
  position: absolute;
  z-index: 1;
  left: 90px;
  font-weight: bold;
  top: 443px;
  font-size: 10px;
  color: red;
}

#errorSignName {
  position: absolute;
  z-index: 1;
  left: 90px;
  font-weight: bold;
  top: 333px;
  font-size: 10px;
  color: red;
}

#rightSign {
  position: absolute;
  z-index: 1;
  left: 360px;
  top: 393px;
  width: 20px;
  height: 20px;
}

#showText {
  position: absolute;
  z-index: 1;
  left: 320px;
  top: 395px;
  width: 17px;
  height: 17px;
}

.ee_input {
  padding: 0 30px;
}

#LoadPassWord {
  position: absolute;
  z-index: 1;
  left: 90px;
  top: 395px;
  width: 20px;
  height: 20px;
}

#LoadPerson {
  position: absolute;
  z-index: 1;
  left: 90px;
  top: 288px;
  width: 20px;
  height: 20px;
}

#Username {
  position: absolute;
  top: 250px;
  font-size: 12px;
  color: #939090;
  font-family: "Fira Code Light";
  left: 90px;
}

#Password {
  position: absolute;
  top: 354px;
  font-size: 12px;
  color: #939090;
  font-family: "Fira Code Light";
  left: 90px;
}

#UsernameInput {
  color: #9393b9;
  position: absolute;
  left: 85px;
  top: 275px;
  width: 270px;
  height: 50px;
  border-radius: 10px; /* 设置圆角半径为50px */
  background: white; /* 设置背景颜色为灰色 */
  /* 设置两个颜色的阴影效果，形成凸起和凹陷的效果 */
  box-shadow: 1px 1px 3px #bebebe, /* 右下方的深灰色阴影 */ -1px -1px 1px #ffffff; /* 左上方的白色阴影 */
}

#UsernameInput:focus {
  text-align: center;
  font-size: 16px;
  font-weight: bold;
  animation: borderChange 1.5s forwards;
  outline: none;
}

#PasswordInput:focus {
  border-color: #80ad32;
  border-style: solid;
  outline: none;
}

#PasswordInput {
  color: black;
  position: absolute;
  left: 85px;
  top: 380px;
  width: 270px;
  height: 50px;
  border-radius: 10px; /* 设置圆角半径为50px */
  background: white; /* 设置背景颜色为灰色 */
  /* 设置两个颜色的阴影效果，形成凸起和凹陷的效果 */
  box-shadow: 1px 1px 3px #bebebe, /* 右下方的深灰色阴影 */ -1px -1px 1px #ffffff; /* 左上方的白色阴影 */
}

#svg8 {
  position: absolute;
  top: 515px;
  width: 50px;
  height: 70px;
  left: 659px;
}

#svg7 {
  position: absolute;
  top: 535px;
  width: 50px;
  height: 50px;
  left: 739px;
}

#svg5 {
  position: absolute;
  top: 535px;
  width: 40px;
  height: 40px;
  left: 509px;
}

#svg6 {
  position: absolute;
  top: 535px;
  width: 40px;
  height: 40px;
  left: 549px;
}

#svg2 {
  position: absolute;
  top: 515px;
  width: 60px;
  height: 60px;
  left: 1009px;
}

#svg3 {
  position: absolute;
  top: 515px;
  width: 60px;
  height: 60px;
  left: 809px;
}

#svg4 {
  position: absolute;
  top: 515px;
  width: 40px;
  height: 40px;
  left: 869px;
}

#svg1 {
  position: absolute;
  top: 485px;
  width: 100px;
  height: 100px;
  left: 900px;
}

.point {
  font-size: 20px;
  font-weight: bold;
}

#topic1 {
  position: absolute;
  top: 50px;
  font-size: 29px;
  color: #ffffff;
  font-weight: bold;
  font-family: "Fira Code Light";
  left: 540px;
}

#detail1 {
  position: absolute;
  top: 115px;
  font-size: 12px;
  color: #ffffff;
  font-family: "Fira Code Light";
  left: 540px;
}

#topic2 {
  position: absolute;
  top: 240px;
  font-size: 29px;
  color: #ffffff;
  font-weight: bold;
  font-family: "Fira Code Light";
  left: 540px;
}

#detailF {
  position: absolute;
  top: 305px;
  font-size: 12px;
  color: #ffffff;
  font-family: "Fira Code Light";
  left: 540px;
}

#detailS {
  position: absolute;
  top: 345px;
  font-size: 12px;
  color: #ffffff;
  font-family: "Fira Code Light";
  left: 540px;
}

#detailT {
  position: absolute;
  top: 385px;
  font-size: 12px;
  color: #ffffff;
  font-family: "Fira Code Light";
  left: 540px;
}

#Welcom {
  position: absolute;
  top: 93px;
  font-size: 20px;
  color: #595858;
  font-family: "Fira Code Light";
  left: 90px;
}

#LargeWebName {
  position: absolute;
  top: 120px;
  font-size: 54px;
  color: black;
  font-family: "Fira Code Light";
  font-weight: bold;
  left: 90px;
}

#websiteHeadPic {
  width: 40px;
  height: 40px;
}

#websiteName {
  position: absolute;
  top: 30px;
  font-size: 22px;
  color: #595858;
  font-family: "Fira Code Light";
  left: 140px;
}

#websiteHead {
  position: absolute;
  top: 30px;
  width: 40px;
  height: 40px;
  left: 90px;
  background-color: #d0874e;
  border-radius: 150px;
}

#LoadCard {
  position: absolute;
  left: 170px;
  top: 80px;
  width: 1200px;
  height: 600px;
  border-radius: 50px; /* 设置圆角半径为50px */
  background: white; /* 设置背景颜色为灰色 */
  /* 设置两个颜色的阴影效果，形成凸起和凹陷的效果 */
  box-shadow: 2px 2px 6px #bebebe, /* 右下方的深灰色阴影 */ -2px -2px 6px #ffffff; /* 左上方的白色阴影 */
}

#introduce {
  position: absolute;
  top: -6px;
  left: 105px;
  width: 1102px;
  height: 610px;
  border-radius: 65px;
  background-color: rgb(245, 146, 55);
  z-index: 1;
  border-style: solid;
  border-color: rgb(245, 146, 55);
  clip-path: circle(56.5% at 82% 53%);
}

#backFrowards {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}
</style>