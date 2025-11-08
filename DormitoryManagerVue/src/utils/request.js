<<<<<<< HEAD
import { getToken, removeToken } from '@/utils/auth'
import axios from 'axios'
import { Message, MessageBox } from 'element-ui'
import store from "@/store";

const service = axios.create({
    baseURL: process.env.VUE_APP_BASE_API,  // 创建axios实例，配置基本的url和超时时间
    timeout: 10000
})

// request拦截器
service.interceptors.request.use(
    async config => {
        let token = getToken()  // 获取token
        if (token) {
            config.headers['_ut'] = token  // 如果token存在，在请求头中添加token
=======
import {getToken, removeToken} from '@/utils/auth'
import axios from 'axios'
import {Message, MessageBox} from 'element-ui'
import store from "@/store";

const service = axios.create({
    baseURL: process.env.VUE_APP_BASE_API,
    timeout: 10000
})
// request拦截器
service.interceptors.request.use(
    async config => {
        let token = getToken()
        if (token) {
            config.headers['_ut'] = token
>>>>>>> e6897d3eee7dd92889ec4638067e9f9148ca1f07
        }
        return config
    },
    error => {
        console.log(error)
<<<<<<< HEAD
        return Promise.reject(error)  // 抛出错误
    }
)

//response 拦截器
service.interceptors.response.use(
    response => {  // 处理响应数据
        const res = response.data
        if (res.statusCode === 200) {  // 如果状态码为200，返回数据
            return res
        } else {
            if (res.statusCode === 407) {  // 如果状态码为407，处理重新登录逻辑
=======
        return Promise.reject(error)
    }
)
//response 拦截器
service.interceptors.response.use(
    response => {
        const res = response.data
        if (res.statusCode === 200) {
            return res
        } else {
            if (res.statusCode === 407) {
>>>>>>> e6897d3eee7dd92889ec4638067e9f9148ca1f07
                if (!store.state.reLoginConfirm) {
                    MessageBox.confirm('登录过期,请重新登录', '确认退出', {
                        confirmButtonText: '重新登录',
                        type: 'warning'
                    }).then(() => {
<<<<<<< HEAD
                        removeToken()  // 移除token
                        location.reload()  // 重新加载页面
                    })
                    store.commit("SET_RE_LOGIN_COMFIRM")  // 提交更新重新登录确认状态
                } else {
                    Message({
                        message: res.msg,  // 显示消息
=======
                        removeToken()
                        location.reload()
                    })
                    store.commit("SET_RE_LOGIN_COMFIRM")
                } else {
                    Message({
                        message: res.msg,
>>>>>>> e6897d3eee7dd92889ec4638067e9f9148ca1f07
                        type: 'error'
                    })
                }
            } else {
                Message({
<<<<<<< HEAD
                    message: res.msg || 'Error',  // 显示错误消息或默认消息
                    type: 'error',
                    duration: 4 * 1000  // 持续时间
                })
            }
            return Promise.resolve(new Error(res.msg || 'Error'))  // 返回一个带有错误消息的解析过的Promise
=======
                    message: res.msg || 'Error',
                    type: 'error',
                    duration: 4 * 1000
                })
            }
            return Promise.resolve(new Error(res.msg || 'Error'))
>>>>>>> e6897d3eee7dd92889ec4638067e9f9148ca1f07
        }
    },
    error => {
        Message({
<<<<<<< HEAD
            message: error.message || "Error",  // 显示错误消息或默认消息
            type: 'error',
            duration: 4 * 1000  // 持续时间
        })
        return Promise.resolve(error)  // 返回一个解析过的Promise，带有错误
    }
)

export default service  // 导出axios实例
=======
            message: error.message || "Error",
            type: 'error',
            duration: 4 * 1000
        })
        return Promise.resolve(error)
    }
)

export default service
>>>>>>> e6897d3eee7dd92889ec4638067e9f9148ca1f07
