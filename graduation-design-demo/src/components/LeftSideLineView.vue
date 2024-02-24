<template>

  <el-row class="tac" :gutter="20">
    <el-col :span="4">
      <el-menu
          default-active="2"
          class="el-menu-vertical-demo"
          id="list"
          @open="handleOpen"
          @close="handleClose"
      >
        <img v-bind:src="backtoHead"  @mouseover="changeImg" @mouseleave="changebackImg" @click="backHeadPage" id="backImg">

        <el-menu-item index="0" id="title">
          <img v-bind:src="imgSrc"  id="titleImg">
<!--      姓名    -->
          <p id="usernameTitle">{{username}}</p>
          <p id="introduce">{{introduce}}</p>
          <hr id="bootomLine"/>
        </el-menu-item>



        <el-sub-menu index="1" class="itemStyle">
          <template #title>
            <el-icon><Postcard /></el-icon>
            <span class="serviceClass">我的服务</span>
          </template>
          <el-menu-item-group title="Funcation One">
            <el-menu-item index="1-1" @click="mainMenuShowOfNew">新房</el-menu-item>
            <el-menu-item index="1-2" @click="mainMenuShowOfSec">二手房</el-menu-item>
            <el-menu-item index="1-3" @click="mainMenuShowOfRent">租房</el-menu-item>
          </el-menu-item-group>
          <el-menu-item-group title="Funcation Two">
            <el-menu-item index="1-4" @click="myPublish">我的发布</el-menu-item>
          </el-menu-item-group>
          <el-menu-item-group title="Funcation Three">
            <el-menu-item index="1-5" @click="addNewHouseResource">发布房源</el-menu-item>
            <el-menu-item index="1-6" @click="myOrder">我的订单</el-menu-item>
          </el-menu-item-group>
        </el-sub-menu>

        <el-menu-item index="2" class="insert" disabled>
        </el-menu-item>

        <el-sub-menu index="3" class="itemStyle">
          <template #title>
            <el-icon><TakeawayBox /></el-icon>
            <span class="serviceClass">购房工具</span>
          </template>
          <el-menu-item-group title="Group One">
            <el-menu-item index="3-1" >购房百科</el-menu-item>
            <el-menu-item index="3-2">帮我找房</el-menu-item>
          </el-menu-item-group>
          <el-menu-item-group title="Group Two">
            <el-menu-item index="3-3">降价房源</el-menu-item>
            <el-menu-item index="3-4">金选好房</el-menu-item>
          </el-menu-item-group>

        </el-sub-menu>

        <el-menu-item index="4" class="insert" disabled>
        </el-menu-item>

        <el-menu-item index="5" disabled>
          <el-icon><ZoomOut /></el-icon>
          <span class="serviceClass">VR看房(上线中。。。)</span>
        </el-menu-item>

        <el-menu-item index="6" class="insert" disabled>
        </el-menu-item>


        <el-sub-menu index="7" class="itemStyle">
          <template #title>
            <el-icon><setting /></el-icon>
            <span class="serviceClass">其他服务</span>
          </template>
          <el-menu-item-group>
            <el-menu-item index="7-1" >装修案例</el-menu-item>
            <el-menu-item index="7-2">户型方案</el-menu-item>
            <el-menu-item index="7-3" disabled>直播看房</el-menu-item>
            <el-menu-item index="7-4">家具选择</el-menu-item>
          </el-menu-item-group>

        </el-sub-menu>

        <el-menu-item index="8" id="end">
          <span></span>
        </el-menu-item>

      </el-menu>
    </el-col>
  </el-row>




</template>

<script>
import router from "@/router";
import store from "core-js/internals/shared-store";
import axios from "axios";

export default {
  name:"LeftSideLineView",
  mounted(){
    this.getUserInfoBefor();
  },
  data() {
    return {
      stringplus: "data:image/png;base64,",
      imgSrc: require('../assets/侧边栏资源/默认头像.png'),
      backtoHead: require('../assets/侧边栏资源/返回.png'),
      username:"",
      introduce:"你的信息空空如也。。。"
    }
  },
  methods:{
    mainMenuShowOfNew(){
      store.state.leftSideChange="back";
      router.push("/MainMeunShowPage/"+ "New");
    },
    mainMenuShowOfSec(){
      store.state.leftSideChange="back";
      router.push("/MainMeunShowPage/"+ "Sec");
    },
    mainMenuShowOfRent(){
      store.state.leftSideChange="back";
      router.push("/MainMeunShowPage/"+ "Rent");
    },
    myOrder(){
      router.push("/ShowDepositOrderData")
    },
    addNewHouseResource(){
      router.push("/AddNewHouseResourcePage")
    },
    myPublish(){
      router.push("/MyPublish")
    },
    changeImg(){
      this.backtoHead=require('../assets/侧边栏资源/返回 (1).png');
    },
    changebackImg(){
      this.backtoHead=require('../assets/侧边栏资源/返回.png');
    },
    getUserInfoBefor(){
      let _this = this;
      // this.username = "张虹生"
      this.username=store.state.LoadUsername
      axios({
        method: "get",
        url: "http://localhost:2000/baseUserController/selectUserInfoByName?username="+this.username
      }).then(function () {
           _this.getUserInfo();
      })
    },
    getUserInfo(){
      let _this = this;
      // this.username = "张虹生"
      this.username=store.state.LoadUsername
      axios({
        method: "get",
        url: "http://localhost:2000/baseUserController/selectUserInfoByName?username="+this.username
      }).then(function (resp) {
        if(resp.data["result"].introduce != '0'){
          _this.introduce = resp.data["result"].introduce
        }if (resp.data["result"].headimg != '0'){
          _this.imgSrc =  _this.stringplus + resp.data["result"].headimg;
        }
      })
    },
    backHeadPage(){
      router.push("/MainMeunShowPage/"+ "main");
      store.state.leftSideChange="back";
    }
  }

}
</script>


<style scoped>
#usernameTitle{
  position: absolute;
  font-size: 17px;
  top: 28px;
  font-weight: bolder;
  font-family: 华文中宋;
  color: rgb(64, 158, 255);
  left: 98px;
}
#bootomLine{
  position: absolute;
  width: 100%;
  top: 130px;
  color: grey;
  left: 0;
}
#introduce{
  position: absolute;
  font-size: 11px;
  top: 60px;
  font-weight: lighter;
  color: grey;
  left: 98px;
}
#titleImg{
  position: absolute;
  width: 60px;
  height: 60px;
  left: 20px;
  top: 40px;
  border-radius: 100px;
}
#backImg{
  position: absolute;
  width: 24px;
  height: 24px;
  top: 10px;
  z-index: 1;
  right: 10px;
}
.insert{
  height: 40px;
}
#title{
  height: 170px;
  background-color: white;
}
.serviceClass{
  position: relative;
  width: 250px;
  height: 70px;
  top: 10px;
  left: 15px;
  font-family: 华文隶书;
  font-size: 18px;
}
#end{
  height:235px;
  background-color: white;
}

</style>
