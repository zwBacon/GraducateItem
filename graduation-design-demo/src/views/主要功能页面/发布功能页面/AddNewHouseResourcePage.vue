<template>
  <HeadLineViewForMain/>
  <el-alert v-model:title="this.errorMessage" type="error" center id="errorMessage" v-show="this.errorDisper"/>
  <el-alert v-model:title="this.errorMessage" type="success" center id="successMessage" v-show="this.successDisper"/>
  <div id="addNewSourceCard">
    <van-uploader class="vanUpload" v-model="fileList" reupload max-count="1">
    </van-uploader>
    <span id="rightSideOfImg"></span>
    <span id="selectOfType">
      <span id="styleSelect">
      <span class="smallTitle">房源类型:</span>&ensp;&emsp;
    <el-select v-model="SubmitForm.style" placeholder="请选择您的房源类型">
        <el-option label="租房" value="2"/>
        <el-option label="二手房" value="1"/>
    </el-select>
    </span>
      <span id="addressInput">
        <span class="smallTitle" style=" position: relative;left: -330px;top: 5px">地&emsp;&emsp;址:</span>&ensp;&emsp;
        <span style="position: relative;left: 110px;top: -30px;"><el-input style="width: 220px" v-model="SubmitForm.address" placeholder="按照xx省/xx市/xx区县/xx格式填写"></el-input></span>
      </span>

      <span id="rowLine"></span>

<!--      二手房-->
      <div  v-show="SubmitForm.style === '1'">

        <span id="priceInput">
        <span class="smallTitle" style=" position: absolute;left: 0px;top: 100px">价&emsp;&emsp;格:</span>&ensp;&emsp;
        <span style="position: relative;left: 80px;top: 33px;"><el-input style="width: 150px;color: red" v-model="SubmitForm.price" placeholder="请输入您的总价"></el-input><span class="smallTitle">&emsp;万</span></span>
      </span>

        <span id="communityInput">
        <span class="smallTitle" style=" position: absolute;left: 0px;top: 150px">小&nbsp;区&nbsp;名:</span>&ensp;&emsp;
        <span style="position: relative;left: -135px;top: 85px;"><el-input style="width: 220px" v-model="SubmitForm.community" placeholder="输入房源所在小区"></el-input></span>
      </span>

        <span id="rentNumInput">
        <span class="smallTitle" style=" position: absolute;left: 0px;top: 200px">房厅数量:</span>&ensp;&emsp;
        <span style="position: absolute;left: 108px;top: 198px;"><el-input style="width: 50px" v-model="SubmitForm.roomNum"></el-input><span class="smallTitle">&emsp;室&emsp;</span><el-input style="width: 50px" v-model="SubmitForm.dinerNum"></el-input><span class="smallTitle">&emsp;厅</span></span>
      </span>

        <span id="areaInput">
        <span class="smallTitle" style=" position: absolute;left: -1px;top: 250px">面&emsp;&emsp;积:</span>&ensp;&emsp;
        <span style="position: relative;left: 105px;top: 151px;"><el-input style="width: 150px" v-model="SubmitForm.area" placeholder="输入房源面积"></el-input><span class="smallTitle">&emsp;m²</span></span>
      </span>

        <span id="towardsInput">
        <span class="smallTitle" style=" position: absolute;left: -1px;top: 300px">朝&emsp;&emsp;向:</span>&ensp;&emsp;
        <span style="position: relative;left: -110px;top: 202px;"><el-input style="width: 150px" v-model="SubmitForm.towards" placeholder="输入房源朝向"></el-input></span>
      </span>

        <span id="phoneInput">
        <span class="smallTitle" style=" position: absolute;left: -1px;top: 350px">手&nbsp;机&nbsp;号:</span>&ensp;&emsp;
        <span style="position: relative;left: -288px;top: 255px;"><el-input style="width: 175px" v-model="SubmitForm.phone" :disabled="true"></el-input></span>
      </span>

        <span id="emailInput">
        <span class="smallTitle" style=" position: absolute;left: -1px;top: 400px">邮&emsp;&emsp;箱:</span>&ensp;&emsp;
        <span style="position: relative;left: 75px;top: 270px;"><el-input style="width: 175px" v-model="SubmitForm.email" :disabled="true"></el-input></span>
      </span>
      </div>

