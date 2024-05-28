(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["chunk-eb775e54"], { c24f: function (e, t, a) { "use strict"; a.r(t), a.d(t, "getUserInfo", (function () { return r })), a.d(t, "update", (function () { return n })), a.d(t, "changePassword", (function () { return i })), a.d(t, "changeIcon", (function () { return c })); var s = a("b775"); function r() { return Object(s["default"])({ url: "/permission/userinfo", method: "get" }) } function n(e) { return Object(s["default"])({ url: "/system/user/update", data: e, method: "post" }) } function i(e) { return Object(s["default"])({ url: "/system/user/change", data: e, method: "post" }) } function c(e) { return Object(s["default"])({ url: "/system/user/changeIcon/" + e, method: "get" }) } }, cda1: function (e, t, a) { }, ceb0: function (e, t, a) { "use strict"; a.r(t); var s = function () { var e = this, t = e._self._c; return t("div", [t("div", { staticClass: "container" }, [t("div", { staticClass: "inner_container" }, [t("div", { staticClass: "user-form" }, [t("div", { staticClass: "avatar", attrs: { title: "点击更换头像" } }, [t("el-upload", { staticClass: "avatar-uploader", attrs: { action: e.action, "show-file-list": !1, headers: e.header, "on-success": e.handleAvatarSuccess, "before-upload": e.beforeAvatarUpload } }, [e.user.icon ? t("img", { staticClass: "avatar", attrs: { src: e.user.icon, alt: "" } }) : t("i", { staticClass: "el-icon-plus avatar-uploader-icon" })])], 1), t("br"), t("el-form", { ref: "form", attrs: { rules: e.rules, model: e.user, "label-width": "100px" } }, [t("el-form-item", { attrs: { label: "用户名称:", prop: "realName" } }, [t("el-input", { model: { value: e.user.realName, callback: function (t) { e.$set(e.user, "realName", t) }, expression: "user.realName" } })], 1), t("el-form-item", { attrs: { label: "电话:", prop: "cellphone" } }, [t("el-input", { model: { value: e.user.cellphone, callback: function (t) { e.$set(e.user, "cellphone", t) }, expression: "user.cellphone" } })], 1), t("el-form-item", { attrs: { label: "邮箱:", prop: "email" } }, [t("el-input", { model: { value: e.user.email, callback: function (t) { e.$set(e.user, "email", t) }, expression: "user.email" } })], 1), t("el-form-item", [t("el-button", { staticStyle: { "font-size": "16px" }, attrs: { type: "success" }, on: { click: function (t) { return e.update("form") } } }, [e._v(" 修改")]), t("el-button", { staticStyle: { "font-size": "16px" }, attrs: { type: "warning" }, on: { click: e.reset } }, [e._v(" 重置")])], 1)], 1)], 1)])])]) }, r = [], n = (a("d9e2"), a("c24f")), i = a("5f87"), c = a("4360"), o = { name: "index", data() { let e = (e, t, a) => { if (0 === t.length) return void a(); let s = /^1[3456789]\d{9}$/; s.test(t) ? a() : a(new Error) }, t = (e, t, a) => { if (0 === t.length) return void a(); let s = /^[0-9a-zA-Z_.-]+[@][0-9a-zA-Z_.-]+([.][a-zA-Z]+){1,2}$/; s.test(t) ? a() : a(new Error) }; return { header: { _ut: Object(i["getToken"])() }, action: "http://localhost:9141/permission/upload", user: { id: void 0, realName: "", icon: void 0, cellphone: "", email: "" }, rules: { realName: [{ required: !0, message: "名称不能为空", trigger: "change" }, { min: 2, max: 20, message: "长度在 2 到 20 个字符", trigger: "change" }], cellphone: [{ validator: e, message: "请输入正确的手机号", trigger: "change" }], email: [{ validator: t, message: "请输入正确的邮箱", trigger: "change" }] } } }, methods: { async handleAvatarSuccess(e) { await this.changeIcon(e.data) }, beforeAvatarUpload(e) { const t = -1 !== e.type.indexOf("image/"), a = e.size / 1024 / 1024 < 2; return t || this.$message.error("只能上传图片"), a || this.$message.error("上传头像图片大小不能超过 2MB!"), t && a }, async changeIcon(e) { Object(n["changeIcon"])(e).then(e => { 200 === e.statusCode && (this.$message({ message: e.msg, type: "success" }), this.reset()) }) }, update(e) { this.$refs[e].validate(e => { if (!e) return this.$message({ message: "请正确填写参数", type: "error" }), !1; Object(n["update"])(this.user).then(e => { c["default"].commit("SET_USER_NAME", e.data.realName), this.$message({ message: e.msg, type: "success" }) }) }) }, async reset() { const { data: e } = await Object(n["getUserInfo"])(); e.icon && (e.icon = "http://localhost:9141/upload_img/" + e.icon), this.user = e, c["default"].commit("SET_USER_ICON", this.user.icon) } }, created() { this.reset() } }, l = o, u = (a("cedb"), a("2877")), d = Object(u["a"])(l, s, r, !1, null, "7c199ee0", null); t["default"] = d.exports }, cedb: function (e, t, a) { "use strict"; a("cda1") } }]);
//# sourceMappingURL=chunk-eb775e54.b4f89271.js.map