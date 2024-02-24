import { createApp } from 'vue'
import App from './App.vue'
import router from "./router";
import elementIcon from "./plugins/icons";
import 'element-plus/dist/index.css'
import ElementPlus from 'element-plus'
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import globalCss from './assets/globalCSS.css'

const vuetify = createVuetify({
    components,
    directives,
})

import vant from 'vant'
import NutUI from "@nutui/nutui";
import "@nutui/nutui/dist/style.css";
import ant from 'ant-design-vue'
import { IconFont } from '@nutui/icons-vue';
import '@nutui/icons-vue/dist/style_iconfont.css';
import anticon from '@ant-design/icons-vue'
import Antd from 'ant-design-vue';
import store from "core-js/internals/shared-store";



const app = createApp(App)
app.use(router)
app.use(elementIcon)
app.mount('#app')
app.use(ElementPlus)
app.use(vuetify)
app.use(vant)
app.use(NutUI)
app.use(ant)
app.use(IconFont)
app.use(anticon)
app.use(Antd)
app.use(store)
app.use(globalCss)



