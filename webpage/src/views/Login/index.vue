<template>
  <div class="login-wrap">
    <div class="login">
      <div class="login-box d-flex container">
        <a-row>
          <a-col :span="12">
            <div class="logo-title">
              <div class="login-top">
                <a href="javascript:;" class="login-logo"></a>
                <p class="logi-t-p">协同办公</p>
              </div>
            </div>
          </a-col>
          <a-col :span="12">
            <div class="login-form">
              <a-form  layout="horizontal" :wrapper-col="{ span: 24 }">
                <!-- <input type="hidden" name="_csrf" th:value="${_csrf.token}"> -->
                <!-- 使用单点登录 登录系统的时候，需要将此行代码注释掉 -->
                <!-- <input type="hidden" name="return" th:value="${returnurl}"> -->
                <!-- <div style="margin-bottom: 12px;color:red;" th:text="${MSG}"></div> -->
                <div
                  style="text-align: left;margin-bottom: 12px;color: red;font-size: 14px;"
                  v-if="showError"
                >用户名或密码错误</div>

                <a-form-item>
                  <a-input autofocus placeholder="用户名" name="username" v-model="username">
                    <a-icon slot="prefix" type="user" style="color: rgba(0,0,0,.25)" />
                  </a-input>
                </a-form-item>
                <a-form-item>
                  <a-input type="password" placeholder="密码" name="password" v-model="password">
                    <a-icon slot="prefix" type="lock" style="color: rgba(0,0,0,.25)" />
                  </a-input>
                </a-form-item>
                <a-form-item v-show="showcode">
                  <svg class="icon" aria-hidden="true">
                    <use xlink:href="#icon-key" />
                  </svg>
                  <img
                    v-bind:src="imageCodeUrl"
                    id="ImageCatpcha"
                    @click="getCaptcha"
                    alt="正在加载"
                    class="yzm"
                  />
                  <a-input placeholder="验证码" v-model="code" class="dis-table" name="code"></a-input>
                </a-form-item>
                <a-form-item>
                  <!--<el-button type="submit" @click="submitForm('ruleForm2')" class="login-btn">登录</el-button>-->
                  <a-button
                    class="login-form-button"
                    type="primary"
                    @click="handleLogin"
                  >
                    <span>登录</span>
                  </a-button>
                  <input type="hidden" name="time" />
                  <input type="hidden" name="md5" />
                </a-form-item>
                <!-- <div  class="forget-a"><a href="#">忘记密码</a></div>-->
              </a-form>
            </div>
          </a-col>
        </a-row>
      </div>
    </div>
  </div>
</template>

<script>
import { LoginApi } from '@/api'
import { mapGetters, mapMutations, mapActions } from 'vuex'
import { getCookie /* setToken */ } from '../../api/token'

export default {
  name: 'login',
  data () {
    return {
      formLayout: 'horizontal',

      showcode: getCookie('showcode') === 1,
      activeName: 'first',
      username: 'admin',
      password: '123456',
      md5: '',
      time: '',
      showError: '',
      imageCodeUrl: '',
      code: '' // 验证码
      // crsfToken: ''
    }
  },
  created () {
  },
  computed: {
    ...mapGetters(['crsfToken', 'loginPreUrl'])
  },
  methods: {
    ...mapMutations({
      setCsrfToken: 'SET_CSRF_TOKEN',
      setUsername: 'SET_USERNAME',
      setHeadImg: 'SET_HEAD_IMG'
    }),
    ...mapActions({
      getCsrfToken: 'getCsrfToken',
      getUserInfo: 'getUserInfo'
    }),
    handleLogin () {
      const _this = this

      const data = {
        username: this.username,
        password: this.password
        // code: this.code,
        // md5: this.md5,
        // time: this.time,
        // _csrf: this.crsfToken
      }

      LoginApi.login(data)
        .then(resp => {
          // if (resp.code === 200) {
          // 登录成功跳转到首页
          // _this.$router.push({ path: _this.loginPreUrl })
          _this.$router.push({ path: '/' })

          // if (resp.data) _this.setUsername(resp.data.username)
          // else _this.setUsername(_this.username)

          // 将csrfToken放入缓存中
          // const { csrf } = resp
          // setToken(csrf, 'CSRF-TOKEN')

          // 获取用户信息
          // this.getUserInfo()
          // }
        })
        .catch(error => {
          if (error) {
            this.showError = true
            this.showcode = getCookie('showcode') === 1
          }
        })
    },
    getCaptcha () {
      LoginApi.getCaptcha().then(resp => {
        if (resp.code === 200) {
          const { data } = resp
          this.md5 = data.md5
          this.time = data.time
          this.imageCodeUrl = data.image

          // 获取CsrfToken
          this.getCsrfToken()
        }
      })
    }
  }
}
</script>

