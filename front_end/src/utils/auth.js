import Cookies from 'js-cookie'

const TokenKey = 'NJUSE-TOKEN'

export function getToken() {
  return localStorage.getItem(TokenKey)
}

export function setToken(token) {
  localStorage.setItem(TokenKey,token)
}

export function removeToken() {
  localStorage.setItem(TokenKey,'')
}
