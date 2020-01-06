<template>
  <div id="app" class="app">
    <app-header></app-header>
    <router-view/>
  </div>
</template>

<script>
import header from '@/components/Header.vue';

export default {
  name: 'app',
  components: {
    'app-header': header,
  },
};
</script>


<style lang="scss">

* {
  box-sizing: border-box;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

body {
  margin: 0;
  padding: 0;
}

.app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
}


@media only screen and (max-width: 640px) {
  body {
    font-size: 12px;
  }
}
</style>
