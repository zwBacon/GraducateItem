<template>
  <div id="leftDiv">
    <div id="logoDiv">
      <img src="../../../assets/首页资源/logowhite.png" id="logo">
    </div>
    <div id="mainPage" @click="returnMainPage">
      <el-icon style="color: white;position: absolute;top: 10px;left: 30px" :size="29"><HomeFilled /></el-icon>&emsp;&nbsp;&nbsp;
      <span style="color: white;position: absolute;top: 45px;letter-spacing: 2px">首页</span>
    </div>
    <div id="myPublishSign">
      <el-icon style="color: white;position: absolute;top: 10px;left: 30px" :size="29"><Platform /></el-icon>&emsp;&nbsp;&nbsp;
      <span style="color: white;position: absolute;top: 45px;letter-spacing: 1px;font-size: 14px;left: 15px">我的发布</span>
    </div>
  </div>

  <div id="mainShowDiv">
    <div id="topDiv">
      <span style="position: absolute;top: 20px;left: 20px;font-weight: bold;font-family: 华文新魏;font-size: 19px">寄居蟹房屋租售网站后台管理</span>
      <el-icon style="position: absolute;top: 25px;right: 303px;" :size="22"><Bell /></el-icon>
      <el-icon style="position: absolute;top: 25px;right: 253px;" :size="22"><Service /></el-icon>
      <img :src="this.stringplus + this.userTitle" style="position: absolute;width: 40px;height: 40px;border-radius: 25px;right: 160px;top: 16px">
      <span style="position: absolute;width: 70px;height: 40px;border-radius: 25px;right: 80px;top: 20px;font-size: 13px;font-weight: bold;letter-spacing: 2px">{{this.userName}}</span>
      <span style="position: absolute;width: 70px;height: 40px;border-radius: 25px;right: 80px;top: 39px;font-size: 12px;color: #c2c2c2">{{this.introduce}}</span>
    </div>

    <div id="middleDiv">
      <span style="position: absolute;top: 18px;left: 20px;font-weight: bold;font-family: 华文宋体;font-size: 24px">我的发布</span>
      <span style="position: absolute;top: 32px;left: 118px;font-weight: lighter;font-family: 华文宋体;font-size: 14px;color: #c2c2c2">&nbsp;/ Oma postaus houseSource</span>
      <span style="position: absolute;top: 72px;left: 20px;font-weight:bold;font-family: 华文宋体;font-size: 14px;color: #4e80fc"><el-icon><Management /></el-icon>&nbsp;&nbsp;房源发布</span>
      <div style="position: absolute;top: 104px;left: 20px;height: 4px;width: 76px;background-color: #4e80fc;border-radius: 20px"></div>
    </div>

    <div id="mainData">
      <el-icon style="z-index: 1;position: absolute;top: 15px;left: 12px"><Search /></el-icon><el-input style="width: 150px;position: absolute;top: 12px;height: 22px;left: 35px" v-model="searchForm.productName" placeholder="请输入产品名" />
      <el-select v-model="searchForm.type" placeholder="请选择发布产品的类型" size="small" style="position: absolute;left: 200px;width: 150px;height: 20px;top: 12px">
        <el-option label="全部" value="" />
        <el-option label="二手房" value="1" />
        <el-option label="出租房" value="2" />
      </el-select>
      <el-select v-model="searchForm.state" placeholder="请选择发布产品的状态" size="small" style="position: absolute;left: 370px;width: 150px;height: 20px;top: 12px">
        <el-option label="全部" value="" />
        <el-option label="审批失败" value="0" />
        <el-option label="审批成功" value="1" />
        <el-option label="正在审批" value="2" />
        <el-option label="已经售出" value="3" />
        <el-option label="撤回申请" value="4" />
        <el-option label="商品下架" value="5" />
      </el-select>
      <button style="position: absolute;right: 20px;top:10px;width: 50px;height: 29px;background-color: #4e80fc;color: white;border-radius: 5px;font-size: 12px;font-weight: bold;letter-spacing: 2px" @click="selectAllProduct">搜索</button>
      <el-table :data="hourseSourceData" style="width: 100%;position: absolute;top: 45px" max-height="450" :cell-style="{'text-align':'center'}"
                :header-cell-style="{'text-align':'center'}">
        <el-table-column fixed prop="firstPage" label="上传图片" width="80">
          <template #default="scope">
            <img :src=" this.stringplus + scope.row.firstPage"  width="50" height="50" class="img" @click="showLargeImg(scope.row.firstPage)"/>
          </template>
        </el-table-column>
        <el-table-column prop="productName" label="房产名" width="190" />
        <el-table-column prop="price" label="价格" width="100">
          <template #default="scope">
            <span class="pricesStyle">{{scope.row.price}}{{scope.row.priceDescription}}</span>
          </template>
        </el-table-column>
        <el-table-column prop="city" label="地址" width="270">
          <template #default="scope">
            <span>{{scope.row.province}}{{scope.row.city}}{{scope.row.county}}&nbsp;{{scope.row.community}}</span>
          </template>
        </el-table-column>
        <el-table-column prop="type" label="类型" width="100">
          <template #default="scope">
            <el-tag color="#585BFAFF"><p class="productState">{{scope.row.type === '1' ? '二手房' : '出租房'}}</p></el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="area" label="区域大小" width="80">
          <template #default="scope">
            <span>{{scope.row.area}}</span>
          </template>
        </el-table-column>
        <el-table-column prop="area" label="朝向" width="100">
          <template #default="scope">
            <span>{{scope.row.direction}}</span>
          </template>
        </el-table-column>
        <el-table-column prop="zip" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.state === '1' ? 'success' : scope.row.state === '2' ? 'primary' : 'danger'">{{scope.row.state === '1' ? '审批成功' : scope.row.state === '2' ? '正在审批' : scope.row.state === '3'?'已售出':scope.row.state === '0'?'申请驳回':scope.row.state === '4'?'申请撤销':'房源下架'}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="请求时间" width="195" />
        <el-table-column fixed="right" label="操作" width="170">
          <template #default="scope">
            <el-button link type="danger" size="small" @click="reverseApplication(scope.row.productID,scope.row.id)" v-show="scope.row.state === '2'">&emsp;撤回申请</el-button>
            <el-button link type="warning" size="small" @click="lookUpResaon(scope.row.productID)" v-show="scope.row.state === '0'">查看原因</el-button>
            <el-button link style="color: #0e0109" size="small" @click="lookUpInformation(scope.row.productID,scope.row.type,scope.row.userID)" v-show="scope.row.state === '1'">查看信息</el-button>
            <el-button link style="color: #d21257" size="small" @click="offShellProduct(scope.row.productID,scope.row.id)" v-show="scope.row.state === '1'">下架房源</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
          id="elPagination"
          v-model:current-page="searchForm.currentPage"
          v-model:page-size="searchForm.pageSize"
          :page-sizes="[10, 15, 30, 35]"
          :small="true"
          :disabled="disabled"
          :background="true"
          layout="total, sizes, prev, pager, next"
          :total="totalCount"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />

      <el-dialog v-model="dialogFormVisible" title="" width="570">
        <img :src=" this.stringplus + this.largImgSrc"  width="530" height="480" class="largeimg"/>
      </el-dialog>

      <el-dialog v-model="againstFormVisible" title="" width="390" style="height: 150px">
        <span style="position: absolute;top: 50px;left: 30px;color: #c2c2c2">审批人： admin</span>
        <span style="position: absolute;top: 90px;left: 30px;color: #c2c2c2">驳回原因：</span><span style="position: absolute;top: 90px;left: 110px;color: red;font-weight: bold">{{againstReason}}</span>
      </el-dialog>
    </div>
  </div>
