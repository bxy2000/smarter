package com.boxy.smarter.service.dto;

import com.boxy.smarter.domain.Organization;
import com.boxy.smarter.domain.Role;
import com.boxy.smarter.domain.UserExtends;
import com.boxy.smarter.domain.enumeration.Gender;

import java.util.Set;
import java.util.stream.Collectors;

public class UserExtendsDTO {
    private Long id;
    private String username;
    private Gender gender;
    private String mobile;
    private Organization organization;
    private Organization dataPermission;
    private Set<String> roles;

    public UserExtendsDTO(UserExtends userExtends) {
        this.id = userExtends.getId();
        this.username = userExtends.getUsername();
        this.gender = userExtends.getGender();
        this.mobile = userExtends.getMobile();
        this.dataPermission = userExtends.getDataPermission();
        this.organization = userExtends.getOrganization();
        this.roles = userExtends.getRoles().stream()
            .map(Role::getRoleName)
            .collect(Collectors.toSet());
    }

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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Organization getDataPermission() {
        return dataPermission;
    }

    public void setDataPermission(Organization dataPermission) {
        this.dataPermission = dataPermission;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
