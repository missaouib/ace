package com.service;

import com.constant.Constant;
import com.dao.UsersDao;
import com.exception.PasswordNotMatchException;
import com.exception.UserNotFoundException;
import com.mapper.UsersMapper;
import com.models.entity.dao.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.util.DateTimeUtil;
import com.util.NullUtil;
import com.util.RandomUtil;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Classname: usersService
 * @Date: 1/7/2021 10:00 下午
 * @Author: garlam
 * @Description:
 */

@Service
public class UsersService {
    private static final Logger log = LogManager.getLogger(UsersService.class.getName());

    private UsersDao usersDao;
    private UsersMapper usersMapper;
    private PasswordEncoder passwordEncoder;
    private UserRolesService userRolesService;
    private RolesService rolesService;
    private PermissionsService permissionsService;


    @Autowired
    public UsersService(UsersMapper usersMapper, UsersDao usersDao, PasswordEncoder passwordEncoder, UserRolesService userRolesService, RolesService rolesService, PermissionsService permissionsService) {
        this.usersDao = usersDao;
        this.passwordEncoder = passwordEncoder;
        this.userRolesService = userRolesService;
        this.rolesService = rolesService;
        this.permissionsService = permissionsService;
        this.usersMapper = usersMapper;
    }


    public List<Users> findUsersByUsernameLikeIgnoreCaseOrderByLoginDateTime(String username) {
        List<Users> usersList = usersDao.findUsersByUsernameLikeIgnoreCaseOrderByLoginDateTime("%" + username + "%");
        calcAge(usersList);
        return usersList;
    }

    public List<Users> findAll() {
        List<Users> usersList = usersDao.findAll();
        calcAge(usersList);
        return usersList;
    }


    public List<Users> findAllByMybatis() {
        List<Users> usersList = usersMapper.findAll();
        calcAge(usersList);
        return usersList;
    }


    public List<Users> findUsersOrderByLoginDateTime(Integer limit) {
        List<Users> usersList = usersDao.findUsersOrderByLoginDateTime(limit == null ? 0 : limit);
        calcAge(usersList);
        return usersList;
    }


    public Users findByUserAccount(Users param) throws UserNotFoundException, PasswordNotMatchException {
        Users user = usersDao.findByUserAccount(param.getUserAccount());
        if (NullUtil.isNull(user) || NullUtil.isNull(user.getUserId())) {
            //抛出异常，会根据配置跳到登录失败页面
            throw new UserNotFoundException(param.getUserAccount());
        }
        boolean matches = passwordEncoder.matches(param.getPassword(), user.getPassword());
        log.info("Match result: {}", matches);
        if (!matches) {
            throw new PasswordNotMatchException();
        }
        calcAge(user);
        return user;
    }

    public Users findByUserAccount(String userAccount) {
        Users user = usersDao.findByUserAccount(userAccount.toLowerCase());
        calcAge(user);
        return user;
    }


    public List<Map> findUserRolePermission() {
        return usersDao.findUserRolePermission();
    }

    /**
     * accountRegistration
     *
     * @param users
     * @return
     */
    @Transactional
    public Users accountRegistration(Users users) {
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        users.setDescription(Constant.Viewer);
        users.setUsername(users.getUserAccount() + "_ace" + RandomUtil.getRangeInt(1, 500));
        users.setExpireDate(LocalDateTime.now().plusYears(3));
        Users u = usersDao.saveAndFlush(users);

        //default role is viewer
        Roles role = rolesService.findByRoleCode(Constant.ROLECODE_VIEWER);

        UserRoles userRoles = new UserRoles();
        userRoles.setUserId(u.getUserId());
        userRoles.setRoleId(role.getRoleId());
        userRolesService.save(userRoles);

        //default permission is select
        Permissions permissions = permissionsService.findPermissionsByPermissionCode(Constant.SELECT);

        RolePermissions rolePermissions = new RolePermissions();
        rolePermissions.setPermissionsId(permissions.getPermissionsId());
        rolePermissions.setRoleId(role.getRoleId());
        return users;
    }

    @Transactional
    public Users save(Users users) {
        return usersDao.save(users);
    }

    @Transactional
    public Users saveAndFlush(Users users) {
        return usersDao.saveAndFlush(users);
    }

    @Transactional
    public void flush() {
        usersDao.flush();
    }


