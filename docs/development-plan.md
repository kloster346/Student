# 学生管理系统功能扩展开发计划

## 一、后端开发计划

### 1. 多表联合查询功能
- [ ] 1.1 在 `StudentController` 中添加联合查询接口
  ```java
  // 查询学生选课信息
  GET /api/students/courses
  参数：
  - studentName (可选): 学生姓名
  - courseName (可选): 课程名称
  - minGrade (可选): 最低分数
  - maxGrade (可选): 最高分数
  - page: 页码
  - size: 每页大小
  - sort: 排序字段
  - order: 排序方式(asc/desc)
  ```
- [ ] 1.2 创建对应的 Service 层方法
- [ ] 1.3 实现 MyBatis 多表联合查询
- [ ] 1.4 添加相应的单元测试

### 2. 批量删除功能
- [ ] 2.1 在各个 Controller 中添加批量删除接口
  ```java
  // 批量删除学生
  DELETE /api/students/batch
  // 批量删除课程
  DELETE /api/courses/batch
  // 批量删除选课记录
  DELETE /api/sc/batch
  ```
- [ ] 2.2 实现 Service 层的批量删除方法
- [ ] 2.3 添加事务管理
- [ ] 2.4 添加相应的单元测试

### 3. 条件查询与排序功能
- [ ] 3.1 设计通用的查询条件和排序参数结构
  ```java
  public class QueryParams {
      private Map<String, Object> conditions;
      private String sortField;
      private String sortOrder;
      private int page;
      private int size;
  }
  ```
- [ ] 3.2 修改现有的查询接口，支持多条件
- [ ] 3.3 实现动态 SQL 查询
- [ ] 3.4 添加相应的单元测试

### 4. 文件导入功能
- [ ] 4.1 创建文件上传接口
  ```java
  // 导入学生数据
  POST /api/students/import
  // 导入课程数据
  POST /api/courses/import
  // 导入选课数据
  POST /api/sc/import
  ```
- [ ] 4.2 实现文件解析服务
  - 支持 CSV 格式
  - 数据验证
  - 错误处理
- [ ] 4.3 实现批量导入服务
- [ ] 4.4 添加相应的单元测试

## 二、前端开发计划

### 1. 多表查询界面
- [ ] 1.1 创建新的组件 `StudentCourseList.vue`
- [ ] 1.2 实现查询表单
  - 学生姓名输入
  - 课程名称输入
  - 成绩范围选择
- [ ] 1.3 实现结果展示表格
- [ ] 1.4 集成分页和排序功能

### 2. 批量操作功能
- [ ] 2.1 修改现有列表组件
  - 添加表格多选功能
  - 添加批量删除按钮
- [ ] 2.2 实现批量删除确认对话框
- [ ] 2.3 实现批量删除 API 调用
- [ ] 2.4 添加操作结果提示

### 3. 高级查询功能
- [ ] 3.1 设计查询条件组件
  - 支持多个查询条件
  - 支持条件组合
- [ ] 3.2 实现排序功能
  - 表头排序图标
  - 排序切换逻辑
- [ ] 3.3 优化分页组件
  - 显示总记录数
  - 显示当前页信息

### 4. 文件导入功能
- [ ] 4.1 创建文件上传组件
  - 文件选择
  - 上传进度显示
  - 结果提示
- [ ] 4.2 实现文件格式说明
- [ ] 4.3 实现上传结果展示
  - 成功记录数
  - 失败记录列表
  - 错误原因说明

## 三、API 接口规范

### 1. 多表查询接口
```json
GET /api/students/courses
请求参数：
{
  "studentName": "张三",
  "courseName": "计算机网络",
  "minGrade": 60,
  "maxGrade": 100,
  "page": 1,
  "size": 10,
  "sortField": "grade",
  "sortOrder": "desc"
}

响应数据：
{
  "code": 200,
  "message": "success",
  "data": {
    "total": 100,
    "list": [
      {
        "studentNo": "2005",
        "studentName": "张三",
        "courseNo": "C001",
        "courseName": "计算机网络",
        "grade": 85
      }
    ]
  }
}
```

### 2. 批量删除接口
```json
DELETE /api/students/batch
请求参数：
{
  "ids": ["2005", "2006", "2007"]
}

响应数据：
{
  "code": 200,
  "message": "success",
  "data": {
    "deletedCount": 3
  }
}
```

### 3. 文件上传接口
```json
POST /api/students/import
请求：
Content-Type: multipart/form-data
file: [文件内容]

响应数据：
{
  "code": 200,
  "message": "success",
  "data": {
    "totalCount": 100,
    "successCount": 98,
    "failureCount": 2,
    "failures": [
      {
        "line": 15,
        "content": "2005,张三,男",
        "reason": "学号已存在"
      }
    ]
  }
}
```

## 四、开发时间安排

### 第一周：后端开发
1. 多表查询功能（2天）
2. 批量删除功能（1天）
3. 条件查询与排序功能（1天）
4. 文件导入功能（1天）

### 第二周：前端开发
1. 多表查询界面（2天）
2. 批量操作功能（1天）
3. 高级查询功能（1天）
4. 文件导入功能（1天）

### 第三周：测试与优化
1. 单元测试编写（2天）
2. 集成测试（1天）
3. 性能优化（1天）
4. 文档完善（1天）

## 五、注意事项

1. 后端开发注意事项：
   - 确保所有新接口都有适当的参数验证
   - 添加必要的日志记录
   - 实现统一的错误处理
   - 注意大数据量时的性能优化

2. 前端开发注意事项：
   - 保持与现有UI风格一致
   - 添加适当的加载状态提示
   - 实现友好的错误提示
   - 注意表单验证
   - 优化用户操作体验

3. 测试要点：
   - 边界条件测试
   - 大数据量测试
   - 并发操作测试
   - 文件上传限制测试 