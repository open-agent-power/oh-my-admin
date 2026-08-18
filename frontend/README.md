# oh-my-admin Frontend

基于 [ContiNew Admin UI](https://github.com/continew-org/continew-admin-ui) 4.2.0 二次开发的中后台前端，已随项目 PRD 完成功能瘦身（移除社交登录、消息中心、短信、租户、任务调度、代码生成等 13 类功能的页面与入口）。

- 框架：Vue 3 · TypeScript · Vite
- UI：Arco Design Vue
- 状态管理：Pinia · 路由：Vue Router · 请求：Axios

## 目录结构

```text
frontend/src/
├─ apis/            # API 请求封装（按模块）
├─ views/           # 页面（system 系统管理 / monitor 系统监控 / dashboard 工作台 / login / about）
├─ layout/          # 布局组件
├─ components/      # 公共组件
├─ stores/          # Pinia 状态
├─ router/          # 路由配置
├─ hooks/           # 组合式函数
├─ utils/           # 工具函数
└─ types/           # 类型定义
```

## 开发

```bash
pnpm install     # 安装依赖
pnpm dev         # 启动开发服务（默认 http://localhost:5173）
pnpm typecheck   # 类型检查（vue-tsc）
pnpm build       # 生产构建（输出 dist/）
```

## 环境变量

配置见 `.env.development` / `.env.production`（均以 `VITE_` 开头）：

| 变量 | 默认值 | 说明 |
|---|---|---|
| `VITE_API_BASE_URL` | `http://localhost:8000` | 后端接口地址 |
| `VITE_API_PREFIX` | `/dev-api` | 接口路径前缀（由 Vite 代理转发） |
| `VITE_CLIENT_ID` | — | 登录客户端 ID |

## 说明

- 登录页支持账号/手机号/邮箱登录及行为/图形/短信/邮箱验证码（依赖后端保留的认证基础设施）
- 页面菜单由后端动态下发（基于角色权限），前端仅负责渲染
- 接口文档入口：`关于项目 → 接口文档`（指向后端 `/doc.html`）
