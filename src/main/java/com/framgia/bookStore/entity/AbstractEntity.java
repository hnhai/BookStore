package com.framgia.bookStore.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.util.Objects;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "DELETED", nullable = false)
    private Boolean deleted;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("deleted", deleted)
            .toString();
    }

    @PrePersist
    public void setDefaultValues() {
        if (Objects.isNull(deleted)) {
            deleted = false;
        }
    }
}