<template>
  <div class="home">
    <div id="header">
      <div class="index-wrap">
        <div id="logo">
          <a href="javascript:;">
            <h1>协同平台</h1>
          </a>
        </div>
        <div class="index-nav-wrap">
          <ul class="main-nav">
            <!-- <li class="has-dropdown">
                <a href="javascript:;"
                  >大数据产品<i class="icon-arrow-down"></i>
                </a>

                <div class="main-nav-dropdown">
                  <div class="main-nav-p">
                    <div class="title">分析</div>
                    <a href="https://data.qq.com/product/mta"
                      >腾讯移动分析（MTA）</a
                    >
                    <a href="https://data.qq.com/product/ta"
                      >网站数据分析（TA）</a
                    >
                    <a href="https://data.qq.com/product/h5">HTML5数据分析</a>
                    <a href="https://data.qq.com/product/wx">微信小程序分析</a>
                  </div>
                  <div class="main-nav-p" style="width: 220px;">
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
                  </div>
                </div>
            </li>-->
            <li>
                <a href="https://data.qq.com/reports">首页</a>
              </li>
              <li>
                <a href="https://data.qq.com/blog">关于</a>
            </li>
            <li>
              <router-link :to="'/login'">登录</router-link>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <div class="index-banner" style="height: calc(100vh - 142px);">
      <div class="index-banner-text-wrap banner-0 active">
        <div class="index-banner-text">
          <img
            class="slideUp"
            src="@/assets/images/banner-text-2-1.png"
            style="height: 60px;"
            alt
          />
          <div class="split"></div>
        </div>
      </div>

      <div id="particles-js" style="height: 100%; width: 100%;"></div>
    </div>

    <div class="index-bright" style="height: 142px;">
      <div class="index-wrap">
        <ul class="bd-report-list" id="reportList">
          <li>
            <a href="javascript:;" target="_blank">
              <i class="icon-index-1-1"></i>
              <p class="index-head">协同平台介绍</p>
              <div class="index-desc">海量数据，无限未来</div>
            </a>
          </li>
          <li>
            <a href="javascript:;" target="_blank">
              <i class="icon-index-1-2"></i>
              <p class="index-head">协同平台</p>
              <div class="index-desc">海量数据，无限未来</div>
            </a>
          </li>
          <li>
            <a href="javascript:;" target="_blank">
              <i class="icon-index-1-3"></i>
              <p class="index-head">数据报告</p>
              <div class="index-desc">海量数据，无限未来</div>
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
    this.initPaticles()
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
}
</style>
