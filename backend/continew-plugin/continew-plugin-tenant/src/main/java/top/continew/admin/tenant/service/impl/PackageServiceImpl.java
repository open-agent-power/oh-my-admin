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

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.continew.admin.common.enums.DisEnableStatusEnum;
import top.continew.admin.tenant.mapper.PackageMapper;
import top.continew.admin.tenant.model.entity.PackageDO;
import top.continew.admin.tenant.service.PackageService;
import top.continew.starter.core.util.validation.CheckUtils;

/**
 * 套餐业务实现
 *
 * @author 小熊
 * @author Charles7c
 * @since 2024/11/26 11:25
 */
@Service
public class PackageServiceImpl extends ServiceImpl<PackageMapper, PackageDO> implements PackageService {

    @Override
    public void checkStatus(Long id) {
        PackageDO entity = this.getById(id);
        CheckUtils.throwIfEqual(DisEnableStatusEnum.DISABLE, entity.getStatus(), "租户套餐已被禁用");
    }
}
