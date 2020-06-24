import {message} from 'ant-design-vue'
import store from '@/store'
import {
    getHotelsAPI,
    getHotelByIdAPI,
    getBizregionsAPI,
    getHotelByBizAndAddAPI,
    getHotelExactlyAPI,
    updateHotelDetailAPI
} from '@/api/hotel'
import {
    reserveHotelAPI,
    getHotelCommentAPI,
    getBookedHotelsAPI,
    getUserHotelOrdersAPI
} from '@/api/order'
import {
    orderMatchCouponsAPI,
} from '@/api/coupon'

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
        searchModalVisible: false,
        hotelDetailModalVisible:false,
        currentOrderRoom: {},
        orderMatchCouponList: [],
        bizRegions:[],
        hotelCommentList:[],
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
        },
        set_searchModalVisible: function (state, data) {
            state.searchModalVisible = data
        },
        set_hotelCommentList: function (state,data) {
            state.hotelCommentList = data
        },
        set_hotelDetailModalVisible: function (state,data) {
            state.hotelDetailModalVisible = data
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
        },
        getHotelExactly: async ({commit,state},data)=>{
            const res = await getHotelExactlyAPI(data)
            if (res){
                commit('set_hotelList',res)
                commit('set_searchModalVisible',false)
            }
        },
        getHotelComment: async ({commit,state},data)=>{
            const res = await getHotelCommentAPI(data)
            if (res){
                commit('set_hotelCommentList',res)
            }
        },
        updateHotelDetail: async ({commit,state},data)=>{
            const res = await updateHotelDetailAPI(data)
            if (res){
                console.log('修改成功')
            }
        },
        getBookedHotels :async ({commit,state},data)=>{
            const res = await getBookedHotelsAPI(data)
            if (res){
                commit('set_hotelList',res)
            }
        },
        getUserHotelOrders : async ({commit,state},data)=>{
            const res = await getUserHotelOrdersAPI(data)
            if (res){
                commit('set_userOrderList',res)
            }
        }

    }
}

export default hotel
