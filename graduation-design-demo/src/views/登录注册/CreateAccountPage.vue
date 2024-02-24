<template>

  <el-alert v-model:title="this.errorMessage" type="error" center id="errorMessage" v-show="this.errorDisper"/>
  <el-alert v-model:title="this.errorMessage" type="success" center id="successMessage" v-show="this.successDisper"/>
  <img src="../../assets/登录页资源/crab.png" id="titlePic">
  <p id="title">Start on hermit</p>
  <div id="CreateAccountTebel">
    <div id="input-data1" class="input-data">
      <input type="text" v-model="CreateFromData.username" required>
      <div class="underline"></div>
      <label>Username</label>
    </div>
    <div id="input-data2" class="input-data">
      <input type="text" v-model="CreateFromData.password" required>
      <div class="underline"></div>
      <label>Password</label>
    </div>
    <div id="input-data3" class="input-data">
      <input type="text" v-model="CreateFromData.phone" required>
      <div class="underline"></div>
      <label>Phone</label>
    </div>
    <div id="input-data4" class="input-data">
      <input type="text" v-model="CreateFromData.modifyCode" required>
      <div class="underline"></div>
      <label>VertifyCode</label>
      <img src="../../assets/登录页资源/captcha.png" id="captcha" @click="resetPicSmscaptcha">
    </div>
  </div>
  <div id="backLast" @click="back">Back</div>
  <img src="../../assets/登录页资源/向左.png" id="toLeft">
  <div id="continueNow" @click="signup">Next</div>
  <img src="../../assets/登录页资源/向右.png" id="toRight">
</template>

<script>
import router from "@/router";
import axios from "axios";
import store from "core-js/internals/shared-store";

export default {
  name: "SignOrLoadPage",
  methods: {
    back() {
      router.push('/SignOrLoadPage')
    },
    signup() {
      let _this = this;
      axios({
        method: "post",
        url: "http://localhost:2000/baseUserController/userRegister",
        data: this.CreateFromData
      }).then(function (res) {
        if (res.data["body"] === 1) {
          _this.errorMessage = "注册成功";
          _this.SuccessMessageApperance();
          store.state.LoadUsername = _this.CreateFromData.username;
          store.state.LoadUserId = res.data["userId"]
          setTimeout(() => {
            router.push('/MainMeunShowPage/'+ "main")
          }, 1500)
        } else {
          _this.errorMessage = res.data["body"];
          _this.ErrorMessageApperance();
        }
      })
    },
    resetPicSmscaptcha() {
      axios({
        method: "get",
        url: "http://localhost:2000/baseUserController/getImage",
      }).then(function () {

      })
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
  },
  data() {
    return {
      errorDisper: false,
      successDisper: false,
      errorMessage: "",
      // 登录标识
      CreateFromData: {
        username: "",
        password: "",
        phone: "",
        modifyCode: ""
      },
    }
  }
}
</script>

<style scoped>
#errorMessage {
  position: absolute;
  width: 300px;
  height: 30px;
  top: 65px;
  z-index: 2;
  left: 600px;
}

#successMessage {
  position: absolute;
  width: 300px;
  height: 30px;
  top: 65px;
  z-index: 2;
  left: 600px;
}

#captcha {
  position: absolute;
  left: 160px;
  width: 150px;
  height: 50px;
  top: 0px;
}

#backLast {
  position: absolute;
  left: 623px;
  border-radius: 25px;
  top: 537px;
  color: #d81e06;
  font-size: 24px;
  font-weight: bold;
  letter-spacing: 0.5px;
}

#backLast:hover {
  color: #ff1f02;
}

#continueNow:hover {
  color: rgba(8, 0, 255, 0.97);
}

#continueNow {
  text-align: center;
  position: absolute;
  left: 858px;
  border-radius: 25px;
  top: 537px;
  color: #1146db;
  font-weight: bold;
  font-size: 24px;
  letter-spacing: 0.5px;
}

#toLeft {
  position: absolute;
  left: 580px;
  top: 540px;
  width: 40px;
  height: 30px;
}

#toRight {
  position: absolute;
  left: 920px;
  top: 540px;
  width: 40px;
  height: 30px;
}

#titlePic {
  position: absolute;
  left: 720px;
  top: 30px;
  width: 75px;
  height: 70px;
}

#title {
  position: absolute;
  left: 680px;
  top: 115px;
  font-size: 24px;
  font-weight: lighter;
  color: rgb(60, 35, 40);
}

#CreateAccountTebel {
  border-style: solid;
  border-width: 1px;
  border-color: #aba9a9;
  border-radius: 10px;
  position: absolute;
  left: 570px;
  top: 157px;
  width: 400px;
  height: 340px;
  background-color: rgb(246, 248, 250);
}

* {
  margin: 0;
  padding: 0;
  outline: none;
  /* 这个属性是告诉浏览器：你想要设置的边框和内边距的值是包含在总宽高内的 */
  box-sizing: border-box;
}

#CreateAccountTebel #input-data1 {
  /* 相对定位 */
  top: 30px;
  left: 50px;
  position: absolute;
  width: 60%;
  height: 50px;
}

#CreateAccountTebel #input-data2 {
  /* 相对定位 */
  top: 100px;
  left: 50px;
  position: absolute;
  width: 60%;
  height: 50px;
}

#CreateAccountTebel #input-data3 {
  /* 相对定位 */
  top: 170px;
  left: 50px;
  position: absolute;
  width: 60%;
  height: 50px;
}

#CreateAccountTebel #input-data4 {
  /* 相对定位 */
  top: 240px;
  left: 50px;
  position: absolute;
  width: 30%;
  height: 50px;
}

#CreateAccountTebel .input-data input {
  width: 100%;
  height: 100%;
  border: none;
  font-size: 13px;
  border-bottom: 2px solid #c0c0c0;
}

/* 输入框获得焦点时 */
#CreateAccountTebel .input-data input:focus ~ label,
  /* 输入框的值为合法时 */
#CreateAccountTebel .input-data input:valid ~ label {
  /* label上移，同时改变字号、字体颜色 */
  transform: translateY(-25px);
  font-size: 17px;
  color: #4158D0;
}

#CreateAccountTebel .input-data label {
  /* 绝对定位 */
  position: absolute;
  bottom: 10px;
  left: 0px;
  color: #441b1b;
  /* 这个属性可以使点击label穿透到输入框 */
  pointer-events: none;
  /* 现在动画有点生硬，在这里需要给动画加个过渡 */
  transition: all 0.3s ease;
}

#CreateAccountTebel .input-data .underline {
  position: absolute;
  bottom: 0px;
  height: 2px;
  width: 100%;
  background-color: #4158D0;
  /* 沿X轴缩小 */
  transform: scaleX(0);
  /* 这里同样给动画加个过渡 */
  transition: all 0.3s ease;
}

#CreateAccountTebel .input-data input:focus ~ .underline,
#CreateAccountTebel .input-data input:valid ~ .underline {
  /* 沿X轴放大 */
  transform: scaleX(1);
}
</style>