package com.framgia.bookStore.entity;

import com.framgia.bookStore.constants.Gender;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class UserEntity extends AbstractEntity{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERNAME", nullable = false, unique = true)
    private String username;

    @Column(name = "FULLNAME", nullable = false)
    private String fullname;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PHONE_NUMBER", length = 20)
    private String phoneNumber;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "GENDER")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "ENABLED", nullable = false)
    private Boolean enabled;

    @Column(name = "STATUS")
    private Boolean status;

    @Column(name = "TOKEN", length = 50)
    private String token;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userEntity")
    @Where(clause = "DELETED = 0")
    @OrderBy("id.roleId")
    private Set<UserRoleEntity> userRoleEntities = new HashSet<>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    @Where(clause = "DELETED = 0")
    private Set<OrderEntity> orders = new HashSet<>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    @Where(clause = "DELETED = 0")
    private Set<OrderEntity> emOrders = new HashSet<>(0);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Set<UserRoleEntity> getUserRoleEntities() {
        return userRoleEntities;
    }

    public void setUserRoleEntities(Set<UserRoleEntity> userRoleEntities) {
        this.userRoleEntities = userRoleEntities;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderEntity> orders) {
        this.orders = orders;
    }

    public Set<OrderEntity> getEmOrders() {
        return emOrders;
    }

    public void setEmOrders(Set<OrderEntity> emOrders) {
        this.emOrders = emOrders;
    }
}
