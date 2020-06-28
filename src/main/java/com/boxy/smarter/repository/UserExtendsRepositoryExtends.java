package com.boxy.smarter.repository;

import com.boxy.smarter.domain.UserExtends;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the UserExtends entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserExtendsRepositoryExtends extends JpaRepository<UserExtends, Long>, JpaSpecificationExecutor<UserExtends> {

    @Query(value = "select distinct user_extends from UserExtends user_extends " +
        " left join fetch user_extends.roles " +
        " left join user_extends.organization " +
        " where (:login = '' or user_extends.user.login like concat('%',:login,'%')) " +
        " and (:username = '' or user_extends.username like concat('%',:username,'%')) " +
        " and (:organizationCode = '' or user_extends.organization.code like concat('',:organizationCode,'%')) ",
        countQuery = "select count(distinct user_extends) from UserExtends user_extends " +
            " left join user_extends.organization " +
            " where (:login = '' or user_extends.user.login like concat('%',:login,'%')) " +
            " and (:username = '' or user_extends.username like concat('%',:username,'%')) " +
            " and (:organizationCode = '' or user_extends.organization.code like concat('',:organizationCode,'%')) ")
    Page<UserExtends> findAllWithEagerRelationships(@Param("login") String login,
                                                    @Param("username") String username,
                                                    @Param("organizationCode") String organizationCode,
                                                    Pageable pageable);
    @Query(value = "select distinct user_extends from UserExtends user_extends left join fetch user_extends.roles")
    List<UserExtends> findAllWithEagerRelationships();

    @Query("select user_extends from UserExtends user_extends left join fetch user_extends.roles where user_extends.id =:id")
    Optional<UserExtends> findOneWithEagerRelationships(@Param("id") Long id);
}
