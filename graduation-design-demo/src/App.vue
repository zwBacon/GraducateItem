<template>
<router-view></router-view>
</template>

<script>
import router from "@/router";

export default {
  name: 'App',
  mounted() {
      router.push("/WelcomPage")
    // router.push("/MainMeunShowPage/"+ "main");
    // router.push("/NewProductConfirmPage");
  }
}
</script>

<style>

</style>
