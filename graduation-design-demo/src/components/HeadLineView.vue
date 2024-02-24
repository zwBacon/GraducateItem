<template>
  <div id="HeadLine" ref="HeadLine">
    <img src="../assets/首页资源/logo.png" id="logo" class="details">
    <div id="title"><span id="insideTitle">HERMIT</span> CRAB</div>
    <p class="details" id="HOME">HOME</p>
    <p class="details" id="ABOUT">ABOUT</p>
    <p class="details" id="CLASS">CLASS</p>
    <p class="details" id="SERVICE">SERVICE</p>
    <p class="details" id="TRAINER">TRAINER</p>
    <p class="details" id="REVIEW">REVIEW</p>
    <p class="details" id="BLOG">BLOG</p>
    <p class="details" id="CONTACT">CONTACT</p>

  </div>

</template>

<script>
export default {
  name: "HeadLineView",
  mounted() {

  },
  methods: {},

}
</script>

<style>
@keyframes BacCorChange {
  0% {
    background-color: #F1F1F1;
  }
  100% {
    background-color: #F36617;
  }
}

@keyframes BackBacCorChange {
  0% {
    background-color: #F36617;
  }
  100% {
    background-color: #F1F1F1;
  }
}
</style>

<style scoped>
#insideTitle {
  color: #FF8000;
}

.animate {
  animation: BacCorChange 2s forwards;
  will-change: transform;
}

.Backanimate {
  animation: BackBacCorChange 2s forwards;
  will-change: transform;
}

#logo {
  position: absolute;
  top: 18px;
  left: 70px;
  width: 95px;
  height: 70px;
  z-index: 1;
}

#HeadLine {
  position: fixed;
  width: 100%;
  height: 100px;
  z-index: 1;
//background-color: #FF8000; will-change: animate;
  background-color: #F1F1F1;
}

#title {
  position: absolute;
  top: 37px;
  left: 178px;
  color: black;
  font-weight: bold;
  font-family: "华文行楷";
  font-size: 20px;
  letter-spacing: 1px;
}

.details {
  position: absolute;
  color: black;
  font-weight: bold;
  font-family: "华文中宋";
  font-size: 15px;
  top: 40px;
}

#HOME {
  left: 800px;
  color: #e51616;
}

#ABOUT {
  left: 880px;
}

#CLASS {
  left: 960px;
}

#SERVICE {
  left: 1040px;
}

#TRAINER {
  left: 1130px;
}

#REVIEW {
  left: 1220px;
}

#BLOG {
  left: 1300px;
}

#CONTACT {
  left: 1370px;
}
</style>