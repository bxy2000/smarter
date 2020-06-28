package com.boxy.smarter.web.rest.vm;

import com.boxy.smarter.domain.UserExtends;

import javax.validation.constraints.Size;


public class UserExtendsVM extends UserExtends {
	private static final long serialVersionUID = 1L;

	public static final int PASSWORD_MIN_LENGTH = 6;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    public UserExtendsVM() {
        // Empty constructor needed for Jackson.
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserExtendsVM{" +
            "} " + super.toString();
    }

    public UserExtends toUserExtends() {
    	UserExtends userExtends = new UserExtends();

    	userExtends.setId(this.getId());
    	userExtends.setUsername(this.getUsername());
    	userExtends.setGender(this.getGender());
    	userExtends.setMobile(this.getMobile());
        userExtends.setUser(this.getUser());
        userExtends.setOrganization(this.getOrganization());
        userExtends.setDataPermission(this.getDataPermission());
        userExtends.setRoles(this.getRoles());

    	return userExtends;
    }
}