    public boolean validate(Users users) {
        boolean validate = true;
        if (NullUtil.isNotNull(users)) {
            if (NullUtil.isNull(users.getEmail()) || NullUtil.isNull(users.getUserAccount().trim()) || NullUtil.isNull(users.getPassword())) {
                validate = false;
            }
        }
        return validate;
    }


    public Integer countByUserAccountOrEmail(Users users) {
        return usersDao.countByUserAccountOrEmail(users.getUserAccount(), users.getEmail());
    }


    public Users findUsersById(long id) {
        Users user = usersDao.findById(id);
        calcAge(user);
        return user;
    }

    @Transactional
    public boolean saveAll(List<Users> usersIterable) {
        List<Users> usersList = new ArrayList<>();
        for (Users u : usersIterable) {
            String encode = passwordEncoder.encode(u.getPassword());
            u.setPassword(encode);
            usersList.add(u);
        }
        try {
            usersDao.saveAll(usersList);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean update(Users users) {
        try {
            usersDao.update(users);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean delete(Users users) {
        try {
            usersDao.delete(users);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Long id) {
        try {
            usersDao.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void deleteAll() {
        usersDao.deleteAll();
    }


    public void calcAge(List<Users> userList) {
        if (NullUtil.isNotNull(userList)) {
            for (Users users : userList) {
                if (NullUtil.isNotNull(users.getBirthday())) {
                    LocalDateTime now = LocalDateTime.now();
                    LocalDateTime birthDate = users.getBirthday();
                    long age = DateTimeUtil.differenceYearsByLocalDateTime(birthDate, now);
                    users.setAge(age);
                }
            }
        } else {
            log.warn("User list is null");
        }
    }

    public void calcAge(Users user) {
        if (NullUtil.isNotNull(user) && NullUtil.isNotNull(user.getBirthday())) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime birthDate = user.getBirthday();
            long age = DateTimeUtil.differenceYearsByLocalDateTime(birthDate, now);
            user.setAge(age);
        } else {
            log.warn("User is null");
        }
    }

    private Specification<Users> toPredicate(Users users) {
        return (Specification<Users>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicatesList = new ArrayList<>();
            if (users.getUserId() != null) {
                Predicate predicate = criteriaBuilder.equal(root.get("id"), users.getUserId());
                predicatesList.add(predicate);
            }
            if (users.getPassword() != null) {
                Predicate predicate = criteriaBuilder.equal(root.get("password"), users.getPassword());
                predicatesList.add(predicate);
            }
            if (users.getUsername() != null) {
                Predicate predicate = criteriaBuilder.like(root.get("userName"), "%" + users.getUsername().toLowerCase() + "%");
                predicatesList.add(predicate);
            }
//			if (users.getStatus() != null) {
//				Predicate predicate = criteriaBuilder.equal(root.get("status"), users.getStatus());
//				predicatesList.add(predicate);
//			}
            if (users.getEmail() != null) {
                Predicate predicate = criteriaBuilder.like(root.get("email"), "%" + users.getEmail().toLowerCase() + "%");
                predicatesList.add(predicate);
            }
            if (users.getMobile() != null) {
                Predicate predicate = criteriaBuilder.like(root.get("mobile"), "%" + users.getMobile() + "%");
                predicatesList.add(predicate);
            }
            if (users.getDomain() != null) {
                Predicate predicate = criteriaBuilder.like(root.get("domain"), "%" + users.getDomain().toLowerCase() + "%");
                predicatesList.add(predicate);
            }
            if (users.getIp() != null) {
                Predicate predicate = criteriaBuilder.like(root.get("ip"), "%" + users.getIp().toLowerCase() + "%");
                predicatesList.add(predicate);
            }
            if (users.getHostName() != null) {
                Predicate predicate = criteriaBuilder.like(root.get("hostName"), "%" + users.getHostName().toLowerCase() + "%");
                predicatesList.add(predicate);
            }
            if (users.getCreatedBy() != null) {
                Predicate predicate = criteriaBuilder.like(root.get("createdBy"), "%" + users.getCreatedBy() + "%");
                predicatesList.add(predicate);
            }
            if (users.getLastUpdatedBy() != null) {
                Predicate predicate = criteriaBuilder.like(root.get("lastUpdatedBy"), "%" + users.getLastUpdatedBy() + "%");
                predicatesList.add(predicate);
            }
            return criteriaBuilder.and(predicatesList.toArray(new Predicate[predicatesList.size()]));
        };
    }


}

