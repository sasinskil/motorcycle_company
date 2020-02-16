import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
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

Vue.component('font-awesome-icon', FontAwesomeIcon)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