</template>


<script>
import axios from "axios";
import router from "@/router";
import {ElMessage, ElMessageBox} from "element-plus";
import store from "core-js/internals/shared-store";

export default {
  name: "MyPublish",
  mounted() {
    this.requestForm.userId = store.state.LoadUserId
    this.searchForm.userId = store.state.LoadUserId
    // this.requestForm.userId = "GCCI-U-Scuqw8q9h398f2ccdd"
    // this.searchForm.userId = "GCCI-U-Scuqw8q9h398f2ccdd"
     this.selectUserInfo()
    this.selectAllProduct()
  },
  data(){
    return{
      largImgSrc:"",
      dialogFormVisible:false,
      totalCount:0,
      userTitle:"",
      userName:"",
      introduce:"",
      stringplus: "data:image/png;base64,",
      requestForm:{
        userId:""
      },
      searchForm:{
        productName:"",
        pageSize:"10",
        currentPage:"1",
        type:"",
        state:"",
        userId:""
      },
      hourseSourceData:[],
      againstFormVisible:false,
      againstReason: ""
    }
  },
  methods:{
    offShellProduct(productId,id){
      ElMessageBox.confirm(
          '您将下架此房源信息，是否继续?',
          '温馨提示',
          {
            confirmButtonText: 'Continue',
            cancelButtonText: 'Cancel',
            type: 'warning',
          }
      )
          .then(() => {
            this.offShellHandel(productId,id)
            ElMessage({
              type: 'success',
              message: '下架成功！',
            })
          })
          .catch(() => {
            ElMessage({
              type: 'info',
              message: '取消下架申请',
            })
          })
    },
    offShellHandel(productId,id){
      let _this = this
      axios({
        method: "get",
        url: "http://localhost:3000/productServe/offShellHandel?productId="+productId+"&id="+id,
      }).then(function () {
        _this.selectAllProduct()
      })
    },
    reverseApplication(productId,id){
      ElMessageBox.confirm(
          '您将撤回此发布信息，是否继续?',
          '温馨提示',
          {
            confirmButtonText: 'Continue',
            cancelButtonText: 'Cancel',
            type: 'warning',
          }
      )
          .then(() => {
            this.reverseHandel(productId,id)
            ElMessage({
              type: 'success',
              message: '撤回成功！',
            })
          })
          .catch(() => {
            ElMessage({
              type: 'info',
              message: '取消撤回申请',
            })
          })
    },
    reverseHandel(productId,id){
      let _this = this
      axios({
        method: "get",
        url: "http://localhost:3000/productServe/reverseApplication?productId="+productId+"&id="+id,
      }).then(function () {
        _this.selectAllProduct()
      })
    },
    lookUpResaon(productId){
      this.againstFormVisible = true;
      let _this = this
      axios({
        method: "get",
        url: "http://localhost:3000/productServe/lookUpResaon?productId="+productId,
      }).then(function (resp) {
         _this.againstReason = resp.data
      })
    },
    selectUserInfo(){
      let _this = this
      axios({
        method: "post",
        url: "http://localhost:2000/baseUserController/selectUserInfoByUserId",
        data: this.requestForm
      }).then(function (resp) {
        _this.userTitle = resp.data._headImg
        _this.userName = resp.data.userName
        _this.introduce = resp.data.introduce
      })
    },
    lookUpInformation(productId,type,userId){
      router.push('/ProductInfoDetailsPage/'+productId+'/'+type+'/'+userId+"/admin")
    },
    selectAllProduct(){
      let _this = this
      axios({
        method: "post",
        url: "http://localhost:3000/productServe/selectAllMyPubshingByCondition",
        data: this.searchForm
      }).then(function (resp) {
        _this.hourseSourceData = resp.data["records"]
        _this.totalCount = resp.data["totalCount"]
      })
    },
    handleSizeChange(val) {
      this.searchForm.pageSize = val;
      this.selectAllProduct();
    },
    handleCurrentChange(val) {
      this.searchForm.currentPage = val;
      this.selectAllProduct();
    },
    showLargeImg(imgSrc){
      this.largImgSrc = imgSrc
      this.dialogFormVisible = true;
    },
    returnMainPage(){
      router.push("/MainMeunShowPage/"+ "main");
    }
  }
}
</script>