<!--      租房-->
      <div  v-show="SubmitForm.style === '2'">
      <span id="rentTypeSelect">
        <span class="smallTitle">租房类型:</span>&ensp;&emsp;
        <el-select v-model="SubmitForm.rentType" placeholder="请选择您的租房类型">
          <el-option label="整租" value="1"/>
          <el-option label="合租" value="2"/>
        </el-select>
      </span>

        <span id="priceInput">
        <span class="smallTitle" style=" position: relative;left: -330px;top: 90px">价&emsp;&emsp;格:</span>&ensp;&emsp;
        <span style="position: relative;left: 110px;top: 55px;"><el-input style="width: 150px;color: red" v-model="SubmitForm.price" placeholder="请输入您的月租"></el-input><span class="smallTitle">&emsp;元/月</span></span>
      </span>

        <span id="communityInput">
        <span class="smallTitle" style=" position: relative;left: -215px;top: 105px">小&nbsp;区&nbsp;名:</span>&ensp;&emsp;
        <span style="position: relative;left: -212px;top: 105px;"><el-input style="width: 220px" v-model="SubmitForm.community" placeholder="输入房源所在小区"></el-input></span>
      </span>

        <span id="rentNumInput">
        <span class="smallTitle" style=" position: absolute;left: 0px;top: 254px">房厅数量:</span>&ensp;&emsp;
        <span style="position: relative;left: 110px;top: 124px;"><el-input style="width: 50px" v-model="SubmitForm.roomNum"></el-input><span class="smallTitle">&emsp;室&emsp;</span><el-input style="width: 50px" v-model="SubmitForm.dinerNum"></el-input><span class="smallTitle">&emsp;厅</span></span>
      </span>

        <span id="areaInput">
        <span class="smallTitle" style=" position: absolute;left: -1px;top: 305px">面&emsp;&emsp;积:</span>&ensp;&emsp;
        <span style="position: relative;left: -108px;top: 175px;"><el-input style="width: 150px" v-model="SubmitForm.area" placeholder="输入房源面积"></el-input><span class="smallTitle">&emsp;m²</span></span>
      </span>

        <span id="towardsInput">
        <span class="smallTitle" style=" position: absolute;left: -1px;top: 355px">朝&emsp;&emsp;向:</span>&ensp;&emsp;
        <span style="position: relative;left: 110px;top: 195px;"><el-input style="width: 150px" v-model="SubmitForm.towards" placeholder="输入房源朝向"></el-input></span>
      </span>

        <span id="phoneInput">
        <span class="smallTitle" style=" position: absolute;left: -1px;top: 405px">手&nbsp;机&nbsp;号:</span>&ensp;&emsp;
        <span style="position: relative;left: -69px;top: 245px;"><el-input style="width: 175px" v-model="SubmitForm.phone" :disabled="true"></el-input></span>
      </span>

        <span id="emailInput">
        <span class="smallTitle" style=" position: absolute;left: -1px;top: 455px">邮&emsp;&emsp;箱:</span>&ensp;&emsp;
        <span style="position: relative;left: -272px;top: 295px;"><el-input style="width: 175px" v-model="SubmitForm.email" :disabled="true"></el-input></span>
      </span>
      </div>

      <el-button color="#3d280a" id="submitRequest" :dark="isDark" @click="BeforeSubmitRequest">提交申请</el-button>
    </span>
  </div>
</template>

<script>
import HeadLineViewForMain from "@/components/HeadLineViewForMain.vue";
import axios from "axios";
import router from "@/router";
import {ElMessage, ElMessageBox} from "element-plus";
import store from "core-js/internals/shared-store";

