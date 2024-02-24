<template>

  <el-alert v-model:title="this.errorMessage" type="success" center id="successMessage" v-show="this.errorDisper"/>

  <div id="body">
<!--    商品全部图片-->
    <div class="block text-center" m="t-4">
      <el-carousel trigger="click">
        <el-carousel-item v-for="item in imgList" :key="item">
          <img :src="this.stringplus + item" class="detailImgStyle">
        </el-carousel-item>
      </el-carousel>
    </div>

<!--    小区测评及评分-->
    <div id="productRate">
          <p id="title">小区测评</p>
          <div id="rateArea">
            <p id="rate">{{this.responseForm.rate}}</p>
            <el-rate
                v-model="value"
                disabled
                text-color="#ff9900"
                :colors="colors"
                size="large"
                allow-half
                id="ratePic"
            />
          </div>
          <span><p class="rateDetail">产品价值</p><el-progress class="rateProgress" :percentage="productValue" :color="customColor" :show-text="false"/></span>
          <span><p class="rateDetail">配套价值</p><el-progress class="rateProgress" :percentage="supportValue" :color="customColor" :show-text="false"/></span>
          <span><p class="rateDetail">交易价值</p><el-progress class="rateProgress" :percentage="tradeValue" :color="customColor" :show-text="false"/></span>
          <span><p class="rateDetail">区域价值</p><el-progress class="rateProgress" :percentage="areaValue" :color="customColor" :show-text="false"/></span>
          <p id="comment">{{this.responseForm.comment}}</p>
    </div>

<!--    各种详细信息-->
    <div id="allInfoDetails">
       <p id="productName">{{this.productForm.productName}}</p>
<!--      二手房-->
       <p class="detailTitle" id="Rcontent1">售价<span class="contentDetail" id="_Rcontent1">{{this.productForm.type == '0' ? this.productForm.price+'元/平' :this.productForm.price/10000+'万'}}</span></p>
       <p class="detailTitle" v-show="this.productForm.type != '0'" id="Rcontent2">房型<span class="contentDetail" id="_Rcontent2">{{this.productForm.productName.indexOf("合租") >= 0 || this.productForm.productName.indexOf("整租") >= 0 ? this.productForm.productName.substring(3,7) : this.productForm.productName.indexOf("厅 ") >= 0 ? this.productForm.productName.substring(0,5) : this.productForm.productName}}</span></p>
       <p class="detailTitle" v-show="this.productForm.type !== '0'" id="Rcontent3">建筑面积<span class="contentDetail" id="_Rcontent3">{{this.productForm.area}}平</span></p>
       <p class="detailTitle" v-show="this.productForm.type !== '0'">单价<span class="contentDetail">{{Math.round(this.productForm.price/this.productForm.area)}}元/平</span></p>
       <p class="detailTitle" v-show="this.productForm.type !== '0'">楼层<span class="contentDetail">{{this.responseForm.floor}}</span></p>
       <p class="detailTitle" v-show="this.productForm.type !== '0'">套内<span class="contentDetail">{{this.responseForm.insideSet}}</span></p>
       <p class="detailTitle" v-show="this.productForm.type !== '0'">挂牌<span class="contentDetail">{{this.productForm.createTime.substring(0,10)}}</span></p>
       <p class="detailTitle" v-show="this.productForm.type !== '0'">朝向<span class="contentDetail">{{this.productForm.direction}}</span></p>
       <p class="detailTitle" v-show="this.productForm.type !== '0'">装修<span class="contentDetail">{{this.responseForm.fitment}}</span></p>
       <p class="_detailTitle" v-show="this.productForm.type !== '0'">电梯<span class="_contentDetail">{{this.responseForm.lift === '1' ? '有电梯' : '无电梯'}}</span></p>
       <p class="_detailTitle" v-show="this.productForm.type !== '0'">楼型<span class="_contentDetail">{{this.responseForm.floorType}}</span></p>
       <p class="_detailTitle" v-show="this.productForm.type !== '0'">类型<span class="_contentDetail">{{this.responseForm.type}}</span></p>
       <p class="_detailTitle" v-show="this.productForm.type !== '0'">权属<span class="_contentDetail">{{this.responseForm.ownerShip}}</span></p>
       <p class="_detailTitle" v-show="this.productForm.type !== '0'">年代<span class="_contentDetail">{{this.responseForm.age}}</span></p>
       <p class="_detailTitle" v-show="this.productForm.type !== '0'">小区<span class="_contentDetail">{{this.productForm.productName.indexOf("合租") >= 0 || this.productForm.productName.indexOf("整租") >= 0 ? this.productForm.productName.substring(8,this.productForm.productName.length) : this.productForm.productName.indexOf("厅 ") >= 0? this.productForm.productName.substring(5,this.productForm.productName.length) : this.productForm.productName}}</span></p>

