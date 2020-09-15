/* eslint-disable eol-last */
import HttpRequest from './HttpRequest'

const baseUrl = process.env.VUE_APP_BASE_URL

export default class BaseApi {
  constructor () {
    this.http = new HttpRequest(baseUrl)
  }

  get (params) {
    return this.http.request(Object.assign({ method: 'GET' }, params))
  }

  post (data) {
    return this.http.request(Object.assign({ method: 'POST' }, data))
  }

  all (...request) {
    return Promise.all([...request])
  }
}
