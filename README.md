# oh-my-admin

基于 [ContiNew Admin](https://github.com/Charles7c/continew-admin) 4.2.0 二次开发并完成**功能瘦身**的中后台管理系统。按 `docs/prd.md` 移除了与业务无关的冗余功能（社交登录、消息中心、短信、租户、任务调度、代码生成等 13 类），保留核心认证、用户权限、组织管理与基础配置能力。

- 后端：Spring Boot 3 · Sa-Token · MyBatis Plus · MySQL/Redis
- 前端：Vue 3 · Arco Design Vue · TypeScript · Vite
- 详细说明见 [backend/README.md](backend/README.md)

## 功能

- **系统管理**：用户、角色、菜单、部门、通知公告、文件、字典、系统配置
- **系统监控**：在线用户、登录日志、操作日志
- **登录认证**：账号/手机号/邮箱登录 + 行为/图形/短信/邮箱验证码
- **其他**：工作台（仪表盘）、个人中心

## 目录结构

```text
oh-my-admin/
├─ backend/          # 后端（Maven 多模块：continew-common / continew-system / continew-server）
├─ frontend/         # 前端（Vue 3 + TS + Vite）
├─ docs/             # PRD、技术方案、验收清单
└─ README.md
```

## 快速开始

```bash
# 后端（首次启动自动建表 + 初始化数据，详见 backend/README.md）
cd backend && mvn clean package -DskipTests
cd continew-server/target/app && java -jar bin/continew-admin.jar

# 前端
cd frontend && pnpm install && pnpm dev
```

- 前端地址：http://localhost:5173
- 后端地址 / 接口文档：http://localhost:8000 / http://localhost:8000/doc.html
- 默认账号：`admin` / `123456`

## 文档

- `docs/prd.md` — 功能瘦身删除范围 PRD
- `docs/TECHNICAL_PLAN.md` — 技术方案
- `docs/FEATURE_DELETE_LIST.md` — 功能删除清单
- `docs/ACCEPTANCE_CHECKLIST.md` — 验收清单

## License

[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
