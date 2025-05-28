# 学生选课管理系统

这是一个基于Spring Boot + Vue.js的学生选课管理系统，采用前后端分离架构。本项目是计算机网络编程课程的期末项目。

## 项目结构

```
项目根目录
├── ch7_5/                # 后端项目目录
│   ├── src/             # 源代码目录
│   │   ├── main/
│   │   │   ├── java/   # Java源代码
│   │   │   │   └── demo/ch7_5/
│   │   │   │       ├── config/    # 配置类
│   │   │   │       ├── controller/ # 控制器
│   │   │   │       ├── entity/    # 实体类
│   │   │   │       ├── mapper/    # MyBatis映射接口
│   │   │   │       ├── service/   # 服务层
│   │   │   │       └── Ch75Application.java # 启动类
│   │   │   └── resources/ # 配置文件
│   │   │       └── application.properties # 应用配置
│   │   └── test/       # 测试代码
│   └── pom.xml         # Maven配置文件
│
└── frontend/           # 前端项目目录
    ├── src/           # Vue.js源代码
    │   ├── api/      # API接口封装
    │   ├── components/ # 组件目录
    │   │   ├── StudentList.vue  # 学生管理组件
    │   │   ├── CourseList.vue   # 课程管理组件
    │   │   └── SelectionList.vue # 选课管理组件
    │   ├── App.vue    # 根组件
    │   └── main.js    # 入口文件
    ├── public/        # 公共资源
    ├── babel.config.js # Babel配置
    ├── jsconfig.json  # JavaScript配置
    ├── vue.config.js  # Vue CLI配置
    └── package.json   # 项目依赖配置
```

## 功能特性

### 1. 学生管理
- 学生信息列表展示
- 添加新学生
- 编辑学生信息
- 删除学生

### 2. 课程管理
- 课程信息列表展示
- 添加新课程
- 编辑课程信息
- 删除课程

### 3. 选课管理
- 选课记录列表展示
- 学生选课功能
- 成绩录入功能
- 退课功能

## 技术栈

### 后端技术栈
- Spring Boot 3.4.5
- MyBatis-Plus 3.5.8
- MySQL 8.0
- Java 17
- Lombok

### 前端技术栈
- Vue.js 3.2.13
- Element Plus 2.5.3
- Axios 1.6.7

## 开发环境要求

- JDK 17+
- Node.js 14+
- MySQL 8.0+
- Maven 3.6+

## 快速开始

### 后端启动
1. 进入后端项目目录
```bash
cd ch7_5
```

2. 使用Maven安装依赖
```bash
mvn install
```

3. 启动Spring Boot应用
```bash
mvn spring-boot:run
```

### 前端启动
1. 进入前端项目目录
```bash
cd frontend
```

2. 安装依赖
```bash
npm install
```

3. 启动开发服务器
```bash
npm run serve
```

## 项目特点

1. 简单直观的用户界面
   - 清晰的导航菜单
   - 直观的表单操作
   - 即时的操作反馈

2. 高效的数据管理
   - 表格化展示数据
   - 快速的增删改查
   - 实时的数据更新

3. 友好的用户体验
   - 下拉选择替代手动输入
   - 支持搜索过滤
   - 表单验证提示

## 注意事项
1. 确保MySQL服务已启动，并创建了springtest数据库
2. 检查application.properties中的数据库配置是否正确
3. 前端开发时注意跨域配置
4. 确保MySQL的root用户密码与配置文件中的一致

## 相关文档
- [开发计划](DEVELOPMENT_PLAN.md) - 详细的功能开发计划和进度跟踪
- [数据库设计文档](docs/database.md) - 数据库表结构、关系图和初始化脚本
- [API文档](docs/api.md) - API接口文档

## 许可证
MIT License 