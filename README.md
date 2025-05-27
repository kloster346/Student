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
└── frontend/            # 前端项目目录
    ├── src/            # Vue.js源代码
    │   ├── assets/     # 静态资源
    │   ├── components/ # 公共组件
    │   ├── router/     # 路由配置
    │   ├── store/      # Vuex状态管理
    │   ├── views/      # 页面组件
    │   ├── App.vue     # 根组件
    │   └── main.js     # 入口文件
    ├── public/         # 公共资源
    ├── babel.config.js # Babel配置
    ├── jsconfig.json   # JavaScript配置
    ├── vue.config.js   # Vue CLI配置
    └── package.json    # 项目依赖配置
```

## 技术栈

### 后端技术栈
- Spring Boot 3.4.5
- MyBatis-Plus 3.5.8
- MySQL 8.0
- Java 17
- Lombok

### 前端技术栈
- Vue.js 3.2.13
- Vue Router 4.0.3
- Vuex 4.0.0
- Axios
- Element Plus
- Sass
- ESLint + Prettier
- Babel
- Vue CLI

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

4. 构建生产环境代码
```bash
npm run build
```

## 开发规范

### 代码规范
- 后端遵循阿里巴巴Java开发手册
- 前端遵循Vue.js官方风格指南
- 使用ESLint和Prettier进行代码格式化
- 组件命名采用PascalCase
- 文件命名采用kebab-case

### Git提交规范
- feat: 新功能
- fix: 修复bug
- docs: 文档更新
- style: 代码格式调整
- refactor: 代码重构
- test: 测试用例
- chore: 其他修改

## 部署说明

### 后端部署
1. 打包项目
```bash
mvn clean package
```

2. 运行jar包
```bash
java -jar target/ch7_5-0.0.1-SNAPSHOT.jar
```

### 前端部署
1. 构建生产环境代码
```bash
npm run build
```

2. 将dist目录下的文件部署到Web服务器

## 注意事项
1. 确保MySQL服务已启动，并创建了springtest数据库
2. 检查application.properties中的数据库配置是否正确
3. 前端开发时注意跨域配置
4. 确保MySQL的root用户密码与配置文件中的一致
5. 如果使用不同的数据库配置，请相应修改application.properties文件
6. 前端开发时注意Node.js版本兼容性
7. 确保安装了所有必要的npm包
8. 文件上传功能需要配置适当的文件大小限制
9. 注意文件存储路径的权限设置

## 相关文档
- [开发计划](DEVELOPMENT_PLAN.md) - 详细的功能开发计划和进度跟踪
- [数据库设计文档](docs/database.md) - 数据库表结构、关系图和初始化脚本
- [API文档](docs/api.md) - API接口文档
- [部署文档](docs/deployment.md) - 部署指南（待完善）

## 贡献指南
1. Fork 本仓库
2. 创建新的功能分支
3. 提交代码
4. 创建 Pull Request

## 许可证
MIT License 