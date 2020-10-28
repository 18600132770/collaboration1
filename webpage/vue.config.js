// const path = require('path')
const webpack = require('webpack')

// const resolve = dir => path.join(__dirname, dir)

module.exports = {
  // 项目部署路径
  publicPath: '/',
  chainWebpack: config => {
    config.plugin('provide').use(webpack.ProvidePlugin, [
      {
        /* THREE: 'three/build/three',
        $: 'jquery',
        jQuery: 'jquery',
        jquery: 'jquery',
        'window.jQuery': 'jquery',
        d3: 'd3',
        E: 'wangeditor',
        _: 'lodash' */
      }
    ])
  },
  // 打包时不生成.map文件
  productionSourceMap: false,
  // 启用dll,动态库文件每次打包生成的vendor的[chunkhash]值就会一样,
  // 用于后期迭代时 vendor 缓存.
  // dll: true,
  pages: {
    index: {
      // page 的入口
      entry: 'src/main.js',
      // 模板来源
      template: 'public/index.html',
      // 在 dist/index.html 的输出
      filename: 'index.html',
      // 当使用 title 选项时，
      // template 中的 title 标签需要是 <title><%= htmlWebpackPlugin.options.title %></title>
      title: '华创建宸',
      // 在这个页面中包含的块，默认情况下会包含
      // 提取出来的通用 chunk 和 vendor chunk。
      chunks: ['chunk-vendors', 'chunk-common', 'index']
    }
  },
  devServer: {
    port: 8088, // 端口号
    proxy: {
      '/': {
        target: 'http://127.0.0.1:8083/crud/',
        ws: false, // sockjs.js?9be2:1683 WebSocket connection to 'ws://192.168.191.3:8085/sockjs-node/557/cclttzfg/websocket' failed: Invalid frame header
        changeOrigin: true,
        pathRewrite: {
          '^/': ''
        }
      }
    }
  }
}
