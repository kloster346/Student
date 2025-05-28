# 学生管理系统前端文档

## 项目概述
这是一个功能完整的学生管理系统前端实现，使用 Vue 3 + Element Plus + Axios 技术栈，实现了学生信息管理、课程管理、选课管理等核心功能，并支持文件导入、高级查询等扩展功能。

## 技术栈
- Vue 3 - 前端框架
- Element Plus - UI组件库
- Axios - HTTP请求库
- Vue Router - 路由管理
- ESLint - 代码规范检查

## 功能模块

### 1. 学生管理
- 学生列表展示（支持分页、排序、搜索）
- 添加学生信息
- 修改学生信息
- 删除学生（支持批量删除）
- 导入学生数据（支持CSV文件导入）

### 2. 课程管理
- 课程列表展示（支持分页、排序、搜索）
- 添加课程信息
- 修改课程信息
- 删除课程（支持批量删除）
- 导入课程数据（支持CSV文件导入）

### 3. 选课管理
- 选课记录列表（支持分页、排序、搜索）
- 选课功能（支持多选）
- 成绩录入和修改
- 退课功能（支持批量退课）
- 导入选课数据（支持CSV文件导入）

### 4. 高级功能
- 多条件组合查询
- 文件导入导出
- 数据校验和错误提示
- 批量操作功能
- 响应式界面设计

### 4. 文件导入功能
- 支持拖拽上传和点击上传
- 支持CSV格式文件导入
- 文件大小限制：5MB
- 实时上传进度显示
- 详细的导入结果反馈
- 失败记录详情展示
- 文件格式说明和示例
- 友好的错误提示

#### 使用说明
1. 将CSV文件拖拽到上传区域或点击上传按钮选择文件
2. 系统会自动验证文件格式和大小
3. 上传过程中显示进度条
4. 上传完成后显示导入结果
5. 如果有导入失败的记录，可以展开查看详情

#### 注意事项
- 请确保CSV文件使用UTF-8编码
- 文件第一行必须是标题行
- 请严格按照模板格式准备数据
- 建议单次导入数据不超过1000条
- 导入前请仔细检查数据格式

## 项目结构
```
frontend/
├── public/           # 静态资源
├── src/
│   ├── api/         # API 接口封装
│   ├── assets/      # 项目资源文件
│   ├── components/  # 通用组件
│   │   ├── StudentList.vue    # 学生列表组件
│   │   ├── CourseList.vue     # 课程列表组件
│   │   └── SelectionList.vue  # 选课列表组件
│   ├── layout/      # 布局组件
│   │   └── MainLayout.vue     # 主布局组件
│   ├── views/       # 页面视图组件
│   │   ├── student/
│   │   │   └── StudentView.vue
│   │   ├── course/
│   │   │   └── CourseView.vue
│   │   └── selection/
│   │       └── SelectionView.vue
│   ├── router/      # 路由配置
│   ├── utils/       # 工具函数
│   ├── App.vue      # 根组件
│   └── main.js      # 入口文件
├── .eslintrc.js     # ESLint配置
├── .gitignore       # Git忽略文件
├── babel.config.js  # Babel配置
├── package.json     # 项目依赖
└── vue.config.js    # Vue配置
```

## 开发说明

1. 安装依赖：
```bash
npm install
```

2. 运行开发服务器：
```bash
npm run serve
```

3. 构建生产版本：
```bash
npm run build
```

4. 代码检查：
```bash
npm run lint
```

## 路由说明

应用使用 Vue Router 进行路由管理，主要路由配置如下：

- / - 默认重定向到学生管理页面
- /student - 学生管理页面
- /course - 课程管理页面
- /selection - 选课管理页面

所有页面都使用 MainLayout 作为基础布局，包含侧边栏导航和顶部标题栏。

## 接口说明

所有接口基础URL为：`http://localhost:8081/api`

### 基础接口
#### 学生接口
- 获取学生列表：GET /students
  - 支持分页参数：page, size
  - 支持排序参数：sort, order
  - 支持搜索参数：search
- 添加学生：POST /students
- 更新学生：PUT /students/{sno}
- 删除学生：DELETE /students/{sno}
- 批量删除：DELETE /students/batch

#### 课程接口
- 获取课程列表：GET /courses
  - 支持分页参数：page, size
  - 支持排序参数：sort, order
  - 支持搜索参数：search
- 添加课程：POST /courses
- 更新课程：PUT /courses/{cno}
- 删除课程：DELETE /courses/{cno}
- 批量删除：DELETE /courses/batch

#### 选课接口
- 获取选课记录：GET /sc
  - 支持分页参数：page, size
  - 支持排序参数：sort, order
  - 支持搜索参数：search
- 选课：POST /sc
- 更新成绩：PUT /sc/{sno}/{cno}
- 退课：DELETE /sc/{sno}/{cno}
- 批量退课：DELETE /sc/batch

### 扩展接口
#### 导入接口
- 导入学生数据：POST /import/students
- 导入课程数据：POST /import/courses
- 导入选课数据：POST /import/sc

#### 高级查询接口
- 学生选课信息查询：GET /students/courses
  - 支持多条件查询：studentName, courseName, minGrade, maxGrade
  - 支持分页和排序

## 开发规范
1. 组件命名采用大驼峰命名法（如：StudentView, CourseList）
2. 视图组件放在 views 目录下，通用组件放在 components 目录下
3. 布局相关组件放在 layout 目录下
4. API接口统一在api目录下管理
5. 使用ESLint进行代码规范检查
6. 保持良好的代码注释
7. 遵循Vue 3的组合式API编写规范

## 注意事项
1. 开发前请确保后端服务已启动
2. 文件导入支持的格式为CSV
3. 批量操作时注意数据一致性
4. 请合理使用分页功能，避免一次性加载过多数据
5. 组件命名必须是多词组合（ESLint规范要求）
