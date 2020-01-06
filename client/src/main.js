import Vue from 'vue';
import vueResource from 'vue-resource';
import { library } from '@fortawesome/fontawesome-svg-core';
import {
  faEraser, faTrashAlt, faSignOutAlt, faPlus, faUser, faLock,
} from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import App from './App.vue';
import router from './router';
import store from './store';


library.add(
  faTrashAlt, faEraser, faSignOutAlt, faPlus, faUser, faLock,
);

Vue.component('font-awesome-icon', FontAwesomeIcon);

Vue.use(vueResource);

Vue.config.productionTip = false;

Vue.http.interceptors.push((request, next) => {
  if (localStorage.getItem('token')) {
    request.headers.set('Authorization', 'Bearer ' + localStorage.getItem('token'));
  }

  next((response) => {
    if (response.status === 400 || response.status === 401 || response.status === 403) {
      router.push({ path: '/login' });
    }
  });
});

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app');