<style lang="less">
.login-wrap {
  height: 100%;
  background: url('~@/assets/images/login-bg.jpeg') no-repeat center;
  background-size: cover;
}
.login-top {
  text-align: center;
}
.login {
  position: absolute;
  top: 50%;
  bottom: 0;
  left: 0;
  right: 0;
  margin: -300px auto 0;
}
.login-logo {
  display: inline-block;
  width: 72px;
  height: 72px;
  vertical-align: middle;
  background: url('~@/assets/images/logo.png') no-repeat;
  background-size:  100% 100%;
  margin-right: 25px;
}
.logi-t-p {
  display: inline-block;
  margin-left: 25px;
  vertical-align: middle;
  font-size: 36px;
  font-weight: normal;
  color: #fff;
  margin: 0;
}
.login-box {
  width: 860px;
  // width: 534px; /* height: 444px; */
  min-height: 400px;
  margin: 55px auto 0;
  background: #fff;
  border: 1px solid #e4e4e4;
  border-radius: 6px;
  overflow: hidden;
}
.login-form .a-form-item__label {
  display: none;
}
.login-form .a-input__inner {
  padding-left: 38px;
  height: 46px;
  line-height: 46px;
  border: 1px solid #e4e4e4;
  border-radius: 4px;
}
.login-form button:hover,
.login-btn.cur {
  color: #fff;
  background: #156498;
  border-color: #156498;
}
.login-wrap .a-tabs__content {
  padding: 45px 75px;
  background: #fff;
}
.login-wrap .a-tabs__header {
  margin: 0;
}
.login-wrap .a-tabs__item {
  box-sizing: border-box;
  width: 50%;
  height: 69px;
  line-height: 69px;
  font-size: 18px;
  color: #999;
  background: #fafafa;
  border-bottom: 2px solid #e4e7ed;
}
.login-wrap .a-tabs__item.is-active {
  color: #333;
  background: #fff;
}
.login-wrap .a-tabs__active-bar {
  opacity: 0;
}
.login-wrap .a-tabs__nav {
  float: none;
  text-align: center;
}
.login-wrap .a-form-item {
  position: relative;
  margin-bottom: 18px;
}
.login-wrap .a-form-item__content > svg {
  position: absolute;
  top: 14px;
  left: 14px;
  font-size: 18px;
  color: #ccc;
  z-index: 10;
}
.yzm {
  float: right;
  width: 113px;
  height: 45px;
  margin-left: 20px;
}
.forget-a {
  display: block;
  text-align: right;
  color: #b4b4b4;
}
.dis-table {
  display: table-cell;
  width: 900px;
}
[v-cloak] {
  display: none !important;
}

.logo-title {
  height: 400px;
  background-color: #1890ff;
  display: flex;
  align-items: center;
  justify-content: space-around;
  .login-top {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
  }
}
.login-form {
  height: 400px;
  display: flex;
  align-items: center;
  justify-content: center;
  .ant-form {
    width: 80%;
  }
}
.login-form-button {
  width: 100%;
}
</style>
