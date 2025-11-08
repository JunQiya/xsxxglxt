import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

<<<<<<< HEAD

Vue.use(VueRouter)


=======
Vue.use(VueRouter)

>>>>>>> e6897d3eee7dd92889ec4638067e9f9148ca1f07
const constantRoutes = [
    {
        path: '/',
        name: 'Home',
        component: Home,
        redirect: '/index',
        children: [
            {
                path: 'index',
                name: 'index',
                component: Home,
<<<<<<< HEAD
                meta: { title: '首 页' }
            }
        ],
=======
                meta: {title: '首 页'}
            }
        ]
>>>>>>> e6897d3eee7dd92889ec4638067e9f9148ca1f07
    },
    {
        path: '/404',
        component: () => import('../views/error-page/404'),
<<<<<<< HEAD
        meta: { title: '404' }
=======
        meta: {title: '404'}
>>>>>>> e6897d3eee7dd92889ec4638067e9f9148ca1f07
    },
    {
        path: '/personal/info',
        component: () => import('../views/personal/info'),
<<<<<<< HEAD
        meta: { title: '个人信息' }
=======
        meta: {title: '个人信息'}
>>>>>>> e6897d3eee7dd92889ec4638067e9f9148ca1f07
    },
    {
        path: '/personal/change_password',
        component: () => import('../views/personal/changePassword'),
<<<<<<< HEAD
        meta: { title: '修改密码' }
    }

]

const createRouter = () => new VueRouter({
    scrollBehavior: () => ({ y: 0 }),
=======
        meta: {title: '修改密码'}
    }
]

const createRouter = () => new VueRouter({
    scrollBehavior: () => ({y: 0}),
>>>>>>> e6897d3eee7dd92889ec4638067e9f9148ca1f07
    routes: constantRoutes
})
const router = createRouter()

export function resetRouter() {
    const newRouter = createRouter()
    router.matcher = newRouter.matcher // reset router
}

export default router
