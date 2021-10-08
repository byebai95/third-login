三方登录支持： google 、 github 、 facebook

step 1
```
user:
  github:
    clientId: c3c514279462548fdf61
    clientSecret: 76a2bb4074731d9d4856ec952832512564aa471a
    tokenUrl: https://github.com/login/oauth/access_token
    redirectUrl: http://localhost:8080/github/thirdLoginCallBack
    userInfoUrl: https://api.github.com/user
  google:
    clientId: 918272533109-mq26hm62t2vb7ommrh9t5qtu24n1jusm.apps.googleusercontent.com
    clientSecret: Yy57lgdCs36kMlk4mg4IaS14
    redirectUrl: http://localhost:8080/google/thirdLoginCallBack
    tokenUrl: https://oauth2.googleapis.com/token
    grantType: authorization_code
    userInfoUrl: https://www.googleapis.com/oauth2/v1/userinfo
  facebook:
    clientId: 416481940036394
    clientSecret: 1fbdf979783f714c7e83247911a8ddef
    redirectUrl: http://localhost:8080/facebook/thirdLoginCallBack
    tokenUrl: https://graph.facebook.com/v12.0/oauth/access_token
    state: {st=state123abc,ds=123456789}
    userInfoUrl: https://graph.facebook.com/me


server:
  port: 8080

spring:
  thymeleaf:
    prefix: classpath:/templates/
```
step 2
```aidl
//github 登陆
http://localhost:8080/github

//google 登陆
http://localhost:8080/google

//facebook
http://localhost:8080/facebook
```

