## 人脉网界面api
url:
>http://193.112.69.104:8091

### 创建新的人脉圈
```text
url: /network/createNetwork
method: POST
param:network
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
"data":一个map包含list,
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

