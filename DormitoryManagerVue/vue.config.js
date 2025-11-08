/*
 * @Description: 
 * @Author: Rabbiter
 * @Date: 2023-03-05 20:17:11
 */
module.exports = {
  publicPath: "./",
  //打包输出文件夹
<<<<<<< HEAD
<<<<<<< HEAD
  outputDir: "dms",
=======
  outputDir:"dms",
>>>>>>> e6897d3eee7dd92889ec4638067e9f9148ca1f07
=======
  outputDir:"dms",
>>>>>>> parent of 99ae58fb (修正代码，并打包成功上传部署到服务器（测试IP地址）)
  chainWebpack: config => {
    //关闭语法检查
    config.module.rules.delete('eslint');
    config.plugin('html').tap((args) => {
<<<<<<< HEAD
<<<<<<< HEAD
      args[0].title = 'dms';
=======
      args[0].title = 'myproject';
>>>>>>> e6897d3eee7dd92889ec4638067e9f9148ca1f07
=======
      args[0].title = 'myproject';
>>>>>>> parent of 99ae58fb (修正代码，并打包成功上传部署到服务器（测试IP地址）)
      return args;
    });
  },
  //错误输出为编译警告
  lintOnSave: false,
  devServer: {
    open: true,
    port: 9142,
    https: false
  }
}
