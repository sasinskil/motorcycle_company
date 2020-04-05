<template>
  <header v-if="checkIsLogged" class="header">
    <nav class="navigation">
      <span class="navigation__logo">
        <router-link tag="span" class="navigation__logo" to="/">
          <DuosKolosLogo />
        </router-link>
      </span>
      <button @click="showMenu" class="burger-show">
        <span class="burger-show--box">
          <span class="burger-show--inner"></span>
        </span>
      </button>
      <ul class="navigation__list">
        <button @click="hideMenu" class="burger-hide">
          <span class="burger-hide--box">
            <span class="burger-hide--inner"></span>
          </span>
        </button>
        <li class="navigation__item">
          <router-link class="navigation__link" to="/">
            <font-awesome-icon class="icon home-icon" icon="home" />
          </router-link>
        </li>
        <li class="navigation__item" v-for="route in links" :key="route.name">
          <router-link class="navigation__link" :to="`/${route.pathTo}`"
            >{{route.name}}
          </router-link>
        </li>
        <li class="current-user">
          <font-awesome-icon class="icon user" icon="user" />
          <ul class="current-user__about-list">
            <li class="current-user__about-item">
              <strong>
                <font-awesome-icon class="icon user-icon" icon="id-card" />
              </strong>
              {{ user.username }}
            </li>
            <li class="current-user__about-item">
              <ul class="current-user__about-list--roles">
                <li
                  class="current-user__about-item--role"
                  v-for="role in user.authorities"
                  :key="role"
                >
                  <p class="role">{{ role }}</p>
                </li>
              </ul>
            </li>
            <li
              v-if="checkIsAdmin"
              class="current-user__about-item current-user__about-item--admin"
            >
              <button @click="toRegister" class="current-user__about-item--btn">
                Nowy użytkownik
              </button>
            </li>
          </ul>
        </li>
        <button class="navigation__logout" @click="logout">
          Wyloguj
          <font-awesome-icon class="icon" icon="door-open" />
        </button>
      </ul>
    </nav>
  </header>
</template>

<script>
// eslint-disable-next-line no-unused-vars
import { mapGetters } from "vuex";
import DuosKolosLogo from "@/components/components_others/home/DuosKolosLogo.vue";
import { routes } from "@/variables";

