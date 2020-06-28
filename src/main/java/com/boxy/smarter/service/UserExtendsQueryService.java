package com.boxy.smarter.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.boxy.smarter.domain.UserExtends;
import com.boxy.smarter.domain.*; // for static metamodels
import com.boxy.smarter.repository.UserExtendsRepository;
import com.boxy.smarter.service.dto.UserExtendsCriteria;

/**
 * Service for executing complex queries for {@link UserExtends} entities in the database.
 * The main input is a {@link UserExtendsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserExtends} or a {@link Page} of {@link UserExtends} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserExtendsQueryService extends QueryService<UserExtends> {

    private final Logger log = LoggerFactory.getLogger(UserExtendsQueryService.class);

    private final UserExtendsRepository userExtendsRepository;

    public UserExtendsQueryService(UserExtendsRepository userExtendsRepository) {
        this.userExtendsRepository = userExtendsRepository;
    }

    /**
     * Return a {@link List} of {@link UserExtends} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserExtends> findByCriteria(UserExtendsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserExtends> specification = createSpecification(criteria);
        return userExtendsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link UserExtends} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserExtends> findByCriteria(UserExtendsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserExtends> specification = createSpecification(criteria);
        return userExtendsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserExtendsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserExtends> specification = createSpecification(criteria);
        return userExtendsRepository.count(specification);
    }

    /**
     * Function to convert {@link UserExtendsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserExtends> createSpecification(UserExtendsCriteria criteria) {
        Specification<UserExtends> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UserExtends_.id));
            }
            if (criteria.getUsername() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsername(), UserExtends_.username));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildSpecification(criteria.getGender(), UserExtends_.gender));
            }
            if (criteria.getMobile() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobile(), UserExtends_.mobile));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(UserExtends_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getOrganizationId(),
                    root -> root.join(UserExtends_.organization, JoinType.LEFT).get(Organization_.id)));
            }
            if (criteria.getDataPermissionId() != null) {
                specification = specification.and(buildSpecification(criteria.getDataPermissionId(),
                    root -> root.join(UserExtends_.dataPermission, JoinType.LEFT).get(Organization_.id)));
            }
            if (criteria.getRoleId() != null) {
                specification = specification.and(buildSpecification(criteria.getRoleId(),
                    root -> root.join(UserExtends_.roles, JoinType.LEFT).get(Role_.id)));
            }
        }
        return specification;
    }
}