<!--      新房-->
      <p class="detailTitle2" v-show="this.productForm.type === '0'" id="NDetail1">建筑面积<span class="contentDetail2" id="NContent1">{{this.productForm.area}}平</span></p>
      <p class="detailTitle2" v-show="this.productForm.type === '0'" id="NDetail2">产权年限<span class="contentDetail2" id="NContent2">{{this.responseForm.equity}}</span></p>
      <p class="detailTitle2" v-show="this.productForm.type === '0'">物业类型<span class="contentDetail2">{{this.responseForm.usage}}</span></p>
      <p class="detailTitle2" v-show="this.productForm.type === '0'">建筑类型<span class="contentDetail2">{{this.responseForm.typeOfBuild}}</span></p>
      <p class="detailTitle2" v-show="this.productForm.type === '0'">装修状况<span class="contentDetail2">{{this.responseForm.renovation}}</span></p>
      <p class="detailTitle2" v-show="this.productForm.type === '0'">地址<span class="contentDetail2">{{this.responseForm.address}}</span></p>
      <p class="detailTitle2" v-show="this.productForm.type === '0'">开盘时间<span class="contentDetail2">{{this.productForm.createTime.substring(0,10)}}</span></p>
<!--&lt;!&ndash;      建筑概况&ndash;&gt;-->
      <p class="detailTitle2" v-show="this.productForm.type === '0'">占地面积<span class="contentDetail2">{{this.responseForm.area}}m²</span></p>
      <p class="detailTitle2" v-show="this.productForm.type === '0'">建筑面积<span class="contentDetail2">{{this.responseForm.floorSpace}}m²</span></p>
      <p class="detailTitle2" v-show="this.productForm.type === '0'">容积率<span class="contentDetail2">{{this.responseForm.floorArea}}</span></p>
      <p class="detailTitle2" v-show="this.productForm.type === '0'">绿化率<span class="contentDetail2">{{this.responseForm.greenRate}}</span></p>
      <p class="_detailTitle2" v-show="this.productForm.type === '0'">规划户数<span class="_contentDetail2">{{this.responseForm.planHouseHold}}</span></p>
      <p class="_detailTitle2" v-show="this.productForm.type === '0'">车位情况<span class="_contentDetail2">{{this.responseForm.parkAvailability}}</span></p>
      <p class="_detailTitle2" v-show="this.productForm.type === '0'">车位配比<span class="_contentDetail2">{{this.responseForm.parkSpaceRatio}}</span></p>
<!--&lt;!&ndash;      物业信息&ndash;&gt;-->
      <p class="_detailTitle2" v-show="this.productForm.type === '0'">物业公司<span class="_contentDetail2">{{this.responseForm.strataCompany}}</span></p>
      <p class="_detailTitle2" v-show="this.productForm.type === '0'">物业费用<span class="_contentDetail2">{{this.responseForm.propertyFee}}元/m²/月</span></p>
      <p class="_detailTitle2" v-show="this.productForm.type === '0'">供水类型<span class="_contentDetail2">{{this.responseForm.waterSupply}}</span></p>
      <p class="_detailTitle2" v-show="this.productForm.type === '0'">供电类型<span class="_contentDetail2">{{this.responseForm.electricSupply}}</span></p>
      <p class="_detailTitle2" v-show="this.productForm.type === '0'">供暖方式<span class="_contentDetail2">{{this.responseForm.heatSupply}}</span></p>

