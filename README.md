# Houses · 房产销售平台

> 基于 Spring Boot 4.1.1 的多模块单体房产销售/中介管理平台，涵盖房源发布、用户经纪人管理、评论、邮件通知、文件上传等典型业务场景。

[![Java](https://img.shields.io/badge/Java-21+-blue.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.1-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.x-red.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](#license)

---

## 目录

- [项目简介](#项目简介)
- [功能特性](#功能特性)
- [技术栈](#技术栈)
- [项目结构](#项目结构)
- [系统架构](#系统架构)
- [快速开始](#快速开始)
- [配置说明](#配置说明)
- [模块说明](#模块说明)
- [运维监控](#运维监控)
- [开发规范](#开发规范)
- [常见问题](#常见问题)
- [License](#license)

---

## 项目简介

`houses` 是一个面向房产中介与个人用户的销售平台，采用经典 **Spring Boot + MyBatis + FreeMarker** 技术栈，使用 Maven 多模块（parent / common / biz / web）划分，便于业务分层和独立演进。该项目可用于学习以下主题：

- Spring Boot 自动装配机制与自定义 Starter（`autoconfig` 模块）
- MyBatis 与 Druid 连接池整合
- FreeMarker 模板渲染
- Servlet Filter / Spring Interceptor 请求处理链
- Spring Boot Actuator 健康监控
- 邮件发送、文件上传、Redis（Jedis）缓存等常见集成

## 功能特性

- 🏠 **房源管理**：房源发布、编辑、列表、详情、收藏与浏览记录
- 👥 **用户体系**：注册 / 登录 / 找回密码 / 邮箱激活 / 个人中心
- 🧑‍💼 **经纪人模块**：经纪人列表、详情、个人主页
- 💬 **评论互动**：房源评论与楼盘讨论
- 📩 **邮件通知**：基于 `spring-boot-starter-mail` 的注册激活与通知邮件
- 🖼️ **图片服务**：本地文件存储与静态资源访问
- 📊 **健康监控**：通过 Spring Boot Actuator 暴露应用运行状态
- 🛡️ **请求过滤**：自定义 `LogFilter` 与拦截器，统一日志与登录态校验

## 技术栈

| 类别 | 技术 / 组件                                                               |
| --- |---------------------------------------------------------------------------|
| 核心框架 | Spring Boot 4.1.1、Spring Web MVC                                         |
| 持久层 | MyBatis 4.1.0 (Spring Boot Starter)、MySQL                                     |
| 数据源 | Alibaba Druid 1.2.28                                                      |
| 缓存 | Redis（Jedis 2.9.0）                                                      |
| 视图层 | FreeMarker                                                                |
| Web 容器 | Jetty（替换默认 Tomcat）                                                  |
| 工具库 | Guava、Apache Commons Lang3、commons-beanutils、Lombok、Jsoup、HttpClient |
| 监控 | Spring Boot Actuator                                                      |
| 构建 | Maven 3.x、JDK 21                                                         |
| 邮件 | Spring Mail（SMTP）                                                       |

## 项目结构

```
houses/
├── pom.xml                   # 父级 POM，统一管理依赖与版本
├── house-common/             # 通用工具与基础类
│   └── src/main/java/com/aiuiot/common/utils
├── house-biz/                # 业务层（Service / Mapper / 配置）
│   └── src/main/
│       ├── java/com/aiuiot/house/biz
│       │   ├── config/       # 业务相关配置
│       │   ├── mapper/       # MyBatis Mapper 接口
│       │   └── service/      # 业务服务（House/User/Agency/Comment/Mail/File...）
│       └── resources/
│           ├── mapper/       # MyBatis XML 映射文件
│           └── mybatis/      # MyBatis 全局配置
├── house-web/                # Web 启动模块
│   ├── autoconfig/           # 自定义 HttpClient AutoConfiguration 示例
│   ├── filter/               # Servlet Filter / MyBatis 配置
│   └── src/main/
│       ├── java/com/aiuiot/house/
│       │   ├── HousesApplication.java   # SpringBoot 启动类
│       │   └── web/
│       │       ├── controller/          # 控制器（Home/House/User/Agency/Error...）
│       │       ├── filter/              # 过滤器
│       │       ├── interceptor/         # 拦截器
│       │       └── autoconfig/          # 自动装配
│       └── resources/
│           ├── application.properties
│           ├── logback.xml
│           ├── static/                  # 静态资源
│           └── templates/               # FreeMarker 模板
└── README.md
```

## 系统架构

```
            ┌──────────────────────────┐
            │      浏览器 / 客户端     │
            └────────────┬─────────────┘
                         │ HTTP
                         ▼
            ┌──────────────────────────┐
            │  house-web (Jetty)       │
            │  Controller / Filter /   │
            │  Interceptor / FTL       │
            └────────────┬─────────────┘
                         │
                         ▼
            ┌──────────────────────────┐
            │  house-biz               │
            │  Service / Mapper        │
            └────────┬────────┬────────┘
                     │        │
              ┌──────▼──┐  ┌──▼──────┐
              │  MySQL  │  │  Redis  │
              │ (Druid) │  │ (Jedis) │
              └─────────┘  └─────────┘
                     ▲
                     │
            ┌────────┴─────────┐
            │  house-common    │
            │  utils / 基础类  │
            └──────────────────┘
```

## 快速开始

### 环境要求

- JDK **1.8+**
- Maven **3.5+**
- MySQL **5.7 / 8.0**
- Redis **3.x+**（可选，用于缓存）
- SMTP 邮件服务器（可选，用于邮件功能）

### 1. 克隆项目

```bash
git clone <your-repo-url> houses
cd houses
```

### 2. 初始化数据库

创建数据库 `houses`：

```sql
CREATE DATABASE houses DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

> 业务表结构请根据 [house-biz/src/main/resources/mapper](house-biz/src/main/resources/mapper) 中的 XML 字段建表，或导入项目维护的 SQL 脚本（如有）。

### 3. 修改配置

编辑 [house-web/src/main/resources/application.properties](house-web/src/main/resources/application.properties)：

```properties
# 数据源
spring.druid.url=jdbc:mysql://localhost:3306/houses
spring.druid.username=your_username
spring.druid.password=your_password

# 文件上传路径（本地目录，请改为可写路径）
file.path=/your/local/path/imgs
file.prefix=http://127.0.0.1:8081/static/imgs

# 邮件（可选）
spring.mail.host=smtp.163.com
spring.mail.username=your_mail@163.com
spring.mail.password=your_smtp_auth_code
```

### 4. 构建与运行

```bash
# 编译打包
mvn clean install -DskipTests

# 运行 web 模块
cd house-web
mvn spring-boot:run
```

或直接运行打包后的 jar：

```bash
java -jar house-web/target/house-web-1.0.0-SNAPSHOT.jar
```

### 5. 访问

- 首页：<http://localhost:8082/>
- 健康检查：<http://localhost:8092/health>

## 配置说明

| 配置项 | 说明 | 默认值 |
| --- | --- | --- |
| `server.port` | 业务端口 | `8082` |
| `management.port` | Actuator 监控端口 | `8092` |
| `spring.druid.*` | Druid 数据源参数 | 详见 application.properties |
| `mybatis.config-location` | MyBatis 全局配置 | `classpath:/mybatis/mybatis-config.xml` |
| `spring.freemarker.*` | FreeMarker 模板配置 | UTF-8 / `.ftl` |
| `file.path` | 上传文件本地存储路径 | `/Users/duxuetech/opt/imgs` |
| `file.prefix` | 文件外部访问 URL 前缀 | `http://127.0.0.1:8081/static/imgs` |
| `domain.name` | 应用域名（拼接邮件链接等） | `127.0.0.1:8090` |
| `spring.mail.*` | SMTP 邮件配置 | — |

## 模块说明

### house-common

通用基础设施模块，提供：

- 工具类（`com.aiuiot.common.utils`）
- 内嵌 Web 容器（替换 Tomcat 为 Jetty）
- HttpClient、Guava、Lombok、commons-beanutils 等公共依赖

### house-biz

业务层，包含：

- **Mapper**：`AgencyMapper`、`CityMapper`、`CommentMapper`、`HouseMapper`、`UserMapper`
- **Service**：`HouseService`、`UserService`、`AgencyService`、`CommentService`、`FileService`、`MailService`、`RecommendService`、`CityService`
- **集成**：MyBatis、Druid、MySQL、Jedis、Spring Mail、Jsoup

### house-web

Web 启动模块（包含 `HousesApplication` 启动类）：

- **Controller**：`HomepageController`、`HouseController`、`UserController`、`AgencyController`、`HelloController`、`ErrorHandler`
- **Filter**：`LogFilter`（请求日志）
- **拦截器**：登录校验等
- **autoconfig**：`HttpClientAutoConfiguration` —— 演示自定义 Spring Boot Starter / 条件装配
- **模板**：FreeMarker 视图（`templates/` 目录）

## 运维监控

集成 Spring Boot Actuator，常用端点（监听端口 `management.port=8092`）：

| Endpoint | 描述 |
| --- | --- |
| `/health` | 应用健康状态 |
| `/info` | 自定义应用信息 |
| `/beans` | Spring 容器中所有 Bean |
| `/autoconfig` | 自动装配报告 |
| `/env` | 环境与配置变量 |
| `/mappings` | URL 与 Controller 映射关系 |
| `/metrics` | JVM / 请求指标 |

> 生产环境建议关闭敏感端点或加上权限控制。

## 开发规范

- 包结构遵循 `com.aiuiot.house.<module>.<layer>` 约定
- Mapper XML 与接口同名，便于 MyBatis 扫描
- 静态资源放于 `house-web/src/main/resources/static/`
- 模板按业务领域分目录（`homepage / house / user / user.agent / error / common`）
- **不要提交日志、构建产物、IDE 元数据**（已在 `.gitignore` 中忽略 `logs/`、`*.log`、`target/`、`.idea/`、`*.iml` 等）

## 常见问题

**Q：启动报 `Communications link failure` ？**
A：检查 MySQL 是否启动、`spring.druid.url` 用户名密码是否正确，注意 MySQL 8 需要使用 `com.mysql.cj.jdbc.Driver`。

**Q：图片上传后 404？**
A：确认 `file.path` 是绝对且可写的目录，并保证 `file.prefix` 指向的静态文件服务可访问。

**Q：邮件发送 `535 Error`？**
A：使用邮箱客户端授权码而不是登录密码；并开启 SMTP 服务。

**Q：8 小时连接断开？**
A：已通过 `spring.druid.validation-query=SELECT 'X'` 配置心跳查询解决。

## License

本项目仅用于学习与交流，遵循 MIT License。
