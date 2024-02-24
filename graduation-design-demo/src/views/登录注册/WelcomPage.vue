<template>
  <HeadLineView/>
  <!--  第一页-->
  <div id="gloabal">
    <img src="../../assets/首页资源/带子.png" id="daizi">
    <div id="mainbody1">
      We turn build houses
    </div>
    <div id="mainbody2">
      into fantsy fams
    </div>
    <div id="circelBtn" @click="signOrEnter">
      ENTER OUR WEBSITE
    </div>
  </div>
  <nut-animate type="float" :loop="true">
    <div id="xiahua">
      <IconFont name="rect-down"></IconFont>
    </div>
  </nut-animate>

  <!--  第二页-->
  <div id="secondPage">
    <img src="../../assets/首页资源/首页1.png" id="shouye1">
    <!--  文字主题  -->
    <p id="secPageTopic">Your Home will in this Expert <br> Buildings</p>
    <!--  文字介绍  -->
    <p id="secPageIntro">Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots<br>in a piece
      of classical Latin literature from 45 BC, making it over 2000 years<br> old. Richard McClintock, a Latin professor
      at Hampden-Sydney College in<br> Virginia.</p>
    <!--  文字小标题  -->
    <p id="secPageSTopic">Updating Items</p>
    <!--  文字小介绍  -->
    <p id="secPageSIntro">4 July, 2023</p>
    <p id="secPageSIntro2">4 July, 2023</p>
    <!--  文字结尾   -->
    <p id="secPageEnd">Making it over 100 years old <br>Build & Fitness</p>
    <p id="secPageEnd2">Making it over 100 years old <br>Build & Fitness</p>
    <!--    按钮-->
    <button id="secPageBtn">Our Activbe History</button>
    <div id="liubai1"></div>
  </div>

  <!--  第三页-->
  <div id="thirdPage">
    <!--     文字标题-->
    <p id="thirPageTitle">WIR CLASSES</p>
    <!--    进度条插画-->
    <span id="thirCircel1"></span>
    <span id="thirCircel3"></span>
    <span id="thirdLongThin"></span>
    <!--    文字介绍-->
    <p id="thirdPageInto">Lorem Ipsum is not simply random text. It has roots in a piece of classical at<br>Hampden-Sydney
      College.</p>
    <!--    选项卡-->
    <button id="thirdPageBtn">ENJOYING</button>
    <span id="fashion">FASHION</span>
    <span id="SOLO">SOLO</span>
    <span id="GROUP">GROUP</span>
    <img src="../../assets/首页资源/第三页图片.png" id="thirdPagePic">
    <div id="ENJOYING">Why are your Enjoy?</div>
    <div id="ENJOYINGInro">All the Lorem Ipsum generators on the Internet tend to repeat predefined<br> chunks as
      necessary, making this the first true Clicking generator on the<br> Internet.
    </div>
    <div id="Time">When comes Enjoy Your Time.</div>
    <div id="TimeDeatil">Saturday-Sunday: 8:00am - 10:00am <br>Monday-Tuesday: 10:00am - 12:00pm <br>Wednesday-Friday:
      3:00pm - 6:00pm
    </div>
    <button id="thirdPageLastBtn">Kontakt Now</button>
  </div>

  <!--  第四页-->
  <div id="fourthPage">
    <!--     文字标题-->
    <p id="fourthPageTitle">Contact Now</p>
    <!--    进度条插画-->
    <span id="fourthCircel1"></span>
    <span id="fourthCircel3"></span>
    <span id="fourthLongThin"></span>
    <!--    文字介绍-->
    <p id="fourthPageInto">Lorem Ipsum is not simply random text. It has roots in a piece of classical at<br>&emsp;&emsp;&emsp;&emsp;&emsp;Hampden-Sydney
      College.</p>
    <!--    文块1-->
    <span id="Mobile">Mobile Number</span>
    <span id="Email">Email Address</span>
    <!--    文块2-->
    <span id="MobileInfo">+187 6654 0917</span>
    <span id="EmailInfo">exam@hermitcrab.com</span>
    <!--    标题1-->
    <p id="fourthTopic">Make An Appoinment</p>
    <!--    输入框1-->
    <input id="input1" placeholder="&emsp;&ensp;Your Name">
    <!--    输入框2-->
    <input id="input2" placeholder="&emsp;&ensp;Your Email">
    <!--    输入框3-->
    <textarea id="input3" placeholder="&emsp;&ensp;Your Message"></textarea>
    <!--    按钮-->
    <button id="fourthBtn">Send Message</button>
    <img src="../../assets/首页资源/LastPic.jpg" id="LastPic">
  </div>

  <!--  结尾-->
  <div id="EndPage">
    <p id="EndPageDetail">©Copyright - All Right Reserved. Designed by<span id="EndPageSpecial">&emsp;&emsp;Theme Family<hr></span>
    </p>
    <img src="../../assets/首页资源/10001.svg" class="icons" id="svg1">
    <img src="../../assets/首页资源/10002.svg" class="icons" id="svg2">
    <img src="../../assets/首页资源/10003.svg" class="icons" id="svg3">
    <img src="../../assets/首页资源/10004.svg" class="icons" id="svg4">
    <img src="../../assets/首页资源/10005.svg" class="icons" id="svg5">
  </div>
