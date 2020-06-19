import {message} from 'ant-design-vue'
import store from '@/store'
import {
    getHotelsAPI,
    getHotelByIdAPI,
    getBizregionsAPI,
    getHotelByBizAndAddAPI
} from '@/api/hotel'
import {
    reserveHotelAPI
} from '@/api/order'
import {
    orderMatchCouponsAPI,
} from '@/api/coupon'
import {dataToArray} from "ant-design-vue/lib/vc-drawer/src/utils";

const hotel = {
    state: {
        hotelList: [],
        hotelListParams: {
            pageNo: 0,
            pageSize: 12
        },
        hotelListLoading: true,
        currentHotelId: '',
        currentHotelInfo: {},
        orderModalVisible: false,
        currentOrderRoom: {},
        orderMatchCouponList: [],
        bizRegions:[]
    },
    mutations: {
        set_hotelList: function (state, data) {
            state.hotelList = data
        },
        set_hotelListParams: function (state, data) {
            state.hotelListParams = {
                ...state.hotelListParams,
                ...data,
            }
        },
        set_hotelListLoading: function (state, data) {
            state.hotelListLoading = data
        },
        set_currentHotelId: function (state, data) {
            state.currentHotelId = data
        },
        set_currentHotelInfo: function (state, data) {
            state.currentHotelInfo = {
                ...state.currentHotelInfo,
                ...data,
            }
        },
        set_orderModalVisible: function (state, data) {
            state.orderModalVisible = data
        },
        set_currentOrderRoom: function (state, data) {
            state.currentOrderRoom = {
                ...state.currentOrderRoom,
                ...data,
            }
        },
        set_orderMatchCouponList: function (state, data) {
            state.orderMatchCouponList = data
        },
        set_bizRegions:function (state,data) {
            state.bizRegions = data
        }
    },

    actions: {
        getHotelList: async ({commit, state}) => {
            commit('set_hotelListLoading', true)
            const res = await getHotelsAPI()
            if (res) {
                commit('set_hotelList', res)
                commit('set_hotelListLoading', false)
            }
        },
        getHotelById: async ({commit, state}) => {
            const res = await getHotelByIdAPI({
                hotelId: state.currentHotelId
            })
            if (res) {
                commit('set_currentHotelInfo', res)
            }
        },
        addOrder: async ({state, commit}, data) => {
            const res = await reserveHotelAPI(data)
            if (res) {
                message.success('预定成功')
                commit('set_orderModalVisible', false)
            }else {
                commit('set_orderModalVisible', false)
            }
        },
        getOrderMatchCoupons: async ({state, commit}, data) => {
            const res = await orderMatchCouponsAPI(data)
            if (res) {
                commit('set_orderMatchCouponList', res)
            }
        },
        getBizregions:async ({commit,state})=>{
            const res = await getBizregionsAPI()
            if (res){
                commit('set_bizRegions',res)
            }
        },
        getHotelByBizAndAdd:async ({commit,state},data)=>{
            const res = await getHotelByBizAndAddAPI(data)
            if (res){
                commit('set_hotelList',res)
            }
        }
    }
}

export default hotel
