package com.configDataGenerator;

import com.constant.Constant;
import com.models.entity.dao.Roles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.entity.Users;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname: insertRoles
 * @Date: 10/12/2021 6:48 AM
 * @Author: garlam
 * @Description:
 */


public class insertRoles {
    private static final Logger log = LogManager.getLogger(insertRoles.class.getName());

    public List<Roles> insertRoles(Users users) {
        List<Roles> ls = new ArrayList<>();

        Roles r1 = new Roles();
        Roles r2 = new Roles();
        Roles r3 = new Roles();
        Roles r4 = new Roles();

        r1.setCreatedBy(users.getUserId());
        r2.setCreatedBy(users.getUserId());
        r3.setCreatedBy(users.getUserId());
        r4.setCreatedBy(users.getUserId());

        r1.setCreatedDate(LocalDateTime.now());
        r2.setCreatedDate(LocalDateTime.now());
        r3.setCreatedDate(LocalDateTime.now());
        r4.setCreatedDate(LocalDateTime.now());

        r1.setDescription(Constant.administrator);
        r2.setDescription(Constant.disable);
        r3.setDescription(Constant.user);
        r4.setDescription(Constant.viewer);

        r1.setLastUpdatedBy(users.getUserId());
        r2.setLastUpdatedBy(users.getUserId());
        r3.setLastUpdatedBy(users.getUserId());
        r4.setLastUpdatedBy(users.getUserId());

        r1.setLastUpdateDate(LocalDateTime.now());
        r2.setLastUpdateDate(LocalDateTime.now());
        r3.setLastUpdateDate(LocalDateTime.now());
        r4.setLastUpdateDate(LocalDateTime.now());

        r1.setRoleCode(Constant.ROLECODE_ADMIN);
        r1.setRoleCode(Constant.ROLECODE_DISABLE);
        r1.setRoleCode(Constant.ROLECODE_USER);
        r1.setRoleCode(Constant.ROLECODE_VIEWER);

        return ls;
    }

}

