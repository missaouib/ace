package com.dao;

import com.models.entity.dao.Users;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UsersDao extends JpaRepository<Users, Long>, JpaSpecificationExecutor<Users> {


    //LastModifiedDate不起作用的解决办法
	/*在实体中添加注解 @EntityListeners(AuditingEntityListener.class)监听实体变化
	在自动更新时间戳字段增加 @LastModifiedDate
	在Spring boot启动类增加注解 @EnableJpaAuditing启用JPA审计(自动填充默认值)
	如果你是使用JPA的save(实体)方法去更新数据是没有问题的，如果是使用SQL/JPQL语句就会失效。比如：
	@Query("update xxx set x = ? where x = ?")，这里提供最简单的解决办法，语句里时间字段赋值CURRENT_TIMESTAMP即可*/

    @Modifying
    @Query("update Users t set t.username = :#{#users.username} , t.email =:#{#users.email}, t.mobile = :#{#users.mobile}  where t.userId=:#{#users.userId}")
    int update(@Param("users") Users users);

    Users findById(long id);

    List<Users> findUsersByUsername(String username);

    List<Users> findUsersByUsernameLikeIgnoreCaseOrderByLoginDateTime(String username);

    Users findByUserId(Long userId);

    Users findByUserAccount(String userAccount);

    List<Users> findByUserAccountOrEmailIgnoreCase(String userAccount, String email);

    Integer countByUserAccountOrEmail(String userAccount, String email);

    void deleteAll();

    @Query(nativeQuery = true, value = "select u.userId, r.roleId, p.permissionsId, p.permissionCode, u.username, p.action, r.roleCode, u.description, u.userAccount from role_permissions rp, permissions p, roles r, user_roles ur, users u where 1 = 1 and rp.permissionsId = p.permissionsId and rp.roleId = r.roleId and ur.roleId = r.roleId and ur.userId = u.userId order by userId")
    List<Object[]> findUserRolePermission();

    @Query(nativeQuery = true, value = "select u.* from Users u order by u.loginDateTime desc limit :#{#limit}")
    List<Users> findUsersOrderByLoginDateTime(@Param("limit") Integer limit);


}