</template>

import HeadLineView from "@/components/HeadLineView.vue";

<script>

import router from "@/router";

export default {
  name: "WelcomePage",
  mounted() {
    window.addEventListener('scroll', this.updateScrollTop)
  },
  beforeUnmount() {
    window.removeEventListener('scroll', this.updateScrollTop)
  },
  methods: {
    signOrEnter() {
      router.push("/SignOrLoadPage")
    },
    updateScrollTop() {
      let scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop
      let element = document.getElementById("HeadLine")
      if (scrollTop > 10 && this.flag) {
        this.flag = false
        requestAnimationFrame(function () {
          element.classList.toggle("animate")
        })
      } else if (scrollTop == 0 && !this.flag) {
        this.flag = true
        requestAnimationFrame(function () {
          element.classList.toggle("Backanimate")
        })
      }
    }
  },
  data() {
    return {
      flag: true
    }
  }
}

</script>

<style scoped>
@keyframes btnChange {
  0% {
    border-width: 2px;
    color: black;
  }
  100% {
    background-color: #f39417;
    border-width: 0;
    color: white;
  }
}

#EndPageDetail {
  position: absolute;
  left: 140px;
  top: 34px;
  font-size: 15px;
  color: black;
  letter-spacing: 0.5px;
}

#EndPageSpecial {
  font-size: 17px;
  font-weight: bold;
  color: #F36617;
}

.icons {
  z-index: 1;
  top: 30px;
  position: absolute;
}

#svg1 {
  right: 330px;
  height: 32px;
  width: 32px;
}

#svg2 {
  height: 32px;
  width: 32px;
  right: 380px;
}

#svg3 {
  height: 32px;
  width: 32px;
  right: 430px;
}

#svg4 {
  height: 32px;
  width: 32px;
  right: 480px;
}

#svg5 {
  height: 32px;
  width: 32px;
  right: 280px;
}

#EndPage {
  position: absolute;
  top: 3100px;
  width: 100%;
  height: 91px;
  background-color: #F1F1F1;
}

input::-webkit-input-placeholder {
  /* placeholder颜色  */
  color: #aab2bd;
  /* placeholder字体大小  */
  font-size: 12px;
  /* placeholder位置  */
  text-align: left;
}

textarea::-webkit-input-placeholder {
  /* placeholder颜色  */
  color: #aab2bd;
  /* placeholder字体大小  */
  font-size: 13px;
  /* placeholder位置  */
  text-align: left;
}

#LastPic {
  position: absolute;
  top: 2574px;
  left: 803px;
  width: 596px;
  height: 435px;
}

#fourthBtn {
  position: absolute;
  top: 2956px;
  left: 103px;
  font-size: 15px;
  letter-spacing: 0.5px;
  font-weight: bold;
  color: white;
  width: 496px;
  height: 45px;
  border-radius: 4px;
  background-color: #f39417;
}

#input1 {
  position: absolute;
  top: 2714px;
  left: 103px;
  width: 500px;
  background-color: rgb(241, 248, 255);
  height: 30px;
}

#input2 {
  position: absolute;
  top: 2770px;
  left: 103px;
  width: 500px;
  background-color: rgb(241, 248, 255);
  height: 30px;
}

#input3 {
  position: absolute;
  top: 2826px;
  left: 103px;
  width: 500px;
  background-color: rgb(241, 248, 255);
  height: 100px;
}

#Mobile {
  position: absolute;
  top: 2574px;
  left: 103px;
  color: #1C1C1FFF;
  font-weight: bold;
  font-family: "Microsoft YaHei UI";
  font-size: 18px;
  letter-spacing: 0.5px;
}

#MobileInfo {
  position: absolute;
  top: 2614px;
  left: 103px;
  color: #1C1C1FFF;
  font-family: "Microsoft YaHei UI";
  font-size: 16px;
  letter-spacing: 0px;
}

