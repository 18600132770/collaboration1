import BaseApi from '../BaseApi'
import { initFormData } from '../../utils'

class LoginApi extends BaseApi {
  login (data) {
    return this.post({ url: '/user/login', data: initFormData(data) })
  }

  logout () {
    return this.get({ url: '/logout1', data: initFormData({}) })
  }
}

export default new LoginApi()
