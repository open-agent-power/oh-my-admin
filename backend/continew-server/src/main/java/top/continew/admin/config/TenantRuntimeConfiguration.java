/*
 * Copyright (c) 2022-present Charles7c Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.continew.admin.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.continew.admin.common.api.tenant.PackageMenuApi;
import top.continew.admin.common.api.tenant.TenantApi;
import top.continew.admin.common.config.TenantExtensionProperties;
import top.continew.starter.extension.tenant.config.TenantProvider;
import top.continew.starter.extension.tenant.context.TenantContext;

import java.util.Collections;

/**
 * 租户运行时配置
 *
 * <p>功能瘦身移除了租户管理（continew-plugin-tenant）模块后，其原本提供的租户扩展配置属性与
 * 租户提供者 Bean 随之消失，但多租户运行时（行级隔离、菜单过滤等）仍被核心代码依赖。
 * 本配置在保留多租户运行时的前提下，提供最小可用实现：始终以默认租户（id 0）作为当前租户上下文，
 * 不再依赖已删除的租户管理 Service，适用于单租户部署场景。</p>
 *
 * @author WorkBuddy
 * @since 2026/8/18
 */
@Configuration
@EnableConfigurationProperties(TenantExtensionProperties.class)
public class TenantRuntimeConfiguration {

    private final TenantExtensionProperties tenantExtensionProperties;

    public TenantRuntimeConfiguration(TenantExtensionProperties tenantExtensionProperties) {
        this.tenantExtensionProperties = tenantExtensionProperties;
    }

    /**
     * 租户提供者（最小实现）
     *
     * <p>原 DefaultTenantProvider 通过 TenantService 校验租户编码/状态，本实现在单租户场景下
     * 直接返回默认租户上下文，跳过数据库校验。</p>
     */
    @Bean
    public TenantProvider tenantProvider() {
        return (tenantIdAsString, verify) -> {
            TenantContext context = new TenantContext();
            context.setTenantId(tenantExtensionProperties.getDefaultTenantId());
            return context;
        };
    }

    /**
     * 套餐菜单关联 API（最小实现）
     *
     * <p>原 PackageMenuApiImpl 依赖已删除的租户套餐管理 Service。单租户部署下无套餐概念，
     * 查询菜单时返回空列表即可。</p>
     */
    @Bean
    public PackageMenuApi packageMenuApi() {
        return packageId -> Collections.emptyList();
    }

    /**
     * 租户业务 API（最小实现）
     *
     * <p>原 TenantApiImpl 依赖已删除的租户管理 Mapper，用于绑定租户管理员用户。
     * 单租户部署下不会触发租户初始化，此处为空操作。</p>
     */
    @Bean
    public TenantApi tenantApi() {
        return (tenantId, userId) -> {};
    }
}