#Email {
  position: absolute;
  top: 2574px;
  left: 393px;
  color: #1C1C1FFF;
  font-weight: bold;
  font-family: "Microsoft YaHei UI";
  font-size: 18px;
  letter-spacing: 0.5px;
}

#EmailInfo {
  position: absolute;
  top: 2614px;
  left: 393px;
  color: #1C1C1FFF;
  font-family: "Microsoft YaHei UI";
  font-size: 16px;
}

#fourthTopic {
  position: absolute;
  top: 2664px;
  left: 103px;
  color: #000000;
  font-weight: bold;
  font-family: "Microsoft YaHei UI";
  font-size: 21px;
  letter-spacing: 0.5px;
}

#fourthPageTitle {
  z-index: -1;
  position: absolute;
  top: 2370px;
  left: 683px;
  color: #f39417;
  font-weight: bolder;
  font-family: "Microsoft YaHei UI";
  font-size: 35px;
  letter-spacing: 1px;
}

#fourthPageInto {
  position: absolute;
  top: 2474px;
  left: 563px;
  color: rgb(78, 78, 105);
  font-family: "Microsoft YaHei UI";
  font-size: 15px;
  letter-spacing: 0.5px;
}

#fourthCircel1 {
  position: absolute;
  border-radius: 100px;
  top: 2434px;
  left: 763px;
  width: 11px;
  height: 11px;
  background-color: #9d211f;
  font-weight: bolder;
}

#fourthCircel3 {
  position: absolute;
  border-radius: 100px;
  top: 2434px;
  left: 783px;
  width: 11px;
  height: 11px;
  background-color: #9d211f;
  font-weight: bolder;
}

#fourthLongThin {
  position: absolute;
  border-radius: 100px;
  top: 2434px;
  left: 803px;
  width: 70px;
  height: 8px;
  background-color: #9d211f;
  font-weight: bolder;
}

#ENJOYING {
  position: absolute;
  top: -450px;
  left: -820px;
  font-size: 20px;
  font-weight: bold;
  color: #312e2e;
}

#ENJOYINGInro {
  position: absolute;
  top: -400px;
  left: -820px;
  font-size: 14px;
  letter-spacing: 0.5px;
  color: rgb(95, 95, 95);
}

#Time {
  position: absolute;
  top: -290px;
  left: -820px;
  font-size: 20px;
  font-weight: bold;
  color: #312e2e;
}

#TimeDeatil {
  position: absolute;
  top: -240px;
  left: -820px;
  font-size: 14px;
  letter-spacing: 0.5px;
  color: rgb(95, 95, 95);
}

#thirPageTitle {
  position: absolute;
  top: -780px;
  left: -250px;
  color: rgb(53, 85, 146);
  font-weight: bolder;
  font-family: "Microsoft YaHei UI";
  font-size: 35px;
  letter-spacing: 1px;
}

#thirdPageLastBtn {
  position: absolute;
  top: -90px;
  left: -820px;
  font-size: 15px;
  letter-spacing: 0.5px;
  font-weight: bold;
  color: white;
  width: 180px;
  height: 45px;
  border-radius: 4px;
  background-color: rgb(53, 85, 146);
}

#thirdPagePic {
  position: absolute;
  top: -500px;
  left: -30px;
  width: 520px;
}

#thirdPageBtn {
  position: absolute;
  border-radius: 15px;
  font-weight: bold;
  top: -560px;
  left: -550px;
  width: 170px;
  height: 50px;
  color: white;
  background-color: rgb(53, 85, 146);
  font-size: 18px;
}

#fashion {
  position: absolute;
  border-radius: 15px;
  top: -555px;
  left: -275px;
  color: rgb(13, 110, 253);
  font-size: 24px;
}

#SOLO {
  position: absolute;
  border-radius: 15px;
  top: -555px;
  left: -65px;
  color: rgb(13, 110, 253);
  font-size: 24px;
}

#GROUP {
  position: absolute;
  border-radius: 15px;
  top: -555px;
  left: 120px;
  color: rgb(13, 110, 253);
  font-size: 24px;
}

#thirdPageInto {
  position: absolute;
  top: -680px;
  left: -420px;
  color: rgb(78, 78, 105);
  font-family: "Microsoft YaHei UI";
  font-size: 15px;
  letter-spacing: 0.5px;
}

#thirCircel1 {
  position: absolute;
  border-radius: 100px;
  top: -710px;
  left: -150px;
  width: 11px;
  height: 11px;
  background-color: #f39417;
  font-weight: bolder;
}

