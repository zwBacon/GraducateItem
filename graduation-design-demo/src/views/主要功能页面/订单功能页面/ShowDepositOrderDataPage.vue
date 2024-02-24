<template>
<!--  上面-->
  <el-icon :size="25" id="meunIcon"><Expand /></el-icon>
  <el-breadcrumb separator="/" id="breadcrumb">
    <el-breadcrumb-item :to="{ path: '/MainMeunShowPage/main' }">首页</el-breadcrumb-item>
    <el-breadcrumb-item :to="{ path: '/MainMeunShowPage/main' }">订单</el-breadcrumb-item>
    <el-breadcrumb-item :to="{ path: '/MainMeunShowPage/main' }">订单详情</el-breadcrumb-item>
    <el-breadcrumb-item>{{DynamicUrl}}</el-breadcrumb-item>
  </el-breadcrumb>
  <img id="HeadTitleImg" v-bind:src="imgSrc">
  <span id="headLine1"></span>

<!--  侧面-->
  <div id="leftSide">
    <el-menu
        active-text-color="#08b0ea"
        background-color="#304057"
        class="el-menu-vertical-demo"
        default-active="3"
        text-color="#fff"
        @open="handleOpen"
        @close="handleClose"
    >
      <el-menu-item index="1" id="_el-menu-item">
        <el-icon><HomeFilled /></el-icon>&emsp;&nbsp;&nbsp;
        <span>首页</span>
      </el-menu-item>
      <el-sub-menu index="2">
        <template #title>
          <el-icon><List /></el-icon>&emsp;&nbsp;&nbsp;
          <span>订单</span>
        </template>
        <el-menu-item-group>
          <el-menu-item index="3" class="el-menu-item"  @click="_smallRouterChange"><el-icon><WalletFilled /></el-icon>&nbsp;&nbsp;代支付尾款</el-menu-item>
          <el-menu-item index="4" class="el-menu-item"  @click="smallRouterChange"><el-icon><GoodsFilled /></el-icon>&nbsp;&nbsp;已支付尾款</el-menu-item>
        </el-menu-item-group>
      </el-sub-menu>
    </el-menu>
  </div>

<!--  查询部分-->
  <div id="allSearchCondition" v-show="allSearchConditionDisabel">
    <el-button id="resetConditionBtn" @click="resetCondition">重置</el-button>
    <el-button type="primary" id="searchConditionBtn" @click="searchNow">查询搜索</el-button>
    <p id="allSearchConditionTitle"><el-icon><Search /></el-icon>&nbsp;筛选搜索</p>
    <span class="titleFontStyle" id="selectFont1">输入搜索：</span><span id="selectInput1"><el-input  placeholder="订单编号" v-model="searchForm.orderId" clearable maxlength="30" show-word-limit class="el-input-condition"></el-input></span>
    <span class="titleFontStyle" id="selectFont2">房源主：</span><span id="selectInput2"><el-input placeholder="房源主人姓名/手机号" v-model="searchForm.ownerNameOrOhone" clearable  class="el-input-condition"></el-input></span>
    <span class="titleFontStyle" id="selectFont3">提交时间：</span><span id="selectInput3"><el-date-picker type="date" placeholder="请选择时间" v-model="searchForm.createDate" class="el-input-condition" style="width: 100%;"></el-date-picker></span>
    <span class="titleFontStyle" id="selectFont4">订单状态：</span><span id="selectInput4"><el-select v-model="searchForm.orderSta" clearable placeholder="全部" class="el-input-condition">
    <el-option
        v-for="item in orderState"
        :key="item.value"
        :label="item.label"
        :value="item.value">
    </el-option>
  </el-select></span>
    <span class="titleFontStyle" id="selectFont5">定金支付区间：</span><span id="selectInput5"><el-input placeholder="最低金额" v-model="searchForm.minPrice" clearable class="_el-input-condition"></el-input></span><el-col id="middleLine" :span="7">-</el-col><span id="_selectInput5"><el-input placeholder="最高金额" v-model="searchForm.maxPrice" class="_el-input-condition"></el-input></span>
    <span class="titleFontStyle" id="selectFont6">即将到期：</span><span id="selectInput6"><el-select v-model="searchForm.expireDate" clearable placeholder="全部时间">
    <el-option
        v-for="item in expireDateOrder"
        :key="item.value"
        :label="item.label"
        :value="item.value">
    </el-option>
  </el-select></span>
  </div>

