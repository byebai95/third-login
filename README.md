step 1
```
user:
  github:
    clientId: xxxx
    clientSecret: xxxxx
    tokenUri: https://github.com/login/oauth/access_token
    redirectUri: http://localhost:8080/thirdLoginCallBack
    userInfo: https://api.github.com/user

server:
  port: 8080

spring:
  thymeleaf:
    prefix: classpath:/templates/
```
step 2
```aidl
http://localhost:8080
```