#thirCircel3 {
  position: absolute;
  border-radius: 100px;
  top: -710px;
  left: -130px;
  width: 11px;
  height: 11px;
  background-color: #f39417;
  font-weight: bolder;
}

#thirdLongThin {
  position: absolute;
  border-radius: 100px;
  top: -708px;
  left: -110px;
  width: 70px;
  height: 8px;
  background-color: #f39417;
  font-weight: bolder;
}

#thirdPage {
  position: absolute;
  top: 1450px;
  right: -100px;
  width: 1000px;
  height: 800px;
  border-width: 900px;
  border-top: 850px solid #F1F1F1;
  border-left: 300px solid transparent;
  border-right: 100px solid transparent;
}

#liubai1 {
  height: 60px;
  width: 100%;
  position: absolute;
  top: 1330px;
  left: 780px;
}

#secPageBtn {
  z-index: -1;
  border-radius: 10px;
  font-size: 14px;
  font-weight: bold;
  font-family: "Alibaba PuHuiTi R";
  position: absolute;
  top: 1300px;
  left: 780px;
  width: 200px;
  height: 60px;
  color: #f6f6f6;
  text-align: center;
  letter-spacing: 1px;
  background-color: #f39417;
}

#secPageEnd {
  position: absolute;
  top: 1180px;
  left: 780px;
  font-weight: bold;
  color: rgba(65, 60, 60, 0.98);
  font-weight: bold;
  font-family: "华文中宋";
  font-size: 20px;
  letter-spacing: 1px;
}

#secPageEnd2 {
  position: absolute;
  top: 1180px;
  left: 1180px;
  font-weight: bold;
  color: rgba(65, 60, 60, 0.98);
  font-weight: bold;
  font-family: "华文中宋";
  font-size: 20px;
  letter-spacing: 1px;
}

#secPageSIntro {
  position: absolute;
  top: 1150px;
  left: 780px;
  color: rgba(119, 117, 116, 0.99);
  font-family: "华文中宋";
  font-size: 15px;
  letter-spacing: 1px;
}

#secPageSIntro2 {
  position: absolute;
  top: 1150px;
  left: 1180px;
  color: rgba(119, 117, 116, 0.99);
  font-family: "华文中宋";
  font-size: 15px;
  letter-spacing: 1px;
}

#secPageTopic {
  position: absolute;
  top: 820px;
  left: 780px;
  color: #f39417;
  font-weight: bold;
  font-family: "华文中宋";
  font-size: 45px;
  letter-spacing: 1px;
}

#secPageIntro {
  position: absolute;
  top: 970px;
  left: 780px;
  color: rgba(119, 117, 116, 0.99);
  font-family: "华文中宋";
  font-size: 15px;
  letter-spacing: 1px;
}

#secPageSTopic {
  position: absolute;
  top: 1090px;
  left: 780px;
  font-weight: bold;
  color: rgba(7, 7, 7, 0.98);
  font-weight: bold;
  font-family: "华文中宋";
  font-size: 25px;
  letter-spacing: 1px;
}

#shouye1 {
  width: 490px;
  height: 520px;
  position: absolute;
  top: 800px;
  left: 120px;
}

#xiahua {
  position: absolute;
  bottom: 150px;
  left: 730px;
  z-index: 1;
}

#circelBtn {
  border-radius: 20px;
  border-style: solid;
  font-size: 13px;
  font-weight: bold;
  font-family: "Alibaba PuHuiTi R";
  position: absolute;
  left: 650px;
  top: 470px;
  width: 170px;
  height: 30px;
  z-index: 1;
  color: black;
  text-align: center;
  border-width: 2px;
  letter-spacing: 1px;
}

#circelBtn:hover {
//background-color: #f39417; //border-width: 0; //color: white; animation: btnChange 0.5s forwards;
}

#gloabal {
  width: 1650px;
  height: 747px;
  background-color: #F1F1F1;
}

#daizi {
  width: 50px;
  height: 70px;
  position: absolute;
  top: 200px;
  left: 0;
}

#mainbody1 {
  font-weight: bold;
  font-size: 80px;
  position: absolute;
  left: 432px;
  font-family: "华文楷体";
  top: 220px;
  width: 800px;
  height: 120px;
  z-index: 1;
  color: #F36617;
  letter-spacing: 2px;
}

#mainbody2 {
  font-weight: bold;
  font-size: 75px;
  position: absolute;
  left: 510px;
  font-family: "华文楷体";
  top: 320px;
  width: 700px;
  height: 100px;
  z-index: 1;
  color: #F36617;
  letter-spacing: 2px;
}

#mainbody2 {

}
</style>