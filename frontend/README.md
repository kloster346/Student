# 学生管理系统前端文档

## 项目概述
这是一个简单的学生管理系统前端实现，使用 Vue 3 + Element Plus + Axios 技术栈，实现与后端的数据交互和基本的增删改查功能。

## 技术栈
- Vue 3 - 前端框架
- Element Plus - UI组件库
- Axios - HTTP请求库

## 功能模块

### 1. 学生管理
- 学生列表展示
- 添加学生
- 修改学生信息
- 删除学生

### 2. 课程管理
- 课程列表展示
- 添加课程
- 修改课程信息
- 删除课程

### 3. 选课管理
- 选课记录列表
- 选课功能
- 成绩录入
- 退课功能

## 项目结构
```
frontend/
├── src/
│   ├── api/          # API 接口
│   ├── components/   # 组件
│   ├── App.vue       # 根组件
│   └── main.js       # 入口文件
└── package.json      # 项目配置
```

## 开发说明

1. 安装依赖：
```bash
npm install
```

2. 运行项目：
```bash
npm run serve
```

## 接口说明

所有接口基础URL为：`http://localhost:8080/api`

### 学生接口
- 获取学生列表：GET /students
- 添加学生：POST /students
- 更新学生：PUT /students/{sno}
- 删除学生：DELETE /students/{sno}

### 课程接口
- 获取课程列表：GET /courses
- 添加课程：POST /courses
- 更新课程：PUT /courses/{cno}
- 删除课程：DELETE /courses/{cno}

### 选课接口
- 获取选课记录：GET /sc
- 选课：POST /sc
- 更新成绩：PUT /sc/{sno}/{cno}
- 退课：DELETE /sc/{sno}/{cno}
