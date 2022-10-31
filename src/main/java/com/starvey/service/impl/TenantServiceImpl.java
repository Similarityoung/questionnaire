package com.starvey.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starvey.entity.Tenant;
import com.starvey.service.TenantService;
import com.starvey.mapper.TenantMapper;
import org.springframework.stereotype.Service;

/**
* @author Song
* @description 针对表【tenant】的数据库操作Service实现
* @createDate 2022-10-30 13:48:23
*/
@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant>
    implements TenantService{
    @Override
    public boolean addQuestionnaireTo(String id) {
        Tenant tenant = this.getById(id);
        if (tenant == null) {
            return false;
        }
        tenant.setQuestionnaireCount(tenant.getQuestionnaireCount() + 1);
        tenant.setCost(tenant.getCost() + 0.5);
        return true;
    }
}




