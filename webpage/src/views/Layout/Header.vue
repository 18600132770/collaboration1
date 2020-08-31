<template>
  <!-- 业务页面导航 -->
  <div class="top">
    <div class="logo">
      <img src="@/assets/images/logo.png" alt="">
      <h1 class="top-t">专业协同</h1>
    </div>
    <a-menu class="a-menu-demo nav" mode="horizontal" v-if="navList">
      <template v-for="(m,index) in navList">
        <a-menu-item
          :index="index+''"
          v-if="m.childrens.length==0"
          :class="currentPath === m.linkUrl ? 'is-active' : ''"
          :key="index"
          @click="goToView(m.linkUrl)"
        >
          <a>{{ m.title }}</a>
        </a-menu-item>
        <a-submenu v-else :index="index+''" :key="index">
          <template slot="title">{{ m.title }}</template>
          <a-menu-item v-for="(n,index2) in m.childrens" :index="index+'-'+index2" :key="index2">
            <a :href="n.linkUrl" target="_blank">{{ n.title }}</a>
          </a-menu-item>
        </a-submenu>
      </template>
    </a-menu>

    <div v-if="loginStatus" class="top-user">
      <a-dropdown class="top-head">
        <div class="head-box dvm">
          <!-- <img th:src="@{/images/head-pic.png}" alt="" class="head-pic"><span
          class="dvm" sec:authentication="name"></span>-->
          <img src="/user/showImg" alt class="head-pic" />
          <span v-if="userName" class="dvm">{{ userName}}</span>
        </div>
        <a-dropdown-menu slot="dropdown" class="top-head-ul">
          <a-dropdown-item
            v-for="(l, index) in drapdownMenu"
            :key="index"
            @click.native="goToView(l.link)"
          >
            <a>{{ l.title }}</a>
          </a-dropdown-item>
        </a-dropdown-menu>
      </a-dropdown>
      <a @click="handleLogout">
        <svg class="icon" aria-hidden="true">
          <use xlink:href="#icon-logout" />
        </svg>
      </a>
    </div>
    <div v-if="!loginStatus" class="top-user">
      <div class="head-box dvm">
        <a @click="goLogin">登录</a>
      </div>
    </div>
  </div>
</template>
<script>
import { getToken, removeAll } from '@/api/token'
import { mapGetters, mapActions } from 'vuex'

const contextRoot = '/' // '/m/'
const navList = [
  {
    title: '首页',
    linkUrl: '/',
    active: false,
    childrens: []
  },
  {
    title: '项目信息',
    linkUrl: contextRoot + 'projects',
    active: false,
    childrens: []
  },
  {
    title: '专业协同',
    linkUrl: contextRoot + 'professional',
    active: false,
    childrens: []
  },
  {
    title: '人员工作信息',
    linkUrl: contextRoot + 'kgmanager',
    active: false,
    childrens: []
  },
  {
    title: '人员信息管理',
    linkUrl: contextRoot + 'search',
    active: false,
    childrens: []
  },
  {
    title: '关于',
    linkUrl: '#',
    active: false,
    childrens: []
  }
]

const drapdownMenu = [
  {
    title: '我的收藏',
    link: '/collect/myFavModels'
  },
  {
    title: '驾驶舱',
    link: '/boats'
  },
  {
    title: '个人设置',
    link: `${contextRoot}userinfo`
  },
  {
    title: '数据源管理',
    link: '/system/datasource'
  },
  {
    title: '个人数据管理',
    link: `${contextRoot}myExcel`
  },
  {
    title: '系统管理',
    link: `${contextRoot}system`
  }
]

