import Vue from 'vue'
import VueResource from "vue-resource";
import App from './App.vue'
import router from './router'
import store from './store'
// eslint-disable-next-line no-unused-vars
import moment from 'moment';
import { library } from '@fortawesome/fontawesome-svg-core'
import {
  faTrashAlt,
  faEdit,
  faEraser,
  faPlus,
  faDoorOpen,
  faUser,
  faIdCard,
  faUserShield,
  faEnvelope,
  faAngleDoubleLeft,
  faCheck,
  faFileExport,
  faArrowLeft,
  faLock,
  faUserTie
} from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

Vue.config.productionTip = false

Vue.use(require("moment"));

Vue.use(VueResource);

library.add(
  faTrashAlt,
  faEdit,
  faEraser,
  faPlus,
  faDoorOpen,
  faUser,
  faIdCard,
  faUserShield,
  faEnvelope,
  faAngleDoubleLeft,
  faCheck,
  faFileExport,
  faArrowLeft,
  faLock,
  faUserTie
);

Vue.component('font-awesome-icon', FontAwesomeIcon);

// eslint-disable-next-line no-unused-vars
Vue.http.interceptors.push((request, next) => {
  if (localStorage.getItem('token')) {
    request.headers.set('Authorization', 'Bearer ' + localStorage.getItem('token'));
  }
  
});

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
