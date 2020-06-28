package com.boxy.smarter.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * 角色
 */
@ApiModel(description = "角色")
@Entity
@Table(name = "role")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 80)
    @Column(name = "role_name", length = 80, nullable = false, unique = true)
    private String roleName;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "role_menu",
               joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "menu_id", referencedColumnName = "id"))
    private Set<Menu> menus = new HashSet<>();

    @ManyToMany(mappedBy = "roles")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<UserExtends> userExtends = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public Role roleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public Role menus(Set<Menu> menus) {
        this.menus = menus;
        return this;
    }

    public Role addMenu(Menu menu) {
        this.menus.add(menu);
        menu.getRoles().add(this);
        return this;
    }

    public Role removeMenu(Menu menu) {
        this.menus.remove(menu);
        menu.getRoles().remove(this);
        return this;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    public Set<UserExtends> getUserExtends() {
        return userExtends;
    }

    public Role userExtends(Set<UserExtends> userExtends) {
        this.userExtends = userExtends;
        return this;
    }

    public Role addUserExtends(UserExtends userExtends) {
        this.userExtends.add(userExtends);
        userExtends.getRoles().add(this);
        return this;
    }

    public Role removeUserExtends(UserExtends userExtends) {
        this.userExtends.remove(userExtends);
        userExtends.getRoles().remove(this);
        return this;
    }

    public void setUserExtends(Set<UserExtends> userExtends) {
        this.userExtends = userExtends;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Role)) {
            return false;
        }
        return id != null && id.equals(((Role) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Role{" +
            "id=" + getId() +
            ", roleName='" + getRoleName() + "'" +
            "}";
    }
}