<!--  已支付订单-->
  <div id="allSearchCondition" v-show="SecallSearchConditionDisabel">
    <el-button id="resetConditionBtn" @click="SecresetCondition">重置</el-button>
    <el-button type="primary" id="searchConditionBtn" @click="SecsearchNow">查询搜索</el-button>
    <p id="allSearchConditionTitle"><el-icon><Search /></el-icon>&nbsp;筛选搜索</p>
    <span class="titleFontStyle" id="selectFont1">输入搜索：</span><span id="selectInput1"><el-input  placeholder="订单编号" v-model="SecsearchForm.orderId" clearable maxlength="30" show-word-limit class="el-input-condition"></el-input></span>
    <span class="titleFontStyle" id="selectFont2">房源主：</span><span id="selectInput2"><el-input placeholder="房源主人姓名/手机号" v-model="SecsearchForm.ownerNameOrOhone" clearable  class="el-input-condition"></el-input></span>
    <span class="titleFontStyle" id="selectFont3">提交时间：</span><span id="selectInput3"><el-date-picker type="date" placeholder="请选择时间" v-model="SecsearchForm.createDate" class="el-input-condition" style="width: 100%;"></el-date-picker></span>
    <span class="titleFontStyle" id="selectFont4">订单状态：</span><span id="selectInput4"><el-select v-model="SecsearchForm.orderSta" clearable placeholder="全部" class="el-input-condition">
    <el-option
        v-for="item in SecorderState"
        :key="item.value"
        :label="item.label"
        :value="item.value">
    </el-option>
  </el-select></span>
    <span class="titleFontStyle" id="selectFont5">尾款支付区间：</span><span id="selectInput5"><el-input placeholder="最低金额" v-model="SecsearchForm.minPrice" clearable class="_el-input-condition"></el-input></span><el-col id="middleLine" :span="7">-</el-col><span id="_selectInput5"><el-input placeholder="最高金额" v-model="SecorderState.maxPrice" class="_el-input-condition"></el-input></span>
    <span class="titleFontStyle" id="selectFont6">支付时间：</span><span id="selectInput6"><el-date-picker type="date" placeholder="请选择时间" v-model="SecsearchForm.expireDate" class="el-input-condition" style="width: 100%;"></el-date-picker></span>
  </div>

<!--  中间-->
  <div id="middleAreaForUseless">
       <p id="dataListFont"><el-icon id="MemoIcon"><Memo /></el-icon>&nbsp;数据列表</p>
  </div>

