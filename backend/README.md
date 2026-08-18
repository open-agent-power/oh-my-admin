# ContiNew Admin（定制版）

> 基于 [ContiNew Admin](https://github.com/Charles7c/continew-admin) 4.2.0 二次开发的中后台管理系统，已按项目 PRD 完成功能瘦身：**移除与实际业务无关的冗余功能**，保留系统核心认证、用户权限、组织管理与基础配置能力，降低系统复杂度与维护成本。

- 后端：Spring Boot 3 · Sa-Token · MyBatis Plus（多模块 Maven 工程）
- 前端：Vue 3 · Arco Design Vue · TypeScript · Vite
- 数据库：MySQL 8+（兼容 PostgreSQL），Liquibase 自动迁移建表
- 缓存：Redis（Redisson + JetCache）

---

## 功能概览

| 模块 | 功能 |
|---|---|
| 登录认证 | 账号/手机号/邮箱登录，行为验证码（滑块/点选）、图形验证码、短信/邮箱验证码 |
| 系统管理 | 用户管理、角色管理、菜单管理、部门管理、通知公告、文件管理、字典管理、系统配置 |
| 系统配置 | 网站配置、安全配置、登录配置、邮件配置、存储配置 |
| 系统监控 | 在线用户、登录日志、操作日志 |
| 其他 | 工作台（仪表盘）、个人中心（资料/密码/头像）、关于项目（接口文档） |

> 多租户运行时以**单租户模式**运行（默认租户 ID 为 0），保留多租户框架能力但不再管理租户数据。

---

## 技术栈

**后端**

| 技术 | 说明 |
|---|---|
| Spring Boot 3 / Java 17+ | 基础框架（推荐 JDK 21） |
| Sa-Token | 认证授权（JWT-Simple 模式） |
| MyBatis Plus | ORM |
| Redisson / JetCache | Redis 客户端 / 多级缓存 |
| Liquibase | 数据库变更管理（自动建表与种子数据） |
| FastExcel | Excel 导入导出 |
| P6Spy | SQL 日志 |
| Spring Doc（Knife4j） | 接口文档（`/doc.html`） |

**前端**

| 技术 | 说明 |
|---|---|
| Vue 3 + TypeScript | 基础框架 |
| Vite | 构建工具 |
| Arco Design Vue | UI 组件库 |
| Pinia / Vue Router | 状态管理 / 路由 |
| Axios | HTTP 请求 |

---

## 目录结构

```text
oh-my-admin/
├─ backend/                     # 后端（Maven 多模块）
│  ├─ continew-common/          # 公共模块（基类、工具、上下文、配置）
│  ├─ continew-system/          # 系统业务模块（用户/角色/菜单/部门/文件/日志等）
│  ├─ continew-server/          # 启动模块（打包部署、运行时配置）
│  └─ pom.xml
├─ frontend/                    # 前端（Vue 3）
│  ├─ src/
│  │  ├─ apis/                  # API 请求封装
│  │  ├─ views/                 # 页面（system / monitor / dashboard / login / about）
│  │  ├─ layout/                # 布局
│  │  ├─ stores/                # Pinia 状态
│  │  ├─ router/                # 路由
│  │  ├─ components/            # 公共组件
│  │  └─ hooks/                 # 组合式函数
│  └─ package.json
└─ docs/                        # PRD、技术方案、验收清单等文档
```

---

## 快速开始

### 环境要求

- JDK 17+（推荐 21）、Maven 3.9+
- MySQL 8+、Redis 6+
- Node.js 18+、pnpm 9+

### 1. 初始化数据库

提前创建数据库（默认库名 `continew_admin`，UTF-8）：

```sql
CREATE DATABASE IF NOT EXISTS `continew_admin` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

应用首次启动时，Liquibase 会自动建表并写入初始化数据（管理员账号、菜单、字典、系统配置等）。

### 2. 启动后端

```bash
cd backend
mvn clean package -DskipTests
cd continew-server/target/app
```

配置环境变量并启动：

```bash
export DB_PWD=你的数据库密码        # 数据库密码
export REDIS_PWD=                  # Redis 密码（无则留空）
export PROFILES_ACTIVE=dev         # 环境：dev / prod
export SERVER__PORT=8000           # 服务端口
java -jar bin/continew-admin.jar
```

也可以直接在 IDE 中运行 `ContiNewAdminApplication`（需配置 `DB_PWD`、`REDIS_PWD` 环境变量）。

启动成功后会输出服务地址与接口文档地址：

- 服务地址：`http://localhost:8000`
- 接口文档：`http://localhost:8000/doc.html`

### 3. 启动前端

```bash
cd frontend
pnpm install
pnpm dev
```

访问 `http://localhost:5173`。

### 默认账号

| 账号 | 密码 | 说明 |
|---|---|---|
| `admin` | `123456` | 超级管理员（首次登录后请及时修改密码） |

> 前端接口地址在 `frontend/.env.development` 中配置（默认 `http://localhost:8000`）。

---

## 构建部署

### 前端

```bash
cd frontend
pnpm build
# 产物输出到 dist/
```

### 后端

```bash
cd backend
mvn clean package -DskipTests
# 可部署产物：continew-server/target/app/（配置文件、依赖与主程序分离）
```

### 生产环境安全配置

以下密钥已支持环境变量注入，**生产环境务必设置独立强密钥**：

| 环境变量 | 默认值（仅限本地开发） | 说明 |
|---|---|---|
| `JWT_SECRET_KEY` | `asdasdasifhueuiwyurfewbfjsdafjk` | JWT 签名密钥，泄露可伪造登录令牌 |
| `AES_SECRET_KEY` | `abcdefghijklmnop` | 数据库字段加密密钥，**修改后已有加密数据将无法解密，请谨慎** |

---

## 相对上游已移除的功能

本项目按 `docs/prd.md` 对上游 ContiNew Admin 做了功能瘦身，移除以下模块（前端页面、菜单、后端代码、数据库表与 SQL 迁移、配置项均已一并清理）：

- 仪表盘最新公告 / 最新动态
- 微信第三方账号登录 / 绑定 / 解绑（社交登录）
- 消息中心（站内信）
- 短信配置 / 短信日志
- 客户端多端认证管理
- 应用管理（AK/SK 开放能力）
- 租户管理 / 租户套餐
- 任务调度 / 任务日志
- 代码生成

保留的登录基础设施：账号/手机号/邮箱登录、行为/图形/短信/邮箱验证码、单租户运行时接口（`/tenant/common/id`）等。

---

## 相关文档

- `docs/prd.md` — 功能瘦身删除范围 PRD
- `docs/TECHNICAL_PLAN.md` — 技术方案
- `docs/FEATURE_DELETE_LIST.md` — 功能删除清单
- `docs/ACCEPTANCE_CHECKLIST.md` — 验收清单

---

## License

[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
