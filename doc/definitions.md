# 模型定义

## <a id="error"></a>**Error**
服务端返回的错误信息

#### 属性定义

属性名|数据类型|必填|属性说明
---|:---|:---:|:---
code|string|N|错误代码
message|string|N|错误描述

## <a id="attend"></a>**打卡记录** (*attend*)


#### 属性定义

属性名|数据类型|必填|属性说明
---|:---|:---:|:---
id|integer|N|
empid|integer|N|
taskid|integer|Y|
attendTime|string|Y|
attendAddress|string|Y|
deleted|integer|Y|

## <a id="attend_status"></a>**打卡记录** (*attendStatus*)


#### 属性定义

属性名|数据类型|必填|属性说明
---|:---|:---:|:---
asid|integer|N|
empid|integer|N|
taskid|integer|Y|
status|integer|Y|
createTime|string|Y|
deleted|integer|Y|

## <a id="employee"></a>**员工** (*employee*)


#### 属性定义

属性名|数据类型|必填|属性说明
---|:---|:---:|:---
eid|integer|N|
enumber|integer|N|
ename|string|N|
gender|string|Y|
department|string|Y|
username|string|Y|
password|string|N|
email|string|Y|
createTime|string|Y|
deleted|integer|Y|

## <a id="task"></a>**Task Info** (*task*)


#### 属性定义

属性名|数据类型|必填|属性说明
---|:---|:---:|:---
id|integer|N|
taskName|string|N|
createUser|string|N|
beginTimeStart|string|Y|
beginTimeOver|string|Y|
endTimeStart|string|Y|
endTimeOver|string|Y|
address|string|Y|
type|string|N|
status|integer|Y|
createTime|string|Y|
deleted|integer|Y|

