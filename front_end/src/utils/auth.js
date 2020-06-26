import Cookies from 'js-cookie'

const TokenKey = 'NJUSE-TOKEN'
const LongTokenKey = 'NJUSE-LONG-TOKEN'

export function getToken() {
  return localStorage.getItem(TokenKey)
}

export function setToken(token) {
  localStorage.setItem(TokenKey,token)
}

export function setLongToken(longToken) {
  localStorage.setItem(LongTokenKey,longToken)
}

export function getLongToken() {
  return localStorage.getItem(LongTokenKey)
}

export function removeToken() {
  localStorage.setItem(TokenKey,'')
}



