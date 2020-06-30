import Vue from 'vue'
import axios from 'axios'
import { VueAxios } from './axios'
import {notification, message} from 'ant-design-vue'
import store from '@/store'
import { getToken,getLongToken } from './auth'
import router from '../router'

// 创建 axios 实例
const service = axios.create({
  baseURL: process.env.NODE_ENV === 'production' ? 'http://106.15.248.13:8080': 'http://njuse.njulzh.com',
  withCredentials: true
})
console.log(process.env.NODE_ENV)
 const err = (error) => {
  if (error.response) {
    const data = error.response.data
    const token = Vue.ls.get('ACCESS_TOKEN')
    if (error.response.status === 403) {
      notification.error({
        message: 'Forbidden',
        description: data.message
      })
    }
    if (error.response.status === 401 && !(data.result && data.result.isLogin)) {
      notification.error({
        message: 'Unauthorized',
        description: 'Authorization verification failed'
      })
      if (token) {
        store.dispatch('Logout').then(() => {
          setTimeout(() => {
            window.location.reload()
          }, 1500)
        })
      }
    }
  }
  return Promise.reject(error)
 }

//request incerceptor
service.interceptors.request.use((config) => {
  const requestConfig = {
    ...config,
    url: `${config.url}`,
    headers: {
      'nju-token':getToken(),
      'nju-long-token':getLongToken(),
      'userId':localStorage.getItem('uid')
    }
  }
  return requestConfig
}, err)

service.interceptors.response.use((response) => {
  if(response.headers["nju-token"]){
    localStorage.setItem('NJUSE-TOKEN',response.headers["nju-token"])
  }
  if(response.headers["nju-long-token"]){
    localStorage.setItem('NJUSE-LONG-TOKEN',response.headers["nju-long-token"])
  }
  switch (response.status) {
    case 200:
      if(response.data.success){
        return response.data.content
      }
      message.error(response.data.message)
      break
    case 404:
      return false
    default:
      message.error(response.data.message)
  }
})

const installer = {
  vm: {},
  install (Vue) {
    Vue.use(VueAxios, service)
  }
}

export {
  installer as VueAxios,
  service as axios
}
