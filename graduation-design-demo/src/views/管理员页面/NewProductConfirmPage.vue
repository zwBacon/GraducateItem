<template>
<!--  头部-->
  <div id="head">
    <img src="../../assets/首页资源/logowhite.png" id="logo">
    <span id="title">寄居蟹</span>
    <img src="../../assets/侧边栏资源/默认头像.png" id="defineTitle">
  </div>

<!--  侧边-->
  <div id="left">
    <div id="NewHomeApproval" class="item"><el-icon color="#009cfd" :size="18"><Checked /></el-icon>新房审批</div>
    <div id="NewUserApproval" class="item"><el-icon :size="18"><WalletFilled /></el-icon>用户审批</div>
    <div id="UserManage" class="item"><el-icon :size="18"><Stamp /></el-icon>用户管理</div>
    <div id="ComplaintHandle" class="item"><el-icon :size="18"><ChromeFilled /></el-icon>投诉处理</div>
  </div>

<!--  主要页面-->
  <div id="mainFunction">

    <p id="mainFunctionTitle">新房申请列表</p>
    <div id="mainFunctionTable">
    <el-table :data="hourseSourceData" style="width: 100%">
      <el-table-column fixed prop="firstPage" label="发布图片" width="80">
        <template #default="scope">
          <img :src=" this.stringplus + scope.row.firstPage"  width="50" height="50" class="img" @click="showLargeImg(scope.row.firstPage)"/>
        </template>
      </el-table-column>
      <el-table-column prop="productName" label="Name" width="100" />
      <el-table-column prop="price" label="Price" width="100">
        <template #default="scope">
            <span class="pricesStyle">{{scope.row.price}}{{scope.row.priceDescription}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="city" label="Address" width="150">
        <template #default="scope">
          <span>{{scope.row.province}}{{scope.row.city}}{{scope.row.county}}&nbsp;{{scope.row.community}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="type" label="Type" width="100">
        <template #default="scope">
          <el-tag color="#585BFAFF"><p class="productState">{{scope.row.type === '1' ? '二手房' : '出租房'}}</p></el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="area" label="Area" width="100">
        <template #default="scope">
          <span>{{scope.row.area}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="zip" label="State" width="100">
        <template #default="scope">
          <el-tag :type="scope.row.state === '1' ? 'success' : scope.row.state === '2' ? 'primary' : 'danger'">{{scope.row.state === '1' ? '审批成功' : scope.row.state === '2' ? '正在审批' : scope.row.state === '0' ? '审批打回':'房源下架'}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="CreateTime" width="135" />
      <el-table-column prop="userInfo.userName" label="UserName" width="100" />
      <el-table-column prop="userInfo.phone" label="LinkPhone" width="120" />
      <el-table-column prop="userInfo.email" label="LinkEmail" width="120" />
      <el-table-column fixed="right" label="Operations" width="120">
        <template #default="scope">
          <el-button link type="danger" size="small" @click="aginstApplication(scope.row.productID,scope.row.id)" v-show="scope.row.state === '2'">&emsp;打回申请</el-button>
          <el-button link type="warning" size="small" @click="agreeApplication(scope.row.productID,scope.row.id,scope.row.userID)" v-show="scope.row.state === '2'">批准申请</el-button>
          <el-button link type="info" size="small" @click="lookUpInformation(scope.row.productID,scope.row.type,scope.row.userID)"  v-show="scope.row.state === '1'">查看信息</el-button>
        </template>
      </el-table-column>
    </el-table>
      <el-pagination
          id="elPagination"
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[7, 15, 30, 35]"
          :small="true"
          :disabled="disabled"
          :background="background"
          layout="total, sizes, prev, pager, next"
          :total="totalCount"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />

      <el-dialog v-model="dialogFormVisible" title="" width="570">
        <img :src=" this.stringplus + this.largImgSrc"  width="530" height="480" class="largeimg"/>
      </el-dialog>

      <!--  驳回申请-->
      <el-dialog v-model="againstFormVisible" title="" width="570" >
        <el-form :model="againstForm">
          <el-form-item label="驳回原因" :label-width="formLabelWidth">
            <el-input
                v-model="againstForm.againstReason"
                :autosize="{ minRows: 2, maxRows: 4 }"
                type="textarea"
            />
          </el-form-item>
        </el-form>
        <template #footer>
          <div class="dialog-footer">
            <el-button  style="color: black" @click="againstFormVisible = false">Cancel</el-button>
            <el-button type="primary" @click="confirmAgainst"  style="color: white">
              Confirm
            </el-button>
          </div>
        </template>
      </el-dialog>
      <!--  批准申请-->
      <el-dialog v-model="agreeFormVisible" title="完善房源信息" width="770" style="height: 600px;background-color: #efefef">
        <el-form :model="agreeForm">
          <span style="position: absolute;top: 80px;font-size: 18px;font-weight: bold;color: #332206;font-family: 华文中宋">房源实地照片</span>
          <span style="position: absolute;top: 110px;left: 0;width: 100%;height: 0.5px;background-color: black"></span>
          <el-form-item label="" style="position: absolute;top: 120px;left: 40px">
            <van-uploader  v-model="agreeForm.fileList" reupload max-count="4">
            </van-uploader>
          </el-form-item>
          <span style="position: absolute;top: 200px;font-size: 18px;font-weight: bold;color: #332206;font-family: 华文中宋">房源详细信息</span>
          <span style="position: absolute;top: 230px;left: 0;width: 100%;height: 0.5px;background-color: black"></span>
          <el-form-item label="标签" style="width: 200px;position: absolute;top: 490px;left: 18px;">
          <el-select
              v-model="agreeForm.tagList"
              multiple
              multiple-limit="3"
              placeholder="为房源添加标签"
              style="width: 200px;position: absolute;top: 0px"
          >
            <el-option
                v-for="item in tags"
                :key="item.dataId"
                :label="item.tagName"
                :value="item.dataId"
            />
          </el-select>
          </el-form-item>
          <el-form-item label="楼层" style="width: 200px;position: absolute;top: 250px;left: 20px;">
            <el-input v-model="agreeForm.floor" autocomplete="off"  placeholder="高楼层/共16层"/>
          </el-form-item>
          <el-form-item label="装修" style="width: 200px;position: absolute;top: 290px;left: 20px;">
            <el-input v-model="agreeForm.fitment" autocomplete="off"  placeholder="精装修/毛坯"/>
          </el-form-item>
          <el-form-item label="电梯" style="width: 200px;position: absolute;top: 330px;left: 20px;">
            <el-select v-model="agreeForm.lift" placeholder="电梯情况">
              <el-option label="有电梯" value="1" />
              <el-option label="无电梯" value="0" />
            </el-select>
          </el-form-item>
          <el-form-item label="楼型" style="width: 200px;position: absolute;top: 370px;left: 20px;">
          <el-input v-model="agreeForm.floorType" autocomplete="off"  placeholder="板楼"/>
        </el-form-item>
          <el-form-item label="类型" style="width: 200px;position: absolute;top: 410px;left: 20px;">
          <el-input v-model="agreeForm.type" autocomplete="off"  placeholder="住宅"/>
        </el-form-item>
          <el-form-item label="权属" style="width: 200px;position: absolute;top: 450px;left: 20px;">
            <el-input v-model="agreeForm.ownerShip" autocomplete="off" placeholder="商品房"/>
          </el-form-item>
          <el-form-item label="年&emsp;&emsp;代" style="width: 270px;position: absolute;top: 250px;left: 360px;">
            <el-input v-model="agreeForm.age" autocomplete="off"  placeholder="2024"/>
          </el-form-item>
          <el-form-item label="产品价值" style="width: 270px;position: absolute;top: 290px;left: 360px;">
            <el-input v-model="agreeForm.productValue" autocomplete="off"  placeholder="10分最高，0分最低"/>
          </el-form-item>
          <el-form-item label="配套价值" style="width: 270px;position: absolute;top: 330px;left: 360px;">
            <el-input v-model="agreeForm.supportValue" autocomplete="off"  placeholder="10分最高，0分最低"/>
          </el-form-item>
          <el-form-item label="交易价值" style="width: 270px;position: absolute;top: 370px;left: 360px;">
            <el-input v-model="agreeForm.tradeValue" autocomplete="off"  placeholder="10分最高，0分最低"/>
          </el-form-item>
          <el-form-item label="区域价值" style="width: 270px;position: absolute;top: 410px;left: 360px;">
            <el-input v-model="agreeForm.areaValue" autocomplete="off" placeholder="10分最高，0分最低"/>
          </el-form-item>
          <el-form-item label="评&emsp;&emsp;价" style="width: 270px;position: absolute;top: 450px;left: 360px;">
            <el-input v-model="agreeForm.comment" :autosize="{ minRows: 2, maxRows: 4 }"
                      type="textarea" autocomplete="off"/>
          </el-form-item>
        </el-form>
        <template #footer>
          <div class="dialog-footer" style="color: whitesmoke;position: absolute;top: 550px;left: 680px">
            <el-button type="primary" @click="confirmAgreeForm">
              提交
            </el-button>
          </div>
        </template>
      </el-dialog>
    </div>
  </div>

</template>

<script>
import axios from "axios";
import router from "@/router";

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
export default {
  mounted(){
       this.selectAllProduct()
  },
  data(){
    return{
       hourseSourceData:[],
       pageSize:"7",
      currentPage:"1",
      totalCount:0,
      stringplus: "data:image/png;base64,",
      largImgSrc:"",
      dialogFormVisible:false,
      againstFormVisible:false,
      agreeFormVisible:false,
      againstForm:{
         productId:"",
         againstReason:"",
         id:""
      },
      // 批准申请
      agreeForm:{
        fileList:[],
        tagList:[],
        floor:"",
        fitment:"",
        lift:"",
        floorType:"",
        type:"",
        ownerShip:"",
        age:"",
        productValue:"",
        supportValue:"",
        tradeValue:"",
        areaValue:"",
        comment:"",
      //   基本信息
        productId: "",
        userId:"",
        id:""
      },
      titleForm: {
        file: File,
        productId: ""
      },
      tags:[]
    }
  },
  methods:{
    selectAllProduct(){
      let _this = this
      axios({
        method: "get",
        url: "http://localhost:3000/productServe/selectAllConfimingProduct?beginNum="+this.currentPage+"&size="+this.pageSize
      }).then(function (resp) {
        _this.hourseSourceData = resp.data["records"]
        _this.totalCount = resp.data["total"]
      })
    },
    lookUpInformation(productId,type,userId){
      router.push('/ProductInfoDetailsPage/'+productId+'/'+type+'/'+userId+"/admin")
    },
    // 申请驳回
    aginstApplication(productId,id){
       this.againstForm.productId = productId
       this.againstForm.id = id
       this.againstFormVisible = true
    },
    confirmAgainst(){
       this.againstFormVisible = false
      let _this = this
      axios({
        method: "post",
        url: "http://localhost:3000/productServe/againstProductPublish",
        data: this.againstForm
      }).then(function () {
        _this.selectAllProduct()
        _this.againstForm.againstReason = ''
      })
    },
    // 通过申请
    agreeApplication(productId,id,userId){
      this.agreeFormVisible = true
      this.agreeForm.productId = productId
      this.agreeForm.id = id
      this.agreeForm.userId = userId
    //   查询所有标签
      let _this = this
      axios({
        method: "get",
        url: "http://localhost:3000/productServe/selectAllTags",
      }).then(function (resp) {
        _this.tags = resp.data
      })
    },
    confirmAgreeForm(){
      this.beforeConfirmAgreeForm()
    },

    beforeConfirmAgreeForm(){
      this.agreeFormVisible = false
      let _this = this
      axios({
        method: "post",
        url: "http://localhost:3000/productServe/insertNewHouseInfo",
        data: this.agreeForm
      }).then(function () {
        _this.selectAllProduct()
        _this.middleConfirmAgreeForm()
        _this.endConfirmAgreeForm()
      })
    },

    middleConfirmAgreeForm(){
        for(let i = 0;i < this.agreeForm.fileList.length;i++){
          this.titleForm.file = this.agreeForm.fileList[i].file
          if(i === 0){
            this.onSubmitTitle(this.agreeForm.productId,"DETAIL_IMG1")
          }else if(i === 1){
            this.onSubmitTitle(this.agreeForm.productId,"DETAIL_IMG2")
          }else if(i === 2){
            this.onSubmitTitle(this.agreeForm.productId,"DETAIL_IMG3")
          }else if(i === 3){
            this.onSubmitTitle(this.agreeForm.productId,"DETAIL_IMG4")
          }
        }
    },

    endConfirmAgreeForm(){
      this.agreeForm.fileList = [];
      this.agreeForm.tagList = [];
      this.agreeForm.floor = "";
      this.agreeForm.fitment = "";
      this.agreeForm.lift = "";
      this.agreeForm.floorType = "";
      this.agreeForm.type = "";
      this.agreeForm.ownerShip = "";
      this.agreeForm.age = "";
      this.agreeForm.productValue = "";
      this.agreeForm.supportValue = "";
      this.agreeForm.tradeValue = "";
      this.agreeForm.areaValue = "";
      this.agreeForm.comment = "";
      this.agreeFormVisible = false
    },


    // 提交图片数据
    onSubmitTitle(productId,sign) {
      const formData = new FormData();
      formData.append("productId", productId)
      formData.append("file",this.titleForm.file)
      formData.append("sign",sign)
      axios({
        method: "post",
        url: "http://localhost:3000/productServe/insertImgForDetails",
        data: formData
      }).then(function () {
      })
    },

    // 分页
    handleSizeChange(val) {
      this.pageSize = val;
      this.selectAllProduct();
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.selectAllProduct();
    },
    showLargeImg(imgSrc){
      this.largImgSrc = imgSrc
      this.dialogFormVisible = true;
    }
  }
}
</script>

<style scoped>
.img{
  border-radius: 10px;
}
.productState{
  color: white;
}
.pricesStyle{
  text-align: center;
  color: #d20707;
  font-weight: bolder;
}
.largeimg{
  border-radius: 40px;
}
#elPagination{
  position: relative;
  top:15px;
  left: 450px;
}
#mainFunctionTable{
  position: absolute;
  left: 200px;
  top: 130px;
  font-size: 30px;
  font-family: "华文宋体";
}
.item{
  position: absolute;
  width: 170px;
  height: 70px;
  line-height: 70px;
  text-align: center;
  font-size: 20px;
}
#mainFunctionTitle{
  position: absolute;
  left: 200px;
  top: 70px;
  font-size: 30px;
  font-family: 华文行楷;
  color: #9b5050;
}
#NewHomeApproval{
  top: 60px;
  font-family: 华文隶书;
  font-weight: bold;
  font-size: 25px;
  background-color: #4d4949;
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
  top: 60px;
  background-color: #f6f4f4;
  width: 170px;
  height: 700px;
}
#defineTitle{
  position: absolute;
  height: 33px;
  width: 36px;
  right: 30px;
  top: 12px;
}
#title{
  position: absolute;
  font-size: 25px;
  font-weight: bold;
  font-family: 华文隶书;
  color: white;
  left: 100px;
  top: 12px;
}
#head{
  position: absolute;
  height: 60px;
  width: 100%;
  background-color: #312e2e;
  top: 0;
}
#logo{
  position: absolute;
  height: 53px;
  width: 63px;
  left: 30px;
  top: 2px;
}
</style>