export default {
  name: "appHeader",
  components: {
    DuosKolosLogo
  },
  data() {
    return {
      user: {},
      links: routes,
    };
  },
  computed: {
    checkIsLogged() {
      return this.$store.getters.isLogged;
    },
    checkIsAdmin() {
      return this.$store.getters.isAdmin;
    },
    menuSelector() {
      return document.querySelector(".navigation__list");
    },
    linkSelector() {
      return document.querySelector(".navigation__link"); 
    }
  },
  created() {
    this.user = this.$store.getters.getCurrentUser;

    document.addEventListener("keyup", e => {
      if (
        e.keyCode === 27 &&
        this.menuSelector.classList.contains("navigation__list--visible")
      ) {
        this.hideMenu();
      }
    });
  },
   watch: {
    '$route' () {
      if(this.menuSelector.classList.contains("navigation__list--visible")) {
        this.hideMenu();
      }
    },
   },
  methods: {
    showMenu() {
      this.menuSelector.classList.add("navigation__list--visible");
    },
    hideMenu() {
      this.menuSelector.classList.remove("navigation__list--visible");
    },
    logout() {
      this.$store.dispatch("logout");
    },
    toRegister() {
      this.$router.push("/register");
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
.user {
  width: 23px;
  height: 23px;
}

.current-user {
  margin: 0 0 0 3rem;
  position: relative;

  &:hover > &__about-list {
    transform: translateX(9px);
    opacity: 1;
    visibility: visible;
    @media only screen and(max-width: 1300px) {
      transform: translateX(80px);
    }
  }

  &__about-list {
    background: $navy-blue;
    color: $white;
    padding: 1.5rem 1rem;
    position: absolute;
    left: -200px;
    width: 100%;
    min-width: 220px;
    list-style-type: none;
    visibility: hidden;
    transform: translateX(-50px);
    opacity: 0;
    text-align: left;
    box-shadow: $default-shadow-hover;
    transition: transform 0.3s ease-in-out, opacity 0.3s ease-in-out;

    @media only screen and(max-width: 1300px) {
      top: -80px;
    }

    &--roles {
      list-style-type: none;
      padding: 0 0 0 1.5rem;
      margin: 0;
    }

    &--roles p {
      margin: 0;
      padding: 0;
    }
  }

  &__about-item {
    border-bottom: 1px solid $darken-grey;
    padding-bottom: 0.3rem;

    strong {
      display: inline-block;
    }

    &--role {
      position: relative;

      &::before {
        display: block;
        content: "-";
        position: absolute;
        top: 0;
        left: -17px;
      }
    }

    &--admin {
      border-bottom: none;
      padding-bottom: 0;
      padding-top: 0.5rem;
    }

    &--btn {
      @include default-button($white);

      &:hover {
        color: $navy-blue;
      }
    }
  }

  &__about-item + &__about-item {
    margin-top: 1rem;
  }
}

.user-icon {
  margin-right: 0.4rem;
}

.home-icon {
  font-size: 1.2rem;
  width: 23px;
  height: 20px;
}

@mixin hamburger-line {
  width: 100%;
  height: 0.2rem;
  background-color: $navy-blue;
  position: absolute;
}
.header {
  padding: 0.5rem;
  position: fixed;
  z-index: 9997;
  top: 0;
  left: 0;
  width: 100%;
  background: $white;
  box-shadow: $default-shadow;
}

.header::before {
  position: absolute;
  content: "";
  top: 0;
  left: 0;
  display: block;
  width: 100%;
  height: 0.1rem;
  background-color: $navy-blue;
}

.navigation {
  position: relative;
  width: 100%;
  max-width: 1400px;
  margin: 0 auto;
  padding: 0 1rem;
  display: flex;
  justify-content: space-between;
  align-items: center;

  &__list {
    padding: 0;
    margin: 0;
    list-style-type: none;
    display: flex;
    justify-content: space-between;
    align-items: center;
    transform: translateX(0px);
    transition: transform 0.3s ease-in-out;
  }

  &__logo {
    cursor: pointer;
  }

  &__link {
    text-decoration: none;
    color: $navy-blue;
    font-weight: bold;
    position: relative;

    &::after {
      position: absolute;
      content: "";
      width: 0%;
      height: 0.1rem;
      background: $navy-blue;
      bottom: -10px;
      left: 0;
      transition: width 0.4s ease-in-out;
    }
  }
}

.router-link-exact-active::after {
  width: 100%;
}

.burger-show {
  cursor: pointer;
  padding: 0.2rem;
  border: none;
  background-color: transparent;

  &--box {
    width: 30px;
    height: 20px;
    display: inline-block;
    position: relative;
  }

  &--inner {
    @include hamburger-line;
    left: 0;
    top: 50%;
    transform: translateY(-50%);

    &::before,
    &::after {
      @include hamburger-line;
      left: 0;
      content: "";
    }

    &::before {
      top: -10px;
    }

    &::after {
      top: 10px;
    }
  }
}

.burger-hide {
  position: absolute;
  top: 20px;
  right: 15px;
  cursor: pointer;
  padding: 0.6rem;
  border: none;
  background-color: transparent;

  &--box {
    width: 30px;
    height: 25px;
    display: inline-block;
    position: relative;
  }

  &--inner {
    @include hamburger-line;
    left: 0;
    top: 50%;
    transform: translateY(-50%);
    background-color: transparent;

    &::before,
    &::after {
      @include hamburger-line;
      left: 0;
      content: "";
    }

    &::before {
      top: -10px;
      transform: translateY(10px) rotate(45deg);
    }

    &::after {
      top: 10px;
      transform: translateY(-10px) rotate(-45deg);
    }
  }
}

.navigation__item + .navigation__item {
  margin-left: 20px;
}

.navigation__logout {
  @include default-button($navy-blue);
  border: 2px solid $navy-blue;
  margin-left: 3rem;
}

@media (min-width: 1301px) {
  .burger-show,
  .burger-hide {
    display: none;
  }
}

@media (max-width: 1300px) {
  .burger-show,
  .burger-hide {
    display: block;
  }

  .navigation {
    max-width: 100vw;
    text-align: left;
  }

  .navigation__logo {
    width: 100%;
    text-align: left;
  }

  .navigation__list {
    background: $white;
    padding: 1rem 0;
    margin: 0;
    position: fixed;
    transform: translateX(-1300px);
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    box-shadow: $default-shadow;
  }

  .navigation__list--visible {
    transform: translateX(0px);
  }

  .navigation__item + .navigation__item {
    margin-left: 0;
  }

  .navigation__item {
    padding: 2rem 0 0 0;
  }

  .navigation__logout {
    margin: 2rem 0 0 0;
  }

  .current-user {
    margin: 2rem 0 0 0;
  }
}
</style>
