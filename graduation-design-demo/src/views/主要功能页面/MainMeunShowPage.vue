<template>

  <el-alert v-model:title="this.errorMessage" type="success" center id="successMessage" v-show="this.successDisper"/>

  <div id = "all"></div>
  <img src="../../assets/首页头资源/菜单.png" id="open" @click="open">
  <HeadLineViewForMain/>
  <router-view></router-view>

  <div id="OfficialSubscribe">
    <p id="titleContent">寄居蟹公众号</p>
    <img v-bind:src="towards" id="changePosition" @click="changePosition('item','grid-container')" @mouseover="changePositionColor(this.imgSrc)" @mouseleave="changePositionColorBack(this.imgSrc)">

<!--    公众号列表-->
    <div id="horizontal-display" v-show="OfficalAccountIsVisible">
      <el-card v-for="(item,index) in OfficalAccountResponseData" :key="item" class="OfficalAccount" :class="{ 'OfficalAccount1': index === 0, 'OfficalAccount2': index === 1 ,'OfficalAccount3': index === 2}">
        <img :src="this.stringplus + item.accountImg" class="OfficalAccountImg"> <p class="OfficalAccountName">{{item.accountName}}</p> <img src="../../assets/精选页资源/关注前.png" v-if="showItem[index]"  class="subscribeBtnImg"><img src="../../assets/精选页资源/关注后.png" v-else  class="subscribeBtnImg">
        <button class="subscribeBtn" @click="subscribe(index,item.accountName)"><p class="subscribeFont">关注</p></button> </el-card>
    </div>

    <span id="line1"></span>

  </div>

  <transition-group name="move-elements" tag="div">
  <div id="mainContent">
    <p id="SectitleContent" class="item">"蟹"的精选</p>
    <span id="line2" class="item"></span>
<!--搜索框-->
      <el-card id="secher" class="item">
        <input type="text"  placeholder="查询房型名称" id="secherInput" class="item" v-model="searchText"/>
        <img src='../../assets/精选页资源/搜索.png' id="serchImg" class="item" @click="searchOfProductNameByEs">
      </el-card>

  </div>
  </transition-group>

<!--  主页房产-->
  <div id = "mainMeunHouses">

    <div class="grid-container">
      <div v-for="(item, index) in mainMeunHouses" :key="index" class="grid-item">
        <el-card class="ShowCardClass" @click="lookForProductInfoDetails(item.productID,item.type,item.userID)">
          <img :src="this.stringplus + item.firstPage" class="houseImgStyleClass">
<!--          房子名称-->
          <p class="productName" v-html="item.productName"></p>
<!--          房子信息（面积，方向，地区）-->
<!--          位置-->
          <span class="county" >{{item.county}}</span>
<!--          方向-->
          <span class="direction" v-show="item.direction != null">{{item.direction}}&nbsp;&nbsp;|</span>
<!--          面积-->
          <span class="area" >{{item.area}}</span>
<!--          房子标签-->
          <div v-for="(tag,i) in this.tagList[index]" :key="i" class="tagStyle" :class="{ 'goldTag1': tag[0] === '成' && tag[1] === '熟' && tag[2] === '商',
          'goldTag2': tag[0] === '绿' && tag[1] === '化' && tag[2] === '率',
          'goldTag3': tag[0] === '车' && tag[1] === '位' && tag[2] === '充' }"><p>{{tag}}</p></div>
<!--          价格-->
          <span class="price">{{item.price}}<span class="priceDes">{{item.priceDescription}}</span></span><span v-show="item.pricePerMeter != null" class="pricePerMeter">{{item.pricePerMeter}}</span>
        </el-card>


      </div>
    </div>

    <el-pagination
        id="pageination"
        v-model:current-page=currentPageNum
        v-model:page-size=size
        :page-sizes="[15, 20, 25, 30]"
        :disabled="disabled"
        :background="background"
        layout="total, sizes, prev, pager, next"
        :total=totalCount
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    />
  </div>

</template>

<script>
import HeadLineViewForMain from "@/components/HeadLineViewForMain.vue";
import store from "core-js/internals/shared-store";
import router from "@/router";
import axios from "axios";


