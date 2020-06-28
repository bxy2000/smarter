package com.boxy.smarter.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.boxy.smarter.domain.enumeration.Gender;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.boxy.smarter.domain.UserExtends} entity. This class is used
 * in {@link com.boxy.smarter.web.rest.UserExtendsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-extends?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UserExtendsCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Gender
     */
    public static class GenderFilter extends Filter<Gender> {

        public GenderFilter() {
        }

        public GenderFilter(GenderFilter filter) {
            super(filter);
        }

        @Override
        public GenderFilter copy() {
            return new GenderFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter username;

    private GenderFilter gender;

    private StringFilter mobile;

    private LongFilter userId;

    private LongFilter organizationId;

    private LongFilter dataPermissionId;

    private LongFilter roleId;

    public UserExtendsCriteria() {
    }

    public UserExtendsCriteria(UserExtendsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.username = other.username == null ? null : other.username.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.mobile = other.mobile == null ? null : other.mobile.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.organizationId = other.organizationId == null ? null : other.organizationId.copy();
        this.dataPermissionId = other.dataPermissionId == null ? null : other.dataPermissionId.copy();
        this.roleId = other.roleId == null ? null : other.roleId.copy();
    }

    @Override
    public UserExtendsCriteria copy() {
        return new UserExtendsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getUsername() {
        return username;
    }

    public void setUsername(StringFilter username) {
        this.username = username;
    }

    public GenderFilter getGender() {
        return gender;
    }

    public void setGender(GenderFilter gender) {
        this.gender = gender;
    }

    public StringFilter getMobile() {
        return mobile;
    }

    public void setMobile(StringFilter mobile) {
        this.mobile = mobile;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(LongFilter organizationId) {
        this.organizationId = organizationId;
    }

    public LongFilter getDataPermissionId() {
        return dataPermissionId;
    }

    public void setDataPermissionId(LongFilter dataPermissionId) {
        this.dataPermissionId = dataPermissionId;
    }

    public LongFilter getRoleId() {
        return roleId;
    }

    public void setRoleId(LongFilter roleId) {
        this.roleId = roleId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UserExtendsCriteria that = (UserExtendsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(username, that.username) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(mobile, that.mobile) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(organizationId, that.organizationId) &&
            Objects.equals(dataPermissionId, that.dataPermissionId) &&
            Objects.equals(roleId, that.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        username,
        gender,
        mobile,
        userId,
        organizationId,
        dataPermissionId,
        roleId
        );
    }

    @Override
    public String toString() {
        return "UserExtendsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (username != null ? "username=" + username + ", " : "") +
                (gender != null ? "gender=" + gender + ", " : "") +
                (mobile != null ? "mobile=" + mobile + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (organizationId != null ? "organizationId=" + organizationId + ", " : "") +
                (dataPermissionId != null ? "dataPermissionId=" + dataPermissionId + ", " : "") +
                (roleId != null ? "roleId=" + roleId + ", " : "") +
            "}";
    }

}