<!--      底部-->
      <img :src="this.stringplus + this.responseForm.userInfo._headImg" id="personImg">
      <p id="personName">{{this.responseForm.userInfo.userName}}</p>
      <img v-bind:src="imgSrc" class="scribedImg" v-show="this.responseForm.isSubscrib === '0' && this.userName !== this.responseForm.userInfo.userName && neumorphism2Disabel && this.$route.params.flag !== 'admin'" @click="click(this.responseForm.isSubscrib)">
      <img v-bind:src="imgSrc1" class="scribedImg" v-show="this.responseForm.isSubscrib === '1' && this.userName !== this.responseForm.userInfo.userName && neumorphism2Disabel && this.$route.params.flag !== 'admin'" @click="click(this.responseForm.isSubscrib)">
      <button class="neumorphism" v-show="this.userName !== this.responseForm.userInfo.userName && this.$route.params.flag !== 'admin'" @click="chatOnline()"><span>在线聊天</span></button>
      <button class="neumorphism2" v-show="this.userName !== this.responseForm.userInfo.userName && this.productForm.type === '2' && neumorphism2Disabel && this.$route.params.flag !== 'admin'" @click="generateNewOrder"><span v-show="!this.generateNewOrderDisabel">支付定金</span><span v-show="this.generateNewOrderDisabel"><el-icon class="is-loading"><Loading /></el-icon>生成订单中</span></button>
    </div>

  </div>
</template>

<script>
import axios from "axios";
import router from "@/router";
import store from "core-js/internals/shared-store";

export default {
  name: "ProductInfoDetailsPage",
  mounted() {
    this.requestForm.productId = this.$route.params.productID
    this.requestForm.type = this.$route.params.type
    this.requestForm.userId = this.$route.params.userId
    this.selectOneDetails()
    this.selectOneProduct()
    this.userName=store.state.LoadUsername
    // this.userName = "张虹生"
    this.userId = store.state.LoadUserId

    // this.userId = "GCCI-U-Scuqw8q9h398f2ccdd"
    if(this.$route.params.flag === 'order'){
      this.neumorphism2Disabel = false
    }
  },
  data(){
    return{
      generateNewOrderDisabel:false,
      stringplus: "data:image/png;base64,",
      imgSrc: require('/src/assets/首页资源/未关注.png'),
      imgSrc1: require('/src/assets/首页资源/已关注.png'),
      errorMessage:"",
      errorDisper:false,
      userName: "",
      userId:"",
      requestForm:{
        productId:"",
        type:"",
        userId:""
      },
      colors: ['#99A9BF', '#F7BA2A', '#FF9900'],
      customColor: '#ff8200',
      value:"",
      productValue:"",
      areaValue:"",
      tradeValue:"",
      supportValue:"",
      responseForm:{
        // 二手或合租
        age:"",
        fitment:"",
        floor:"",
        floorType:"",
        insideSet:"",
        isSubscrib:"",
        lift:"",
        ownerShip:"",
        productID:"",
        type:"",
        // 新房
        address:"",
        area:"",
        electricSupply:"",
        equity:"",
        floorArea:"",
        floorSpace:"",
        greenRate:"",
        heatSupply:"",
        parkAvailability:"",
        parkSpaceRadio:"",
        planHouseHold:"",
        propertyFee:"",
        renovation:"",
        strataCompany:"",
        typeOfBuild:"",
        usage:"",
        waterSupply:"",
        areaValue:0,
        productValue:0,
        rate:0,
        supportValue:0,
        tradeValue:0,
        comment:"",
        userInfo:{
          _headImg:"",
          userName:"",
          phone:"",
          userID:""
        }
      },
      productForm:{
        community:"",
        createTime:"",
        county:"",
        direction:"",
        price:"",
        productName:"",
        area:"",
        type:""
      },
      imgList:[],
      neumorphism2Disabel:true
    }
  },
  methods:{
     selectOneDetails(){
       let _this = this
       axios({
         method: "post",
         url: "http://localhost:3000/productDetail/selectOneProductDetailById",
         data: this.requestForm
       }).then(function (resp) {
         _this.responseForm = {}
         _this.responseForm = resp.data["body"]
         _this.imgList = resp.data["imgList"]
         _this.value = resp.data["body"].rate - 5
         _this.productValue = resp.data["body"].productValue * 10
         _this.areaValue = resp.data["body"].areaValue * 10
         _this.tradeValue = resp.data["body"].tradeValue * 10
         _this.supportValue = resp.data["body"].supportValue * 10
       })
     },
    // 生成订单
    generateNewOrder(){
      this.generateNewOrderDisabel = true
      setTimeout(() => {
        this.generateNewOrderDisabel = false
        router.push("/GenerateNewOrderPage/"+this.requestForm.productId+"/"+this.requestForm.type+"/"+this.requestForm.userId)
      }, 1500);
    },
    selectOneProduct(){
      let _this = this;
      axios({
        method: "get",
        url: "http://localhost:3000/productServe/selectOneProductByID?productId="+this.requestForm.productId,
      }).then(function (resp) {
        _this.productForm = {}
        _this.productForm = resp.data["body"]
      })
    },
    chatOnline(){
       let userId = store.state.LoadUserId;
       // let userId = "GCCI-U-Scuqw8q9h398f2ccdd";
       router.push("/TalkRecordPage/"+userId+"/"+this.requestForm.userId)
    },
    click(state){
       let _this = this;
      axios({
        method: "get",
        url: "http://localhost:3000/productDetail/updateSubscribedById?productId="+this.requestForm.productId+"&type="+this.productForm.type+"&opertion="+state+"&userId="+this.userId,
      }).then(function (resp) {
        if(resp.data["body"] === 1 && state === '0'){
          _this.errorMessage = '关注成功'
          _this.ErrorMessageApperance();
          _this.responseForm.isSubscrib = '1'
          _this.imgSrc = require('/src/assets/首页资源/已关注.png')
        }else if(resp.data["body"] === 1 && state === '1'){
          _this.responseForm.isSubscrib = '0'
          _this.imgSrc = require('/src/assets/首页资源/未关注.png')
          _this.errorMessage = '已取消关注'
          _this.ErrorMessageApperance();
        }
      })
    },
    ErrorMessageApperance() {
      this.errorDisper = true;
      setTimeout(() => {
        this.errorDisper = false
      }, 4000);
    },
  }
}
</script>

