<template>
  <div class="bodyLayout">
    <div class="loginPanel">
      <h2 class="form-title">Sign in</h2>
      <form method="POST" class="form" id="login-form">
            <div class="form-group">
                <label for="username"><font-awesome-icon class="user-icon icon" icon="user" /></label>
                <input 
                  v-model="credential.username"
                  :class="[credential.username.length < 6 ? 'red' : 'green']"
                  type="text"
                  class="form-input"
                  id="username"
                  placeholder="Username"
                  autofocus
                >
            </div>
            <div class="form-group">
                <label for="password"><font-awesome-icon class="lock-icon icon" icon="lock" /></label>
                <input
                  v-model="credential.password"
                  :class="[credential.password.length < 6 ? 'red' : 'green']"
                  type="password"
                  class="form-input"
                  id="password"
                  placeholder="Password"
                >
            </div>
            <div class="form-group form-button">
                <button @click="signin" :disabled="credential.username.length < 6 || credential.password.length < 6" class="btn btn-dark loginBtn">Log in</button>
            </div>
        </form>
    </div>
  </div>
</template>

<script>
export default {
  name: "login",

  data() {
    return {
      credential: {
        username: '',
        password: '',
      },
    };
  },

  methods: {
    signin() {
      this.$store.dispatch("login", this.credential);
      this.credential.username = "";
      this.credential.password = "";
    },
  },
};
</script>


<style lang="scss" scoped>

.bodyLayout {
  width: 100%;
  height: 100vh;
  background-image: url('../assets/background.jpg');
  background-size: cover;
  background-position: center center;
  display: flex;
  justify-content: center;
  align-items: center;
}

.loginPanel {
  display: flex;
  flex-direction: column;
  justify-content: center;
  text-align: center;
  background-color: rgba(255,255,255,0.9);
  box-shadow: 0px 6px 16px rgba(24, 41, 67, 0.2);
  margin: 0 auto;
  width: 350px;
  padding: 1rem 3.5rem 2rem;
}

.header {
  background-color: rgba(105, 105, 105, 0.8);
  height: 100px;
  width: 100%;
}

#companyName {
  margin-top: 30px;
}

.formLayout {
  height: 450px;
  width: 100%;
  background-color: rgba(220, 220, 220, 0.9);
  display: flex;
  flex-direction: column;
  border:2px solid lightslategray;
}

.form {
  margin: 1rem 0;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.form-group {
  width: 100%;
  position: relative;
}

label {
  position: absolute;
  top: 36px;
  left: 0;
}

.icon {
  font-size: 13px;
}

.form-title {
  margin: 2rem 0 1rem 0;
  font-size: 2rem;
  width: 100%;
}

.form-input {
  margin-top: 2rem;
  width: 100%;
  padding: 0.5rem 1.5rem;
  border: none;
  background: none;
  outline: none;
}

.loginBtn {
  font-weight: bold;
  margin-top: 3rem;
  padding: .7rem 2.5rem;
  border: none;
  background-color: #4fc08d;
  color: #fff;
  cursor: pointer;
  transition: box-shadow 0.5s ease-in-out;

  &:hover {
    box-shadow: 0px 6px 16px rgba(24, 41, 67, 0.2);
  }

  &:active {
    background-color: #44ae7e;
  }
}

.red {
  border-bottom: 1px solid red;
}

.green {
  border-bottom: 1px solid lightgreen;
}
</style>