export default {
  name: "MainMeunShowPage",
  components: {
    HeadLineViewForMain
  },
  beforeRouteUpdate(to, from, next) {
    // 执行逻辑，模拟 mounted 行为
    this.$nextTick(() => {
      if(to.path === '/MainMeunShowPage/New'){
        this.sign = "New"
        this.type = "0"
        this.selectAllHousesByCondition();
      }else if(to.path === '/MainMeunShowPage/Sec'){
        this.sign = "Sec"
        this.type = "1"
        this.selectAllHousesByCondition();
      }else if(to.path === '/MainMeunShowPage/Rent'){
        this.sign = "Rent"
        this.type = "2"
        this.selectAllHousesByCondition();
      }else if(to.path === '/MainMeunShowPage/main'){
        this.type = ""
        this.selectAllHousesCount();
        this.selectAllHouses();
      }
    });

    next();
  },
  mounted() {
    this.OfficalAccountRequestData.userName = store.state.LoadUsername
      this.selectAllHousesCount();
      this.selectAllHouses();
      this.headPageSubsribeShow();
  },
  updated(){
    var all = document.getElementById("all")
    if(store.state.leftSideChange == "back"){
      all.style.boxShadow="";
    }
  },
  beforeUpdate() {

  },
  data(){
    return{
      searchText:"",
      type:"",
      sign:"main",
      errorMessage: "",
      successDisper: false,
      showItem: Array(100).fill(true), // 用于控制显示与隐藏的数组
      stringplus: "data:image/png;base64,",
      OfficalAccountIsVisible:true,
      towards: require('../../assets/精选页资源/向下1.png'),
      imgSrc: '../../assets/精选页资源/向下1.png',
      OfficalAccountRequestData:{
        userName:""
      },
      OfficalAccountResponseData:[],
      SubscribeAccountData:{
        userName:"",
        accountName:"",
        index:""
      },
    //   房产数组
      mainMeunHouses:[],
      middtagList:[],
      tagList:[],
      currentPageNum:1,
      size:20,
      totalCount:200
    }
  },
  methods:{
    // 根据条件查询所有
    selectAllHousesByCondition(){
      this.tagList = [];
      this.middtagList = [];
      let _this = this;
      axios({
        method: "get",
        url: "http://localhost:3000/productServe/selectAllHousesByCondition?beginNum="+this.currentPageNum+"&size="+this.size+"&city=苏州市"+"&type="+this.sign,
      }).then(function (resp) {
        _this.mainMeunHouses = resp.data["body"]["records"]
        _this.totalCount = resp.data["count"]
        _this.mainMeunHouses.forEach(e => {
          _this.middtagList.push(e.tagList)
        })
        for (let i = 0; i < _this.middtagList.length; i++) {
          _this.tagList[i] = _this.middtagList[i].split(",");
        }
      })
    },
    // 查询所有房产
    selectAllHouses(){
      this.tagList = [];
      this.middtagList = [];
      let _this = this;
      axios({
        method: "get",
        url: "http://localhost:3000/productServe/selectAllProductByPage?beginNum="+this.currentPageNum+"&size="+this.size+"&city=苏州市",
      }).then(function (resp) {
         _this.mainMeunHouses = resp.data["records"]
        _this.mainMeunHouses.forEach(e => {
          _this.middtagList.push(e.tagList)
        })
        for (let i = 0; i < _this.middtagList.length; i++) {
          _this.tagList[i] = _this.middtagList[i].split(",");
        }
      })
    },
    // 查询每件商品的详细信息
    lookForProductInfoDetails(productId,type,userId){
      router.push("/ProductInfoDetailsPage/"+productId+"/"+type+"/"+userId+"/"+"product")
    },
    // 查询共有多少数量
    selectAllHousesCount(){
      let _this = this;
      axios({
        method: "get",
        url: "http://localhost:3000/productServe/selectAllProductCountByCity?city=苏州市"
      }).then(function (resp) {
        _this.totalCount = resp.data["count"]
      })
    },
    searchOfProductNameByEs(){
      this.sign = "searchOfES";
      let _this = this;
      this.tagList = [];
      this.middtagList = [];
      axios({
        method: "get",
        url: "http://localhost:3000/productServe/selectAllProductByConditionOfPageAndUnitES?productName=" + this.searchText + "&currentPage=" + this.currentPageNum + "&size=" + this.size + "&type=" + this.type
      }).then(function (resp) {
        _this.mainMeunHouses = resp.data["body"]
        _this.totalCount = resp.data["total"]
        _this.mainMeunHouses.forEach(e => {
          _this.middtagList.push(e.tagList)
        })
        for (let i = 0; i < _this.middtagList.length; i++) {
          _this.tagList[i] = _this.middtagList[i].split(",");
        }
      })
    },
    // 分页
    handleSizeChange(val) {
      this.size = val;
      if(this.sign === "main") {
        this.selectAllHouses();
      }else if (this.sign === "searchOfES"){
        this.searchOfProductNameByEs();
      }else {
        this.selectAllHousesByCondition();
      }
    },
    handleCurrentChange(val) {
      this.currentPageNum = val;
      if(this.sign === "main") {
        this.selectAllHouses();
      }else if (this.sign === "searchOfES"){
        this.searchOfProductNameByEs();
      }else {
        this.selectAllHousesByCondition();
      }
    },
    open() {
      store.state.leftSideChange = "go"
      router.push('/MainMeunShowPage/'+ this.sign + '/LeftSideLineView')
      var all = document.getElementById("all")
      all.style.backgroundColor="rgb(127, 127, 127)"
      all.style.boxShadow="0 0 0 1000px rgba(0,0,0,.3)";
    },
    // 查询公众号
    headPageSubsribeShow(){
      let _this = this;
      axios({
        method: "post",
        url: "http://localhost:2000/officalAccountController/headPageSubsribeShow",
        data: this.OfficalAccountRequestData
      }).then(function (resp) {
          _this.OfficalAccountResponseData = resp.data
      })
    },
    // 成功信息
    SuccessMessageApperance() {
      this.successDisper = true;
      setTimeout(() => {
        this.successDisper = false
      }, 2000);
    },
    // 关注公众号
    subscribe(index,accounName){
      let _this = this;
      this.showItem[index] = !this.showItem[index];
      // this.SubscribeAccountData.userName = "张虹生";
      this.SubscribeAccountData.userName = store.state.LoadUsername;
      this.SubscribeAccountData.accountName = accounName;
      axios({
        method: "post",
        url: "http://localhost:2000/officalAccountController/addSubscribeAccount",
        data: this.SubscribeAccountData
      }).then(function (resp) {
        _this.errorMessage = resp.data["result"]
        _this.showItem[index] = !_this.showItem[index];
        _this.SuccessMessageApperance();
        _this.headPageSubsribeShow();
      })
    },
    // 变化位置
    changePosition(className1,className2){
        this.OfficalAccountIsVisible = false
        if(this.imgSrc === '../../assets/精选页资源/向上1.png'){
          this.resetChangePosition(className1,className2);
        }else {
          this.towards = require('../../assets/精选页资源/向上1.png')
          this.imgSrc = '../../assets/精选页资源/向上1.png'
          this.changePositionInside(className1)
          this.changePositionInside(className2)
        }
    },
    changePositionInside(className){
      var positionList = document.getElementsByClassName(className)
      for (let i = 0; i < positionList.length; i++) {
        var _position = positionList[i];
        var position = window.getComputedStyle(_position);
        var topValue = position.getPropertyValue('top');
        var topNumber = parseInt(topValue, 10);
        if (topNumber > 150) {
          var newtopNumber = topNumber - "150";
          _position.style.top = newtopNumber + "px"
        }
      }
    },
    resetChangePosition(className1,className2){
      this.OfficalAccountIsVisible = true
      this.towards=require('../../assets/精选页资源/向下1.png')
      this.imgSrc = '../../assets/精选页资源/向下1.png'
      this.resetChangePositionInside(className1);
      this.resetChangePositionInside(className2);
    },
    resetChangePositionInside(className){
      var positionList = document.getElementsByClassName(className)
      for (let i = 0; i < positionList.length; i++) {
        var _position = positionList[i];
        var position = window.getComputedStyle(_position);
        var topValue = position.getPropertyValue('top');
        var topNumber = parseInt(topValue,10);
        if(topNumber > 30) {
          var newtopNumber = topNumber + 150;
          _position.style.top = newtopNumber + "px"
        }
      }
    },
  //   更改颜色
    changePositionColor(imgSrc){
       if (imgSrc==='../../assets/精选页资源/向下1.png'){
         this.towards=require('../../assets/精选页资源/向下1 (1).png')
       }else if (imgSrc==='../../assets/精选页资源/向上1.png'){
         this.towards=require('../../assets/精选页资源/向上1 (1).png')
       }
    },
    changePositionColorBack(imgSrc){
      if (imgSrc==='../../assets/精选页资源/向下1 (1).png'){
        this.towards=require('../../assets/精选页资源/向下1.png')
      }else if (imgSrc==='../../assets/精选页资源/向上1 (1).png'){
        this.towards=require('../../assets/精选页资源/向上1.png')
      }
    }
  }
}
</script>

