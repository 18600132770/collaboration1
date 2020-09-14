import BaseApi from '../BaseApi'
// import { initFormData } from '../../utils'

class ProfessionalApi extends BaseApi {
  /**
   * 获取项目列表
   * @param {Object} data 参数 {currentPage: 1, pageSize: 10, projectName: ''}
   */
  findByProjectNameAndRoles (data) {
    return this.get({
      url: '/projectSummary/findByProjectNameAndRoles',
      params: data
    })
  }
}

export default new ProfessionalApi()
