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

package top.continew.admin.tenant.service.impl;

import com.alicp.jetcache.anno.Cached;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.collection.CollUtil;
import top.continew.admin.common.api.system.RoleApi;
import top.continew.admin.common.api.system.RoleMenuApi;
import top.continew.admin.common.config.TenantExtensionProperties;
import top.continew.admin.common.constant.CacheConstants;
import top.continew.admin.common.enums.DisEnableStatusEnum;
import top.continew.admin.common.enums.RoleCodeEnum;
import top.continew.admin.tenant.constant.TenantCacheConstants;
import top.continew.admin.tenant.mapper.TenantMapper;
import top.continew.admin.tenant.model.entity.TenantDO;
import top.continew.admin.tenant.service.PackageService;
import top.continew.admin.tenant.service.TenantService;
import top.continew.starter.cache.redisson.util.RedisUtils;
import top.continew.starter.core.constant.StringConstants;
import top.continew.starter.core.util.validation.CheckUtils;
import top.continew.starter.data.service.impl.ServiceImpl;
import top.continew.starter.extension.tenant.util.TenantUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * 租户业务实现
 *
 * @author 小熊
 * @author Charles7c
 * @since 2024/11/26 17:20
 */
@Service
@RequiredArgsConstructor
public class TenantServiceImpl extends ServiceImpl<TenantMapper, TenantDO> implements TenantService {

    private final TenantExtensionProperties tenantExtensionProperties;
    private final PackageService packageService;
    private final RoleMenuApi roleMenuApi;
    private final RoleApi roleApi;

    @Override
    @Cached(name = TenantCacheConstants.TENANT_KEY_PREFIX, key = "#domain")
    public Long getIdByDomain(String domain) {
        return baseMapper.lambdaQuery()
            .select(TenantDO::getId)
            .eq(TenantDO::getDomain, domain)
            .oneOpt()
            .map(TenantDO::getId)
            .orElse(null);
    }

    @Override
    @Cached(name = TenantCacheConstants.TENANT_KEY_PREFIX, key = "#code")
    public Long getIdByCode(String code) {
        return baseMapper.lambdaQuery()
            .select(TenantDO::getId)
            .eq(TenantDO::getCode, code)
            .oneOpt()
            .map(TenantDO::getId)
            .orElse(null);
    }

    @Override
    public void checkStatus(Long id) {
        // 默认租户
        if (tenantExtensionProperties.getDefaultTenantId().equals(id)) {
            return;
        }
        TenantDO tenant = baseMapper.selectById(id);
        CheckUtils.throwIfEqual(DisEnableStatusEnum.DISABLE, tenant.getStatus(), "租户已被禁用");
        CheckUtils.throwIf(tenant.getExpireTime() != null && tenant.getExpireTime()
            .isBefore(LocalDateTime.now()), "租户已过期");
        // 检查套餐
        packageService.checkStatus(tenant.getPackageId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTenantMenu(List<Long> newMenuIds, Long packageId) {
        List<Long> tenantIdList = this.listIdByPackageId(packageId);
        if (CollUtil.isEmpty(tenantIdList)) {
            return;
        }
        // 所有租户角色：删除旧菜单
        tenantIdList.forEach(tenantId -> TenantUtils.execute(tenantId, () -> {
            // 删除旧菜单
            roleMenuApi.deleteByNotInMenuIds(newMenuIds);
            // 更新在线用户上下文
            Set<Long> roleIdSet = roleMenuApi.listRoleIdByNotInMenuIds(newMenuIds);
            roleIdSet.forEach(roleApi::updateUserContext);
        }));
        // 租户管理员：新增菜单
        tenantIdList.forEach(tenantId -> TenantUtils.execute(tenantId, () -> {
            Long roleId = roleApi.getIdByCode(RoleCodeEnum.TENANT_ADMIN.getCode());
            roleMenuApi.add(newMenuIds, roleId);
            // 更新在线用户上下文
            roleApi.updateUserContext(roleId);
        }));
        // 删除缓存
        RedisUtils.deleteByPattern(CacheConstants.ROLE_MENU_KEY_PREFIX + StringConstants.ASTERISK);
    }

    @Override
    public Long countByPackageIds(List<Long> packageIds) {
        return baseMapper.lambdaQuery().in(TenantDO::getPackageId, packageIds).count();
    }

    /**
     * 根据套餐 ID 查询租户 ID 列表
     *
     * @param id 套餐 ID
     * @return 租户 ID 列表
     */
    private List<Long> listIdByPackageId(Long id) {
        return baseMapper.lambdaQuery()
            .select(TenantDO::getId)
            .eq(TenantDO::getPackageId, id)
            .list()
            .stream()
            .map(TenantDO::getId)
            .toList();
    }
}
