package com.boxy.smarter.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

/**
 * 功能（菜单）
 */
@ApiModel(description = "功能（菜单）")
@Entity
@Table(name = "menu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "jhi_group")
    private Boolean group;

    @Size(max = 2048)
    @Column(name = "link", length = 2048)
    private String link;

    @Size(max = 2048)
    @Column(name = "external_link", length = 2048)
    private String externalLink;

    @Column(name = "target")
    private String target;

    @Column(name = "icon")
    private String icon;

    @Column(name = "hide")
    private Boolean hide;

    @Column(name = "description")
    private String description;

    @Column(name = "menu_type")
    private Integer menuType;

    @Size(max = 2048)
    @Column(name = "menu_link", length = 2048)
    private String menuLink;

    @Column(name = "menu_order")
    private Integer menuOrder;

    @Column(name = "menu_height")
    private Integer menuHeight;

    @OneToMany(mappedBy = "parent")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Menu> children = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("children")
    private Menu parent;

    @ManyToMany(mappedBy = "menus")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public Menu text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean isGroup() {
        return group;
    }

    public Menu group(Boolean group) {
        this.group = group;
        return this;
    }

    public void setGroup(Boolean group) {
        this.group = group;
    }

    public String getLink() {
        return link;
    }

    public Menu link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getExternalLink() {
        return externalLink;
    }

    public Menu externalLink(String externalLink) {
        this.externalLink = externalLink;
        return this;
    }

    public void setExternalLink(String externalLink) {
        this.externalLink = externalLink;
    }

    public String getTarget() {
        return target;
    }

    public Menu target(String target) {
        this.target = target;
        return this;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getIcon() {
        return icon;
    }

    public Menu icon(String icon) {
        this.icon = icon;
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean isHide() {
        return hide;
    }

    public Menu hide(Boolean hide) {
        this.hide = hide;
        return this;
    }

    public void setHide(Boolean hide) {
        this.hide = hide;
    }

    public String getDescription() {
        return description;
    }

    public Menu description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public Menu menuType(Integer menuType) {
        this.menuType = menuType;
        return this;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public String getMenuLink() {
        return menuLink;
    }

    public Menu menuLink(String menuLink) {
        this.menuLink = menuLink;
        return this;
    }

    public void setMenuLink(String menuLink) {
        this.menuLink = menuLink;
    }

    public Integer getMenuOrder() {
        return menuOrder;
    }

    public Menu menuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
        return this;
    }

    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }

    public Integer getMenuHeight() {
        return menuHeight;
    }

    public Menu menuHeight(Integer menuHeight) {
        this.menuHeight = menuHeight;
        return this;
    }

    public void setMenuHeight(Integer menuHeight) {
        this.menuHeight = menuHeight;
    }

    public Set<Menu> getChildren() {
        return children;
    }

    public Menu children(Set<Menu> menus) {
        this.children = menus;
        return this;
    }

    public Menu addChildren(Menu menu) {
        this.children.add(menu);
        menu.setParent(this);
        return this;
    }

    public Menu removeChildren(Menu menu) {
        this.children.remove(menu);
        menu.setParent(null);
        return this;
    }

    public void setChildren(Set<Menu> menus) {
        this.children = menus;
    }

    public Menu getParent() {
        return parent;
    }

    public Menu parent(Menu menu) {
        this.parent = menu;
        return this;
    }

    public void setParent(Menu menu) {
        this.parent = menu;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Menu roles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Menu addRole(Role role) {
        this.roles.add(role);
        role.getMenus().add(this);
        return this;
    }

    public Menu removeRole(Role role) {
        this.roles.remove(role);
        role.getMenus().remove(this);
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
        if (!(o instanceof Menu)) {
            return false;
        }
        return id != null && id.equals(((Menu) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Menu{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", group='" + isGroup() + "'" +
            ", link='" + getLink() + "'" +
            ", externalLink='" + getExternalLink() + "'" +
            ", target='" + getTarget() + "'" +
            ", icon='" + getIcon() + "'" +
            ", hide='" + isHide() + "'" +
            ", description='" + getDescription() + "'" +
            ", menuType=" + getMenuType() +
            ", menuLink='" + getMenuLink() + "'" +
            ", menuOrder=" + getMenuOrder() +
            ", menuHeight=" + getMenuHeight() +
            "}";
    }
}
