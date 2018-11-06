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
param:networkName,networkUrl
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
param:user,network
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

### 查看我的人脉圈(列出人脉圈列表)
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
### 查看我的各个人脉圈所对应的人数
```text
url: /network/getMyNetworksUserCount
method: GET
param:user
example: url/network/getUserInfo
return:
```
> 成功
```json
"code": 0,
"data":{
    "朋友圈":1
    "帮我传播问题的人":20
    "帮我回答问题的人":10
},
"message":"success"
```

### 人脉网界面随机点击用户的得到其所有人的人脉
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
"message":"fail"
```

### 点击进入某一个人脉圈,显示我的所有好友
```text
url: /network/getMyAllUsers
method: GET
param: nid
example: url/network/getMyAllUsers
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
"data":List{},
"message":"fail"
```

### 进入人脉网后查看我周围某个用户的信息

```text
url: /network/getOneUserInfo
method: GET
param: uid
example: url/network/getOneUserInfo
return:
```
> 成功
```json

"code": 0,
"data":User{id=1, name='hello', gender=1,definition="definition",wxid="xxx",avatarUrl="xxxx"},
"message":"success"
```

