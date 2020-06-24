import { axios } from '@/utils/request'
const api = {
    hotelPre: '/api/hotel'
}
export function addRoomAPI(data) {
    return axios({
        url: `${api.hotelPre}/roomInfo`,
        method: 'POST',
        data,
    })
}
export function addHotelAPI(data) {
    return axios({
        url: `${api.hotelPre}/addHotel`,
        method: 'POST',
        data,
    })
}
export function delHotelAPI(data) {
    return axios({
        url: `${api.hotelPre}/${data.hotelId}/delete`,
        method: 'POST',
    })
}

export  function getHotelByManagerAPI(data) {
    return axios({
        url: `${api.hotelPre}/${data}/getHotel`,
        method: 'Get'
    })
}
export function delRoomByIdAPI(data) {
    return axios({
        url: `${api.hotelPre}/delRoom/${data}`,
        method: 'POST'
    })
}

