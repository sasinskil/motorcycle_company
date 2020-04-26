<template>
  <div class="wrapper">
    <div class="login-box">
      <div class="company">
        <DuosKolosLogoTrans />
        <h2 ref="companyName" class="company__name">Duos Kołos Sp. z.o.o</h2>
      </div>
      <div class="login">
        <div class="loginPanel">
          <h2 class="form-title">Logowanie</h2>
          <form
            method="POST"
            class="form"
            id="login-form"
            @submit.prevent="signin"
          >
            <div class="form-group">
              <label for="username">
                <font-awesome-icon class="user-icon icon" icon="user" />
              </label>
              <input
                v-model="credential.username"
                type="text"
                class="form-input"
                id="username"
                placeholder="Nazwa użytkownika"
                autofocus
              />
            </div>
            <div class="form-group">
              <label for="password">
                <font-awesome-icon class="lock-icon icon" icon="lock" />
              </label>
              <input
                v-model="credential.password"
                type="password"
                class="form-input"
                id="password"
                placeholder="Hasło"
              />
            </div>
            <div class="form-group form-button">
              <span class="form__errors" v-if="validErrors.length">
                <span class="form__errors--info"
                  >Popraw następujące błędy:</span
                >
                <ul class="form__errors-list">
                  <li
                    class="form__errors-item"
                    v-for="error in validErrors"
                    :key="error"
                  >
                    {{ error }}
                  </li>
                </ul>
              </span>
              <button class="btn btn-dark loginBtn" @click.prevent="signin">
                Zaloguj
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
    <InfoModal
      v-if="showModal"
      :headerText="modalHeaderContent"
      :bodyText="modalBodyContent"
      @confirm="close"
    />
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import InfoModal from "@/components/modal/InfoModal";
import DuosKolosLogoTrans from "@/components/components_others/home/DuosKolosLogoTrans.vue";
import { gsap } from "gsap";

export default {
  name: "login",
  components: {
    InfoModal,
    DuosKolosLogoTrans
  },
  data() {
    return {
      credential: {
        username: "",
        password: ""
      },
      validErrors: [],
      modalHeaderContent: "Uwaga",
      modalBodyContent: "Coś poszło nie tak, sprawdź błędy!",
      showModal: false
    };
  },
  methods: {
    login() {
      this.$store.dispatch("login", this.credential);
    },
    signin() {
      const isValid = this.checkForm();
      if (isValid) {
        this.login();
        setTimeout(() => {
          if (this.loginError) {
            this.showModal = true;
            this.validErrors.push("Niepoprawny użytkownik lub hasło!");
          }
        }, 100);
      } else {
        this.showModal = true;
      }
    },
    checkForm() {
      this.validErrors = [];
      if (this.credential.username && this.credential.password) {
        return true;
      }
      if (!this.credential.username) {
        this.validErrors.push("Nazwa użytkownika jest wymagana!");
      }
      if (!this.credential.password) {
        this.validErrors.push("Hasło jest wymagane!");
      }
    },
    close() {
      this.showModal = false;
    }
  },
  computed: {
    ...mapGetters(["loginError"])
  },
  mounted() {
    const { companyName } = this.$refs;
    const tl = gsap.timeline({ defaults: { ease: "power3.inOut" } });
    tl.from(companyName, 2, {
      opacity: 0,
      y: 100
    });
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

.wrapper {
  position: fixed;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  background-image: url("../../assets/images/background-photo.jpg");
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center center;
}

.login-box {
  margin: 0 auto;
  width: 100%;
  height: 100%;
  padding: 0 0.5rem;
  max-width: 950px;
  display: flex;
  justify-content: center;
  align-items: center;

  @media only screen and(max-width: 920px) {
    align-items: center;
  }
}

.company {
  color: $white;
  width: 100%;
  margin-top: 20rem;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;

  @media only screen and(max-width: 920px) {
    display: none;
  }

  &__name {
    margin: 1rem 0 0;
    width: 100%;
    font-size: 2rem;
    letter-spacing: 0.2rem;
    text-align: left;
  }
}

path.circle-left {
  transform-origin: 22% 78%;
}

path.circle-right {
  transform-origin: 86% 78%;
}

.login {
  width: 100%;
  padding: 0.5rem;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.loginPanel {
  display: flex;
  flex-direction: column;
  justify-content: center;
  text-align: center;
  background-color: $white;
  box-shadow: $default-shadow-hover;
  margin: 2rem auto 0;
  width: 100%;
  max-width: 390px;
  padding: 2rem 3.5rem 3rem;

  @media only screen and(max-width: 420px) {
    padding: 1rem 3rem 2rem;
  }
}

.form {
  margin: 1rem 0;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  &__errors {
    font-size: 0.9rem;
    text-align: left;
    margin-top: 2rem;
    color: $bright-red;
    &-list {
      margin: 0;
      padding: 0.5rem 0 0 1.3rem;
    }
  }
}
.form-group {
  width: 100%;
  position: relative;
}
.form-button {
  display: flex;
  flex-direction: column;
}
.form-register {
  margin-top: 1rem;
}
label {
  position: absolute;
  top: 39px;
  left: -5px;

  @media only screen and (max-width: 640px) {
    top: 42px;
  }
}
.icon {
  font-size: 13px;
}
.form-title {
  margin: 2rem 0 2rem 0;
  font-size: 2rem;
  width: 100%;
}
.form-input {
  margin-top: 2rem;
  width: 100%;
  padding: 0.7rem 1.5rem;
  border: none;
  border-bottom: 1px solid $light-black;
  background: none;
  outline: none;
  transition: border-color 0.3s ease-in-out;
  &:focus {
    border-bottom-color: $black;
  }
}
.loginBtn {
  margin-top: 4rem;
  @include default-button($navy-blue);
  background: $navy-blue;
  color: $white;
  transition: box-shadow 0.3s ease-in-out;
  &:hover {
    box-shadow: $default-shadow-hover;
  }
}
</style>
