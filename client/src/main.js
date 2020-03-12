import Vue from 'vue'
import VueResource from "vue-resource";
import App from './App.vue'
import router from './router'
import DatePicker from 'vue2-datepicker';
import 'vue2-datepicker/index.css';
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
  faUserTie,
  faHome,
  faChevronCircleRight,
  faMotorcycle,
  faRoad,
  faHandshake,
  faWrench,
  faUsers,
  faUserTag,
  faCarBattery,
  faWeightHanging
} from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

Vue.config.productionTip = false

Vue.use(require("moment"));

Vue.component('v-date-picker', DatePicker)

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
  faUserTie,
  faHome,
  faChevronCircleRight,
  faMotorcycle,
  faRoad,
  faHandshake,
  faWrench,
  faUsers,
  faUserTag,
  faCarBattery,
  faWeightHanging
);

Vue.component('font-awesome-icon', FontAwesomeIcon);


Vue.http.interceptors.push((request, next) => {
  if (localStorage.getItem('token')) {
    request.headers.set('Authorization', 'Bearer ' + localStorage.getItem('token'));
  }

  next(response => {

    if(response.status === 400 || response.status === 401 || response.status === 403) {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        localStorage.removeItem('authorised');
        router.go(0);
        router.push({path: '/login'});
    }

})
  
});

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
