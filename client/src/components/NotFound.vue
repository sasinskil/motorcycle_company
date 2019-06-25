<template>
  <div class="login container mt-2">
    <h3>Sorry...something goes wrong :(</h3>
    <br/>
      <router-link v-if="isLogged" to="/home">Home</router-link>
      <router-link v-else to="/login">Log in</router-link>
 </div>
</template>

<script>

import {mapGetters} from 'vuex'


export default {
  name: "NotFound",

   computed: mapGetters([
    'isLogged'
  ]),
};
</script>

<style lang="scss" scoped>
</style>
