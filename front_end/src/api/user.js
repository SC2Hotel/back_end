import { axios } from '@/utils/request'

const api = {
    userPre: '/api/user'
}
export function loginAPI(data){
    return axios({
        url:`${api.userPre}/login`,
        method: 'POST',
        data
    })
}
export function registerAPI(data){
    return axios({
        url: `${api.userPre}/register`,
        method: 'POST',
        data
    })
}
export function getUserInfoAPI(id){
    return axios({
        url: `${api.userPre}/${id}/getUserInfo`,
        method: 'GET'
    })
}
export function updateUserInfoAPI(data) {
    return axios({
        url: `${api.userPre}/${data.id}/userInfo/update`,
        method: 'POST',
        data
    })
}

export function registerVIPAPI(data) {
    return axios({
        url: `${api.userPre}/${data.id}/registerSenior`,
        method: 'POST',
        params: {type:data.type,message:data.message}
    })
}

export function getCreditLogAPI(data) {
    return axios({
        url: `${api.userPre}/${data}/creditChange`,
        method: 'Post'
    })
}

export function updateCreditAPI(data) {
    return axios({
        url: `${api.userPre}/`+data.id+'/addCredit',
        method: 'Post',
        params: {credit:data.creditNum}
    })
}
