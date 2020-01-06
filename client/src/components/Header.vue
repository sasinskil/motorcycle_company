<template>
  <nav id="headerBlogs" v-if="isLogged" class="main-container--header navigation">
      <router-link to="/home" class="">Logo</router-link>
      <ul class="navigation__list">
           <li class="navigation__item"><router-link class="navigation__link" to="/">Customers</router-link></li>
           <li class="navigation__item"><router-link class="navigation__link" to="/add-blog">Employees</router-link></li>
           <li class="navigation__item"><router-link class="navigation__link" to="/add-blog">Motorcycles</router-link></li>
           <li class="navigation__item"><router-link class="navigation__link" to="/add-blog">Test drives</router-link></li>
           <li class="navigation__item"><router-link class="navigation__link" to="/add-blog">Services</router-link></li>
           <li class="navigation__item"><router-link class="navigation__link" to="/add-blog">Transactions</router-link></li>
           <li class="navigation__item"><button @click="logout" class="btn btn-logout" type="submit"><font-awesome-icon icon="sign-out-alt" /></button></li>
      </ul>
  </nav>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  name: "header",
  computed: mapGetters([
    'isLogged',
  ]),
  methods: {
    logout() {
      this.$store.dispatch('logout');
    },
  },
};
</script>

<style lang="scss" scoped>
.navigation {
    box-shadow: 0px 6px 16px rgba(24, 41, 67, 0.2);
    background: #2c3e50;
    padding: 1rem 0;
    display: flex;

    &__list {
        list-style-type: none;
        display: flex;
        justify-content: flex-start;
        align-items: center;
    }
    &__item {
        padding: 0.5rem 0;
    }
    &__item + &__item{
        margin-left: 2rem;
    }
    &__link {
        text-decoration: none;
        color: #fff;
        font-weight: bold;
        position: relative;
        
        &::after {
            position: absolute;
            content: "";
            width: 0%;
            height: 0.1rem;
            background: #fff;
            bottom: -10px;
            left: 0;
            transition: width .4s ease-in-out;
        }
    }
}
.router-link-exact-active::after {
    width: 100%;
}
</style>