export default {
  components: {
    HeadLineViewForMain
  },
  mounted() {
    this.username=store.state.LoadUsername
    this.requestUserForm.userId = store.state.LoadUserId;
    // this.requestUserForm.userId = "GCCI-U-Scuqw8q9h398f2ccdd"
    // this.username = "张虹生"
    this.selectPhoneAndEmail();
  },
  data() {
    return {
      source: {
        src: 'https://storage.jd.com/about/big-final.mp4?Expires=3730193075&AccessKey=3LoYX1dQWa6ZXzQl&Signature=ViMFjz%2BOkBxS%2FY1rjtUVqbopbJI%3D',
        type: 'video/mp4'
      },
      errorMessage:"",
      errorDisper: false,
      successDisper: false,
      fileList: [], // 图片列表,
      SubmitForm: {  //提交表单
        style: "2",
        address: "",
        // 租房
        rentType:"",
        price:"",
        community:"",
        roomNum:"",
        dinerNum:"",
        area:"",
        towards:"",
        img:"",
      //   用户信息
        phone:"",
        email:"",
        userId:"",
      },
      titleForm: {
        file: File,
        productId: ""
      },
      requestUserForm:{
        userId:""
      },
      userName:"",
    };
  },
  methods: {
    BeforeSubmitRequest(){
  ElMessageBox.confirm(
      '您将发布新的房源信息，请确保信息属实且正确，将会有工作人员进行确认, 是否继续?',
      '提示',
      {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning',
      }
  )
      .then(() => {
        this.submitRequest()
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: 'Publish canceled',
        })
      })
},
    submitRequest(){
      let _this = this;
      if (this.fileList[0] !== undefined){
        this.SubmitForm.img = this.fileList[0].content
        this.titleForm.file = this.fileList[0].file
      }
      this.SubmitForm.userId = this.requestUserForm.userId
      axios({
        method: "post",
        url: "http://localhost:3000/productServe/addNewProduct",
        data: this.SubmitForm
      }).then(function (resp) {
        if(resp.data["body"] !== 'success'){
          _this.errorMessage = resp.data["body"]
          _this.ErrorMessageApperance();
        }else {
          _this.errorMessage = "提交成功"
          _this.titleForm.productId = resp.data["productId"]
          _this.SuccessMessageApperance();
          if (_this.fileList[0] !== undefined){
            _this.onSubmitTitle(_this.titleForm.productId)
          }
        }
      })

    },
    // 提交图片数据
    onSubmitTitle(productId) {
      const formData = new FormData();
      formData.append("productId", productId)
      formData.append("file",this.titleForm.file)
      axios({
        method: "post",
        url: "http://localhost:3000/productServe/insertImg",
        data: formData
      }).then(function () {
      })
    },
    selectPhoneAndEmail(){
      let _this = this;
      axios({
        method: "get",
        url: "http://localhost:2000/baseUserController/selectUserInfoByName?username="+this.username,
      }).then(function (resp) {
        _this.SubmitForm.email = resp.data["result"].email
      })
      axios({
        method: "post",
        url: "http://localhost:2000/baseUserController/selectUserInfoByUserId",
        data: this.requestUserForm
      }).then(function (resp) {
        _this.SubmitForm.phone = resp.data.phone
      })
    },
    ErrorMessageApperance() {
      this.errorDisper = true;
      setTimeout(() => {
        this.errorDisper = false
      }, 3000);
    },
    SuccessMessageApperance() {
      this.successDisper = true;
      setTimeout(() => {
        this.successDisper = false
        router.push("/MainMeunShowPage/"+ "main");
      }, 2000);
    },
  }
};
</script>
<style scss>
#submitRequest{
  position: absolute;
  top: 450px;
  left: 450px;
  height: 40px;
  width: 100px;
  background-color: #3d280a;
  font-weight: bold;
  color: white;
  font-size: 13px;
  border-radius: 5px;
  font-family: fantasy;
  letter-spacing: 2px;
}
#rentTypeSelect{
  position: relative;
  top: 40px;
}
#addressInput {
  position: relative;
  top: 30px;
}
#styleSelect {
  position: relative;
  top: -15px;
}

.smallTitle {
  font-size: 18px;
  font-family: 楷体;
  font-weight: bolder;
}

#selectOfType {
  position: absolute;
  left: 340px;
  top: 40px;
}

#rightSideOfImg {
  position: absolute;
  left: 310px;
  height: 540px;
  width: 2px;
  border-left-style: solid;
  border-left-width: 2px;
  border-left-color: #163116;
}
#rowLine{
  position: absolute;
  left: -30px;
  top: 78px;
  height: 1px;
  width: 600px;
  border-bottom-style: solid;
  border-bottom-width: 2px;
  border-bottom-color: #982e2e;
}
#addNewSourceCard {
  position: absolute;
  left: 300px;
  top: 130px;
  border-radius: 20px;
  background-color: #faf9f9;
  box-shadow: 5px 5px 10px #dedada, -5px -5px 10px #e5e1e1;
  width: 910px;
  height: 540px;
}

.vanUpload {
  position: absolute;
  left: 80px;
  top: 140px;
  border-width: 8px;
  border-left-style: solid;
  border-top-style: solid;
  border-color: rgba(119, 90, 49, 0.83);
  background-color: #5b4141;
}

:root:root {
  --van-uploader-size: 165px;
  --van-uploader-icon-size: 40px;
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
</style>
