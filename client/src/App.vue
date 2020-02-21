<template>
  <div id="app" class="myApp">
    <app-header></app-header>
    <div class="main-wrapper">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import appHeader from "@/components/others/appHeader";

export default {
  name: "App",
  components: {
     "app-header": appHeader
  }
};
</script>

<style lang="scss">
@import "@/assets/styles/main.scss";

html {
  box-sizing: border-box;
  scroll-behavior: smooth;
}
*,
*:before,
*:after {
  box-sizing: inherit;
}
body {
  @include bg-effect;
  font-family: $font-family;
  margin: 0;
  padding: 0;
  font-size: 16px;
  @media only screen and (max-width: 640px) {
    font-size: 12px;
  }
}
.myApp {
  text-align: center;
  color: $navy-blue;
}
.main-wrapper {
  padding: 0 1rem;
  width: 100%;
  max-width: 1400px;
  margin: 10rem auto 3rem;
}
</style>
