import {
    addRoomAPI,
    addHotelAPI,
    delHotelAPI,
    getHotelByManagerAPI
} from '@/api/hotelManager'
import {
    getAllOrdersAPI,
    cancelOrderAPI,
    delayCheckInOrderAPI,
    executeOrderAPI,
    getHotelOrderAPI,
    getHotelCommentAPI,
    getOrderCommentAPI
} from '@/api/order'
import {
    hotelAllCouponsAPI,
    hotelTargetMoneyAPI,
} from '@/api/coupon'

import {message} from 'ant-design-vue'

const hotelManager = {
    state: {
        orderList: [],
        addHotelParams: {
            name: '',
            address: '',
            bizRegion: 'XiDan',
            hotelStar: '',
            rate: 0,
            description: '',
            phoneNum: '',
            managerId: '',
        },
        addHotelModalVisible: false,
        addRoomParams: {
            roomType: '',
            hotelId: '',
            price: '',
            total: 0,
            curNum: 0,
        },
        addRoomModalVisible: false,
        couponVisible: false,
        addCouponVisible: false,
        activeHotelId: 0,
        couponList: [],
        commentItem:""
    },
    mutations: {
        set_orderList: function (state, data) {
            state.orderList = data
        },
        set_addHotelModalVisible: function (state, data) {
            state.addHotelModalVisible = data
        },
        set_addHotelParams: function (state, data) {
            state.addHotelParams = {
                ...state.addHotelParams,
                ...data,
            }
        },
        set_addRoomModalVisible: function (state, data) {
            state.addRoomModalVisible = data
        },
        set_addRoomParams: function (state, data) {
            state.addRoomParams = {
                ...state.addRoomParams,
                ...data,
            }
        },
        set_couponVisible: function (state, data) {
            state.couponVisible = data
        },
        set_activeHotelId: function (state, data) {
            state.activeHotelId = data
        },
        set_couponList: function (state, data) {
            state.couponList = data
        },
        set_addCouponVisible: function (state, data) {
            state.addCouponVisible = data
        },
        set_commentItem: function (state,data) {
            state.commentItem = data
        }

    },
    actions: {
        getAllOrders: async ({state, commit}) => {
            const res = await getAllOrdersAPI()
            if (res) {
                commit('set_orderList', res)
            }
        },
        addHotel: async ({state, dispatch, commit}) => {
            const res = await addHotelAPI(state.addHotelParams)
            if (res) {
                dispatch('getHotelList')
                commit('set_addHotelParams', {
                    name: '',
                    address: '',
                    bizRegion: 'XiDan',
                    hotelStar: '',
                    rate: 0,
                    description: '',
                    phoneNum: '',
                    managerId: '',
                })
                commit('set_addHotelModalVisible', false)
                message.success('添加成功')
            } else {
                message.error('添加失败')
            }
        },
        addRoom: async ({state, dispatch, commit}) => {
            const res = await addRoomAPI(state.addRoomParams)
            if (res) {
                commit('set_addRoomModalVisible', false)
                commit('set_addRoomParams', {
                    roomType: '',
                    hotelId: '',
                    price: '',
                    total: 0,
                    curNum: 0,
                })
                message.success('添加成功')
            } else {
                message.error('添加失败')
            }
        },
        getHotelCoupon: async ({state, commit}) => {
            const res = await hotelAllCouponsAPI(state.activeHotelId)
            if (res) {
                // 获取到酒店策略之后的操作（将获取到的数组赋值给couponList）
                commit('set_couponList', res)
            }
        },
        addHotelCoupon: async ({commit, dispatch}, data) => {
            const res = await hotelTargetMoneyAPI(data)
            console.log(res)
            if (res) {
                // 添加成功后的操作（提示文案、modal框显示与关闭，调用优惠列表策略等）
                this.$message.success('添加成功');
                commit('set_addCouponVisible', false)
            } else {
                // 添加失败后的操作
                this.$message.error('添加失败');
            }
        },
        delOrder : async ({commit, dispatch}, data) => {
            const res = await cancelOrderAPI(data)
            if (res) {
                this.$message.success('删除成功');
            } else {
                // 添加失败后的操作
                this.$message.error('删除失败');
            }
        },
        delHotel : async ({commit, dispatch}, data) => {
            const res = await delHotelAPI(data)
            if (res) {
                this.$message.success('删除成功');
            } else {
                // 添加失败后的操作
                this.$message.error('删除失败');
            }
        },
        //订单为已预订状态时执行订单
        updateExecuteOrder: async ({commit, dispatch}, data) => {
            const res = await executeOrderAPI(data)
            if (res) {
                console.log('执行成功');
            } else {
                // 添加失败后的操作
                console.log('执行失败');
            }
        },
        //异常延时
        delayCheckInOrder: async ({commit, dispatch}, data) => {
            const res = await delayCheckInOrderAPI(data)
            if (res) {
                console.log('延长成功');
            } else {
                // 添加失败后的操作
                console.log('延长失败');
            }
            return res;
        },
        getHotelByManager: async ({commit,state},data)=>{
            const res = await getHotelByManagerAPI(data)
            if(res){
                commit('set_hotelList',res)
            }
        },
        getOrderByHotel:async ({commit,state},data)=>{
            const res = await getHotelOrderAPI(data)
            if (res){
                commit('set_orderList',res)
            }
        },
        getHotelComment:async ({commit,state},data)=>{
            const res = await getHotelCommentAPI(data)
            if (res){
                commit('set_hotelCommentList',res)
            }
        },
        getOrderComment:async ({commit,state},data)=>{
            const res = await getOrderCommentAPI(data)
            if (res){
                commit('set_commentItem',res)
            }
        }
    }
}
export default hotelManager
