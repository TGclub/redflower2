### 创建新的人脉圈
```text
url: /network/addNetwork
method: POST
param:name
example: 
return:
```
> 成功
```json
"code": 0,
"data":{
},
"msg":"successed"
```
> 失败
```json
"code": 1,
"data":{
},
"msg":"failed"/"no login"
```

####  邀请更多人加入人脉网
```text
url: /network/invitation
method: POST
param:nid(networkId),uid(userId)
example: 
return:
```
> 成功
```json
"code": 0,
"data":{
},
"msg":"invite successfully"
```
> 失败
```json
"code": 1,
"data":{
},
"msg":"no login"/"already join"/"failed"
```

#### 查看我的人脉圈
```text
url: /network/myNetworks
method: GET
param:nid(networkId),uid(userId)
example: 
return:
``` 
> 成功
```json
"code": 0,
"data":{
},
"msg":"query successfully"
```
> 失败
```json
"code": 1,
"data":{
},
"msg":"no login"/"my networks are null"
```

#### 人脉网界面个人信息
```text
url: /network/info
method: POST
param:uid1(userId1),uid2(userId2)
example: 
return:
```
> 成功
```json
"code": 0,
"data":{
  "id":
  "name":
  "gender":
  "definition":
  "relation":
  "wxid":
},
"msg":"succeed"
```
> 失败
```json
"code": 1,
"data":{
},
"msg":"no login"/"uid1 is not exist"/"no connection"
```

#### 人脉网界面
```text
url: /network/image
method: POST
param:userId
example: 
return:
```
> 成功
```json
"code": 0,
"data":{
  //返回用户信息
},
"msg":"succeed"
```
> 失败
```json
"code": 1,
"data":{
  //返回用户信息
},
"msg":"no login"/"no userId"/"you do not have network"
```