<style scoped>
#NDetail1{
   top: 121px;
   left: 300px;
}
#NContent1{
  top: -30px;
  left: -68px;
  font-size: 32px;
  font-weight: bold;
  color: black;
}
#NDetail2{
  top: 73px;
  left: 500px;
}
#NContent2{
  top: -30px;
  left: -68px;
  font-size: 32px;
  font-weight: bold;
  color: black;
}
.detailTitle2{
  position: relative;
  font-size: 17px;
  color: #aba9a9;
  top: 80px;
  left: 70px;
  line-height: 41px;
}
.contentDetail2{
  position: relative;
  font-size: 17px;
  color: black;
  left: 15px;
}
._detailTitle2{
  position: relative;
  font-size: 17px;
  color: #aba9a9;
  top: -290px;
  left: 400px;
  line-height: 41px;
}
._contentDetail2{
  position: relative;
  font-size: 17px;
  color: black;
  left: 15px;
}
#personName{
  top: 623px;
  left: 74px;
  font-size: 10px;
  letter-spacing: 0.5px;
  position: absolute;
}
.scribedImg{
  position: absolute;
  width: 45px;
  height: 45px;
  top: 575px;
  left: 120px;
}

#successMessage {
  position: absolute;
  width: 300px;
  top: 60px;
  z-index: 2;
  left: 600px;
}
#personImg{
  height: 40px;
  width: 40px;
  border-radius: 40px;
  top: 580px;
  left: 70px;
  position: absolute;
}
.detailTitle{
  position: relative;
  font-size: 17px;
  color: #aba9a9;
  top: 70px;
  left: 70px;
  line-height: 51px;
}
.contentDetail{
  position: relative;
  font-size: 17px;
  color: black;
  left: 15px;
}
._detailTitle{
  position: relative;
  font-size: 17px;
  color: #aba9a9;
  top: -237px;
  left: 370px;
  line-height: 51px;
}
._contentDetail{
  position: relative;
  font-size: 17px;
  color: black;
  left: 15px;
}
#productName{
  position: absolute;
  font-size: 64px;
  left: 70px;
  width: 1000px;
  font-family: 华文行楷;
}
.detailImgStyle{
  width: 100%;
  height: 100%;
}
#Rcontent1{
  top: 150px;
  line-height: 0px;
}
#_Rcontent1{
  top: -30px;
  font-size: 32px;
  font-weight: bold;
  left: -40px;
  color: #f35917;
  line-height: 0px;
}
#Rcontent2{
  top: 117px;
  left: 230px;
}
#_Rcontent2{
  top: -30px;
  font-size: 32px;
  font-weight: bold;
  left: -40px;
  color: black;
}

