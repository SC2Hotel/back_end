import {axios} from '@/utils/request'

const api = {
    orderPre: '/api/order'
}

export function reserveHotelAPI(data) {
    return axios({
        url: `${api.orderPre}/addOrder`,
        method: 'POST',
        data,
    })
}

export function getAllOrdersAPI() {
    return axios({
        url: `${api.orderPre}/getAllOrders`,
        method: 'GET',
    })
}

export function getUserOrdersAPI(data) {
    return axios({
        url: `${api.orderPre}/${data.userId}/getUserOrders`,
        method: 'GET',
    })
}

export function cancelOrderAPI(data) {
    return axios({
        url: `${api.orderPre}/${data.orderId}/annulOrder`,
        method: 'GET',
    })
}

export function addCommentAPI(data) {
    return axios({
        url: `${api.orderPre}/${data.orderId}/comment`,
        method: 'POST',
        data
    })
}

export function executeOrderAPI(data) {
    return axios({
        url: `${api.orderPre}/${data.userId}/${data.orderId}/executeOrder`,
        method: 'POST',
    })
}

export function delayCheckInOrderAPI(data) {
    return axios({
        url: `${api.orderPre}/${data.orderId}/delayCheckIn`,
        method: 'POST',
    })
}

export function getHotelCommentAPI(data) {
    return axios({
        url: `${api.orderPre}/${data}/getHotelComment`,
        method: 'Get'
    })
}

export  function getHotelOrderAPI(data) {
    return axios({
        url: `${api.orderPre}/${data}/allOrders`,
        method: 'Get'
    })
}
