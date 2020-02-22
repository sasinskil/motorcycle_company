import Vue from 'vue'
import VueRouter from 'vue-router'
import login from '@/components/auth/login.vue'
import register from '@/components/auth/register.vue'
// Cutomers
import customers from '@/components/customer/customers.vue'
import singleCustomer from '@/components/customer/singleCustomer.vue'
import editCustomer from '@/components/customer/editCustomer.vue'
import addCustomer from '@/components/customer/addCustomer.vue'
// Employees
import HelloWorld from '@/components/others/HelloWorld.vue'
import notFound404 from '@/components/others/notFound404.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'HelloWorld',
    component: HelloWorld,
    meta: { requiresAuth: true }
  },
  // Customers
  {
    path: '/customers',
    name: 'customers',
    component: customers,
    meta: { requiresAuth: true }
  },
  {
    path: '/customer/:id',
    name: 'singleCustomer',
    component: singleCustomer,
    meta: { requiresAuth: true }
  },
  {
    path: '/customer/:id/edit',
    name: 'editCustomer',
    component: editCustomer,
    meta: { requiresAuth: true }
  },
  {
    path: "/addCustomer",
    name: 'addCustomer',
    component: addCustomer,
    meta: { requiresAuth: true }
  },
  // Auth
  {
    path: '/register',
    name: 'register',
    component: register,
    meta: { requiresAuth: true }
  },
  {
    path: '/login',
    name: 'login',
    component: login,
    meta: { requiresAuth: false }
  },
  // 404
  {
    path: "*",
    component: notFound404,
    meta: { requiresAuth: false }
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (!localStorage.getItem('token')) {
      next("/login");
    } else next();
  } else {
    next();
  }
});

export default router
