import BaseApi from '../BaseApi'
import { initFormData } from '../../utils'

class LoginApi extends BaseApi {
  login (data) {
    return this.post('/user/login', initFormData(data), {})
  }

  logout () {
    return this.get('/logout1', initFormData({}), {})
  }
}

export default new LoginApi()