<!--  未支付尾款订单-->
  <el-table
      ref="multipleTableRef"
      :data="responseTabel"
      style="width: 100%"
      border
      :cell-style="{'text-align':'center'}"
      id="dataListTabelStyle"
      :header-cell-style="{'text-align':'center'}"
      @selection-change="handleSelectionChange"
      v-show="dataListTabelDisabel"
  >
    <el-table-column label="序号" align="center" width="50">
      <template #default="scope">
        <span v-text="getIndex(scope.$index)"> </span>
      </template>
    </el-table-column>
    <el-table-column property="orderId" label="订单编号" width="145"  class="OrderIdsStyle">
      <template #default="scope">
        <span class="orderIdsStyle">{{scope.row.orderId}}</span>
      </template>
    </el-table-column>
    <el-table-column property="firstHeadProductDTO.productName" label="房源名" width="120"/>
    <el-table-column property="userInfo.userName" label="房源主名" width="90"/>
    <el-table-column property="userInfo.phone" label="房源主手机" width="120" />
    <el-table-column property="state" label="订单状态" width="90">
      <template #default="scope">
        <el-tag :type="scope.row.state === '在途' ? 'primary' : 'danger'">{{scope.row.state}}</el-tag>
      </template>
    </el-table-column>
    <el-table-column property="bargain" label="定金" width="120">
    <template #default="scope">
      <span class="pricesStyle">{{scope.row.bargain}}元</span>
    </template>
    </el-table-column>
    <el-table-column property="finalPayMent" label="尾款" width="120">
      <template #default="scope">
        <span class="pricesStyle">{{scope.row.finalPayMent}}元</span>
      </template>
    </el-table-column>
    <el-table-column property="createTime" label="创建时间" width="125">
      <template #default="scope">
        <span class="_TimesStyle">{{scope.row.createTime}}</span>
      </template>
    </el-table-column>
    <el-table-column property="endTime" label="尾款结束支付时间" width="150">
      <template #default="scope">
        <span class="TimesStyle">{{scope.row.endTime}}</span>
      </template>
    </el-table-column>
    <el-table-column property="操作" label="操作" width="170">
      <template #default="scope">
      <el-button type="info" :icon="FolderOpened" circle class="el-button" @click="showOrderDetails(scope.$index)" />
      <el-button type="success" v-show="scope.row.state === '在途'" :icon="Coin" @click="payFinalMent(scope.$index)" circle class="el-button" />
      <el-button type="danger" :icon="Delete" circle class="el-button" @click="open(scope.row.state,scope.$index)"/>
      </template>
    </el-table-column>
  </el-table>
  <el-pagination
      v-model:current-page="searchForm.currentPage"
      v-model:page-size="searchForm.pageSize"
      v-show="pageDisable"
      small
      :page-sizes="[5, 10, 20, 30]"
      :disabled="disabled"
      :background="background"
      layout="total, sizes, prev, pager, next"
      :total=count
      id="pageAtion"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
  />

  <!--  已支付尾款订单-->
  <el-table
      ref="multipleTableRef"
      :data="SecresponseTabel"
      style="width: 100%"
      border
      :cell-style="{'text-align':'center'}"
      id="dataListTabelStyle"
      :header-cell-style="{'text-align':'center'}"
      @selection-change="handleSelectionChange"
      v-show="SecdataListTabelDisabel"
  >
    <el-table-column label="序号" align="center" width="50">
      <template #default="scope">
        <span v-text="getIndex(scope.$index)"> </span>
      </template>
    </el-table-column>
    <el-table-column property="orderId" label="订单编号" width="145"  class="OrderIdsStyle">
      <template #default="scope">
        <span class="orderIdsStyle">{{scope.row.orderId}}</span>
      </template>
    </el-table-column>
    <el-table-column property="firstHeadProductDTO.productName" label="房源名" width="120"/>
    <el-table-column property="userInfo.userName" label="房源主名" width="90"/>
    <el-table-column property="userInfo.phone" label="房源主手机" width="120" />
    <el-table-column property="state" label="订单状态" width="90">
      <template #default="scope">
        <el-tag :type="scope.row.state === '已完工' ? 'success' : 'info'">{{scope.row.state}}</el-tag>
      </template>
    </el-table-column>
    <el-table-column property="bargain" label="定金" width="80">
      <template #default="scope">
        <span class="pricesStyle">{{scope.row.bargain}}元</span>
      </template>
    </el-table-column>
    <el-table-column property="finalPayMent" label="尾款" width="80">
      <template #default="scope">
        <span class="pricesStyle">{{scope.row.finalPayMent}}元</span>
      </template>
    </el-table-column>
    <el-table-column property="finalPayMent" label="总金额" width="120">
      <template #default="scope">
        <span class="pricesStyle">{{scope.row.firstHeadProductDTO.price}}元</span>
      </template>
    </el-table-column>
    <el-table-column property="createTime" label="创建时间" width="125">
      <template #default="scope">
        <span class="_TimesStyle">{{scope.row.createTime}}</span>
      </template>
    </el-table-column>
    <el-table-column property="endTime" label="尾款支付时间" width="150">
      <template #default="scope">
        <span class="TimesStyle">{{scope.row.endTime}}</span>
      </template>
    </el-table-column>
    <el-table-column property="操作" label="操作" width="140">
      <template #default="scope">
        <el-button type="info" :icon="FolderOpened" circle class="el-button" @click="SecshowOrderDetails(scope.$index)" />
        <el-button type="danger" :icon="Delete" circle class="el-button" @click="Secopen(scope.$index)" v-show="scope.row.state === '已完工'"/>
      </template>
    </el-table-column>
  </el-table>
  <el-pagination
      v-model:current-page="SecsearchForm.currentPage"
      v-model:page-size="SecsearchForm.pageSize"
      small
      v-show="SecpageDisable"
      :page-sizes="[5, 10, 20, 30]"
      :disabled="disabled"
      :background="background"
      layout="total, sizes, prev, pager, next"
      :total=Seccount
      id="pageAtion"
      @size-change="SechandleSizeChange"
      @current-change="SechandleCurrentChange"
  />


