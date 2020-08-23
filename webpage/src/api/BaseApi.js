/* eslint-disable eol-last */
import HttpRequest from './HttpRequest'

const baseUrl = process.env.VUE_APP_BASE_URL

export default class BaseApi {
  constructor () {
    this.http = new HttpRequest(baseUrl)
  }

  get (url, params = {}) {
    return this.http.request({ url, method: 'GET', params })
  }

  post (url, data = {}, params = {}, headers = {}) {
    return this.http.request({ url, method: 'POST', data, params, headers })
  }

  all (...request) {
    return Promise.all([...request])
  }
}
