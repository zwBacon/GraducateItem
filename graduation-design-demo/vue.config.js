const { defineConfig } = require('@vue/cli-service')
const AutoImport = require('unplugin-auto-import/webpack')
const Components = require('unplugin-vue-components/webpack')
const { ElementPlusResolver } = require('unplugin-vue-components/resolvers')
const { VantResolver } = require('unplugin-vue-components/resolvers');
const ComponentsPlugin = require('unplugin-vue-components/webpack');
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave:false,
  configureWebpack: {
    plugins: [
      AutoImport({
        resolvers: [ElementPlusResolver()]
      }),
      Components({
        resolvers: [ElementPlusResolver()]
      }),
      ComponentsPlugin({
        resolvers: [VantResolver()],
      }),
    ]
  },
  css: {
      loaderOptions: {
          // 给 sass-loader 传递选项
          scss: {
              // @/ 是 src/ 的别名
              // 注意：在 sass-loader v7 中，这个选项名是 "data"
              prependData: ` 
            @import "@/assets/custom_theme.scss";
            @import "@nutui/nutui/dist/styles/index.scss";
            `,
          }
      },
  },
  devServer: { // 环境配置
    // proxy:{
    //   '/ws': {
    //     ws: false
    //   }
    // },
    // proxy: {  //配置跨域
    //   '/el': {
    //     target: 'https://jsonplaceholder.typicode.com/posts/',  //这里后台的地址模拟的;应该填写你们真实的后台接口
    //     changOrigin: true,  //允许跨域
    //     pathRewrite: {
    //       '^/el': ''
    //     }
    //   },
    // },
    host: '192.168.10.1',
    client:{
      webSocketURL:'ws://localhost:2000/chat'
    },
    port: 8888,
    https: false,
    hot: true,
    historyApiFallback: true,
    allowedHosts: "all",
    open: false // 配置自动启动浏览器
  },

})
