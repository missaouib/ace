package com.ace.restController;

import com.ace.controller.common.CommonController;
import com.ace.generator.insertRoles;
import com.ace.models.common.AjaxResponse;
import com.ace.models.entity.Roles;
import com.ace.models.entity.Users;
import com.ace.service.RolesService;
import com.ace.service.UsersService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * @Classname: RolesRestController
 * @Date: 12/12/2021 5:54 AM
 * @Author: garlam
 * @Description:
 */

@RestController
@RequestMapping("/rest/roles")
//@Api(tags = "roles")
@Tag(name = "Roles")
public class RolesRestController extends CommonController {
    private static final Logger log = LogManager.getLogger(RolesRestController.class.getName());

    private final UsersService usersService;
    private final RolesService rolesService;

    @Autowired
    public RolesRestController(RolesService rolesService, UsersService usersService) {
        this.rolesService = rolesService;
        this.usersService = usersService;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/insertRoles")
    public AjaxResponse insertRoles() {
        Users user = usersService.findByUserAccount("garlam");
        rolesService.deleteAll();
        log.info("All roles DELETED !");

        //generate roles data
        insertRoles insertRoles = new insertRoles();
        List<Roles> ls = insertRoles.insertRoles(user);

        rolesService.saveAllAndFlush(ls);
        log.info("Default roles has been CREATED !");


        List<String> result = new ArrayList<>();
        for (Roles roles : ls) {
            String u ="   [" + roles.getRoleCode() + "]";
            result.add(u);
        }
        return AjaxResponse.success(result);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getRoles")
    public AjaxResponse getRoles() {
        List<Roles> ls = rolesService.findAll();
        List<String> result = new ArrayList<>();
        for (Roles roles : ls) {
            String u ="   [" + roles.getRoleCode() + "]";
            result.add(u);
        }
        return AjaxResponse.success(result);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/deleteAllRole")
    public AjaxResponse deleteAllRole() {
        rolesService.deleteAll();
        return AjaxResponse.success("All roles deleted");
    }
}

