<template>
  <div class="home">
    <div id="header">
      <div class="index-wrap">
        <div id="logo">
          <div class="logo-img"></div>
          <a href="javascript:;">
            北京华创建宸
          </a>
        </div>
        <div class="index-nav-wrap">
          <ul class="main-nav">
            <li class="has-dropdown">
                <a href="javascript:;">关于我们</a>
                <div class="main-nav-dropdown">
                  <div class="main-nav-p">
                    <!-- <div class="title">分析</div> -->
                    <a href="javascript;"
                      >联系我们</a
                    >
                    <a href="javascript;"
                      >公司简介</a
                    >
                    <a href="javascript;">合作伙伴</a>
                  </div>
                  <!-- <div class="main-nav-p" style="width: 220px;">
                    <div class="title">工具</div>
                    <a href="https://data.qq.com/product/xg"
                      >腾讯移动推送（信鸽）</a
                    >
                    <a
                      href="https://www.qcloud.com/product/postgresql.html"
                      target="_blank"
                      >云数据库（TencentDB for PostgreSQL）</a
                    >
                  </div>
                  <div class="main-nav-p">
                    <div class="title">平台</div>
                    <a
                      href="https://www.qcloud.com/product/tbds.html"
                      target="_blank"
                      >大数据处理套件</a
                    >
                    <a
                      href="https://data.qq.com/product/oceanus"
                      target="_blank"
                      >大数据流处理分析平台（Oceanus）</a
                    >
                    <a href="http://tuijian.qq.com/" target="_blank"
                      >腾讯智能推荐</a
                    >
                  </div> -->
                </div>
            </li>
              <!-- <li>
                <a href="https://data.qq.com/blog">关于</a>
            </li> -->
          </ul>
        </div>
      </div>
    </div>
    <div class="index-banner" style="height: calc(100vh - 142px);">
      <div class="index-banner-text-wrap banner-0 active">
        <div class="index-banner-text">
          <!-- <img
            class="slideUp"
            src="@/assets/images/banner-text-2-1.png"
            style="height: 60px;"
            alt
          /> -->
          <div class="slogan">整合化简，多元协作，融汇创新</div>
          <div class="split"></div>
        </div>
      </div>

      <div id="particles-js" style="height: 100%; width: 100%;"></div>
    </div>

    <div class="index-bright" style="height: 142px;">
      <div class="index-wrap">
        <ul class="bd-report-list" id="reportList">
          <li>
            <a href="http://49.235.198.76:8888" target="_blank">
              <!-- <i class="icon-index-1-1"></i> -->
              <i class="icon-index-1"></i>
              <p class="index-head">协同设计管理平台</p>
              <div class="index-desc">协同共享，互联互通</div>
            </a>
          </li>
          <li>
            <a href="http://49.235.198.76:8888/#/largeDataScreen" target="_blank">
              <!-- <i class="icon-index-1-2"></i> -->
              <i class="icon-index-2"></i>
              <p class="index-head">大数据监控及分析平台</p>
              <div class="index-desc">海量数据，无限未来</div>
            </a>
          </li>
          <li>
            <a href="javascript:;" target="_blank">
              <!-- <i class="icon-index-1-3"></i> -->
              <i class="icon-index-3"></i>
              <p class="index-head">物资流转管理平台</p>
              <div class="index-desc">因势利导，资源共享</div>
            </a>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import '@/assets/css/all.css'
// @ is an alias to /src
import 'particles.js'

export default {
  name: 'Home',
  components: {},
  mounted () {
    // this.initPaticles()
  },
  methods: {
    initPaticles () {
      window.particlesJS('particles-js', {
        particles: {
          number: {
            value: 80,
            density: {
              enable: true,
              value_area: 800
            }
          },
          color: {
            value: '#827397'
          },
          shape: {
            type: 'circle',
            stroke: {
              width: 0,
              color: '#000000'
            },
            polygon: {
              nb_sides: 5
            },
            image: {
              src: 'img/github.svg',
              width: 100,
              height: 100
            }
          },
          opacity: {
            value: 0.5,
            random: false,
            anim: {
              enable: false,
              speed: 1,
              opacity_min: 0.1,
              sync: false
            }
          },
          size: {
            value: 5,
            random: true,
            anim: {
              enable: false,
              speed: 10,
              size_min: 0.1,
              sync: false
            }
          },
          line_linked: {
            enable: true,
            distance: 150,
            color: '#827397',
            opacity: 0.4,
            width: 1
          },
          move: {
            enable: true,
            speed: 2,
            direction: 'none',
            random: false,
            straight: false,
            out_mode: 'out',
            attract: {
              enable: false,
              rotateX: 600,
              rotateY: 1200
            }
          }
        },
        interactivity: {
          detect_on: 'canvas',
          events: {
            onhover: {
              enable: true,
              mode: 'bubble' // [/* "grab",  */"bubble"/* , 'repulse' */],
            },
            onclick: {
              enable: false,
              mode: 'push'
            },
            resize: true
          },
          modes: {
            grab: {
              distance: 400,
              line_linked: {
                opacity: 1
              }
            },
            bubble: {
              distance: 400,
              size: 6,
              duration: 2,
              opacity: 0.4,
              speed: 3
            },
            repulse: {
              distance: 200
            },
            push: {
              particles_nb: 4
            },
            remove: {
              particles_nb: 2
            }
          }
        },
        retina_detect: true,
        config_demo: {
          hide_card: false,
          background_color: '#b61924',
          background_image: '',
          background_position: '50% 50%',
          background_repeat: 'no-repeat',
          background_size: 'cover'
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>
.home {
  height: 100%;
  width: 100%;
  padding-top: 65px;
  .index-banner .banner-0 {
    top: 200px;
  }
  .main-nav li:hover, .main-nav-dropdown {
    background: rgba(245, 34, 45, 0.2);
  }
  .main-nav-dropdown {
    padding: 10px 22px;
    a {
      line-height: 35px;
    }
  }
  .main-nav-p {
    display: inline-block;
    vertical-align: top;
    padding: 0 10px;
    width: 72px;
  }
  .main-nav li > a {
    padding: 22px 30px;
  }
  #logo {
    width: 300px;
    height: 50px;
    text-indent: unset;
    a {
      display: inline-block;
      position: absolute;
      left: 60px;
      height: 50px;
      top: 0;
      width: 300px;
      z-index: 300;
      color: #fff;
      font-size: 26px;
      line-height: 50px;
      text-align: left;
    }
    .logo-img {
      width: 50px;
      height: 50px;
      background: url(~@/assets/images/logo.png);
      background-size: 100% 100%;
    }
  }
  .bd-report-list > li i {
    height: 68px;
  }
  .bd-report-list > li {
    .icon-index-1 {
      background-image: url(~@/assets/images/xietong.png);
      background-size: 100% 100%;
    }
    .icon-index-2 {
      background-image: url(~@/assets/images/daping.png);
      background-size: 100% 100%;
    }
    .icon-index-3 {
      background-image: url(~@/assets/images/wuzi.png);
      background-size: 100% 100%;
    }
  }
}
#header::before {
  // background-color: rgba(42, 49, 57, 0.48);
  background-color: transparent;
}
#particles-js {
  background-image: url(~@/assets/images/banner.jpg);
  background-size: 100% 100%;
}
.slogan {
  font-size: 50px;
  color: #eee;
  user-select: none;
}
@media screen and (min-width: 1920px) {
  .home {
    .index-banner .banner-0 {
      top: 300px;
    }
  }
}
</style>