<!--  订单详细信息-->
  <el-dialog v-model="OrderDetailFormVisible" title="Order Infomation" width="700" :height="700">

    <div id="dialog">
      <img :src="this.stringplus + orderDatail.firstHeadProductDTO.firstPage" class="firstPageImg">
      <p id="productName">{{this.orderDatail.firstHeadProductDTO.productName}}</p>
      <span id="ownerNameTitle" class="titlesStyle">现有户主名</span><p id="ownerName">{{this.orderDatail.userInfo.userName}}</p>
      <span id="bargainTitle" class="titlesStyle">已支付定金</span><p id="bargain">￥{{this.orderDatail.bargain}}</p>
      <span id="shelveTimeTitle" class="titlesStyle">房源上架时间</span><p id="shelveTime">{{this.orderDatail.firstHeadProductDTO.createTime.replace("T"," ")}}</p>
      <span id="addressTitle" class="titlesStyle">地址</span><p id="address">{{this.orderDatail.firstHeadProductDTO.province}}{{this.orderDatail.firstHeadProductDTO.city}}{{this.orderDatail.firstHeadProductDTO.county}}</p>
      <p id="payCountDown"><span id="finalPayMent" class="titlesStyle">尾款交付倒计时</span><nut-countdown v-model="time" :end-time="end" id="countdown">
        <nut-space>
          <span class="countdown-item">{{ time.d }}</span>&nbsp;
          <span class="timeSign">天</span>&ensp;
          <span class="countdown-item">{{ time.h }}</span>&nbsp;
          <span class="timeSign">时</span>&ensp;
          <span class="countdown-item">{{ time.m }}</span>&nbsp;
          <span class="timeSign">分</span>&ensp;
          <span class="countdown-item">{{ time.s }}</span>&nbsp;
          <span class="timeSign">秒</span>&ensp;
        </nut-space>
      </nut-countdown></p>
      <el-button id="BtnClassStyleMessage" @click="chatOnline" ><p>在线沟通</p></el-button>
      <el-button id="BtnClassStyleProduct"  @click="showProductInfo">商品信息</el-button>

    </div>

  </el-dialog>

  <!--  订单详细信息-->
  <el-dialog v-model="SecOrderDetailFormVisible" title="Order Infomation" width="700" :height="700">

    <div id="dialog">
      <img :src="this.stringplus + SecorderDatail.firstHeadProductDTO.firstPage" class="firstPageImg">
      <p id="productName">{{this.SecorderDatail.firstHeadProductDTO.productName}}</p>
      <span id="ownerNameTitle" class="titlesStyle">现有户主名</span><p id="ownerName">{{this.SecorderDatail.userInfo.userName}}</p>
      <span id="bargainTitle" class="titlesStyle">已支付定金</span><p id="bargain">￥{{this.SecorderDatail.bargain}}</p>
      <span id="shelveTimeTitle" class="titlesStyle">房源上架时间</span><p id="shelveTime">{{this.SecorderDatail.firstHeadProductDTO.createTime.replace("T"," ")}}</p>
      <span id="addressTitle" class="titlesStyle">地址</span><p id="address">{{this.SecorderDatail.firstHeadProductDTO.province}}{{this.orderDatail.firstHeadProductDTO.city}}{{this.orderDatail.firstHeadProductDTO.county}}</p>
      <span id="finalPayMent" class="titlesStyle">尾款交付时间</span><p id="payTime">{{this.SecorderDatail.firstHeadProductDTO.createTime.replace("T"," ")}}</p>
      <el-button id="BtnClassStyleMessage" @click="SecchatOnline" ><p>在线沟通</p></el-button>
      <el-button id="BtnClassStyleProduct"  @click="SecshowProductInfo">商品信息</el-button>
    </div>

  </el-dialog>

</template>

<script>

import axios from "axios";
import {Coin, Delete, FolderOpened} from "@element-plus/icons-vue";
import router from "@/router";
import {ElMessage, ElMessageBox} from "element-plus";
import store from "core-js/internals/shared-store";

