<template>
  <div id="app" class="myApp">
    <app-header></app-header>
    <div class="main-wrapper">
      <transition name="router-anim">
        <router-view></router-view>
      </transition>
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

@keyframes going {
  from {
    transform: translateX(0px);
  }

  to {
    transform: translateX(-50px);
    opacity: 0;
  }
}

@keyframes coming {
  from {
    transform: translateX(-50px);
    opacity: 0;
  }

  to {
    transform: translateX(0px);
    opacity: 1;
  }
}

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

.router-anim-enter-active {
  animation: coming 0.5s;
  animation-delay: 0.5s;
  opacity: 0;
}

.router-anim-leave-active {
  animation: going 0.3s;
}
</style>
