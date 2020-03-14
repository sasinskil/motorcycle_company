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
// Motorcycles
import motorcycles from '@/components/motorcycle/motorcycles.vue'
import editMotorcycle from '@/components/motorcycle/editMotorcycle.vue'
import addMotorcycle from '@/components/motorcycle/addMotorcycle.vue'
// Motorcycle details
import motorcycleDetails from '@/components/motorcycle/motorcycleDetails/motorcycleDetails.vue'
import singleMotDetails from '@/components/motorcycle/motorcycleDetails/singleMotDetails.vue'
import editMotDetails from '@/components/motorcycle/motorcycleDetails/editMotDetails.vue'
import addMotDetails from '@/components/motorcycle/motorcycleDetails/addMotDetails.vue'
// Test drives
import testDrives from '@/components/testDrive/testDrives.vue'
import singleTestDrive from '@/components/testDrive/singleTestDrive.vue'
import editTestDrive from '@/components/testDrive/editTestDrive.vue'
import addTestDrive from '@/components/testDrive/addTestDrive.vue'
// Transactions
import transactions from '@/components/transaction/transactions.vue'
import singleTransaction from '@/components/transaction/singleTransaction.vue'
import editTransaction from '@/components/transaction/editTransaction.vue'
import addTransaction from '@/components/transaction/addTransaction.vue'
// Services
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
  // Motorcycles
  {
    path: '/motorcycles',
    name: 'motorcycles',
    component: motorcycles,
    meta: { requiresAuth: true }
  },
  {
    path: '/motorcycle/:id/edit',
    name: 'editMotorcycle',
    component: editMotorcycle,
    meta: { requiresAuth: true }
  },
  {
    path: "/addMotorcycle",
    name: 'addMotorcycle',
    component: addMotorcycle,
    meta: { requiresAuth: true }
  },
  // Motorcycle details
  {
    path: '/motorcycleDetails',
    name: 'motorcycleDetails',
    component: motorcycleDetails,
    meta: { requiresAuth: true }
  },
  {
    path: '/motorcycleDetails/:id',
    name: 'singleMotDetails',
    component: singleMotDetails,
    meta: { requiresAuth: true }
  },
  {
    path: '/motorcycleDetails/:id/edit',
    name: 'editMotDetails',
    component: editMotDetails,
    meta: { requiresAuth: true }
  },
  {
    path: "/addMotDetails",
    name: 'addMotDetails',
    component: addMotDetails,
    meta: { requiresAuth: true }
  },
  // Test Drives
  {
    path: '/testDrives',
    name: 'testDrives',
    component: testDrives,
    meta: { requiresAuth: true }
  },
  {
    path: '/testDrive/:id',
    name: 'singleTestDrive',
    component: singleTestDrive,
    meta: { requiresAuth: true }
  },
  {
    path: '/testDrive/:id/edit',
    name: 'editTestDrive',
    component: editTestDrive,
    meta: { requiresAuth: true }
  },
  {
    path: "/addTestDrive",
    name: 'addTestDrive',
    component: addTestDrive,
    meta: { requiresAuth: true }
  },
  // Transactions
  {
    path: '/transactions',
    name: 'transactions',
    component: transactions,
    meta: { requiresAuth: true }
  },
  {
    path: '/transaction/:id',
    name: 'singleTransaction',
    component: singleTransaction,
    meta: { requiresAuth: true }
  },
  {
    path: '/transaction/:id/edit',
    name: 'editTransaction',
    component: editTransaction,
    meta: { requiresAuth: true }
  },
  {
    path: "/addTransaction",
    name: 'addTransaction',
    component: addTransaction,
    meta: { requiresAuth: true }
  },
  // Services
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