export default {
  name: "ShowDepositOrderDataPage",
  computed: {
    FolderOpened() {
      return FolderOpened
    },
    Coin() {
      return Coin
    },

    Delete() {
      return Delete
    }
  },
  mounted() {
      this.searchForm.ownerId = store.state.LoadUserId
    // this.searchForm.ownerId = "GCCI-U-Scuqw8q9h398f2ccdd";
      this.SecsearchForm.ownerId = store.state.LoadUserId
    // this.SecsearchForm.ownerId = "GCCI-U-Scuqw8q9h398f2ccdd";
    this.searchNow();
    this.SecsearchNow();
  },
  data(){
    return {
      DynamicUrl: "代支付尾款",
      imgSrc: require('../../../assets/侧边栏资源/默认头像.png'),
      // 搜索内容
      orderState: [{
        value: '0',
        label: '在途'
      }, {
        value: '3',
        label: '已取消'
      }],
      expireDateOrder: [{
        value: '<=1',
        label: '一天内'
      }, {
        value: '<=3',
        label: '三天内'
      }, {
        value: '<=5',
        label: '五天内'
      }, {
        value: '>=5',
        label: '五天外'
      }],
      searchForm: {
        pageSize:"10",
        currentPage:"1",
        ownerId:"",
        orderId:"",
        ownerNameOrOhone: "",
        createDate: "",
        minPrice: "",
        maxPrice: "",
        orderSta: "",
        expireDate:'',
        type:"0"
      },
      responseTabel:[],
      count:"",
    //   订单详情
      OrderDetailFormVisible:false,
      orderDatail:{
        firstHeadProductDTO:{
          productName:"",
          firstPage:"",
          createTime:"",
          province:"",
          city:"",
          county:"",
          productID:"",
          type:"",
          // 要说话的
          userID:""
        },
        userInfo:{
          userName:""
        },
        bargain:"",
        orderId:""
      },
      stringplus: "data:image/png;base64,",
      start: Date.now(),
      end: Number,
      time:{
        d: '1',
        h: '00',
        m: '00',
        s: '00'
      },
    //   删除按钮
      deleteForm:{
        orderId:"",
        productId:"",
        productState:"",
        id:"",
        orderState:""
      },
    //   第二个页面切换
      allSearchConditionDisabel:true,
      dataListTabelDisabel:true,
      pageDisable:true,

      SecallSearchConditionDisabel:false,
      SecdataListTabelDisabel:false,
      SecpageDisable:false,
    //   第二个页面
      SecorderState: [{
        value: '1',
        label: '完工'
      }, {
        value: '2',
        label: '已删除'
      }],
      SecsearchForm: {
        pageSize:"10",
        currentPage:"1",
        ownerId:"",
        orderId:"",
        ownerNameOrOhone: "",
        createDate: "",
        minPrice: "",
        maxPrice: "",
        orderSta: "",
        expireDate:'',
        type:"2"
      },
      SecresponseTabel:[],
      Seccount:"",
      SecOrderDetailFormVisible:false,
      SecorderDatail: {
        firstHeadProductDTO: {
          productName: "",
          firstPage: "",
          createTime: "",
          province: "",
          city: "",
          county: "",
          productID: "",
          type: "",
          // 要说话的
          userID: ""
        },
        userInfo: {
          userName: ""
        },
        bargain: "",
        orderId: ""
      }
    }
  },
  methods:{
    // 路由跳转
    smallRouterChange(){
      this.DynamicUrl = "已支付尾款"
      this.allSearchConditionDisabel = false
      this.dataListTabelDisabel = false
      this.pageDisable = false

      this.SecallSearchConditionDisabel = true
      this.SecdataListTabelDisabel = true
      this.SecpageDisable = true
    },
    _smallRouterChange(){
      this.DynamicUrl = "代支付尾款"
      this.allSearchConditionDisabel = true
      this.dataListTabelDisabel = true
      this.pageDisable = true

      this.SecallSearchConditionDisabel = false
      this.SecdataListTabelDisabel = false
      this.SecpageDisable = false
    },
    // 在线聊天
    chatOnline(){
       router.push('/TalkRecordPage/'+this.searchForm.ownerId+'/'+this.orderDatail.firstHeadProductDTO.userID)
    },

    // 商品展示
    showProductInfo(){
      router.push('/ProductInfoDetailsPage/'+this.orderDatail.firstHeadProductDTO.productID+'/'+this.orderDatail.firstHeadProductDTO.type+'/'+this.orderDatail.firstHeadProductDTO.userID+"/order")
    },

    SecchatOnline(){
      router.push('/TalkRecordPage/'+this.SecsearchForm.ownerId+'/'+this.SecorderDatail.firstHeadProductDTO.userID)
    },

    // 商品展示
    SecshowProductInfo(){
      router.push('/ProductInfoDetailsPage/'+this.SecorderDatail.firstHeadProductDTO.productID+'/'+this.SecorderDatail.firstHeadProductDTO.type+'/'+this.SecorderDatail.firstHeadProductDTO.userID+"/order")
    },

    // 订单详细
    showOrderDetails(index){
      this.orderDatail = this.responseTabel[index]
      let dateObj = new Date(this.responseTabel[index].endTime);

      if (!isNaN(dateObj.getTime())) {
        this.end = dateObj.getTime();
      } else {
        console.error("无效的日期字符串");
      }
      this.OrderDetailFormVisible = true
    },

    // 订单详细
    SecshowOrderDetails(index){
      this.SecorderDatail = this.SecresponseTabel[index]
      this.SecOrderDetailFormVisible = true
    },

    // 支付尾款按钮
    payFinalMent(index){
      ElMessageBox.confirm(
          '此订单为代支付尾款订单,您将支付余下尾款共 ' + this.responseTabel[index].finalPayMent + '元  , 是否继续?',
          '提示',
          {
            confirmButtonText: 'OK',
            cancelButtonText: 'Cancel',
            type: 'warning',
          }
      )
          .then(() => {
            this.payFinalMentCore(index)
            var num = Math.random();
            location.href="http://localhost:3000/alipay/pay?name=" + this.responseTabel[index].doubelRentProductDetails.userInfo.userName + "&id="+ num.toString(36).substring(2) + "&price=" + this.responseTabel[index].finalPayMent
            ElMessage({
            })
          })
          .catch(() => {
            ElMessage({
              type: 'info',
              message: 'Pay canceled',
            })
          })
    },
    payFinalMentCore(index){
      axios({
        method: "get",
        url: "http://localhost:4000/orderInfoController/UpdateOrderStateAndTypeAndEndtime?orderId=" + this.responseTabel[index].orderId
      }).then(function () {
      })
    },

    // 删除按钮
    deleteOrder(state,index){
      let _this = this;
      this.deleteForm.orderId = this.responseTabel[index].orderId
      this.deleteForm.productId = this.responseTabel[index].productId
      this.deleteForm.productState = "1"
      this.deleteForm.id = this.responseTabel[index].firstHeadProductDTO.id
      if(state === '在途'){
        this.deleteForm.orderState = "3"
        axios({
          method: "post",
          url: "http://localhost:4000/orderInfoController/UpdateOrderAndProductState",
          data: this.deleteForm
        }).then(function () {
          _this.searchNow();
        })
      }else if(state === '已取消'){
        this.deleteForm.orderState = "2"
        axios({
          method: "get",
          url: "http://localhost:4000/orderInfoController/UpdateOrderStateForCanceled?orderId=" + this.deleteForm.orderId + "&orderState=" + this.deleteForm.orderState
        }).then(function () {
          _this.searchNow();
        })
      }
    },

    SecdeleteOrder(index){
        let _this = this;
        axios({
          method: "get",
          url: "http://localhost:4000/orderInfoController/UpdateOrderStateForCanceled?orderId=" + _this.SecresponseTabel[index].orderId + "&orderState=2"
        }).then(function () {
          _this.SecsearchNow();
        })
    },

    // 查询条件按钮
    searchNow(){
      let _this = this;
      if (this.searchForm.createDate !== '') {
        this.searchForm.createDate = this.newDate(this.searchForm.createDate)
      }
      axios({
        method: "post",
        url: "http://localhost:4000/orderInfoController/selectAllPreparePayOrder",
        data: this.searchForm
      }).then(function (resp) {
        _this.responseTabel = resp.data["orderInfos"];
        _this.count = resp.data["count"];
      })
    },

    // 查询条件按钮
    SecsearchNow(){
      let _this = this;
      if (this.SecsearchForm.createDate !== '') {
        this.SecsearchForm.createDate = this.newDate(this.SecsearchForm.createDate)
      }
      if (this.SecsearchForm.expireDate !== '') {
        this.SecsearchForm.expireDate = this.newDate(this.SecsearchForm.expireDate)
      }
      axios({
        method: "post",
        url: "http://localhost:4000/orderInfoController/selectAllPayedOrder",
        data: this.SecsearchForm
      }).then(function (resp) {
        _this.SecresponseTabel = resp.data["orderInfos"];
        _this.Seccount = resp.data["count"];
      })
    },

    // 时间转换
    newDate(time) {
      var date = new Date(time)
      var y = date.getFullYear()
      var m = date.getMonth() + 1
      m = m < 10 ? '0' + m : m
      var d = date.getDate()
      d = d < 10 ? '0' + d : d
      return y + '-' + m + '-' + d
    },

    // 分页
    handleSizeChange(val) {
      this.searchForm.pageSize = val
      this.searchNow();
    },
    handleCurrentChange(val) {
      this.searchForm.currentPage = val
      this.searchNow();
    },

    SechandleSizeChange(val) {
      this.SecsearchForm.pageSize = val
      this.SecsearchNow();
    },
    SechandleCurrentChange(val) {
      this.SecsearchForm.currentPage = val
      this.SecsearchNow();
    },

  //   序号
    getIndex($index) {
     //表格序号
      return (this.searchForm.currentPage - 1) * this.searchForm.pageSize + $index + 1
    },
    resetCondition(){
      this.searchForm={
            pageSize:"10",
            currentPage:"1",
            ownerId:"",
            orderId:"",
            ownerNameOrOhone: "",
            createDate: "",
            minPrice: "",
            maxPrice: "",
            orderSta: "",
            expireDate:'',
            type:"0"
      }
      // this.searchForm.ownerId = "GCCI-U-Scuqw8q9h398f2ccdd";
        this.searchForm.ownerId = store.state.LoadUserId
    },
    SecresetCondition(){
      this.SecsearchForm={
        pageSize:"10",
        currentPage:"1",
        ownerId:"",
        orderId:"",
        ownerNameOrOhone: "",
        createDate: "",
        minPrice: "",
        maxPrice: "",
        orderSta: "",
        expireDate:'',
        type:"2"
      }
      // this.SecsearchForm.ownerId = "GCCI-U-Scuqw8q9h398f2ccdd";
        this.searchForm.ownerId = store.state.LoadUserId
    },
  //   确认框
    Secopen(index) {
        ElMessageBox.confirm(
            '此订单为已完工订单,将修改订单状态为已删除, 是否继续?',
            '提示',
            {
              confirmButtonText: 'OK',
              cancelButtonText: 'Cancel',
              type: 'warning',
            }
        )
            .then(() => {
              this.SecdeleteOrder(index)
              ElMessage({
                type: 'success',
                message: '订单状态已修改',
              })
            })
            .catch(() => {
              ElMessage({
                type: 'info',
                message: 'Delete canceled',
              })
            })
    },
    open(state,index) {
      if (state === '在途') {
        ElMessageBox.confirm(
            '此订单为在途订单,将修改订单状态并退还定金, 是否继续?',
            '提示',
            {
              confirmButtonText: 'OK',
              cancelButtonText: 'Cancel',
              type: 'warning',
            }
        )
            .then(() => {
              this.deleteOrder(state, index)
              ElMessage({
                type: 'success',
                message: '定金已退还，请关注您的余额信息',
              })
            })
            .catch(() => {
              ElMessage({
                type: 'info',
                message: 'Delete canceled',
              })
            })
      }else if(state === '已取消'){
        ElMessageBox.confirm('此订单为已取消订单,将从历史订单中删除, 是否继续?',
              '提示',
              {
                confirmButtonText: 'OK',
                cancelButtonText: 'Cancel',
                type: 'warning',
              }
        )
        .then(() => {
          this.deleteOrder(state, index)
            ElMessage({
              type: 'success',
              message: '订单已删除',
            })
          })
              .catch(() => {
                ElMessage({
                  type: 'info',
                  message: 'Delete canceled',
                })
              })
      }
    }
  }
}
</script>

