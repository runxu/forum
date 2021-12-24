import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Element from 'element-ui'
import "element-ui/lib/theme-chalk/index.css"
import axios from 'axios'
import "./axios"
import mavonEditor from 'mavon-editor'
import "mavon-editor/dist/css/index.css"
import "./permission"
import Buefy from 'buefy'
import 'buefy/dist/buefy.css'
import Vant from 'vant';
import 'vant/lib/index.css';


Vue.use(Vant)
Vue.use(Buefy)
Vue.config.productionTip = false
Vue.use(Element)
Vue.prototype.$axios = axios
Vue.use(mavonEditor)



new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')


