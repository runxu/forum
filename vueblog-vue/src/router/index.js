import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Blogs from '../views/Blogs.vue'
import BlogEdit from '../views/BlogEdit.vue'
import BlogDetail from '../views/BlogDetail.vue'
import Register from '../views/Register.vue'
import Sheet from '../views/sheet'
import About from '../views/About'
import Search from '../views/Search'
import Thumbs from '../views/thumbs'
import Hot from '../views/Hot'
import Category from '../views/category'
import Userinfo from '../views/userinfo'
import EditPersonInfo from '../views/EditPersonInfo'
import UpdatePassword from '../views/updatepassword'
import UpdatePhone from '../views/updatephone'


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Index',
    redirect: {name:"Sheet"}
  },
    {
        path: '/sheet',
        name: 'Sheet',
        component: Sheet
    },
    {
        path: '/about',
        name: 'About',
        component: About
    },
    {
        path: '/thumbs',
        name: 'Thumbs',
        component: Thumbs
    },
    {
        path: '/hot',
        name: 'Hot',
        component: Hot
    },
    {
        path: '/search',
        name: 'Search',
        component: Search
    },
    {
    path: '/blogs',
    name: 'Blogs',
    component: Blogs
},
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path:'/userinfo',
      name:'Userinfo',
      component: Userinfo
    },
    {
      path:'/editpersoninfo',
      name:'EditPersonInfo',
      component:EditPersonInfo
    },
    {
        path:'/updatephone',
        name:'UpdatePhone',
        component:UpdatePhone
    },
    {
      path:'/updatepassword',
      name:'UpdatePassword',
      component:UpdatePassword
    },
    {
        path: '/blog/add',
        name: 'BlogAdd',
        component: BlogEdit,
        meta:{
            requireAuth: true
        }
    },
    {
        path: '/blog/:blogId',
        name: 'BlogDetail',
        component: BlogDetail
    },
    {
      path: '/category/:categoryid',
      name: 'Category',
      component: Category
    },
    {
        path: '/blog/:blogId/edit',
        name: 'BlogEdit',
        component: BlogEdit,
        meta:{
            requireAuth: true
        }
    }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