#Rcontent3{
  top: 60px;
  left: 400px;
}
#_Rcontent3{
  top: -30px;
  font-size: 32px;
  font-weight: bold;
  left: -70px;
  color: black;
}
#comment{
  position: absolute;
  top: 170px;
  left: 10px;
  font-size: 13px;
  color: #854848;
}
.rateProgress{
  position: relative;
  top: 50px;
  width: 180px;
  height: 6px;
  left: 200px;
}
.rateDetail{
  position: relative;
  font-size: 12px;
  top: 61px;
  left: 150px;
  color: #cec9c9;
}
.neumorphism {
  position: absolute;
  top: 570px;
  left: 200px;
  width: 400px;
  height: 60px;
  border: none;
  border-bottom-left-radius: 10px;
  border-top-left-radius: 10px;
  border-bottom-right-radius: 20px;
  border-top-right-radius: 20px;
  background-color: #f66c0d;
  box-shadow: inset 5px 5px 10px #ff3a00, inset -5px -5px 10px #794c31;
}
.neumorphism:active {
  box-shadow: inset 1px 1px 2px #321064, inset -1px -1px 2px #002aff;
}
.neumorphism:focus {
  outline: none;
}
.neumorphism span {
  display: block;
  user-select: none;
  color: #fffafa;
  font-size: 22px;
  text-align: center;
  line-height: 55px;
  transition: 0.1s;
}
.neumorphism:active span {
  transform: scale(0.95);
}
.neumorphism2 {
  position: absolute;
  top: 590px;
  left: 630px;
  width: 140px;
  height: 40px;
  border: none;
  border-radius: 20px;
  background-color: #e6e6e6;
  box-shadow: 5px 5px 10px #bbbbbb, -5px -5px 10px #ffffff;
}
.neumorphism2:active {
  box-shadow: inset 5px 5px 10px #bbbbbb, inset -5px -5px 10px #ffffff;
}
.neumorphism2:focus {
  outline: none;
}
.neumorphism2 span {
  display: block;
  user-select: none;
  color: #676464;
  font-size: 17px;
  font-weight: bold;
  text-align: center;
  line-height: 40px;
  transition: 0.1s;
}
.neumorphism2:active span {
  transform: scale(0.95);
}

#rate{
   position: absolute;
   font-size: 47px;
  font-weight: bold;
  left: 23px;
  top: 1px;
}
#ratePic{
  position: absolute;
  top: 60px;
  left: 5px;
}
#title{
  position: absolute;
  top: 20px;
  font-size: 25px;
  font-family: 华文行楷;
}
#rateArea{
  position: absolute;
  top: 60px;
  width: 400px;
  height: 160px;
  box-shadow: 3px 3px 10px #bbbbbb, -3px -3px 10px #fcfbfb;
  border: none;
  border-radius: 20px;
  background-color: #e6e6e6;
}
#productRate{
  position: absolute;
  width: 400px;
  height: 270px;
  left: 200px;
  top: 430px;
}
#allInfoDetails{
  position: absolute;
  width: 800px;
  height: 620px;
  left: 650px;
  top: 80px;
}

#body {
  width: 100%;
  height: 730px;
  background-color: #e6e6e6;
}

.el-carousel__item h3 {
  color: #475669;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
  text-align: center;
}

.el-carousel {
  position: relative;
  top: 100px;
  left: 200px;
  width: 400px;
  height: 300px;
  box-shadow: 5px 5px 10px #bbbbbb, -5px -5px 10px #fcfbfb;
  border: none;
  border-radius: 20px;
  background-color: #e6e6e6;
}

.el-carousel-item {

}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
</style>