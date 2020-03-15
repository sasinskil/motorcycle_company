<template>
  <div class="register">
    <div class="registerPanel">
      <h2 class="form-title">Rejestracja</h2>
      <form
        method="POST"
        class="form"
        id="register-form"
        @submit.prevent="signup"
      >
        <div class="form-group">
          <label class="form-group__label" for="name">
            <font-awesome-icon class="user-icon icon" icon="user-tie" />
          </label>
          <input
            v-model="credential.name"
            type="text"
            class="form-input"
            id="name"
            placeholder="Imię"
            autofocus
          />
        </div>
        <div class="form-group">
          <label class="form-group__label" for="username">
            <font-awesome-icon class="lock-icon icon" icon="user" />
          </label>
          <input
            v-model="credential.username"
            type="text"
            class="form-input"
            id="username"
            placeholder="Nazwa użytkownika"
          />
        </div>
        <div class="form-group">
          <label class="form-group__label" for="email">
            <font-awesome-icon class="lock-icon icon" icon="envelope" />
          </label>
          <input
            v-model="credential.email"
            type="email"
            class="form-input"
            id="email"
            placeholder="Email"
          />
        </div>
        <div class="form-group">
          <label class="form-group__label" for="password">
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
         <div class="form-group form-group__checkbox">
          <div class="form-group__checkbox-wrapper">
            <label class="form-group__checkbox--label" for="admin">Administrator</label>
            <input
              type="checkbox"
              checked
              class="form-input form-input--checkbox form-input__admin"
              name="admin"
              id="admin"
              value="admin"
              v-model="credential.role"
            />
          </div>
        </div>
        <div class="form-group form-button">
          <span class="form__errors" v-if="errors.length">
            <span class="form__errors--info">Popraw następujące błędy:</span>
            <ul class="form__errors-list">
              <li
                class="form__errors-item"
                v-for="error in errors"
                :key="error"
              >
                {{ error }}
              </li>
            </ul>
          </span>
          <button class="btn btn-dark registerBtn" @click.prevent="signup">
            Zarejestruj
          </button>
        </div>
      </form>
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
import { registerUrl } from "@/variables";
import InfoModal from "@/components/modal/InfoModal";

export default {
  name: "register",
  components: {
    InfoModal
  },
  data() {
    return {
      credential: {
        name: "",
        username: "",
        email: "",
        role: ["user"],
        password: ""
      },
      isChecked: true,
      errors: [],
      modalHeaderContent: "Uwaga!",
      modalBodyContent: "Coś poszło nie tak, sprawdź błędy!",
      showModal: false
    };
  },
  methods: {
    checkForm() {
      this.errors = [];
      if (
        this.credential.name &&
        this.credential.username &&
        this.credential.email &&
        this.credential.password
      ) {
        return true;
      }
      if (!this.credential.name) {
        this.errors.push("Imię jest wymagane!");
      }
      if (!this.credential.username) {
        this.errors.push("Nazwa użytkownika jest wymagana!");
      }
      if (!this.credential.email) {
        this.errors.push("Email jest wymagany!");
      }
      if (!this.credential.password) {
        this.errors.push("Hasło jest wymagane!");
      }
    },
    signup() {
      const isValid = this.checkForm();
      if (isValid) {
        this.$http
          .post(registerUrl, this.credential)
          .then(() => {
            setTimeout(() => {
              if(this.checkIsLogged) {
                this.$router.push("/");
              } else {
                this.$router.push("/login");
              }
            }, 500);
          })
          .catch(() => {
            this.showModal = true;
            this.errors.push("Podałeś/aś niepoprawne dane, sprawdź ponownie!");
          });
      } else {
        this.showModal = true;
      }
    },
    close() {
      this.showModal = false;
    }
  },
  computed: {
    checkIsLogged() {
      return this.$store.getters.isLogged;
    },
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
@import "@/assets/styles/main.scss";
.register {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.registerPanel {
  display: flex;
  flex-direction: column;
  border-radius: 0.2rem;
  justify-content: center;
  text-align: center;
  background-color: rgba(255, 255, 255, 0.9);
  box-shadow: 0px 6px 16px rgba(24, 41, 67, 0.2);
  margin: 0 auto;
  width: 100%;
  max-width: 375px;
  padding: 1rem 3.5rem 2rem;
}
.header {
  background-color: rgba(105, 105, 105, 0.8);
  height: 100px;
  width: 100%;
}
.formLayout {
  height: 450px;
  width: 100%;
  background-color: rgba(220, 220, 220, 0.9);
  display: flex;
  flex-direction: column;
  border: 2px solid lightslategray;
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
    color: #dc6868;
    &-list {
      margin: 0;
      padding: 0.5rem 0 0 1.3rem;
    }
  }
}
.form-group {
  width: 100%;
  position: relative;

  &__checkbox {
    margin-top: 1rem;
  }

  &__checkbox-wrapper {
    margin: 1rem 0;
    display: flex;
    justify-content: flex-start;
    align-items: flex-start;
  }

  &__label {
    position: absolute;
    top: 38px;
    left: -6px;
  }
}
.form-button {
  display: flex;
  flex-direction: column;
}
.form-login {
  margin-top: 1rem;
}

.icon {
  font-size: 13px;
}
.form-title {
  margin: 2rem 0 1rem 0;
  font-size: 2rem;
  width: 100%;
}

label {
  font-size: 0.9rem;
}

.form-input {
  margin-top: 2rem;
  width: 100%;
  padding: 0.5rem 1.5rem;
  border: none;
  border-bottom: 1px solid rgba(0, 0, 0, 0.38);
  background: none;
  outline: none;
  transition: border-color 0.3s ease-in-out;

  &__admin {
    width: 20%;
  }

  &--checkbox {
    margin: 0.25rem 4rem 0 0;
  }

  &:focus {
    border-bottom-color: rgba(0, 0, 0, 0.99);
  }
}
.registerBtn {
  margin-top: 2rem;
  @include default-button($navy-blue);
  background: $navy-blue;
  color: $white;
}
</style>
