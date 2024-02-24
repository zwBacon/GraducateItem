<template>
  <HeadLineViewForMain/>

  <!--  主页房产-->
  <div id = "mainMeunHouses">

    <div class="grid-container">
      <div v-for="(item, index) in videoInfos" :key="index" class="grid-item">
        <el-card class="ShowCardClass" >
          <nut-video :source="item.videoSrc" :options="state.options" @play="play" @pause="pause" @playend="playend" class="videos">
          </nut-video>
        </el-card>
        <div class="videInfo" @click="clickOpenVideo(item.videoSrc,item.videoTitle)">
          <img :src="this.stringplus + item.publishImg" class="publisherImg">
          <span class="publisherName">{{item.publishName}}</span>
          <span class="publisherDegree"><el-icon><CollectionTag /></el-icon>{{item.hotDegree}}</span>
          <p class="videoTitle">{{item.videoTitle}}</p>
          <p class="videoSubTitle">{{item.videoSubTitle}}</p>
        </div>
      </div>
    </div>
  </div>

  <el-dialog v-model="videoDialogVisible" :title=title width="700">
    <nut-video :source="videoSrc" :options="state.options2" @play="play" @pause="pause" @playend="playend" class="dislogVideos">
    </nut-video>
  </el-dialog>

</template>

<script setup>

import { reactive} from 'vue';
const state = reactive({
  source: {
    src: 'https://696-12.vod.tv.itc.cn/sohu/v1/TmxATmPGq6IXN6kAfD6Bo8E3hSf4eACCW2cI9OHJKBgXpxWsm3doRr.mp4?k=Fjm1Mp&p=jWlvzSxioSXA0SrAoEO349xIWhysfGNtRYAtRYbsfYdtfGWsfhWS0MocRYo70ScAZMx4gGo70O2CfMXS0pbcWhoGgY2Uuho7oB2OwmN7ZD6S0mbcyM0GY&r=TUldziJCtpCmhWlvsmCU0SsWtWaizY&q=OpCChW7IRYodRD6svmfCyY2sWJ1HfJbO5G6tWhX4Rho2ZDvXfFNXWFyOwm4cWJWXr&nid=696',
    type: 'video/mp4'
  },
  options: {
    controls: true
  },
  options2: {
    autoplay: true,
    controls: true
  }
});
</script>

<script>
import HeadLineViewForMain from "@/components/HeadLineViewForMain.vue";
import axios from "axios";

export default {
  name: "GwylioPage",
  components:{
    HeadLineViewForMain
  },
  mounted() {
    this.selectAllVideos();
  },
  data(){
    return{
       videoInfos:[],
       videoDialogVisible:false,
       videoSrc:"",
       title:"",
       stringplus: "data:image/png;base64,",
    }
  },
  methods:{
    selectAllVideos(){
      let _this = this;
      axios({
        method: "get",
        url: "http://localhost:4000/videoInfoController/selectAllVideoInfos"
      }).then(function (resp) {
          _this.videoInfos = resp.data
      })
    },
    clickOpenVideo(videoSrc,videoTitle){
      this.videoSrc = videoSrc
      this.title = videoTitle
      this.videoDialogVisible = true
    },
    play(elm) {
      console.log('play', elm);
    },
    pause(e) {
      console.log('pause',e);
    },
    playend(e) {
      console.log('pause',e);
    },
  }
}
</script>

<style scoped>
.videoTitle{
  position: absolute;
  font-weight: bolder;
  font-size: 22px;
  top: -5px;
  left: 5px;
}
.videoSubTitle{
  position: absolute;
  font-size: 15px;
  color: #c2c2c2;
  text-align: left;
  top: 28px;
  left: 5px;
}
.videoTitle:hover{
  color: #c50a0a;
}
.publisherName{
  position: relative;
  left: -87px;
  top: 65px;
  font-size: 13px;
  color: #a18d62;
}
.publisherDegree{
  position: relative;
  left: 85px;
  top: 65px;
  font-size: 13px;
  color: black;
}
.publisherImg{
  position: relative;
  width: 24px;
  height: 24px;
  border-radius: 30px;
  left: -100px;
  top: 70px;
}
.grid-container {
  position: absolute;
  top: 120px;
  left: 110px;
  display: grid;
  grid-template-columns: repeat(3, 1fr); /* 四列布局 */
  grid-gap: 125px; /* 设置项目之间的间距 */
  grid-row-gap:100px;
  z-index: -1;
}

.videos{
  position: relative;
  left: -19px;
  top: -24px;
  width: 358px;
  height: 225px;
}

.videInfo{
  position: relative;
  left: 0;
  top: 10px;
  width: 358px;
  height: 80px;
  z-index: 3;
}
.grid-item {
  width: 354px;
  height: 325px;
  text-align: center;
  transition: transform 0.3s, box-shadow 0.4s;
  border-radius: 30px;
}
.grid-item:hover{
  background-color: #643e3e;
  border-radius: 30px;
  transform: scale(1.11); /* 缩放，产生凸出效果 */
  box-shadow: 0 0 20px #9B2525A0;
}

.ShowCardClass{
  height: 220px;
  border-radius: 23px;
  box-shadow: 1px 1px 2px 0px #37373A6B;
}
</style>