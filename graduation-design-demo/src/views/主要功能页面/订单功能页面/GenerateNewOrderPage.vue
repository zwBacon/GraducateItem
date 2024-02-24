<template>
  <div id = "body">
    <p id="orderTitle">Order Confirmation in Progress</p>
    <img src="../../../assets/订单相关资源/墨迹.png" id="decortePic" style="transform: rotate(-7deg);">
    <el-steps :active="active" finish-status="success" direction="vertical" align-center id="steps">
      <el-step title="确认房源信息"></el-step>
      <el-step title="确认个人信息"></el-step>
      <el-step title="确认支付信息"></el-step>
    </el-steps>

<!--    确认房源信息-->
    <div id = "ConfirmHouseInfo" v-show="ConfirmHouseInfoDisabel">
     <p class="littleTitle" id="picInfoList">房源实况信息</p>
     <span class="lineClass" id="line1"></span>
      <img :src="this.stringplus + item"  v-for="(item,index) in imgList" :key="index" class="ProductAccountImg">
     <p class="littleTitle" id="houseBasicInfo">房源基本信息</p>
      <span class="lineClass" id="line2"></span>
      <el-descriptions
          class="margin-top"
          :column="3"
          :size="size"
          border
          id="productDeciInfo"
      >
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon><Money /></el-icon>
              房源名称
            </div>
          </template>
          {{this.productHeadInfo.productName}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon><Coin /></el-icon>
              挂价
            </div>
          </template>
          {{this.productHeadInfo.price}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon><Compass /></el-icon>
              地址
            </div>
          </template>
          {{this.productHeadInfo.county}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon><Place /></el-icon>
              小区名
            </div>
          </template>
          {{this.productHeadInfo.community}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon><Clock /></el-icon>
              上架时间
            </div>
          </template>
          {{this.productHeadInfo.createTime.substring(0,10)}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon><SuitcaseLine /></el-icon>
              类型
            </div>
          </template>
          {{this.productDetailInfo.type}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon><Magnet /></el-icon>
              建筑面积
            </div>
          </template>
          {{this.productHeadInfo.area}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon><Position /></el-icon>
              方向
            </div>
          </template>
          {{this.productHeadInfo.direction}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon><Scissor /></el-icon>
              装修情况
            </div>
          </template>
          {{this.productDetailInfo.fitment}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon><Memo /></el-icon>
              评价
            </div>
          </template>
          {{this.productDetailInfo.comment}}
        </el-descriptions-item>
      </el-descriptions>
     <p class="littleTitle" id="ownerBasicInfo">户主基本信息</p>
      <span class="lineClass" id="line3"></span>
      <el-descriptions
          class="margin-top"
          :column="3"
          :size="size"
          border
          id="userDeciInfo"
      >
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon :style="iconStyle">
                <user />
              </el-icon>
              姓名
            </div>
          </template>
          {{this.userInfo.userName}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon :style="iconStyle">
                <iphone />
              </el-icon>
              联系电话
            </div>
          </template>
          {{this.userInfo.phone}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon><Message /></el-icon>
              邮箱
            </div>
          </template>
          {{this.userInfo.email === '' ? '暂无邮箱&emsp;':this.userInfo.email}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon :style="iconStyle">
                <tickets />
              </el-icon>
              个人介绍
            </div>
          </template>
          {{this.userInfo.introduce === '0' ? '暂无介绍&emsp;&emsp;':this.userInfo.introduce}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon :style="iconStyle">
                <location />
              </el-icon>
              地址
            </div>
          </template>
          {{this.userInfo.address === '0' ? '该用户暂未完善地址&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;':this.userInfo.email}}
        </el-descriptions-item>
      </el-descriptions>
    </div>

<!--    确认个人信息-->
    <div id = "ConfirmPersonalInfo" v-show="ConfirmPersonalInfoDisabel">
      <img :src="this.stringplus + this.personalUserInfo.headimg"  id="personalHeadTitle">
<!--      姓名-->
      <p class="infoFont">姓名：<span>{{this.personalUserInfo.userName}}</span></p>
<!--      联系电话-->
      <p class="infoFont">联系电话：<span>{{this.personalUserInfo.phone}}</span></p>
<!--      邮箱-->
      <p class="infoFont">邮箱：<span>{{this.personalUserInfo.email}}</span></p>
<!--      年龄-->
      <p class="infoFont2">年龄：<span>{{this.personalUserInfo.age}}</span></p>
<!--      地址-->
      <p class="infoFont2" id="addressLast">地址：<span>{{this.personalUserInfo.address === '0' ? '您还未完善地址信息':this.personalUserInfo.address}}</span></p>
    </div>
<!--    确认支付信息-->
     <div id = "PayForInfo" v-show="PayForInfoDisabel">
       <p><img src= "../../../assets/订单相关资源/注意事项.png" id="attention"/><span id="LastTitle">请最后确认您的交易信息</span></p>
      <p class="infoTitle">支付金额<span class="infoDetail" id="priceMoney">{{this.price}}&nbsp;元</span></p>
      <p class="infoTitle">支付方式<span class="infoDetail" id="payType"><img src= "../../../assets/订单相关资源/支付宝.png" id="zhifubao"/>支付宝</span></p>
       <p class="infoTitle">支付人姓名<span class="infoDetail">{{this.personalUserInfo.userName}}</span></p>
       <p class="infoTitle">收款人姓名<span class="infoDetail">{{this.userInfo.userName}}</span></p>
       <p class="infoTitle">交易单号<span class="infoDetail">{{this.id}}</span></p>
       <p class="infoTitle">商品<span class="infoDetail">{{this.productHeadInfo.productName}}</span></p>
     </div>

<!--    上下步按钮-->
    <button id="backToPrev" v-show="backToPrevDisabel" @click="prev">返回上一步</button>
    <el-button id="moveToNext" :disabled="this.moveToNextDisabel" @click="next" v-show="this.active !== 2"><p>下一步</p></el-button>
    <el-button id="moveToNext" v-show="this.active == 2" @click="aliPay">支付</el-button>
  </div>
</template>

<script>

import axios from "axios";
import store from "core-js/internals/shared-store";

export default {
  name: "GenerateNewOrderPage",
  mounted() {
    this.requestForm.productId = this.$route.params.productID
    this.requestForm.type = this.$route.params.type
    this.requestForm.userId = this.$route.params.userId
    this.username=store.state.LoadUsername
    this.requestUserForm.userId = store.state.LoadUserId;
    // this.requestUserForm.userId = "GCCI-U-Scuqw8q9h398f2ccdd"
    // this.username = "张虹生"
    this.selectFirstPageProductInfo()
  },
  data() {
    return {
      // 步骤条
      active: 0,
      // 区域显现
      ConfirmHouseInfoDisabel:true,
      ConfirmPersonalInfoDisabel:false,
      PayForInfoDisabel:false,
    //   按钮显现与禁用
      backToPrevDisabel:false,
      moveToNextDisabel:false,
    //   第一页各种信息
    //   请求参数（商品id，类型，所属人id)
      requestForm:{
        productId:"",
        type:"",
        userId:""
      },
       username:"",
      requestUserForm:{
        userId:""
      },
    //   支付参数
      id:"",
      price:"",
    //   返回信息
      imgList:[],
      stringplus: "data:image/png;base64,",
      size:"small",
      userInfo: {
        userName:"",
        phone:"",
        email:"",
        introduce:"",
        address:"",
        userID:""
      },
      productHeadInfo:{
        productName:"",
        price:"",
        direction:"",
        county:"",
        community:"",
        area:"",
        createTime:"",
        id:"",
        productID:""
      },
      productDetailInfo:{
        comment:"",
        fitment:"",
        type:""
      },
      personalUserInfo:{
        address:"",
        age:"",
        headimg:"",
        email:"",
        phone:"",
        userName: ""
      },
      dataUpdateRequestForPay:{
        id:"",
        state:"",
        productId:""
      },
      orderUpdateRequestForPay:{
        productId:"",
        ownerId:"",
        buyerId:"",
        bargain:"",
        finalPayMent:""
      }
    };
  },

  methods: {
    // 查询房源信息（五张图片以及基本信息）以及户主基本信息
    selectFirstPageProductInfo(){
      let _this = this;
      axios({
        method: "post",
        url: "http://localhost:4000/orderInfoController/selectAllInfoOfNewOrder",
        data: this.requestForm
      }).then(function (resp) {
        _this.imgList = resp.data["imgList"]
        _this.userInfo = resp.data["userInfo"]
        _this.productDetailInfo = resp.data["productDeatilInfo"]
        _this.productHeadInfo = resp.data["productHeadInfo"]
      })
    },
    // 查询个人信息
    selectBuyerInfo(){
      let _this = this;
      axios({
        method: "get",
        url: "http://localhost:2000/baseUserController/selectUserInfoByName?username="+this.username,
      }).then(function (resp) {
        _this.personalUserInfo.address = resp.data["result"].address
        _this.personalUserInfo.age = resp.data["result"].age
        _this.personalUserInfo.headimg = resp.data["result"].headimg
        _this.personalUserInfo.email = resp.data["result"].email
        _this.personalUserInfo.userName = resp.data["result"].userName
      })
      axios({
        method: "post",
        url: "http://localhost:2000/baseUserController/selectUserInfoByUserId",
        data: this.requestUserForm
      }).then(function (resp) {
        _this.personalUserInfo.phone = resp.data.phone
      })
    },
    // 支付
    aliPay(){
      var num = Math.random();
      location.href="http://localhost:3000/alipay/pay?name=" + this.username + "&id="+ num.toString(36).substring(2) + "&price=" + this.price
      this.dataSynchronMethod();
      this.orderDataInsertMethod();
    },
    // 同步数据
    dataSynchronMethod(){
      this.dataUpdateRequestForPay.id = this.productHeadInfo.id
      this.dataUpdateRequestForPay.state = "3"
      this.dataUpdateRequestForPay.productId = this.productHeadInfo.productID
      axios({
        method: "post",
        url: "http://localhost:3000/productServe/dataHandel",
        data: this.dataUpdateRequestForPay
      }).then(function () {
      })
    },
    // 订单入库
    orderDataInsertMethod(){
      this.orderUpdateRequestForPay.productId = this.productHeadInfo.productID
      this.orderUpdateRequestForPay.ownerId = this.userInfo.userID
      this.orderUpdateRequestForPay.buyerId = this.requestUserForm.userId
      this.orderUpdateRequestForPay.bargain = this.price
      this.orderUpdateRequestForPay.finalPayMent = parseInt(this.productHeadInfo.price) - parseInt(this.price)
      axios({
        method: "post",
        url: "http://localhost:4000/orderInfoController/InsertDepositOrderData",
        data: this.orderUpdateRequestForPay
      }).then(function () {
      })
    },
    // 前进后退键
    next() {
      this.active++;
      if(this.active == 1){
        this.ConfirmHouseInfoDisabel = false
        this.ConfirmPersonalInfoDisabel = true
        this.moveToNextDisabel = false
        this.backToPrevDisabel = true
        this.selectBuyerInfo()
      }else if(this.active == 2){
        this.price = parseInt(this.productHeadInfo.price) * 0.3

        var num = Math.random();
        var num2 = Math.random();
        //转换为36进制的字符串,36进制即26个字母+10个数字,截取小数点后的字符
        this.id = num.toString(36).substring(2) + num2.toString(36).substring(2)
        this.PayForInfoDisabel = true;
        this.ConfirmPersonalInfoDisabel = false
      }
    },
    prev(){
      this.active--;
      if(this.active === 0){
        this.ConfirmHouseInfoDisabel = true
        this.ConfirmPersonalInfoDisabel = false
        this.backToPrevDisabel = false
      }else if (this.active === 1){
        this.ConfirmHouseInfoDisabel = false
        this.ConfirmPersonalInfoDisabel = true
        this.moveToNextDisabel = false
        this.backToPrevDisabel = true
        this.PayForInfoDisabel = false
        this.selectBuyerInfo()
      }
    }
  }
}
</script>


<style scoped>
#addressLast{
  width: 320px;
}
.infoTitle{
  position: relative;
  color: #c2c2c2;
  margin-top: 30px;
  font-size: 16px;
  left: 280px;
  top: 100px;
}
.infoDetail{
  color: black;
  margin-left: 70px;
}
#priceMoney{
  color: red;
  font-weight: bold;
  font-size: 19px;
}
#payType{
  position: relative;
  left: 20px;
}
#zhifubao{
  position: absolute;
  width: 20px;
  height: 20px;
  left: -23px;
  top: 1px;
}
#LastTitle{
  position: absolute;
  color: #181510;
  font-weight: bold;
  font-size: 20px;
  left: 330px;
  top:20px;
}
#attention{
  position: absolute;
  width: 50px;
  height: 50px;
  left: 270px;
  top: 10px;
}
.infoFont{
  position: relative;
  font-weight: bold;
  font-size: 22px;
  font-family: "Droid Sans Mono Slashed";
  color: black;
  margin-top: 80px;
  top: 80px;
  left: 130px;
}
.infoFont2{
  position: relative;
  font-weight: bold;
  font-size: 22px;
  font-family: "Droid Sans Mono Slashed";
  color: black;
  margin-top: 80px;
  top: -260px;
  left: 470px;
}
#personalHeadTitle{
  position: absolute;
  width: 60px;
  height: 60px;
  left: 365px;
  top: 20px;
  border-radius: 40px;
}
#userDeciInfo{
  position: absolute;
  top: 371px;
  left: 40px;
}
#productDeciInfo{
  position: absolute;
  top: 181px;
  left: 40px;
}
.ProductAccountImg{
  position: relative;
  top: 41px;
  left: 40px;
  width: 115px;
  height: 83px;
  display: inline-block;
  margin-right: 35px; /* 可选：添加项目之间的间距 */
  border-radius: 9px;
  box-shadow: 5px 5px 10px #bbbbbb, -5px -5px 10px #fcfbfb;
  border: none;
}
#backToPrev{
  position: absolute;
  left: 860px;
  top: 640px;
  border-radius: 10px;
  width: 110px;
  height: 40px;
  color: #19191a;
  background-color: #f8f8f8;
}
#backToPrev:hover{
  color: #075d5d;
  border-width: 1px;
  border-style: solid;
  border-color: #075d5d;
}
#moveToNext{
  position: absolute;
  left: 1040px;
  top: 640px;
  border-radius: 10px;
  width: 110px;
  height: 40px;
  color: #012819;
  font-size: 17px;
  background-color: #d4e5e2;
}
#moveToNext:hover{
  color: white;
  font-weight: bold;
  border-color: #013a22;
  border-width: 1px;
  border-style: solid;
  background-color: #013a22;
}
.littleTitle{
  font-size: 21px;
  left: 10px;
  font-family: 华文行楷;
}
.lineClass{
  height: 1px;
  width: 795px;
  background-color: #312e2e;
}
#line1{
  position: absolute;
  top: 347px;
  left:0;
}
#line2{
  position: absolute;
  top: 167px;
  left:0;
}
#line3{
  position: absolute;
  top: 30px;
  left:0;
}
#ownerBasicInfo{
  position: absolute;
  top: 320px;
}
#houseBasicInfo{
  position: absolute;
  top: 140px;
}
#picInfoList {
  position: absolute;
  top: 3px;
}
#ConfirmHouseInfo{
  position: absolute;
  border-style: solid;
  left: 370px;
  top: 140px;
  width: 800px;
  height: 460px;
  border-color: #bdbaba;
  border-radius: 10px;
  border-width: 1px;
}
#ConfirmPersonalInfo{
  position: absolute;
  border-style: solid;
  left: 370px;
  top: 130px;
  width: 800px;
  height: 500px;
  border-color: #bdbaba;
  border-radius: 10px;
  border-width: 1px;
}
#PayForInfo{
  position: absolute;
  border-style: solid;
  left: 370px;
  top: 130px;
  width: 800px;
  height: 500px;
  border-color: #bdbaba;
  border-radius: 10px;
  border-width: 1px;
}
#body {
  width: 100%;
  height: 730px;
  background-color: #e6e6e6;
}
#orderTitle{
  position: absolute;
  left: 540px;
  top: 20px;
  font-size: 29px;
  font-weight: bold;
  letter-spacing: 1px;
  font-family: "Comic Sans MS";
  color: #212529;
}
#decortePic{
  position: absolute;
  left: 840px;
  top: 60px;
  height: 15px;
  width: 175px;
}
#steps{
  position: fixed;
  top: 100px;
  width: 200px;
}
</style>