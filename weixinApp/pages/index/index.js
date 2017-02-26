//index.js
//获取应用实例
var app = getApp()
Page({
  data: {
    motto: '测试代码',
    userInfo: {}
  },
  //发送请求到后台服务器
  bindViewTap: function() {
    var that = this
    wx.request({
      url: 'http://localhost:8080/camel-rest-example/meeting/checkin/',
      data: {name:'test'},
      method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
      header: {
        'content-type': 'application/json'
      }, // 设置请求的 header
      success: function(res){
        console.log(res.data)
        that.setData({
          motto:res.data.date
        })
      },
      fail: function() {
        // fail
        that.setData({
          motto: 'Failed!'
        })
      },
      complete: function() {
        // complete
      }
    })
  },
  onLoad: function () {
    console.log('onLoad')
    var that = this
    //调用应用实例的方法获取全局数据
    app.getUserInfo(function(userInfo){
      //更新数据
      that.setData({
        userInfo:userInfo
      })
    })
  }
})
