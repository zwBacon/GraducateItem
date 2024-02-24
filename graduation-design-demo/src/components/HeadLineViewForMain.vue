<template>
  <div id="Head">
    <img src="../assets/首页资源/logo.png" id="logo" class="details">

    <p class="fontstyle" id="first">精选</p><img v-bind:src="imgSrc1" class="imgStyle" id="firstimg"
                                                 @click="click('first','firstimg')">
    <p class="fontstyle" id="second">看点</p><img v-bind:src="imgSrc2" class="imgStyle" id="secimg"
                                                  @click="click('second','secimg')">
    <p class="fontstyle" id="third">关注</p><img v-bind:src="imgSrc3" class="imgStyle" id="thirimg"
                                                 @click="click('third','thirimg')">
    <p class="fontstyle" id="forth">消息</p><img v-bind:src="imgSrc4" class="imgStyle" id="forthimg"
                                                 @click="click('forth','forthimg')">
    <p class="fontstyle" id="fifth">个人中心</p><img v-bind:src="imgSrc5" class="imgStyle" id="fifimg"
                                                     @click="click('fifth','fifimg')">
  </div>
</template>

<script>

import router from "@/router";
import store from "core-js/internals/shared-store";

export default {
  name: "HeadLineViewForMain",
  mounted() {
    if(store.state.sta === 'firstimg'){
      this.imgSrc1 = require('../assets/首页头资源/首页(2).png');
    }else if(store.state.sta === 'secimg'){
      this.imgSrc1 = require('../assets/首页头资源/首页.png');
      this.imgSrc2 = require('../assets/首页头资源/今日看点(2).png');
    }else if(store.state.sta === 'thirimg'){
      this.imgSrc1 = require('../assets/首页头资源/首页.png');
      this.imgSrc3 = require('../assets/首页头资源/加关注(2).png');
    }else if(store.state.sta === 'forthimg'){
      this.imgSrc1 = require('../assets/首页头资源/首页.png');
      this.imgSrc4 = require('../assets/首页头资源/消息(2).png');
    }else if(store.state.sta === 'fifimg'){
      this.imgSrc1 = require('../assets/首页头资源/首页.png');
      this.imgSrc5 = require('../assets/首页头资源/我的(2).png');
    }
    var fontName = store.state.fontName
    if(fontName !== undefined) {
      var font_name2 = document.getElementById(fontName);
      font_name2.style.color = "#ff6b02"
      font_name2.style.fontWeight = "bold"
    }else if (fontName === undefined){
      var font_name3 = document.getElementById("first");
      font_name3.style.color = "#ff6b02"
      font_name3.style.fontWeight = "bold"
    }
  },
  data() {
    return {
      imgSrc1: require('../assets/首页头资源/首页(2).png'),
      imgSrc2: require('../assets/首页头资源/今日看点.png'),
      imgSrc3: require('../assets/首页头资源/加关注.png'),
      imgSrc4: require('../assets/首页头资源/消息.png'),
      imgSrc5: require('../assets/首页头资源/我的.png'),
      count: 0,
      lastfontName: "",
      lastImgName: "",
      userId:"",
      imageName:"",
      fontName:"",
    }
  },
  methods: {
    click(fontname, img_name) {
      this.userId = store.state.LoadUserId;
      // this.userId = "GCCI-U-Scuqw8q9h398f2ccdd"
      let _this = this;

      if (img_name === 'firstimg') {
        router.push("/MainMeunShowPage/"+ "main")
        _this.imgSrc1 = require('../assets/首页头资源/首页(2).png');
        store.state.sta = "firstimg";

      } else if (img_name === 'secimg') {
        router.push('/GwylioPage')
        _this.imgSrc2 = require('../assets/首页头资源/今日看点(2).png');
        store.state.sta = "secimg";

      } else if (img_name === 'thirimg') {
        router.push("/SubscribedShowPage")
        _this.imgSrc3 = require('../assets/首页头资源/加关注(2).png');
        store.state.sta = "thirimg";

      } else if (img_name === 'forthimg') {
        _this.imgSrc4 = require('../assets/首页头资源/消息(2).png');
        router.push("/TalkRecordPage/"+this.userId+"/"+"message")
        store.state.sta = "forthimg";

      } else if (img_name === 'fifimg') {
        router.push('/PersonalCenterPage')
        _this.imgSrc5 = require('../assets/首页头资源/我的(2).png');
        store.state.sta = "fifimg";
      }

      store.state.fontName = fontname;
    },
  }
}

</script>

<style scoped>
.imgStyle {
  position: absolute;
  width: 32px;
  height: 32px;
  top: 13px;
}


#firstimg {
  left: 217px;
}

#secimg {
  left: 487px;
}

#thirimg {
  left: 1000px;
}

#forthimg {
  left: 1197px;
}

#fifimg {
  left: 1394px;
}

.fontstyle {
  position: absolute;
  color: black;
  font-weight: lighter;
  font-size: 13px;
  letter-spacing: 1px;
  font-family: 华文隶书;
  top: 50px;
}

#first {
  left: 217px;
}

#second {
  left: 487px;
}

#third {
  left: 1000px;
}

#forth {
  left: 1197px;
}

#fifth {
  left: 1380px;
}

#Head {
  position: absolute;
  width: 100%;
  height: 80px;
  border-bottom-style: solid;
  border-bottom-width: 2px;
  border-bottom-color: #592509;
  background-color: #F1F1F1;
  z-index: -1;
}

#logo {
  position: absolute;
  left: 720px;
  height: 10px;
  width: 85px;
  height: 78px;
}
</style>