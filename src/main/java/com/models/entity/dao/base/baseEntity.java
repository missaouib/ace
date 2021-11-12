package com.models.entity.dao.base;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

//import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.LocalDateTime;

/**
 * @Classname: baseEntity
 * @Date: 5/5/2021 12:22 上午
 * @Author: garlam
 * @Description:
 */


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class baseEntity {

    @CreatedDate
    //@Column(name = "created_date", updatable = false)
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    //@Column(name = "last_update_date")
    @Column()
    private LocalDateTime lastUpdateDate;

    @CreatedBy
    //@Column(name = "created_by", updatable = false)
    @Column( updatable = false)
    private String createdBy;

    @LastModifiedBy
    //@Column(name = "last_update_by")
    @Column()
    private String lastUpdatedBy;

    @Version
    @Column(name = "version")
    private Integer version = 1;

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}

