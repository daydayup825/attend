# 接口操作

# <a id="task"></a>Task Info

<a id="createTask"></a>
## <a id="op2"></a>**创建Task Info记录**
> POST /task

给Task Info创建一行新的记录
<!--Content Type-->

<!--Parameters-->
#### **请求参数**

参数名|数据类型|必填|参数位置|参数说明
---|:---|:---:|:---:|:---
task|[task](definitions.md#task#isRightRef)|Y|body|task

#### **返回结果**

状态码|说明|返回值|响应头
:---|:---|:---
201|Success|[task](definitions.md#task#isRightRef)|*无*

<!--demo-->
#### **请求示例**
```
POST /task

{  "address" : "string",  "deleted" : "integer",  "beginTimeOver" : "string",  "createTime" : "string",  "endTimeOver" : "string",  "endTimeStart" : "string",  "taskName" : "string",  "createUser" : "string",  "beginTimeStart" : "string",  "id" : "integer",  "type" : "string",  "status" : "integer"}
```

#### **返回示例**
```

{  "address" : "string",  "deleted" : "integer",  "beginTimeOver" : "string",  "createTime" : "string",  "endTimeOver" : "string",  "endTimeStart" : "string",  "taskName" : "string",  "createUser" : "string",  "beginTimeStart" : "string",  "id" : "integer",  "type" : "string",  "status" : "integer"}
```

***
<a id="queryTask"></a>
## <a id="op3"></a>**查询Task Info列表**
> GET /task

按条件查询Task Info的多行记录
<!--Content Type-->

<!--Parameters-->
#### **请求参数**

参数名|数据类型|必填|参数位置|参数说明
---|:---|:---:|:---:|:---
select|string|N|query|结果字段选择表达式,具体请看API规范
expand|string|N|query|展开查询表达式，具体请看API规范
page_size|integer|N|query|每页返回记录数,默认50,只能结合page使用
page|integer|N|query|第几页,从1开始
limit|integer|N|query|限制返回多少条记录(limit和page不能同时使用)
offset|integer|N|query|从0开始,表示从第几条开始返回,结合limit使用
total|boolean|N|query|是否返回总数,默认返回
orderby|string|N|query|查询结果排序表达式,具体请看API规范
viewId|string|N|query|保留参数，用于扩展使用
filters|string|N|query|查询过滤条件,具体请看API规范
aggregates|string|N|query|聚合字段,具体请看API规范
groupby|string|N|query|分组字段,具体请看API规范
joins|string|N|query|关联查询，具体请看API规范

#### **返回结果**

状态码|说明|返回值|响应头
:---|:---|:---
200|Success|[task](definitions.md#task#isRightRef)[ ]|`X-Total-Count` integer : The total count of query records.<br>

<!--demo-->
#### **请求示例**
```
GET /task?select={select}&expand={expand}&page_size={page_size}&page={page}&limit={limit}&offset={offset}&total={total}&orderby={orderby}&viewId={viewId}&filters={filters}&aggregates={aggregates}&groupby={groupby}&joins={joins}


```

#### **返回示例**
```

[ {  "address" : "string",  "deleted" : "integer",  "beginTimeOver" : "string",  "createTime" : "string",  "endTimeOver" : "string",  "endTimeStart" : "string",  "taskName" : "string",  "createUser" : "string",  "beginTimeStart" : "string",  "id" : "integer",  "type" : "string",  "status" : "integer"} ]
```

***
<a id="deleteTask"></a>
## <a id="op4"></a>**删除Task Info记录**
> DELETE /task/{id}

按主键删除Task Info的一行记录
<!--Content Type-->

<!--Parameters-->
#### **请求参数**

参数名|数据类型|必填|参数位置|参数说明
---|:---|:---:|:---:|:---
id|integer|Y|path|id

#### **返回结果**

状态码|说明|返回值|响应头
:---|:---|:---
204|Success|*无*|*无*

<!--demo-->
#### **请求示例**
```
DELETE /task/{id}


```

#### **返回示例**
```


```

***
<a id="findTask"></a>
## <a id="op5"></a>**获取Task Info记录**
> GET /task/{id}

按主键获取Task Info的一行记录
<!--Content Type-->

<!--Parameters-->
#### **请求参数**

参数名|数据类型|必填|参数位置|参数说明
---|:---|:---:|:---:|:---
id|integer|Y|path|id
select|string|N|query|结果字段选择表达式,具体请看API规范
expand|string|N|query|展开查询表达式，具体请看API规范

#### **返回结果**

状态码|说明|返回值|响应头
:---|:---|:---
200|Success|[task](definitions.md#task#isRightRef)|*无*

<!--demo-->
#### **请求示例**
```
GET /task/{id}?select={select}&expand={expand}


```

#### **返回示例**
```

{  "address" : "string",  "deleted" : "integer",  "beginTimeOver" : "string",  "createTime" : "string",  "endTimeOver" : "string",  "endTimeStart" : "string",  "taskName" : "string",  "createUser" : "string",  "beginTimeStart" : "string",  "id" : "integer",  "type" : "string",  "status" : "integer"}
```

***
<a id="updateTask"></a>
## <a id="op6"></a>**更新Task Info记录**
> PATCH /task/{id}

按主键更新Task Info的一行记录
<!--Content Type-->

<!--Parameters-->
#### **请求参数**

参数名|数据类型|必填|参数位置|参数说明
---|:---|:---:|:---:|:---
id|integer|Y|path|id
task|[task](definitions.md#task#isRightRef)|Y|body|task

#### **返回结果**

状态码|说明|返回值|响应头
:---|:---|:---
204|Success|*无*|*无*

<!--demo-->
#### **请求示例**
```
PATCH /task/{id}

{  "address" : "string",  "deleted" : "integer",  "beginTimeOver" : "string",  "createTime" : "string",  "endTimeOver" : "string",  "endTimeStart" : "string",  "taskName" : "string",  "createUser" : "string",  "beginTimeStart" : "string",  "id" : "integer",  "type" : "string",  "status" : "integer"}
```

#### **返回示例**
```


```

***

# <a id="attend"></a>打卡记录

<a id="createAttend"></a>
## <a id="op7"></a>**创建打卡记录记录**
> POST /attend

给打卡记录创建一行新的记录
<!--Content Type-->

<!--Parameters-->
#### **请求参数**

参数名|数据类型|必填|参数位置|参数说明
---|:---|:---:|:---:|:---
attend|[attend](definitions.md#attend#isRightRef)|Y|body|attend

#### **返回结果**

状态码|说明|返回值|响应头
:---|:---|:---
201|Success|[attend](definitions.md#attend#isRightRef)|*无*

<!--demo-->
#### **请求示例**
```
POST /attend

{  "empid" : "integer",  "deleted" : "integer",  "attendTime" : "string",  "attendAddress" : "string",  "id" : "integer",  "taskid" : "integer"}
```

#### **返回示例**
```

{  "empid" : "integer",  "deleted" : "integer",  "attendTime" : "string",  "attendAddress" : "string",  "id" : "integer",  "taskid" : "integer"}
```

***
<a id="queryAttend"></a>
## <a id="op8"></a>**查询打卡记录列表**
> GET /attend

按条件查询打卡记录的多行记录
<!--Content Type-->

<!--Parameters-->
#### **请求参数**

参数名|数据类型|必填|参数位置|参数说明
---|:---|:---:|:---:|:---
select|string|N|query|结果字段选择表达式,具体请看API规范
expand|string|N|query|展开查询表达式，具体请看API规范
page_size|integer|N|query|每页返回记录数,默认50,只能结合page使用
page|integer|N|query|第几页,从1开始
limit|integer|N|query|限制返回多少条记录(limit和page不能同时使用)
offset|integer|N|query|从0开始,表示从第几条开始返回,结合limit使用
total|boolean|N|query|是否返回总数,默认返回
orderby|string|N|query|查询结果排序表达式,具体请看API规范
viewId|string|N|query|保留参数，用于扩展使用
filters|string|N|query|查询过滤条件,具体请看API规范
aggregates|string|N|query|聚合字段,具体请看API规范
groupby|string|N|query|分组字段,具体请看API规范
joins|string|N|query|关联查询，具体请看API规范

#### **返回结果**

状态码|说明|返回值|响应头
:---|:---|:---
200|Success|[attend](definitions.md#attend#isRightRef)[ ]|`X-Total-Count` integer : The total count of query records.<br>

<!--demo-->
#### **请求示例**
```
GET /attend?select={select}&expand={expand}&page_size={page_size}&page={page}&limit={limit}&offset={offset}&total={total}&orderby={orderby}&viewId={viewId}&filters={filters}&aggregates={aggregates}&groupby={groupby}&joins={joins}


```

#### **返回示例**
```

[ {  "empid" : "integer",  "deleted" : "integer",  "attendTime" : "string",  "attendAddress" : "string",  "id" : "integer",  "taskid" : "integer"} ]
```

***
<a id="deleteAttend"></a>
## <a id="op9"></a>**删除打卡记录记录**
> DELETE /attend/{id}

按主键删除打卡记录的一行记录
<!--Content Type-->

<!--Parameters-->
#### **请求参数**

参数名|数据类型|必填|参数位置|参数说明
---|:---|:---:|:---:|:---
id|integer|Y|path|id

#### **返回结果**

状态码|说明|返回值|响应头
:---|:---|:---
204|Success|*无*|*无*

<!--demo-->
#### **请求示例**
```
DELETE /attend/{id}


```

#### **返回示例**
```


```

***
<a id="findAttend"></a>
## <a id="op10"></a>**获取打卡记录记录**
> GET /attend/{id}

按主键获取打卡记录的一行记录
<!--Content Type-->

<!--Parameters-->
#### **请求参数**

参数名|数据类型|必填|参数位置|参数说明
---|:---|:---:|:---:|:---
id|integer|Y|path|id
select|string|N|query|结果字段选择表达式,具体请看API规范
expand|string|N|query|展开查询表达式，具体请看API规范

#### **返回结果**

状态码|说明|返回值|响应头
:---|:---|:---
200|Success|[attend](definitions.md#attend#isRightRef)|*无*

<!--demo-->
#### **请求示例**
```
GET /attend/{id}?select={select}&expand={expand}


```

#### **返回示例**
```

{  "empid" : "integer",  "deleted" : "integer",  "attendTime" : "string",  "attendAddress" : "string",  "id" : "integer",  "taskid" : "integer"}
```

***
<a id="updateAttend"></a>
## <a id="op11"></a>**更新打卡记录记录**
> PATCH /attend/{id}

按主键更新打卡记录的一行记录
<!--Content Type-->

<!--Parameters-->
#### **请求参数**

参数名|数据类型|必填|参数位置|参数说明
---|:---|:---:|:---:|:---
id|integer|Y|path|id
attend|[attend](definitions.md#attend#isRightRef)|Y|body|attend

#### **返回结果**

状态码|说明|返回值|响应头
:---|:---|:---
204|Success|*无*|*无*

<!--demo-->
#### **请求示例**
```
PATCH /attend/{id}

{  "empid" : "integer",  "deleted" : "integer",  "attendTime" : "string",  "attendAddress" : "string",  "id" : "integer",  "taskid" : "integer"}
```

#### **返回示例**
```


```

***

# <a id="attend_status"></a>打卡记录

<a id="createAttendstatus"></a>
## <a id="op12"></a>**创建打卡记录记录**
> POST /attend_status

给打卡记录创建一行新的记录
<!--Content Type-->

<!--Parameters-->
#### **请求参数**

参数名|数据类型|必填|参数位置|参数说明
---|:---|:---:|:---:|:---
attendstatus|[attendStatus](definitions.md#attend_status#isRightRef)|Y|body|attendstatus

#### **返回结果**

状态码|说明|返回值|响应头
:---|:---|:---
201|Success|[attendStatus](definitions.md#attend_status#isRightRef)|*无*

<!--demo-->
#### **请求示例**
```
POST /attend_status

{  "asid" : "integer",  "empid" : "integer",  "deleted" : "integer",  "createTime" : "string",  "taskid" : "integer",  "status" : "integer"}
```

#### **返回示例**
```

{  "asid" : "integer",  "empid" : "integer",  "deleted" : "integer",  "createTime" : "string",  "taskid" : "integer",  "status" : "integer"}
```

***
<a id="queryAttendstatus"></a>
## <a id="op13"></a>**查询打卡记录列表**
> GET /attend_status

按条件查询打卡记录的多行记录
<!--Content Type-->

<!--Parameters-->
#### **请求参数**

参数名|数据类型|必填|参数位置|参数说明
---|:---|:---:|:---:|:---
select|string|N|query|结果字段选择表达式,具体请看API规范
expand|string|N|query|展开查询表达式，具体请看API规范
page_size|integer|N|query|每页返回记录数,默认50,只能结合page使用
page|integer|N|query|第几页,从1开始
limit|integer|N|query|限制返回多少条记录(limit和page不能同时使用)
offset|integer|N|query|从0开始,表示从第几条开始返回,结合limit使用
total|boolean|N|query|是否返回总数,默认返回
orderby|string|N|query|查询结果排序表达式,具体请看API规范
viewId|string|N|query|保留参数，用于扩展使用
filters|string|N|query|查询过滤条件,具体请看API规范
aggregates|string|N|query|聚合字段,具体请看API规范
groupby|string|N|query|分组字段,具体请看API规范
joins|string|N|query|关联查询，具体请看API规范

#### **返回结果**

状态码|说明|返回值|响应头
:---|:---|:---
200|Success|[attendStatus](definitions.md#attend_status#isRightRef)[ ]|`X-Total-Count` integer : The total count of query records.<br>

<!--demo-->
#### **请求示例**
```
GET /attend_status?select={select}&expand={expand}&page_size={page_size}&page={page}&limit={limit}&offset={offset}&total={total}&orderby={orderby}&viewId={viewId}&filters={filters}&aggregates={aggregates}&groupby={groupby}&joins={joins}


```

#### **返回示例**
```

[ {  "asid" : "integer",  "empid" : "integer",  "deleted" : "integer",  "createTime" : "string",  "taskid" : "integer",  "status" : "integer"} ]
```

***
<a id="deleteAttendstatus"></a>
## <a id="op14"></a>**删除打卡记录记录**
> DELETE /attend_status/{asid}

按主键删除打卡记录的一行记录
<!--Content Type-->

<!--Parameters-->
#### **请求参数**

参数名|数据类型|必填|参数位置|参数说明
---|:---|:---:|:---:|:---
asid|integer|Y|path|asid

#### **返回结果**

状态码|说明|返回值|响应头
:---|:---|:---
204|Success|*无*|*无*

<!--demo-->
#### **请求示例**
```
DELETE /attend_status/{asid}


```

#### **返回示例**
```


```

***
<a id="findAttendstatus"></a>
## <a id="op15"></a>**获取打卡记录记录**
> GET /attend_status/{asid}

按主键获取打卡记录的一行记录
<!--Content Type-->

<!--Parameters-->
#### **请求参数**

参数名|数据类型|必填|参数位置|参数说明
---|:---|:---:|:---:|:---
asid|integer|Y|path|asid
select|string|N|query|结果字段选择表达式,具体请看API规范
expand|string|N|query|展开查询表达式，具体请看API规范

#### **返回结果**

状态码|说明|返回值|响应头
:---|:---|:---
200|Success|[attendStatus](definitions.md#attend_status#isRightRef)|*无*

<!--demo-->
#### **请求示例**
```
GET /attend_status/{asid}?select={select}&expand={expand}


```

#### **返回示例**
```

{  "asid" : "integer",  "empid" : "integer",  "deleted" : "integer",  "createTime" : "string",  "taskid" : "integer",  "status" : "integer"}
```

***
<a id="updateAttendstatus"></a>
## <a id="op16"></a>**更新打卡记录记录**
> PATCH /attend_status/{asid}

按主键更新打卡记录的一行记录
<!--Content Type-->

<!--Parameters-->
#### **请求参数**

参数名|数据类型|必填|参数位置|参数说明
---|:---|:---:|:---:|:---
asid|integer|Y|path|asid
attendstatus|[attendStatus](definitions.md#attend_status#isRightRef)|Y|body|attendstatus

#### **返回结果**

状态码|说明|返回值|响应头
:---|:---|:---
204|Success|*无*|*无*

<!--demo-->
#### **请求示例**
```
PATCH /attend_status/{asid}

{  "asid" : "integer",  "empid" : "integer",  "deleted" : "integer",  "createTime" : "string",  "taskid" : "integer",  "status" : "integer"}
```

#### **返回示例**
```


```

***

# <a id="employee"></a>员工

<a id="createEmployee"></a>
## <a id="op17"></a>**创建员工记录**
> POST /employee

给员工创建一行新的记录
<!--Content Type-->

<!--Parameters-->
#### **请求参数**

参数名|数据类型|必填|参数位置|参数说明
---|:---|:---:|:---:|:---
employee|[employee](definitions.md#employee#isRightRef)|Y|body|employee

#### **返回结果**

状态码|说明|返回值|响应头
:---|:---|:---
201|Success|[employee](definitions.md#employee#isRightRef)|*无*

<!--demo-->
#### **请求示例**
```
POST /employee

{  "enumber" : "integer",  "eid" : "integer",  "ename" : "string",  "password" : "string",  "deleted" : "integer",  "gender" : "string",  "createTime" : "string",  "department" : "string",  "email" : "string",  "username" : "string"}
```

#### **返回示例**
```

{  "enumber" : "integer",  "eid" : "integer",  "ename" : "string",  "password" : "string",  "deleted" : "integer",  "gender" : "string",  "createTime" : "string",  "department" : "string",  "email" : "string",  "username" : "string"}
```

***
<a id="queryEmployee"></a>
## <a id="op18"></a>**查询员工列表**
> GET /employee

按条件查询员工的多行记录
<!--Content Type-->

<!--Parameters-->
#### **请求参数**

参数名|数据类型|必填|参数位置|参数说明
---|:---|:---:|:---:|:---
select|string|N|query|结果字段选择表达式,具体请看API规范
expand|string|N|query|展开查询表达式，具体请看API规范
page_size|integer|N|query|每页返回记录数,默认50,只能结合page使用
page|integer|N|query|第几页,从1开始
limit|integer|N|query|限制返回多少条记录(limit和page不能同时使用)
offset|integer|N|query|从0开始,表示从第几条开始返回,结合limit使用
total|boolean|N|query|是否返回总数,默认返回
orderby|string|N|query|查询结果排序表达式,具体请看API规范
viewId|string|N|query|保留参数，用于扩展使用
filters|string|N|query|查询过滤条件,具体请看API规范
aggregates|string|N|query|聚合字段,具体请看API规范
groupby|string|N|query|分组字段,具体请看API规范
joins|string|N|query|关联查询，具体请看API规范

#### **返回结果**

状态码|说明|返回值|响应头
:---|:---|:---
200|Success|[employee](definitions.md#employee#isRightRef)[ ]|`X-Total-Count` integer : The total count of query records.<br>

<!--demo-->
#### **请求示例**
```
GET /employee?select={select}&expand={expand}&page_size={page_size}&page={page}&limit={limit}&offset={offset}&total={total}&orderby={orderby}&viewId={viewId}&filters={filters}&aggregates={aggregates}&groupby={groupby}&joins={joins}


```

#### **返回示例**
```

[ {  "enumber" : "integer",  "eid" : "integer",  "ename" : "string",  "password" : "string",  "deleted" : "integer",  "gender" : "string",  "createTime" : "string",  "department" : "string",  "email" : "string",  "username" : "string"} ]
```

***
<a id="deleteEmployee"></a>
## <a id="op19"></a>**删除员工记录**
> DELETE /employee/{eid}

按主键删除员工的一行记录
<!--Content Type-->

<!--Parameters-->
#### **请求参数**

参数名|数据类型|必填|参数位置|参数说明
---|:---|:---:|:---:|:---
eid|integer|Y|path|eid

#### **返回结果**

状态码|说明|返回值|响应头
:---|:---|:---
204|Success|*无*|*无*

<!--demo-->
#### **请求示例**
```
DELETE /employee/{eid}


```

#### **返回示例**
```


```

***
<a id="findEmployee"></a>
## <a id="op20"></a>**获取员工记录**
> GET /employee/{eid}

按主键获取员工的一行记录
<!--Content Type-->

<!--Parameters-->
#### **请求参数**

参数名|数据类型|必填|参数位置|参数说明
---|:---|:---:|:---:|:---
eid|integer|Y|path|eid
select|string|N|query|结果字段选择表达式,具体请看API规范
expand|string|N|query|展开查询表达式，具体请看API规范

#### **返回结果**

状态码|说明|返回值|响应头
:---|:---|:---
200|Success|[employee](definitions.md#employee#isRightRef)|*无*

<!--demo-->
#### **请求示例**
```
GET /employee/{eid}?select={select}&expand={expand}


```

#### **返回示例**
```

{  "enumber" : "integer",  "eid" : "integer",  "ename" : "string",  "password" : "string",  "deleted" : "integer",  "gender" : "string",  "createTime" : "string",  "department" : "string",  "email" : "string",  "username" : "string"}
```

***
<a id="updateEmployee"></a>
## <a id="op21"></a>**更新员工记录**
> PATCH /employee/{eid}

按主键更新员工的一行记录
<!--Content Type-->

<!--Parameters-->
#### **请求参数**

参数名|数据类型|必填|参数位置|参数说明
---|:---|:---:|:---:|:---
eid|integer|Y|path|eid
employee|[employee](definitions.md#employee#isRightRef)|Y|body|employee

#### **返回结果**

状态码|说明|返回值|响应头
:---|:---|:---
204|Success|*无*|*无*

<!--demo-->
#### **请求示例**
```
PATCH /employee/{eid}

{  "enumber" : "integer",  "eid" : "integer",  "ename" : "string",  "password" : "string",  "deleted" : "integer",  "gender" : "string",  "createTime" : "string",  "department" : "string",  "email" : "string",  "username" : "string"}
```

#### **返回示例**
```


```

***

# <a id="default"></a>未分类

<a id="get"></a>
## <a id="op1"></a>**Just prints &#39;It works!&#39;.**
> GET /

Just prints 'It works!'.
<!--Content Type-->

<!--Parameters-->
#### **请求参数**

*无*
#### **返回结果**

状态码|说明|返回值|响应头
:---|:---|:---
200||string|*无*

<!--demo-->
#### **请求示例**
```
GET /


```

#### **返回示例**
```


```

***

