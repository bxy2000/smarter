package com.boxy.smarter.service;

import com.boxy.smarter.domain.UserExtends;
import com.boxy.smarter.repository.UserExtendsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserExtends}.
 */
@Service
@Transactional
public class UserExtendsService {

    private final Logger log = LoggerFactory.getLogger(UserExtendsService.class);

    private final UserExtendsRepository userExtendsRepository;

    public UserExtendsService(UserExtendsRepository userExtendsRepository) {
        this.userExtendsRepository = userExtendsRepository;
    }

    /**
     * Save a userExtends.
     *
     * @param userExtends the entity to save.
     * @return the persisted entity.
     */
    public UserExtends save(UserExtends userExtends) {
        log.debug("Request to save UserExtends : {}", userExtends);
        return userExtendsRepository.save(userExtends);
    }

    /**
     * Get all the userExtends.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserExtends> findAll(Pageable pageable) {
        log.debug("Request to get all UserExtends");
        return userExtendsRepository.findAll(pageable);
    }

    /**
     * Get all the userExtends with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<UserExtends> findAllWithEagerRelationships(Pageable pageable) {
        return userExtendsRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one userExtends by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserExtends> findOne(Long id) {
        log.debug("Request to get UserExtends : {}", id);
        return userExtendsRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the userExtends by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserExtends : {}", id);
        userExtendsRepository.deleteById(id);
    }
}
