import Vue from 'vue'
import router from '@/router'
import { getToken, setToken, removeToken,setLongToken } from '@/utils/auth'
import { resetRouter } from '@/router'
import { message } from 'ant-design-vue'
import {
    loginAPI,
    registerAPI,
    getUserInfoAPI,
    updateUserInfoAPI,
    registerVIPAPI,
    getCreditLogAPI
} from '@/api/user'

import {
    getUserOrdersAPI,
    cancelOrderAPI,
    addCommentAPI
} from '@/api/order'

const getDefaultState = () => {
    return {
        userId: '',
        userInfo: {
        },
        userOrderList: [
        ],
        currentOrder: {},
        orderDetailModalVisible:false,
        orderRateModalVisible: false,
        registerVIPModalVisible:false,
        creditLogModalVisible:false,
        creditLog:[]
    }
}

const user = {
    state : getDefaultState(),

    mutations: {
        reset_state: function(state) {
            state.token = '',
            state.userId = '',
            state.userInfo = {
            },
            state.userOrderList = []
        },
        set_token: function(state, token){
            state.token = token
        },
        set_email: (state, data) => {
            state.email = data
        },
        set_userId: (state, data) => {
            state.userId = data
        },
        set_userInfo: (state, data) => {
            state.userInfo = {
                ...state.userInfo,
                ...data
            }
        },
        set_currentOrder: function (state, data) {
            state.currentOrder = data
        },
        set_userOrderList: (state, data) => {
            state.userOrderList = data
        },
        set_orderDetailModalVisible: function (state, data) {
            state.orderDetailModalVisible = data
        },
        set_orderRateModalVisible: function (state, data) {
            state.orderRateModalVisible = data
        },
        set_registerVIPModalVisible: function (state, data) {
            state.registerVIPModalVisible = data
        },
        set_creditLogModalVisible: function (state,data) {
            state.creditLogModalVisible = data
        },
        set_creditLog: function (state,data) {
            state.creditLog = data
        }
    },

    actions: {
        login: async ({ dispatch, commit, state }, userData) => {
            const res = await loginAPI(userData)
            if(res){
                console.log(res)
                setToken(res.njuToken)
                setLongToken(res.njuLongToken)
                commit('set_userId', res.id)
                localStorage.setItem('uid',res.id)
                dispatch('getUserInfo')
                router.push('/hotel/hotelList')
            }
        },
        register: async({ commit }, data) => {
            const res = await registerAPI(data)
            if(res){
                message.success('注册成功')
            }
        },
        getUserInfo({ state, commit }) {
            return new Promise((resolve, reject) => {
              getUserInfoAPI(localStorage.getItem('uid')).then(response => {
                const data = response
                if (!data) {
                  reject('登录已过期，请重新登录')
                }
                commit('set_userInfo', data)
                commit('set_userId', data.id)
                resolve(data)
              }).catch(error => {
                reject(error)
              })
            })
        },
        updateUserInfo: async({ state, dispatch }, data) => {
            const params = {
                id: state.userId,
                ...data,
            }
            const res = await updateUserInfoAPI(params)
            if(res){
                message.success('修改成功')
                dispatch('getUserInfo')
            }
        },
        getUserOrders: async({ state, commit }) => {
            const data = {
                userId: Number(state.userId)
            }
            const res = await getUserOrdersAPI(data)
            if(res){
                commit('set_userOrderList', res)
                console.log(state.userOrderList)
            }
        },
        cancelOrder: async({ state, dispatch }, orderId) => {
            const res = await cancelOrderAPI(orderId)
            if(res) {
                dispatch('getUserOrders')
                message.success('撤销成功')
            }else{
                message.error('撤销失败')
            }
        },
        logout: async({ commit }) => {
            removeToken()
            resetRouter()
            commit('reset_state')
        },
          // remove token
        resetToken({ commit }) {
            return new Promise(resolve => {
                removeToken() // must remove  token  first
                commit('reset_state')
                resolve()
            })
        },
        addComment: async ({state,commit,dispatch},comment)=>{
            const res = await addCommentAPI(comment)
            if (res){
                dispatch('getUserOrders')
                message.success("评价成功")
            }else {
                message.error("评价失败")
            }
            commit('set_orderRateModalVisible',false)
        },
        registerVIP: async ({state,commit},data)=>{
            const res = await registerVIPAPI(data)
            if (res){
                message.success("注册成功")
            }else{
                message.error("注册失败")
            }
            commit('set_registerVIPModalVisible',false)
        },
        getCreditLog: async ({state,commit},data)=>{
            const res = await getCreditLogAPI(data)
            if (res){
                commit('set_creditLog',res)
            }else {
                console.log(res)
            }
        }
    }
}

export default user
