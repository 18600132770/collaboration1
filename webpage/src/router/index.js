import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

import Layout from '@/views/Layout'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login')
  },
  {
    path: '/professional',
    component: Layout,
    redirect: '/professional/index',
    children: [{
      path: 'index',
      name: 'Professional',
      component: () => import('@/views/Professional')
    }]
  },
  {
    path: '/projects',
    component: Layout,
    redirect: '/projects/index',
    children: [{
      path: 'index',
      name: 'Projects',
      component: () => import('@/views/Projects')
    }]
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  }
]

const router = new VueRouter({
  mode: 'hash',
  base: process.env.BASE_URL,
  routes
})

export default router
