-- liquibase formatted sql

-- changeset kai:1
-- comment 初始化任务调度插件数据表
-- 初始化默认菜单
INSERT INTO "sys_menu"
("id", "title", "parent_id", "type", "path", "name", "component", "redirect", "icon", "is_external", "is_cache", "is_hidden", "permission", "sort", "status", "create_user", "create_time")
VALUES
(8000, '任务调度', 0, 1, '/schedule', 'Schedule', 'Layout', '/schedule/log', 'schedule', false, false, false, NULL, 8, 1, 1, NOW()),
(8020, '任务日志', 8000, 2, '/schedule/log', 'ScheduleLog', 'schedule/log/index', NULL, 'find-replace', false, false, false, NULL, 1, 1, 1, NOW()),
(8021, '列表', 8020, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'schedule:log:list', 1, 1, 1, NOW()),
(8022, '停止', 8020, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'schedule:log:stop', 3, 1, 1, NOW()),
(8023, '重试', 8020, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'schedule:log:retry', 4, 1, 1, NOW());