<style scoped>
.county{
  position: relative;
  top: 5px;
  left: -50px;
  font-size: 10px;
  letter-spacing: 0;
}
.direction{
  position: relative;
  top: 5px;
  left: -45px;
  font-size: 10px;
  letter-spacing: 0;
}
.price{
  position: relative;
  bottom: 34px;
  left: 50px;
  color: #ff4500;
  font-weight: bold;
  font-size: 29px;
}
.pricePerMeter{
  position: relative;
  bottom: 34px;
  left: 59px;
  color: #c5c5c5;
  font-size: 12px;
}
.priceDes{
  font-size: 9px;
}
.tagStyle{
  position: relative;
  left: -10px;
  top: 9px;
  font-size: 10px;
  width: 70px;
  height: 18px;
  border-radius: 11px;
  background-color: #bed3f5;
  font-weight: lighter;
  color: #1743f3;
  font-family: "Microsoft JhengHei UI";
  border-bottom: solid #ffffff 3px;
}

.goldTag1{
  position: relative;
  left: -10px;
  top: 9px;
  font-size: 10px;
  width: 70px;
  height: 18px;
  border-radius: 11px;
  background-color: #f3e4c5;
  font-weight: lighter;
  color: #f39417;
  font-family: "Microsoft JhengHei Light";
  border-bottom: solid #ffffff 3px;
}
.goldTag2{
  position: relative;
  left: -10px;
  top: 9px;
  font-size: 10px;
  width: 70px;
  height: 18px;
  border-radius: 11px;
  background-color: #f3e4c5;
  font-weight: lighter;
  color: #f39417;
  font-family: "Microsoft JhengHei Light";
  border-bottom: solid #ffffff 3px;
}
.goldTag3{
  position: relative;
  left: -10px;
  top: 9px;
  font-size: 10px;
  width: 70px;
  height: 18px;
  border-radius: 11px;
  background-color: #f3e4c5;
  font-weight: lighter;
  color: #f39417;
  font-family: "Microsoft JhengHei Light";
  border-bottom: solid #ffffff 3px;
}
.area{
  position: relative;
  top: 5px;
  left: -40px;
  font-size: 10px;
  letter-spacing: 0;
  text-align: left;
}
.productName{
  font-size: 17px;
  font-weight: bold;
  letter-spacing: 1px;
  text-align: left;
}
.houseImgStyleClass{
  position: relative;
  width: 253px;
  height: 158px;
  left: -24px;
  top: -20px;
}
.ShowCardClass{
   height: 300px;
  border-radius: 23px;
  box-shadow: 1px 1px 2px 0px #37373A6B;
  transition: transform 0.3s, box-shadow 0.4s;
}
.ShowCardClass:hover{
  transform: scale(1.25); /* 缩放，产生凸出效果 */
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
}
.grid-container {
  position: absolute;
  top: 520px;
  left: 80px;
  display: grid;
  grid-template-columns: repeat(4, 1fr); /* 四列布局 */
  grid-gap: 120px; /* 设置项目之间的间距 */
  z-index:-1;
}

