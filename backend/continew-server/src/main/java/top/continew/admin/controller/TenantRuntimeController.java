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

package top.continew.admin.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.continew.admin.common.config.TenantExtensionProperties;
import top.continew.starter.extension.tenant.annotation.TenantIgnore;

/**
 * 租户运行时 API
 *
 * <p>功能瘦身移除了租户管理模块后，其对外提供的租户公共接口（根据域名查询租户 ID）随之消失，
 * 但前端登录流程（onGetTenant）仍依赖该接口完成租户上下文初始化。本控制器在单租户部署场景下
 * 提供最小实现：始终返回默认租户 ID（0）。</p>
 *
 * @author WorkBuddy
 * @since 2026/8/18
 */
@Tag(name = "租户运行时 API")
@TenantIgnore
@RestController
@RequestMapping("/tenant/common")
public class TenantRuntimeController {

    private final TenantExtensionProperties tenantExtensionProperties;

    public TenantRuntimeController(TenantExtensionProperties tenantExtensionProperties) {
        this.tenantExtensionProperties = tenantExtensionProperties;
    }

    @Operation(summary = "根据域名查询租户 ID", description = "单租户场景下始终返回默认租户 ID")
    @Parameter(name = "domain", description = "域名", example = "localhost", in = ParameterIn.QUERY)
    @SaIgnore
    @GetMapping("/id")
    public Long getTenantIdByDomain(@RequestParam(required = false) String domain) {
        return tenantExtensionProperties.getDefaultTenantId();
    }
}
