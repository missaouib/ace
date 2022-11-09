package com.config;

import cn.dev33.satoken.stp.StpUtil;
import com.models.entity.dao.Users;
import com.util.NullUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Classname: JpaAuditorAwareConfig
 * @Date: 2022/9/30 上午 11:30
 * @Author: kalam_au
 * @Description:
 */

@Component
public class JpaAuditorAwareConfig implements AuditorAware<Long> {
    private static final Logger log = LogManager.getLogger(JpaAuditorAwareConfig.class.getName());


    /**
     * Returns the current auditor of the application.
     * @return the current auditor.
     */
    @Override
    public Optional<Long> getCurrentAuditor() {
        // String userId = null;//SecurityUtils.getCurrentUserId();
        Users user = (Users) StpUtil.getSession().get("user");
        if (NullUtil.isNotNull(user) && NullUtil.isNotNull(user.getUserId())) {
            return Optional.of(user.getUserId());
        } else {
            return Optional.of(0l);
        }
    }
}

