package com.boxy.smarter.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.boxy.smarter.web.rest.TestUtil;

public class UserExtendsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserExtends.class);
        UserExtends userExtends1 = new UserExtends();
        userExtends1.setId(1L);
        UserExtends userExtends2 = new UserExtends();
        userExtends2.setId(userExtends1.getId());
        assertThat(userExtends1).isEqualTo(userExtends2);
        userExtends2.setId(2L);
        assertThat(userExtends1).isNotEqualTo(userExtends2);
        userExtends1.setId(null);
        assertThat(userExtends1).isNotEqualTo(userExtends2);
    }
}