.grid-item {
  width: 250px;
  height: 270px;
  background-color: #ffffff;
  text-align: center;
}
#pageination{
  position: fixed;
  bottom: 0px;
  left: 500px;
  padding-bottom: -30px;
  z-index: -1;
  opacity:0.7;
}
#horizontal-display {
  position: absolute;
  left: 150px;
  top: 150px;
  z-index: -1;
  display: flex; /* 使用 Flex 布局 */
  flex-direction: row; /* 水平方向展示 */
  justify-content: space-between;
}
.subscribeFont{
  position: relative;
  top: -1px;
  left: 12px;
  color: white;
  font-size: 12px;
  font-weight: bold;
  letter-spacing: 2px;
  z-index: 1;
}
#successMessage {
  position: absolute;
  width: 300px;
  top: 60px;
  z-index: 2;
  left: 600px;
}
.subscribeBtn{
  position: relative;
  left: -5px;
  top: 30px;
  width: 87px;
  height: 25px;
  border-radius: 7px;
  background-color: #5A5B6BFF;
}
.subscribeBtn:hover{
  background-color: #484036;
}
.subscribeBtnImg{
  position: relative;
  left: 24px;
  top: 32px;
  width: 15px;
  height: 15px;
  z-index: 1;
}
.OfficalAccountName{
  position: relative;
  left: 25px;
  top: 10px;
  font-size: 13px;
  color: black;
}
.OfficalAccountImg{
  position: relative;
  left: 30px;
  width: 45px;
  height: 45px;
  border-radius: 50px;
}
.OfficalAccount{
  width: 150px;
  height: 155px;
  display: inline-block;
  margin-right: 40px; /* 可选：添加项目之间的间距 */
  background-color: #f3f1f1;
  border-radius: 9px;
}

