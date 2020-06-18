import {axios} from '@/utils/request'

const api = {
    hotelPre: '/api/hotel'
}

export function getHotelsAPI() {
    return axios({
        url: `${api.hotelPre}/all`,
        method: 'get',
    })
}

export function getHotelByIdAPI(param) {
    return axios({
        url: `${api.hotelPre}/${param.hotelId}/detail`,
        method: 'GET',
    })
}

export function getBizregionsAPI() {
    return axios({
        url: `${api.hotelPre}/bizRegions`,
        method: 'Get'
    })
}

export function getHotelByBizAndAddAPI(data) {
    return axios({
        url: `${api.hotelPre}/search/${data.bizRegion}`,
        method: 'Get',
        params: {address: data.address},
    })
}
