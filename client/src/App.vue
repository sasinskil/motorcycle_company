<template>
  <div id="app" class="container-fluid">
    <nav v-if="isLogged" class="navbar navbar-expand-lg navbar-dark bg-dark">
      <router-link to="/home" class="navbar-brand">DK Company</router-link>
      <button
        class="navbar-toggler"
        type="button"
        data-toggle="collapse"
        data-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <button @click="logout" class="btn btn-outline-danger ml-auto my-2 my-sm-0" type="submit"><font-awesome-icon icon="sign-out-alt" /></button>
      </div>
    </nav>
    <router-view/>
  </div>
</template>

<script>
  import {mapGetters} from 'vuex'

export default {
  name: 'app',
  computed: mapGetters([
    'isLogged'
  ]),
  
  methods: {
    logout() {
      this.$store.dispatch('logout');
    }
  },
};
</script>


<style lang="scss" scoped>

#app {
  margin: 0 auto;
  padding: 0;
}
</style>
