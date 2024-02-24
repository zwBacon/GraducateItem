<template>
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
</template>

<script>
import router from "@/router";

export default {
  name: "OrderOrProductLeftSide",
  methods:{
    smallRouterChange(){
      router.push("/ShowPayedOrderData")
    },
    _smallRouterChange(){
      router.push("/ShowDepositOrderData")
    }
  }
}
</script>

<style scoped>
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