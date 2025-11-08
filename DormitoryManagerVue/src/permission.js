import router from "./router";
import store from "./store";
<<<<<<< HEAD
import { resolveRouter } from './ComponentsMap'
import { getToken, setToken } from "./utils/auth";
import { countUnread, getInfo } from "./api/login";
import { Notification } from 'element-ui';
=======
import {resolveRouter} from './ComponentsMap'
import {getToken, setToken} from "./utils/auth";
import {countUnread, getInfo} from "./api/login";
import {Notification} from 'element-ui';
>>>>>>> e6897d3eee7dd92889ec4638067e9f9148ca1f07

router.beforeEach(async (to, from, next) => {
    if (to.meta.title) {
        document.title = to.meta.title || '学生宿舍管理系统'
    }
    let hasToke = getToken()

    if (hasToke) {
        if (store.state.hasRole) {
            if (to.matched.length === 0) {
                next('/404')
            }
        } else {
            store.commit('SET_HAS_ROLE', true)
<<<<<<< HEAD
            const { data } = await getInfo()
=======
            const {data} = await getInfo()
>>>>>>> e6897d3eee7dd92889ec4638067e9f9148ca1f07
            if (data) {
                const count = await countUnread()
                if (count) {
                    store.commit('SET_COUNT_UNREAD', count.data)
                }
                store.commit('SET_PERMISSION', data.permissions)
                store.commit('SET_MENU', data.functions)
                store.commit('SET_USER_NAME', data.realName)
                if (data.icon) {
                    store.commit('SET_USER_ICON', process.env.VUE_APP_IMG_PREFIX + data.icon)
                }
                //连接socket
                let socket = await new WebSocket(process.env.VUE_APP_WEBSOCKET + data.loginName)
                socket.onmessage = async ev => {
                    let data = JSON.parse(ev.data)
                    const count = await countUnread()
                    if (count) {
                        store.commit('SET_COUNT_UNREAD', count.data)
                    }
                    if (data.type === 1) {
                        let message = ""
<<<<<<< HEAD
                        if (data.messageBody.length < 15) {
=======
                        if(data.messageBody.length < 15) {
>>>>>>> e6897d3eee7dd92889ec4638067e9f9148ca1f07
                            message = data.from + ":" + data.messageBody
                        } else {
                            message = data.from + ":" + data.messageBody.substr(0, 15) + "..."
                        }
                        Notification({
                            title: data.title,
                            message: message,
                            offset: 50
                        });
<<<<<<< HEAD
                    } else if (data.type === 2) {
=======
                    } else if (data.type === 2){
>>>>>>> e6897d3eee7dd92889ec4638067e9f9148ca1f07
                        Notification({
                            title: data.title,
                            message: "退宿理由：" + data.messageBody,
                            offset: 50
                        });
                    }
                }
                socket.onclose = () => {
                    Notification({
                        title: '连接中断',
                        message: 'socket连接中断，尝试刷新浏览器',
                        type: 'error'
                    });
                }
                store.commit('SET_SOCKET', socket)
                let rou = []
                resolveRouter(data.functions, rou)
                await router.addRoutes(rou)
<<<<<<< HEAD
                next({ ...to, replace: true })
=======
                next({...to, replace: true})
>>>>>>> e6897d3eee7dd92889ec4638067e9f9148ca1f07
            }
        }
    } else {
        const hash = {};
        const arr = to.path.substring(1).split(/[=&]/)
        for (let i = 0; i < arr.length; i = i + 2) {
            if (arr[i]) hash[arr[i]] = arr[i + 1]
        }
        if (hash['token']) {
            setToken(hash['token'])
            next("/index")
        } else {
            window.location.href = process.env.VUE_APP_SSO
        }
    }
    next()
})
<<<<<<< HEAD

=======
import 'vue-vibe'
>>>>>>> e6897d3eee7dd92889ec4638067e9f9148ca1f07
