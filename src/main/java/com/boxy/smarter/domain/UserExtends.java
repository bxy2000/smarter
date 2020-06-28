package com.boxy.smarter.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

import com.boxy.smarter.domain.enumeration.Gender;

/**
 * 用户扩展表
 */
@ApiModel(description = "用户扩展表")
@Entity
@Table(name = "user_extends")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserExtends implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "username", length = 30, nullable = false)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Size(max = 20)
    @Column(name = "mobile", length = 20)
    private String mobile;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("userExtends")
    private Organization organization;

    @ManyToOne
    @JsonIgnoreProperties("userExtends")
    private Organization dataPermission;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "user_extends_role",
               joinColumns = @JoinColumn(name = "user_extends_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public UserExtends username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Gender getGender() {
        return gender;
    }

    public UserExtends gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public UserExtends mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public User getUser() {
        return user;
    }

    public UserExtends user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Organization getOrganization() {
        return organization;
    }

    public UserExtends organization(Organization organization) {
        this.organization = organization;
        return this;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Organization getDataPermission() {
        return dataPermission;
    }

    public UserExtends dataPermission(Organization organization) {
        this.dataPermission = organization;
        return this;
    }

    public void setDataPermission(Organization organization) {
        this.dataPermission = organization;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public UserExtends roles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public UserExtends addRole(Role role) {
        this.roles.add(role);
        role.getUserExtends().add(this);
        return this;
    }

    public UserExtends removeRole(Role role) {
        this.roles.remove(role);
        role.getUserExtends().remove(this);
        return this;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserExtends)) {
            return false;
        }
        return id != null && id.equals(((UserExtends) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserExtends{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", gender='" + getGender() + "'" +
            ", mobile='" + getMobile() + "'" +
            "}";
    }
}
