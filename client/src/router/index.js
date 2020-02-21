import Vue from 'vue'
import VueRouter from 'vue-router'
import login from '@/components/auth/login.vue'
import HelloWorld from '@/components/others/HelloWorld.vue'
import notFound404 from '@/components/others/notFound404.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'HelloWorld',
    component: HelloWorld
  },
  {
    path: '/login',
    name: 'login',
    component: login
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

export default router
