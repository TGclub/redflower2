## 人脉网UserApi
url:
>http://193.112.69.104:8091
-----
### 用户登录
```text
url: /user/login
method: POST
param:code
example: url/user/login
return:

```
> 成功
```json
  "code": 0,
  "data": null,
  "message":"success"
```
> 失败
```json
"code":1
"data":null,
"message":"fail"

```
### 返回登录后用户的信息
```text
url: /user/userInfo
method: GET
param:无
example: url/user/userInfo
return:
```
> 成功
```json
"code":0,
"data":{
  User{           "id=" + id +
                  ", name='" + name + '\'' +
                  ", gender=" + gender +
                  ", definition='" + definition + '\'' +
                  ", wxid='" + wxid + '\'' +
                  ", openid='" + openid + '\'' +
                  ", avatarUrl='" + avatarUrl + '\'' +
                  ", state=" + state +
                  '}'
},
"message":success
```
> 失败
```json
"code": 1,
"data":null,
"message":"fail"
```

###  用户退出
```text
url: /user/logout
method: GET
param:无
example: url/user/logout
return:
```
> 成功
```json
"code": 0,
"data":null,
"message":"success"
```
### 用户修改自定义个性签名
```text
url: /user/updateDefinition
method: PUT
param:definition
example: url/user/updateDefinition
return:
```
> 成功
```json
"code": 0,
"data":null,
"message":"success"
```
> 失败
```json
"code": 1,
"data":null,
"message":"fail"
```

###  修改昵称
```text
url: /user/updateUsername
method: PUT
param:username
example: url/user/updateUsername
return:
```
> 成功

```json
"code":0,
"data":null
"message":"success"

```
