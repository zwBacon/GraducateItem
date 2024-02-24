<template>
<HeadLineViewForMain/>
  <!--  侧边-->
  <div id="left">
    <div id="NewHomeApproval" class="item" @click="changeLeftItemStyle('NewHomeApproval')"><img v-bind:src="imgSrc1" class="itemIcon">二手房</div>
    <div id="NewUserApproval" class="item" @click="changeLeftItemStyle('NewUserApproval')"><img v-bind:src="imgSrc2" class="itemIcon">新房</div>
    <div id="UserManage" class="item" @click="changeLeftItemStyle('UserManage')"><img v-bind:src="imgSrc3" class="itemIcon">租房</div>
    <div id="ComplaintHandle" class="item" @click="changeLeftItemStyle('ComplaintHandle')"><img v-bind:src="imgSrc4" class="itemIcon">公众号</div>
  </div>

<!--  表格-->
  <div id="mainFunction">

    <p id="mainFunctionTitle">{{this.itemTitle}}</p>
    <div v-show="tabelDisabel">
    <div id="mainFunctionTable">
      <el-table :data="tableData" style="width: 100%">
        <el-table-column fixed prop="firstPage" label="Image" width="110">
          <template #default="scope">
          <img :src=" this.stringplus + scope.row.firstPage"  width="50" height="50" class="img"/>
           </template>
        </el-table-column>
        <el-table-column prop="productName" label="ProductName" width="220" />
        <el-table-column prop="userInfo.userName" label="UserName" width="150" />
        <el-table-column prop="county" label="Address" width="180" />
        <el-table-column prop="address" label="State" width="110">
          <template #default="scope">
            <el-tag class="ml-2" :type="scope.row.state === '1' ? 'success' : 'danger'">{{scope.row.state === '1' ? '出售中' : '已经售出'}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="price" label="Price" width="110" />
        <el-table-column prop="direction" label="Direction" width="110" />
        <el-table-column prop="zip" label="Tag" width="180">
          <el-tag
              v-for="tag in tagList"
              :key="tag"
              class="mx-1"
              :type="tag.type"
          >
            {{ tag }}
          </el-tag>
        </el-table-column>
        <el-table-column fixed="right" label="Operations" width="150">
          <template #default="scope">
            <el-button link type="primary" size="small" @click="Enter(scope.row.productID,scope.row.userID,scope.row.type)">Enter</el-button>
            <el-button link type="danger" size="small" @click="Delete(scope.row.type,scope.row.productID)">Delete</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    </div>

    <div class="grid-container" v-show="subscribedDisabel" >
      <div v-for="(item, index) in subscribedList" :key="index" class="grid-item">
        <el-card class="ShowCardClass" @mouseover="cardMouseOver(index)"  @mouseleave="changebackImg(index)">
          <img :src="this.stringplus + item.accountImg" class="subscribedImgSrc">
          <!--          公众号名称-->
          <p class="subscribedName">{{item.accountName}}</p>
          <!--          标题-->
          <p class="title" >{{item.accountDetail}}</p>
          <!--          信息-->
          <p class="detail" >{{item.accountIntro}}</p>
        </el-card>
        <button type="danger" class="deleteSubscribe" @mouseover="BtnMouseOver(index)"  @mouseleave="BtnchangebackImg(index)" @click="deletSubscribed(item.accountIndex)"><el-icon class="fontIcon" color="#f56c6c"><DeleteFilled /></el-icon><p class="deleteFont">取消关注</p></button>
      </div>
    </div>
  </div>
</template>

<script>
import HeadLineViewForMain from "@/components/HeadLineViewForMain.vue";
import axios from "axios";
import router from "@/router";
import store from "core-js/internals/shared-store";

const debounce = (fn, delay) => {

  let timer = null;

  return function () {

    let context = this;

    let args = arguments;

    clearTimeout(timer);

    timer = setTimeout(function () {

      fn.apply(context, args);

    }, delay);

  }

}

// 解决ERROR ResizeObserver loop completed with undelivered notifications.

//问题的

const _ResizeObserver = window.ResizeObserver;

window.ResizeObserver = class ResizeObserver extends _ResizeObserver {

  constructor(callback) {

    callback = debounce(callback, 16);

    super(callback);

  }

}
export default{
  name: "SubscribedShowPage",
  mounted() {
    this.changeLeftItemStyle("");
    this.selectAllInfos();
  },
  components: {
    HeadLineViewForMain
  },
  data(){
    return{
      // 侧边资源
      imgSrc1: require('/src/assets/已关注商品页面/二手房.png'),
      imgSrc2: require('/src/assets/已关注商品页面/新房.png'),
      imgSrc3: require('/src/assets/已关注商品页面/租房.png'),
      imgSrc4: require('/src/assets/已关注商品页面/未关注公众号会员.png'),
      itemId:"NewHomeApproval",
      lastItemId:"",
    //   表格资源
      itemTitle:"二手房关注列表",
      userId:"",
      type:"1",
      tableData:[],
      tagList:[],
      tabelDisabel:true,
      stringplus: "data:image/png;base64,",
    //   公众号
      subscribedList:[],
      subscribedDisabel:false,
    }
  },
  methods:{
    selectAllInfos(){
      this.userId = store.state.LoadUserId;
      // this.userId = "GCCI-U-Scuqw8q9h398f2ccdd";
      let _this = this;
      axios({
        method: "get",
        url: "http://localhost:3000/productServe/selectAllProductIsSubscribed?userId="+this.userId+"&type="+this.type
      }).then(function (resp) {
        _this.tableData = resp.data["productDTOS"]
        _this.tagList = resp.data["tagList"]
      })
    },
    Enter(productID,userID,type){
      router.push("/ProductInfoDetailsPage/"+productID+"/"+type+"/"+userID+"/"+"product")
    },
    Delete(type,productID){
      let _this = this;
      axios({
        method: "get",
        url: "http://localhost:3000/productDetail/updateSubscribedById?type="+type+"&opertion=" + "1" + "&productId=" + productID + "&userId=" + this.userId
      }).then(function () {
        _this.selectAllInfos();
      })
    },
    deletSubscribed(accountIndex){
      let _this = this;
      axios({
        method: "get",
        url: "http://localhost:2000/officalAccountController/deletSubscribed?userId="+this.userId+"&accountIndex="+accountIndex
      }).then(function () {
        _this.selectAllSubscribed()
      })
    },
    cardMouseOver(index){
      document.getElementsByClassName("ShowCardClass").item(index).style.opacity="0.6"
      document.getElementsByClassName("ShowCardClass").item(index).style.backgroundColor="#423f3f"
      document.getElementsByClassName("deleteSubscribe").item(index).style.display="block"
    },
    BtnMouseOver(index){
      this.cardMouseOver(index)
      document.getElementsByClassName("fontIcon").item(index).style.color="white"
      document.getElementsByClassName("deleteSubscribe").item(index).style.backgroundColor="#f56c6c"
      document.getElementsByClassName("deleteFont").item(index).style.color="white"
    },
    changebackImg(index){
      document.getElementsByClassName("deleteSubscribe").item(index).style.display="none"
      document.getElementsByClassName("ShowCardClass").item(index).style.opacity="1.0"
      document.getElementsByClassName("ShowCardClass").item(index).style.backgroundColor="white"
    },
    BtnchangebackImg(index){
      this.changebackImg(index)
      document.getElementsByClassName("fontIcon").item(index).style.color="#f56c6c"
      document.getElementsByClassName("deleteSubscribe").item(index).style.backgroundColor="#fef0f0"
      document.getElementsByClassName("deleteFont").item(index).style.color="#f56c6c"
    },
    selectAllSubscribed(){
      this.userId = store.state.LoadUserId;
      // this.userId = "GCCI-U-Scuqw8q9h398f2ccdd";
      let _this = this;
      axios({
        method: "get",
        url: "http://localhost:2000/officalAccountController/selectSubScribedAccByUserId?userId="+this.userId
      }).then(function (resp) {
        _this.subscribedList = resp.data["body"]
      })
    },
    changeLeftItemStyle(itemId){
      if(itemId===""){
        itemId = this.itemId;
      }
      if(this.lastItemId !== ""){
        this.resetLeftItemStyle(this.lastItemId);
      }
      var item = document.getElementById(itemId);
      item.style.fontFamily = "华文隶书";
      item.style.fontWeight = "bold";
      item.style.fontSize = "25px";
      item.style.color = "white";
      item.style.backgroundColor = "#6c6868";
      if(itemId === "NewHomeApproval") {
        this.imgSrc1 = require('../../assets/已关注商品页面/二手房 (1).png')
        this.type = "1"
        this.selectAllInfos();
        this.subscribedDisabel = false;
        this.tabelDisabel = true;
        this.itemTitle = "二手房关注列表"
      }else if(itemId === "NewUserApproval"){
        this.imgSrc2 = require('../../assets/已关注商品页面/新房 (1).png')
        this.type = "0"
        this.selectAllInfos();
        this.subscribedDisabel = false;
        this.tabelDisabel = true;
        this.itemTitle = "新房关注列表"
      }else if(itemId === "UserManage"){
        this.imgSrc3 = require('../../assets/已关注商品页面/租房 (1).png')
        this.type = "2"
        this.selectAllInfos();
        this.subscribedDisabel = false;
        this.tabelDisabel = true;
        this.itemTitle = "租房关注列表"
      }else if(itemId === "ComplaintHandle"){
        this.imgSrc4 = require('../../assets/已关注商品页面/未关注公众号会员 (1).png')
        this.selectAllSubscribed();
        this.tabelDisabel = false;
        this.subscribedDisabel = true;
        this.itemTitle = "公众号关注列表"
      }
      this.lastItemId = itemId;
    },
    resetLeftItemStyle(itemId){
      if(itemId===""){
        itemId = this.lastItemId;
      }
      var item = document.getElementById(itemId);
      item.style.fontFamily = "";
      item.style.fontWeight = "";
      item.style.fontSize = "";
      item.style.color = "";
      item.style.backgroundColor = "";
      if(itemId === "NewHomeApproval") {
        this.imgSrc1 = require('../../assets/已关注商品页面/二手房.png')
      }else if(itemId === "NewUserApproval"){
        this.imgSrc2 = require('../../assets/已关注商品页面/新房.png')
      }else if(itemId === "UserManage"){
        this.imgSrc3 = require('../../assets/已关注商品页面/租房.png')
      }else if(itemId === "ComplaintHandle"){
        this.imgSrc4 = require('../../assets/已关注商品页面/未关注公众号会员.png')
      }
    },
  }
}
</script>

<style scoped>
.grid-container {
  position: absolute;
  top: 170px;
  left: 180px;
  display: grid;
  grid-template-columns: repeat(2, 1fr); /* 四列布局 */
  grid-gap: 120px; /* 设置项目之间的间距 */
  z-index:-1;
}
.ShowCardClass{
  height: 200px;
  border-radius: 23px;
  box-shadow: 1px 1px 2px 0px #37373A6B;
}
.ShowCardClass:hover{
  background-color: #696666;
  opacity: 0.6;
}
.deleteSubscribe{
  position: relative;
  left: 200px;
  top: -100px;
  display: none;
  width: 150px;
  height: 30px;
  border-radius: 30px;
  background-color: #fef0f0;
}
.deleteFont{
  position: relative;
  top: -20px;
  left: 10px;
  font-size: 13px;
  font-weight: bold;
  color: #f56c6c;
}
.fontIcon{
  position: relative;
  top: 2px;
  left: -30px;
}
.grid-item {
  width: 500px;
  height: 200px;
  background-color: #ffffff;
  text-align: left;
}
.subscribedImgSrc{
  position: relative;
  width: 120px;
  height: 120px;
  top: 21px;
  border-radius: 15px;
}
.subscribedName{
  position: relative;
  font-weight: bold;
  font-size: 26px;
  left: 150px;
  top: -130px;
}
.title{
  position: relative;
  font-size: 17px;
  left: 150px;
  top: -120px;
}
.detail{
  position: relative;
  font-weight: lighter;
  color: #c2c2c2;
  font-size: 13px;
  left: 155px;
  top: -120px;
}
#mainFunctionTable{
  position: absolute;
  left: 200px;
  top: 170px;
  font-size: 30px;
  font-family: "Segoe Script";
}
#mainFunctionTitle{
  position: absolute;
  left: 200px;
  top: 90px;
  font-size: 30px;
  font-family: 华文行楷;
  color: #9b5050;
}
.img{
  border-radius: 20px;
}
.itemIcon{
  position: relative;
  top: 7px;
  left: -7px;
   width: 30px;
  height: 30px;
}
.item{
  position: absolute;
  width: 170px;
  height: 70px;
  line-height: 70px;
  text-align: center;
  font-size: 20px;
}
#NewHomeApproval{
  top: 60px;
}
#NewUserApproval{
  top: 170px;
}
#UserManage{
  top: 280px;
}
#ComplaintHandle{
  top: 390px;
}
#left{
  position: absolute;
  left: 0;
  top: 80px;
  background-color: #f6f4f4;
  width: 170px;
  height: 650px;
}
</style>