.OfficalAccount1{
  width: 151px;
  height: 156px;
  display: inline-block;
  margin-right: 40px; /* 可选：添加项目之间的间距 */
  background: linear-gradient(135deg, #ee00dd, #032aff);
}

.OfficalAccount2{
  width: 151px;
  height: 156px;
  display: inline-block;
  margin-right: 40px; /* 可选：添加项目之间的间距 */
  background: linear-gradient(135deg, #f6d365, #fda085);;
}

.OfficalAccount3{
  width: 151px;
  height: 156px;
  display: inline-block;
  margin-right: 40px; /* 可选：添加项目之间的间距 */
  background: linear-gradient(135deg, #ffbe0c, #ffda0c, #f5f2a8, #f0f850, #7aff0c);;
}

#secher{
  position: absolute;
  top: 385px;
  left: 350px;
  width: 780px;
  height: 80px;
  z-index: -1;
}
#secherInput{
  position: absolute;
  top: 20px;
  left: 40px;
  width: 630px;
  height: 35px;
  z-index: 0;
  border-color: grey;
  border-width: 1px;
  border-style: solid;
}
#serchImg{
  position: absolute;
  top: 17px;
  right: 25px;
  width: 45px;
  height: 45px;
  z-index: 0;
}
#titleContent{
  position: absolute;
  top: 95px;
  left: 17px;
  font-weight: bold;
  font-size: 17px;
  letter-spacing: 0.7px;
  color: black;
  font-family: 华文中宋;
  z-index: -1;
}
#changePosition{
  position: absolute;
  width: 17px;
  height: 17px;
  top: 123px;
  left: 8px;
  z-index: -1;
}
#line1{
  position: absolute;
  top: 130px;
  width: 100%;
  height: 1px;
  background-color: grey;
  left: 30px;
  z-index: -1;
}

#SectitleContent{
  position: absolute;
  top: 295px;
  left: 17px;
  font-weight: bold;
  font-size: 17px;
  letter-spacing: 0.7px;
  color: #c43f3f;
  font-family: 华文中宋;
  z-index: -1;
}
#line2{
  position: absolute;
  top: 330px;
  width: 100%;
  height: 3px;
  background: linear-gradient(to right, #def5ab, #e7c75b, #ECD809FF);
  left: 10px;
  z-index: -1;
}

#all{
  position: relative;
  top: 0;
  z-index: 0;
  left: 250px;
  height: 100%;
  width: 1000px;
}
#open{
  position: absolute;
  width: 20px;
  height: 20px;
  top: 10px;
  background-color: #F1F1F1;
  left: 20px;
  z-index: 0;
}
</style>