# API接口文档

## 接口规范

### 基础信息
- 基础URL: `http://localhost:8080/api`
- 请求方式: GET, POST, PUT, DELETE
- 数据格式: JSON
- 字符编码: UTF-8

### 响应格式
```json
{
    "code": 200,           // 状态码
    "message": "success",  // 响应信息
    "data": {}            // 响应数据
}
```

### 状态码说明
- 200: 成功
- 400: 请求参数错误
- 401: 未授权
- 403: 禁止访问
- 404: 资源不存在
- 500: 服务器内部错误

## 学生管理接口

### 1. 获取学生列表
- 请求路径: `/students`
- 请求方式: GET
- 请求参数:
  ```json
  {
    "page": 1,        // 页码，默认1
    "size": 10,       // 每页数量，默认10
    "keyword": "",    // 搜索关键词（可选）
    "dept": ""        // 院系筛选（可选）
  }
  ```
- 响应数据:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "total": 100,
      "list": [
        {
          "sno": "2024001",
          "sname": "张三",
          "ssex": "男",
          "sage": 20,
          "sdept": "计算机系"
        }
      ]
    }
  }
  ```

### 2. 添加学生
- 请求路径: `/students`
- 请求方式: POST
- 请求参数:
  ```json
  {
    "sno": "2024001",
    "sname": "张三",
    "ssex": "男",
    "sage": 20,
    "sdept": "计算机系"
  }
  ```
- 响应数据:
  ```json
  {
    "code": 200,
    "message": "添加成功",
    "data": null
  }
  ```

### 3. 更新学生信息
- 请求路径: `/students/{sno}`
- 请求方式: PUT
- 请求参数:
  ```json
  {
    "sname": "张三",
    "ssex": "男",
    "sage": 20,
    "sdept": "计算机系"
  }
  ```
- 响应数据:
  ```json
  {
    "code": 200,
    "message": "更新成功",
    "data": null
  }
  ```

### 4. 删除学生
- 请求路径: `/students/{sno}`
- 请求方式: DELETE
- 响应数据:
  ```json
  {
    "code": 200,
    "message": "删除成功",
    "data": null
  }
  ```

## 课程管理接口

### 1. 获取课程列表
- 请求路径: `/courses`
- 请求方式: GET
- 请求参数:
  ```json
  {
    "page": 1,        // 页码，默认1
    "size": 10,       // 每页数量，默认10
    "keyword": "",    // 搜索关键词（可选）
    "dept": ""        // 开课院系筛选（可选）
  }
  ```
- 响应数据:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "total": 100,
      "list": [
        {
          "cno": "C001",
          "cname": "计算机网络",
          "credit": 4,
          "cdept": "计算机系"
        }
      ]
    }
  }
  ```

### 2. 添加课程
- 请求路径: `/courses`
- 请求方式: POST
- 请求参数:
  ```json
  {
    "cno": "C001",
    "cname": "计算机网络",
    "credit": 4,
    "cdept": "计算机系"
  }
  ```
- 响应数据:
  ```json
  {
    "code": 200,
    "message": "添加成功",
    "data": null
  }
  ```

### 3. 更新课程信息
- 请求路径: `/courses/{cno}`
- 请求方式: PUT
- 请求参数:
  ```json
  {
    "cname": "计算机网络",
    "credit": 4,
    "cdept": "计算机系"
  }
  ```
- 响应数据:
  ```json
  {
    "code": 200,
    "message": "更新成功",
    "data": null
  }
  ```

### 4. 删除课程
- 请求路径: `/courses/{cno}`
- 请求方式: DELETE
- 响应数据:
  ```json
  {
    "code": 200,
    "message": "删除成功",
    "data": null
  }
  ```

## 选课管理接口