export default {
  props: {
    flag: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      loginStatus: '',
      isLogin: '',
      navList: navList,
      drapdownMenu,
      autofocus: true,
      isOpen: false,
      search_active: false,
      message: '',
      searchword: '',
      activeIndex: '0',
      userName: localStorage.getItem('userName')
    }
  },
  mounted () {
    this.loginStatus = getToken()
  },
  computed: {
    ...mapGetters(['currentPath', 'headImg', 'userInfo'])
  },
  watch: {
    loginStatus (nv) {
      this.isLogin = nv
    },
    userInfo (nv) {
      this.userName = nv.userName
    }
  },
  methods: {
    ...mapActions({
      logout: 'logout'
    }),
    goLogin () {
      this.$router.push({ path: '/login' })
    },
    isAuthenticated () {
      return this.loginStatus
    },
    handleLogout () {
      this.logout()
        .then(resp => {
          if (resp.code === 200) {
            removeAll()
            this.loginStatus = getToken()
            this.goLogin()
          }
        })
        .catch(error => {
          this.$message.error(error.msg || '退出失败')
        })
    },
    goToView (path) {
      this.$router.push({ path: path })
    },
    searchResult () {
      window.location.href = '/search?keyword=' + this.searchword
    },
    searchVoice () {
      window.location.href = '/searchVoice?message=' + this.message
    }
  }
}
</script>
<style lang="less">
.logo {
  width: 300px;
  display: flex;
  align-items: center;
  img {
    width: 40px;
    height: 40px;
    margin-right: 20px;
  }
}
#header::before {
  left: 0;
}
.top {
  display: flex;
  justify-content: space-between;
  height: 65px;
  padding: 0 28px 0 20px;
  line-height: 65px;
  text-align: center;
  /* background: url('~@/assets/images/manager/top-bj.png') no-repeat center right; */
  background-color: rgb(48, 49, 51);
  box-shadow: 0 0 29px rgba(53, 152, 219, 0.19);
}
.top-t {
  float: left;
  font-size: 24px;
  font-weight: normal;
  color: #fff;
  margin: 0;
}
.top-user {
  float: right;
  color: var(--lc);
  color: #bde4ff;
}
.top-user a {
  color: #bde4ff;
  cursor: pointer;
  font-size: 14px;
  margin-left: 5px;
}
.head-pic {
  display: inline-block;
  width: 40px;
  height: 40px;
  margin-right: 8px;
  vertical-align: middle;
  border-radius: 50%;
}
.logout {
  margin-left: 27px;
  vertical-align: middle;
}
.logout:hover {
  color: #fff;
}
.logout svg {
  font-size: 16px;
}
#appheader,
.tab-nav {
  min-width: 1366px;
}

.voice {
  position: absolute;
  width: 0px;
  height: 0px;
  border-radius: 1px;
  overflow: hidden;
}

.dvm {
  color: #fff;
}

/*top-nav*/
.top .ant-menu {
  background: transparent !important;
  border: none !important;
}
.top .ant-menu-horizontal > .ant-menu-item,
.top .ant-menu-horizontal > .a-submenu .a-submenu__title,
.top .a-submenu__title i {
  color: #fff;
}
.top .ant-menu-horizontal .ant-menu-item:not(.is-disabled):focus,
.top .ant-menu-horizontal .ant-menu-item:not(.is-disabled):hover,
.top .ant-menu-horizontal > .a-submenu:focus .a-submenu__title,
.top .ant-menu-horizontal > .a-submenu:hover .a-submenu__title {
  outline: 0;
  color: #bde4ff;
  background: transparent;
}
.top .ant-menu-horizontal > .ant-menu-item:not(.is-disabled):hover {
  border-bottom: 2px solid #ff8a00 !important;
}
.top .ant-menu-horizontal > .ant-menu-item:not(.is-disabled):hover a {
  color: #ff8a00 !important;
}
.top .ant-menu-horizontal > .a-submenu:focus,
.ant-menu-horizontal > .a-submenu:hover .a-submenu__title i {
  color: #bde4ff;
}
.top .ant-menu--popup {
  min-width: 120px;
  margin-top: -2px;
  padding: 0;
  background: #193446;
  border-top: 3px solid #ff8a00;
}
.top .ant-menu-horizontal .ant-menu .ant-menu-item,
.top .ant-menu-horizontal .ant-menu .a-submenu__title {
  float: none;
  height: 48px;
  line-height: 48px;
  text-align: center;
  color: #fff;
  background-color: transparent;
  border-bottom: 1px solid #204056;
}
.top .a-a-menu--popup .ant-menu-item:not(.is-disabled):focus,
.top .a-a-menu--popup .ant-menu--popup .ant-menu-item:not(.is-disabled):hover,
.top .a-a-menu--popup .a-dropdown-menu__item:not(.is-disabled):hover {
  color: #fff;
  background: #132734;
}
.top .ant-menu-horizontal > .a-submenu .a-submenu__title,
.top .ant-menu-horizontal > .ant-menu-item {
  height: 65px;
  line-height: 65px;
}
.top .ant-menu-horizontal > .a-submenu.is-active .a-submenu__title {
  border-bottom: 2px solid #ff8a00;
  color: #bde4ff;
}
.top .ant-menu-horizontal > .ant-menu-item a {
  display: block;
  color: #fff;
}
.top .ant-menu-horizontal > .ant-menu-item.is-active {
  border-bottom: 3px solid #ff8a00 !important;
  color: #bde4ff;
}
.top {
  /deep/.ant-menu-horizontal > .ant-menu-item:hover, .ant-menu-horizontal > .ant-menu-submenu:hover, .ant-menu-horizontal > .ant-menu-item-active, .ant-menu-horizontal > .ant-menu-submenu-active, .ant-menu-horizontal > .ant-menu-item-open, .ant-menu-horizontal > .ant-menu-submenu-open, .ant-menu-horizontal > .ant-menu-item-selected, .ant-menu-horizontal > .ant-menu-submenu-selected {
    border-bottom: 2px solid #ff8a00;
  }
  .ant-menu-horizontal > .ant-menu-item-selected > a {
    color: #ff8a00 !important;
  }
}
.top-head {
  color: var(--lc);
  cursor: pointer;
}
</style>