<style scoped>
.productState{
  color: white;
}
.pricesStyle{
  text-align: center;
  color: #d20707;
  font-weight: bolder;
}
#elPagination{
  position: absolute;
  top: 500px;
  left: 450px;
}
#topDiv{
  position: absolute;
  top: 0;
  width: 1450px;
  height: 70px;
  left: 0;
  background-color: white;
  border-bottom-style: solid;
  border-bottom-width: 1px;
  border-bottom-color: #ded9d9;
}
#mainData{
  position: absolute;
  top: 200px;
  width: 1385px;
  height: 495px;
  left: 25px;
  background-color: white;
}
#middleDiv{
  position: absolute;
  top: 70px;
  width: 1450px;
  height: 110px;
  left: 0;
  background-color: white;
  border-top-style: solid;
  border-top-width: 1px;
  border-top-color: #ded9d9;
}
#mainPage{
  position: absolute;
  top: 70px;
  left: 0;
  height: 85px;
  width: 95px;
}
#myPublishSign{
  position: absolute;
  top: 155px;
  left: 0;
  height: 85px;
  width: 95px;
  background-color: #282828;
}
#leftDiv{
 position: absolute;
  width: 95px;
  height: 100%;
  background-color: #35495e;
}
#logoDiv{
  position: absolute;
  width: 95px;
  height: 70px;
  background-color: #346df8;
}
#logo{
  position: absolute;
  width: 80px;
  height: 70px;
  left: 7px;
  top: 0px;
}
#mainShowDiv{
  position: absolute;
  left: 95px;
  height: 100%;
  width: 1450px;
  background-color: #eae9e9;
}
</style>

<style>
.msg-pagination-container.is-background .el-pager li {
  /*对页数的样式进行修改*/
  background-color: #10263c;
  color: #FFF;
}
.msg-pagination-container.is-background .btn-next {
  /*对下一页的按钮样式进行修改*/
  background-color: #10263c;
  color: #FFF;
}
.msg-pagination-container.is-background .btn-prev {
  /*对上一页的按钮样式进行修改*/
  background-color: #10263c;
  color: #FFF;
}
.msg-pagination-container.is-background .el-pager li:not(.disabled).active {
  /*当前选中页数的样式进行修改*/
  background-color: #446cff;
  color: #FFF;
}
</style>