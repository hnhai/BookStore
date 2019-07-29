package com.framgia.bookStore.entity;

import com.framgia.bookStore.exception.CustomException;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "DELETED", nullable = false)
    private Boolean deleted;

    @Column(name = "VERSION", nullable = false)
    @Version
    private Long version;

    @CreatedBy
    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @CreatedDate
    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;

    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_BY", nullable = false)
    private Long lastModifiedBy;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE", nullable = false)
    private LocalDateTime lastModifiedDate;

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        if (deleted == null) {
            this.deleted = false;
        } else {
            this.deleted = deleted;
        }
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Long getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(Long lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("deleted", deleted)
                .append("version", version)
                .append("createdBy", createdBy)
                .append("createdDate", createdDate)
                .append("lastModifiedBy", lastModifiedBy)
                .append("lastModifiedDate", lastModifiedDate)
                .toString();
    }

    /**
     * @deprecated Use optimistic locking mechanism provided by Java Persistence instead
     * @see javax.persistence.Version
     */
    @Deprecated
    public void checkCurrentVersion(long versionNeedChecked) {
        if (this.getVersion().compareTo(versionNeedChecked) != 0) {
            throw new CustomException("Row was updated or deleted by another");
        }
    }

    @PrePersist
    public void setDefaultValues() {
        if (Objects.isNull(deleted)) {
            deleted = false;
        }
    }
}