<style scoped>
#ownerName{
  position: absolute;
  left: 440px;
  top: 120px;
}
#bargain{
  position: absolute;
  left: 440px;
  top: 200px;
}
#shelveTime{
  position: absolute;
  left: 440px;
  top: 160px;
}
#ownerNameTitle{
  position: absolute;
  left: 280px;
  top: 120px;
}
#address{
  position: absolute;
  left: 440px;
  top: 240px;
}
#bargainTitle{
  position: absolute;
  left: 280px;
  top: 200px;
}
#shelveTimeTitle{
  position: absolute;
  left: 280px;
  top: 160px;
}
#addressTitle{
  position: absolute;
  left: 280px;
  top: 240px;
}
.countdown-item {
  color: #e8220e;
  font-size: 16px;
  font-weight: bolder;
}
.timeSign{
  font-size: 13px;
  color: #b6a793;
}
#finalPayMent{
  position: absolute;
  left: 280px;
  top: 280px;
}
#countdown{
  position: absolute;
  left: 440px;
  top: 280px;
}
.titlesStyle{
  font-size: 16px;
  color: #cbc9c9;
}
#productName{
  position: absolute;
  left: 280px;
  font-size: 25px;
  font-weight: bolder;
  font-family: 华文行楷;
  color: #3d280a;
  top: 70px;
}
.firstPageImg{
  position: absolute;
  left: 20px;
  top: 135px;
  width: 150px;
  height: 150px;
  border-style: solid;
  border-width: 1px;
  border-color: #c2c2c2;
  border-radius: 15px;
  box-shadow: #4f3f3f 5px 5px, #544c4c 10px 10px, #a6a2a2 15px 15px, #c2c2c2 20px 20px, #c2c2c2 25px 25px;
}
#BtnClassStyleProduct{
  position: absolute;
  left: 260px;
  top: 340px;
  border-radius: 10px;
  width: 110px;
  height: 40px;
  font-size: 17px;
  color: white;
  font-weight: bold;
  border-color: #b4986e;
  border-width: 1px;
  border-style: solid;
  background-color: #b4986e;
}
#BtnClassStyleMessage{
  position: absolute;
  left: 480px;
  top: 340px;
  border-radius: 10px;
  width: 110px;
  height: 40px;
  font-size: 17px;;
  color: white;
  font-weight: bold;
  border-color: #5e3111;
  border-width: 1px;
  border-style: solid;
  background-color: #5e3111;
}
#payTime{
  position: absolute;
  left: 440px;
  top: 280px;
}
#dialog{
  height: 300px;
}
.el-button{
  color: white;
}
._TimesStyle{
  font-size: 10px;
}
.pricesStyle{
  text-align: center;
  color: #d20707;
  font-weight: bolder;
}
.orderIdsStyle{
  font-size: 10px;
  color: #858484;
}
.TimesStyle{
  font-size: 11px;
}
#pageAtion{
  position: absolute;
  bottom: 3px;
  left: 250px;
}
#resetConditionBtn{
  position: absolute;
  right: 125px;
  top: 8px;
  color: black;
}
#searchConditionBtn{
  position: absolute;
  right: 15px;
  top: 8px;
  color: white;
  font-size: 14px;
}
.titleFontStyle{
  position: absolute;
  font-size: 14px;
  color: #545151;
  font-weight: normal;
}
.el-input-condition{
  position: absolute;
  width: 230px;
  border-radius: 10px;
}
._el-input-condition{
  position: absolute;
  width: 83px;
  border-radius: 10px;
}
#selectFont1{
  position: absolute;
  top: 53px;
  left: 80px;
}
#selectInput1{
  position: absolute;
  top: 50px;
  left: 158px;
}
#selectFont4{
  position: absolute;
  top: 108px;
  left: 80px;
}
#selectInput4{
  position: absolute;
  top: 105px;
  left: 158px;
}
#selectFont2{
  position: absolute;
  top: 53px;
  left: 480px;
}
#selectInput2{
  position: absolute;
  top: 50px;
  left: 542px;
}
#selectFont5{
  position: absolute;
  top: 108px;
  left: 480px;
}
#selectInput5{
  position: absolute;
  top: 105px;
  left: 582px;
}
#_selectInput5{
  position: absolute;
  top: 105px;
  left: 692px;
}
#middleLine{
  position: absolute;
  top: 105px;
  left: 672px;
  z-index: 1;
}
#selectFont3{
  position: absolute;
  top: 53px;
  left: 860px;
}
#selectInput3{
  position: absolute;
  top: 50px;
  left: 938px;
}
#selectFont6{
  position: absolute;
  top: 108px;
  left: 860px;
}
#selectInput6{
  position: absolute;
  top: 105px;
  left: 938px;
}
#dataListTabelStyle{
  position: absolute;
  width: 1260px;
  height: 343px;
  top: 345px;
  left: 240px;
}
#allSearchConditionTitle{
  position: absolute;
  top: 12px;
  left: 15px;
  font-size: 18px;
}
#breadcrumb{
  position: absolute;
  left: 280px;
  top: 11px;
  font-size: 14px;
}
#dataListFont{
  position: absolute;
  left: 48px;
  top: 12px;
  font-size: 19px;
}
#MemoIcon{
  position: absolute;
  left: -23px;
  top: 5px;
}
#allSearchCondition{
  position: absolute;
  left: 240px;
  top: 65px;
  height: 170px;
  width: 1260px;
  border-style: solid;
  border-width: 1px;
  border-color: #c2c2c2;
  background-color: rgba(252, 250, 250, 0.91);
}
#middleAreaForUseless{
  position: absolute;
  left: 240px;
  top: 265px;
  height: 55px;
  width: 1260px;
  border-style: solid;
  border-width: 1px;
  border-color: #c2c2c2;
  background-color: rgba(255, 252, 252, 0.99);
}
#HeadTitleImg{
  width: 30px;
  height: 30px;
  position: absolute;
  right: 30px;
  top: 5px;
}
#meunIcon{
  position: absolute;
  top: 5px;
  left: 235px;
}
#headLine1{
  position: absolute;
  left: 214px;
  top: 29px;
  height: 10px;
  width: 1320px;
  border-bottom-width: 2px;
  border-bottom-color: #a19d9d;
  border-bottom-style: solid;
}
#leftSide{
  position: absolute;
  left: 0;
  width: 215px;
  height: 100%;
  background-color: #304057;
}
.el-menu-item{
  background-color: #202d3d;
}
#_el-menu-item{
  background-color: #304057;
}
</style>