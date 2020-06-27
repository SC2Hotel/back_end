import { axios } from '@/utils/request'
const api = {
    adminPre: '/api/admin'
}
export function getManagerListAPI(){
    return axios({
        url: `${api.adminPre}/getAllManagers`,
        method: 'GET'
    })
}
export function addManagerAPI(data) {
    return axios({
        url: `${api.adminPre}/addManager`,
        method: 'POST',
        data
    })
}
export function delHotelManagerAPI(data) {
    return axios({
        url: `${api.adminPre}/delUser/`+data,
        method: 'POST'
    })
}

export function resetPasswordAPI(data) {
    return axios({
        url: `${api.adminPre}/${data}/resetPassword`,
        method: 'POST'
    })
}

export function getAllUsersListAPI() {
    return axios({
        url: `${api.adminPre}/getAllUsersInfo`,
        method: 'GET'
    })
}

export function updateAccountAPI(data) {
    return axios({
        url: `${api.adminPre}/updateUserInfo`,
        method: 'Post',
        data
    })
}