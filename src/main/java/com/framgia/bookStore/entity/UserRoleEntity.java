package com.framgia.bookStore.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Where;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USERS_ROLES")
public class UserRoleEntity extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @AttributeOverrides({
        @AttributeOverride(name = "userId", column = @Column(name = "USER_ID", nullable = false)),
        @AttributeOverride(name = "roleId", column = @Column(name = "ROLE_ID", nullable = false))})
    private UserRoleId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false, insertable = false, updatable = false)
    @Where(clause = "DELETED_FLAG=0")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID", nullable = false, insertable = false, updatable = false)
    @Where(clause = "DELETED_FLAG=0")
    private RoleEntity roleEntity;

    public UserRoleEntity() {
    }

    public UserRoleEntity(UserEntity userEntity, RoleEntity roleEntity) {
        this.userEntity = userEntity;
        this.roleEntity = roleEntity;
    }

    public UserRoleId getId() {
        return id;
    }

    public void setId(UserRoleId id) {
        this.id = id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public RoleEntity getRoleEntity() {
        return roleEntity;
    }

    public void setRoleEntity(RoleEntity roleEntity) {
        this.roleEntity = roleEntity;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("id", id)
            .append("userEntity", userEntity)
            .append("roleEntity", roleEntity)
            .appendSuper(super.toString())
            .toString();
    }
}
