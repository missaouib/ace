package com.entity.dao.hibernate;

import com.entity.dao.base.baseEntity;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;


@EntityListeners(AuditingEntityListener.class)
@Table(name = "test")
@Entity
public class TestEntity extends baseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(strategy = "identity", name = "id")
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "email")
    private String email;

/*    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "last_update_date")
    private Date lastUpdateDate;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "last_update_by")
    private String lastUpdateBy;
    @Column(name = "version")
    private int version;*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}

