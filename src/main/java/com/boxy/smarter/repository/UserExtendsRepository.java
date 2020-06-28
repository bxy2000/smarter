package com.boxy.smarter.repository;

import com.boxy.smarter.domain.UserExtends;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the UserExtends entity.
 */
@Repository
public interface UserExtendsRepository extends JpaRepository<UserExtends, Long>, JpaSpecificationExecutor<UserExtends> {

    @Query(value = "select distinct userExtends from UserExtends userExtends left join fetch userExtends.roles",
        countQuery = "select count(distinct userExtends) from UserExtends userExtends")
    Page<UserExtends> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct userExtends from UserExtends userExtends left join fetch userExtends.roles")
    List<UserExtends> findAllWithEagerRelationships();

    @Query("select userExtends from UserExtends userExtends left join fetch userExtends.roles where userExtends.id =:id")
    Optional<UserExtends> findOneWithEagerRelationships(@Param("id") Long id);
}
