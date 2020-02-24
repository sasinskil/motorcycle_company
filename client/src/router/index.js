import Vue from 'vue'
import VueRouter from 'vue-router'
// Auth
import login from '@/components/auth/login.vue'
import register from '@/components/auth/register.vue'
// Cutomers
import customers from '@/components/customer/customers.vue'
import singleCustomer from '@/components/customer/singleCustomer.vue'
import editCustomer from '@/components/customer/editCustomer.vue'
import addCustomer from '@/components/customer/addCustomer.vue'
// Employees
import employees from '@/components/employee/employees.vue'
import singleEmployee from '@/components/employee/singleEmployee.vue'
import editEmployee from '@/components/employee/editEmployee.vue'
import addEmployee from '@/components/employee/addEmployee.vue'
// Home
import Home from '@/components/others/home/Home.vue'
// 404
import notFound404 from '@/components/others/notFound404.vue'

Vue.use(VueRouter)

const routes = [
  // Home
  {
    path: '/',
    name: 'Home',
    component: Home,
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
  // Employees
  {
    path: '/employees',
    name: 'employees',
    component: employees,
    meta: { requiresAuth: true }
  },
  {
    path: '/employee/:id',
    name: 'singleEmployee',
    component: singleEmployee,
    meta: { requiresAuth: true }
  },
  {
    path: '/employee/:id/edit',
    name: 'editEmployee',
    component: editEmployee,
    meta: { requiresAuth: true }
  },
  {
    path: "/addEmployee",
    name: 'addEmployee',
    component: addEmployee,
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
