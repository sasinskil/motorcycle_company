<template>
<div class="bodyLayout">
 <div class="loginPanel">
  <div class="header">
    <h3 id="companyName">Duos Kołos Company</h3>
  </div>
  <div class="formLayout">
    <h4>Login Panel</h4>
    <div class="form">
      <div class="form-input">
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
      <div class="form-input">
        <input
          v-model="credential.password"
          :class="[credential.password.length < 6 ? 'red' : 'green']"
          type="password"
          class="form-input"
          id="password"
          placeholder="Password"
        >
      </div>
      <button @click="signin" :disabled="credential.username.length < 6 || credential.password.length < 6" class="btn btn-dark loginBtn">Log in</button>
         <!-- <div class="alert alert-danger mt-4" role="alert">
  Check your credentials !
</div> -->
    </div>
  </div>
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
        password: ''
      }
    };
  },

  methods: {
    signin() {
      this.$store.dispatch("login", this.credential)
;
    }
  }
};
</script>


<style lang="scss" scoped>

.bodyLayout {
  width: 100%;
  height: 100vh;
  background-image: url('../assets/background.jpg');
  background-size: cover;
  background-position: center center;
}

.loginPanel {
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: center;
  text-align: center;
  margin: 0 auto;
  width: 700px;
  height: 100%;
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
  background-color: rgba(220, 220, 220, 0.3);
  display: flex;
  flex-direction: column;
  border:2px solid lightslategray;
}

.form {
  margin-top: 50px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.form-input {
  margin-top: 10px;
  width: 380px;
  height: 40px;
  border: none;
  background: none;
  outline: none;
}

.loginBtn {
  margin-top: 40px;
  width: 380px;
}

h4 {
  margin-top: 50px;
}

.red {
  border-bottom: 2px solid red;
}

.green {
  border-bottom: 2px solid lightgreen;
}
</style>