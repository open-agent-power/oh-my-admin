-- liquibase formatted sql

-- changeset kai:1
-- comment 初始化任务调度插件数据表
-- 初始化默认菜单
INSERT INTO "sys_menu"
("id", "title", "parent_id", "type", "path", "name", "component", "redirect", "icon", "is_external", "is_cache", "is_hidden", "permission", "sort", "status", "create_user", "create_time")
VALUES
(8000, '任务调度', 0, 1, '/schedule', 'Schedule', 'Layout', NULL, 'schedule', false, false, false, NULL, 8, 1, 1, NOW());
