import {
    getManagerListAPI,
    addManagerAPI,
    delHotelManagerAPI,
    resetPasswordAPI,
    getAllUsersListAPI,
    updateAccountAPI
} from '@/api/admin'
import {
    addHotelAPI
} from '@/api/hotelManager'
import { message } from 'ant-design-vue'
import {getUserInfoAPI, updateUserInfoAPI} from "../../api/user";

const admin = {
    state: {
        managerList: [
        ],
        userList:[],
        addManagerModalVisible: false,
        addManagerParams: {
            email:'',
            password:''
        },
        updataUserInfoModalVisible: false
    },
    mutations: {
        set_managerList: function(state, data) {
            state.managerList = data
        },
        set_addManagerModalVisible: function(state, data) {
            state.addManagerModalVisible = data
        },
        set_addManagerParams: function(state, data) {
            state.addManagerParams = {
                ...state.addManagerParams,
                ...data,
            }
        },
        set_usersList: function (state,data) {
            state.userList = data
        },
        set_updataUserInfoModalVisible: function (state,data) {
            state.updataUserInfoModalVisible = data
        },
        set_targetUserInfo: function (state,data) {
            state.targetUserInfo = data
        }
    },
    actions: {
        getManagerList: async({ commit }) => {
            const res = await getManagerListAPI()
            if(res){
                commit('set_managerList', res)
            }
        },
        getAllUsersList: async ({commit}) => {
            const res = await getAllUsersListAPI()
            if (res){
                commit('set_usersList',res)
            }
        },
        addManager: async({ state, commit, dispatch }) => {
            const res = await addManagerAPI(state.addManagerParams)
            if(res) {
                commit('set_addManagerParams',{
                    email:'',
                    password:''
                })
                commit('set_addManagerModalVisible', false)
                message.success('添加成功')
                dispatch('getManagerList')
            }else{
                message.error('添加失败')
            }
        },
        delHotelManager: async ({commit,state},data)=>{
            const res = await delHotelManagerAPI(data)
            if (res){
                message.success('删除成功')
            }
        },
        resetPassword: async ({commit, state},data)=>{
            const res = await resetPasswordAPI(data)
            if (res){
                message.success('重置成功')
            }
        },
        updateAccountInfo: async ({commit,state},data)=>{
            const res = await updateUserInfoAPI(data)
            if (res){
                message.success('更新成功')
            }
        },
        getTargetUserInfo: async ({commit, state},data)=>{
            console.log(data)
            const res = await getUserInfoAPI(data)
            if (res){
                commit('set_targetUserInfo',res)
            }
        },
        updateOtherAccount: async ({commit,state},data)=>{
            const res = await updateAccountAPI(data)
            if (res){
                message.success('更新成功')
            }
            commit('set_updataUserInfoModalVisible',false)
        }
    }
}
export default admin
