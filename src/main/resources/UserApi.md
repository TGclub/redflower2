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
                  ", value=" + value +
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


### 获取用户id
```text
url: /user/getUserWxId
method: POST
param:openId,encryptedData,session_key,iv
example: url/user/updateDefinition
return:
```
> 成功
```json
"code": 0,
"data":wxid,
"message":"success"
```
> 失败
```json
"code": 1,
"data":null,
"message":"失败！"
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
### 测试接口
```text
url: /user/updateUsername
method: GET
param:无
example: url/user/test
return:
```
> 成功

```json
"code":0,
"data":null
"message":"success"
```
> 失败

```json
"code": 1,
"data":null,
"message":"fail"
```

### 更新用户信息
```text
url: /user/updateUserInfo
method: PUT
param:user
example: url/user/updateUserInfo
return:
```
> 成功
```json
"code":0,
"data":null
"message":"success"
```
> 失败

```json
"code": 1,
"data":null,
"message":"失败！"
```

## 人脉网界面api
url:
>http://193.112.69.104:8091

### 创建新的人脉圈
```text
url: /network/createNetwork
method: POST
param:networkName
example: url/network/createNetwork
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
"data":null
"meaasge":"fail"
```

###  拓展我的人脉圈
```text
url: /network/inviteUser
method: POST
param:user
example: url/network/inviteUser
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

### 查看我的人脉圈
```text
url: /network/getMyNetworks
method: GET
param:无
example: url/network/getMyNetworks
return:
``` 
> 成功
```json
"code": 0,
"data":{
success{
  NetWork{
      id=,
      uid=,
      networkName=""
    },
  NetWork{
      id=,
      uid=,
      networkName=""
    }
  }
},
"message":"success"
```
> 失败
```json
"code": 1,
"data":List{},
"message":"fail"
```

### 人脉网界面个人信息
```text
url: /network/getUserInfo
method: POST
param:user
example: url/network/getUserInfo
return:
```
> 成功
```json
"code": 0,
"data":[
User{id=1, name='hello', gender=1,definition="definition",wxid="xxx",avatarUrl="xxxx"}
User{id=2,name="world",gender=0,definition="definition",wxid="xxx",avatarUrl=""}
]
"meaasge":"success"
```
> 失败
```json
"code": 1,
"data":null,
"msg":"fail message"
```

### 测试
```text
url: /network/test
method: GET
param:user
example: url/network/test
return:
```

> 成功

```json
"code": 0,
"data":null,
"meaasge":"success"
```

