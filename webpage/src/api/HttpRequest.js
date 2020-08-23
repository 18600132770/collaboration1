import axios from 'axios'
import { setToken, getToken } from './token'
import { Message } from 'element-ui'

export default class HttpRequest {
  constructor (baseUrl = '') {
    this.baseUrl = baseUrl
    // 存储请求队列
    this.queue = {}
  }

  getCommonConfig () {
    const config = {
      baseURL: this.baseUrl,
      // 请求超时时间
      timeout: 10 * 1000,
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8',
        // 'X-URL-PATH': location.pathname
        'X-Requested-With': 'XMLHttpRequest'
      }
    }
    return config
  }

  // 销毁请求实例
  destroy (url) {
    delete this.queue[url]
    if (!Object.keys(this.queue).length) {
    }
  }

  // 请求拦截
  interceptors (instance, url) {
    // 添加请求拦截器
    instance.interceptors.request.use(
      config => {
        // 这里可以添加全局loading...
        if (!Object.keys(this.queue).length) {
          //
        }
        this.queue[url] = true
        // 请求发送前处理. 添加token
        //
        if (
          url !== process.env.VUE_APP_BASE_URL + '/login' &&
          url !== process.env.VUE_APP_BASE_URL + '/logout' &&
          config.method !== 'get'
        ) {
          config.headers['X-CSRF-TOKEN'] = getToken('CSRF-TOKEN')
        }

        return config
      },
      error => {
        // 请求错误处理
        return Promise.reject(error)
      }
    )

    // 添加响应拦截器
    instance.interceptors.response.use(
      res => {
        // 请求响应后销毁请求实例
        this.destroy(url)
        let { data } = res
        // code 验证
        let { code } = data
        if (code !== 200) {
          // 无权限
          if (code === 403) {
            Message({
              showClose: true,
              message: data.msg || '没有权限!',
              type: 'error'
            })
            // window.location.href = '/auth'
            return Promise.reject(data)
          }
          // 错误401 token无效 删除token 返回鉴权中心(登录页)
          if (code === 401) {
            // 其他错误 处理
            Message({
              showClose: true,
              message: '登录超时，请重新登录！',
              type: 'warning'
            })
            window.location.href =
              process.env.NODE_ENV === 'development' ? '/#/login' : '/login'
          }
          if (code === 500) {
            Message({
              showClose: true,
              message: data.msg || '服务器错误!',
              type: 'error'
            })
            return Promise.reject(data)
          }
          if (code === 406) {
            Message({
              showClose: true,
              message: data.msg || '请求参数不正确!',
              type: 'error'
            })
            return Promise.reject(data)
          }
        }

        // 存储token
        if (
          res.config.url === process.env.VUE_APP_BASE_URL + '/login' &&
          res.headers['x-auth-token']
        ) {
          setToken(res.headers['x-auth-token'])
        }

        return data
      },
      error => {
        this.destroy(url)

        if (error && error.response) {
          // 响应错误处理
          let { data, status } = error.response
          // http状态码
          if (status === 500) {
            Message({
              showClose: true,
              message: '服务器错误，请稍后重试',
              type: 'error'
            })
            // 返回登录界面
            window.location.href =
              process.env.NODE_ENV === 'development' ? '/#/login' : '/login'
          }
          // 无权限
          if (data && data.status === 403) {
            Message({
              showClose: true,
              message: data.message || '没有权限!',
              type: 'error'
            })
            // 移除缓存
            // removeAll()
            window.location.href =
              process.env.NODE_ENV === 'development' ? '/#/login' : '/login'
            return Promise.reject(data)
          }
        } else if (error && error.code === 'ECONNABORTED') {
          Message.error('请求超时，请检查网络后刷新页面重试')
        }

        return Promise.reject(error)
      }
    )
  }

  request (options) {
    const instance = axios.create()
    // 合并options
    let headers = Object.assign(this.getCommonConfig().headers, options.headers)
    options = Object.assign(this.getCommonConfig(), options)
    options.headers = headers
    // 注册拦截器
    this.interceptors(instance, options.url)
    // 返回实例
    return instance(options)
  }
}