### 1. 获取选课列表
- 请求路径: `/sc`
- 请求方式: GET
- 请求参数:
  ```json
  {
    "page": 1,        // 页码，默认1
    "size": 10,       // 每页数量，默认10
    "sno": "",        // 学号筛选（可选）
    "cno": ""         // 课程号筛选（可选）
  }
  ```
- 响应数据:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "total": 100,
      "list": [
        {
          "sno": "2024001",
          "cno": "C001",
          "grade": 85.5,
          "student": {
            "sname": "张三",
            "sdept": "计算机系"
          },
          "course": {
            "cname": "计算机网络",
            "credit": 4
          }
        }
      ]
    }
  }
  ```

### 2. 添加选课记录
- 请求路径: `/sc`
- 请求方式: POST
- 请求参数:
  ```json
  {
    "sno": "2024001",
    "cno": "C001"
  }
  ```
- 响应数据:
  ```json
  {
    "code": 200,
    "message": "选课成功",
    "data": null
  }
  ```

### 3. 更新成绩
- 请求路径: `/sc/{sno}/{cno}`
- 请求方式: PUT
- 请求参数:
  ```json
  {
    "grade": 85.5
  }
  ```
- 响应数据:
  ```json
  {
    "code": 200,
    "message": "成绩更新成功",
    "data": null
  }
  ```

### 4. 退课
- 请求路径: `/sc/{sno}/{cno}`
- 请求方式: DELETE
- 响应数据:
  ```json
  {
    "code": 200,
    "message": "退课成功",
    "data": null
  }
  ```

## 文件管理接口

### 1. 上传文件
- 请求路径: `/files/upload`
- 请求方式: POST
- 请求参数: multipart/form-data
  ```
  file: 文件
  author: 作者
  ```
- 响应数据:
  ```json
  {
    "code": 200,
    "message": "上传成功",
    "data": {
      "id": 1,
      "filename": "example.pdf",
      "author": "张三",
      "fileType": "pdf",
      "filePath": "/uploads/example.pdf",
      "uploadTime": "2024-03-20 10:00:00"
    }
  }
  ```

### 2. 获取文件列表
- 请求路径: `/files`
- 请求方式: GET
- 请求参数:
  ```json
  {
    "page": 1,        // 页码，默认1
    "size": 10,       // 每页数量，默认10
    "keyword": "",    // 搜索关键词（可选）
    "fileType": ""    // 文件类型筛选（可选）
  }
  ```
- 响应数据:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "total": 100,
      "list": [
        {
          "id": 1,
          "filename": "example.pdf",
          "author": "张三",
          "fileType": "pdf",
          "filePath": "/uploads/example.pdf",
          "uploadTime": "2024-03-20 10:00:00"
        }
      ]
    }
  }
  ```

### 3. 下载文件
- 请求路径: `/files/download/{id}`
- 请求方式: GET
- 响应数据: 文件流

### 4. 删除文件
- 请求路径: `/files/{id}`
- 请求方式: DELETE
- 响应数据:
  ```json
  {
    "code": 200,
    "message": "删除成功",
    "data": null
  }
  ```

## 错误码说明

### 通用错误码
- 400: 请求参数错误
- 401: 未授权
- 403: 禁止访问
- 404: 资源不存在
- 500: 服务器内部错误

### 业务错误码
- 1001: 学号已存在
- 1002: 课程号已存在
- 1003: 选课记录已存在
- 1004: 选课记录不存在
- 1005: 文件上传失败
- 1006: 文件不存在
- 1007: 文件下载失败

## 注意事项

1. 所有请求需要在header中携带token进行身份验证
2. 文件上传大小限制为10MB
3. 支持的文件类型：pdf, doc, docx, xls, xlsx, ppt, pptx, jpg, png
4. 分页参数page从1开始
5. 时间格式统一使用ISO 8601标准
6. 所有接口都需要进行参数验证
7. 文件上传接口需要处理并发情况
8. 选课接口需要考虑课程容量限制
9. 成绩更新需要验证权限
10. 删除操作需要二次确认 