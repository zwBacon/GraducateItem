import {createRouter , createWebHashHistory} from "vue-router";


const routes = [

    // 管理员新房通过页面
    {
      path: "/NewProductConfirmPage",
      component: import('../views/管理员页面/NewProductConfirmPage.vue')
    },

    // 登录注册路径
    {
        path:"/WelcomPage",
        component: import('../views/登录注册/WelcomPage.vue')
    },
    {
        path: "/SignOrLoadPage",
        component: import('../views/登录注册/SignOrLoadPage.vue')
    },
    {
        path: "/CreateAccountPage",
        component: import('../views/登录注册/CreateAccountPage.vue')
    },

//     主要功能页面
    {
        path: "/MainMeunShowPage/:turnMessage",
       component: import('../views/主要功能页面/MainMeunShowPage.vue'),
        children:[
            {
                path:"LeftSideLineView",
                component: () => import('../components/LeftSideLineView.vue')
            },
        ]
    },
    // 个人中心页面
    {
      path: "/PersonalCenterPage",
      component: () => import('../views/主要功能页面/个人中心页面/PersonalCenterPage.vue')
    },
    // 发布房源
    {
        path: "/AddNewHouseResourcePage",
        component: () => import('../views/主要功能页面/发布功能页面/AddNewHouseResourcePage.vue')
    },
    // 查看自己发布的房源
    {
        path: "/MyPublish",
        component: () => import('../views/主要功能页面/发布功能页面/MyPublish.vue')
    },
    // 看点页
    {
        path: "/GwylioPage",
        component: () => import('../views/主要功能页面/GwylioPage.vue')
    },
    // 产品详情页
    {
        // 商品ID,类型，归属人ID
        path:"/ProductInfoDetailsPage/:productID/:type/:userId/:flag",
        component: () => import('../views/主要功能页面/ProductInfoDetailsPage.vue'),
    },
    // 生成新订单页面
    {
        path: "/GenerateNewOrderPage/:productID/:type/:userId",
        component: () => import('../views/主要功能页面/订单功能页面/GenerateNewOrderPage.vue')
    },
    // 查看订单页面
    {
        path: "/ShowDepositOrderData",
        component: () => import('../views/主要功能页面/订单功能页面/ShowDepositOrderDataPage.vue')
    },
    // 聊天界面
    {
        path: "/TalkRecordPage/:userId/:toUserId",
        component: () => import('../views/主要功能页面/TalkRecordPage.vue')
    },
//     关注页面
    {
      path: "/SubscribedShowPage",
      component: () => import('../views/主要功能页面/SubscribedShowPage.vue')
    }
]

const router = createRouter({
    history:createWebHashHistory(),
    routes
})

// router.beforeEach((to, from, next) => {
//     if (to.path === '/LoadPage' || to.path === '/Welcome') {
//         // 登录或者注册才可以往下进行
//         next();
//     } else {
//         // 获取 token
//         var token = localStorage.getItem('token');
//         axios({
//             method:"get",
//             url:"http://localhost:1008/LoginController/getUserFromRedis?login_token="+token
//         }).then(function (resp){
//             localStorage.setItem("username",resp.data)
//             if(resp.data !== ''){
//                 next();
//             }else{
//                 next('/LoadPage')
//             }
//         })
//     }
// })


export default router