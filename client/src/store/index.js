import Vue from 'vue';
import Vuex from 'vuex';
import { loginUrl } from '@/variables';

import router from '@/router';

Vue.use(Vuex);

const types = {
  LOGIN: 'LOGIN',
  LOGOUT: 'LOGOUT',
  AUTHORISED: 'AUTHORISED',
  UNAUTHORISED: 'UNAUTHORISED'
}

const state = {
  logged: localStorage.getItem('token'),
  authorised: localStorage.getItem('authorised'),
  currentUser: JSON.parse(localStorage.getItem('user')),
  loginError: 0
}


const getters = {
  isLogged: state => state.logged,
  isAdmin: state => state.authorised,
  getCurrentUser: state => state.currentUser,
  loginError: state => state.loginError
}

const actions = {
  login({ commit }, credential) {
    commit('resetLoginError')
    Vue.http.post(loginUrl, credential)
      .then(function(response) { 
       return response.json()
      })
      .then(function(result) {
        const { username, authorities, accessToken} = result;
        const roles = [];

        for( let role of authorities) {
          if(role.authority === 'ROLE_ADMIN'){
            localStorage.setItem('authorised', 1);
            commit(types.AUTHORISED);
            roles.push('Administrator');
          } else if (role.authority === 'ROLE_USER') {
            roles.push('Użytkownik');
          }
        }

        const user = {
          username: username,
          authorities: roles,
        }
        const jsonPerson = JSON.stringify(user);

        localStorage.setItem('user', jsonPerson);
        localStorage.setItem('token', accessToken);
        commit(types.LOGIN);
        router.push({ path: '/' });
        router.go(0);
      })
      .catch((err) => {
        console.log(err);
        commit('setLoginError')
      });
  },

  logout({ commit }) {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    localStorage.removeItem('authorised');
    commit(types.LOGOUT);
    commit(types.UNAUTHORISED);
    router.push({ path: '/login' });
  },
}

const mutations = {
  [types.LOGIN] (state) {
    state.logged = 1;
  },

  [types.LOGOUT] (state) {
    state.logged = 0;
  },

  [types.AUTHORISED] (state) {
    state.authorised = 1;
  },

  [types.UNAUTHORISED] (state) {
    state.authorised = 0;
  },

  setLoginError (state) {
    state.loginError = 1
  },

  resetLoginError (state) {
    state.loginError = 0
  }
}

export default new Vuex.Store({
  state,
  getters,
  actions,
  mutations
});