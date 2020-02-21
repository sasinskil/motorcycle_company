import Vue from 'vue'
import VueRouter from 'vue-router'
import login from '@/components/auth/login.vue'
import register from '@/components/auth/register.vue'
import customers from '@/components/customer/customers.vue'
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
  {
    path: '/customers',
    name: 'customers',
    component: customers,
    meta: { requiresAuth: true }
  },
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
