# 陳易伯

## 2022 年 8 月 12 日 Order API （串接 H2 Database） 回家作業 


### GET 請求

* 取得所有 Order
http://localhost:8080/api/v1/order

* 根據 id 取得個別 Order
http://localhost:8080/api/v1/order/:id

### POST 請求

* 使用 RequestBody 來新增 Order
http://localhost:8080/api/v1/order

### PUT 請求

* 根據 id 和使用 RequestBody 來修改已存在的 Order
http://localhost:8080/api/v1/order/:id

### DELETE 請求

* 根據 id 來刪除已存在的 Order
http://localhost:8080/api/v1/order/:id