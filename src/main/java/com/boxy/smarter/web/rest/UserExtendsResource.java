package com.boxy.smarter.web.rest;

import com.boxy.smarter.domain.UserExtends;
import com.boxy.smarter.service.UserExtendsService;
import com.boxy.smarter.web.rest.errors.BadRequestAlertException;
import com.boxy.smarter.service.dto.UserExtendsCriteria;
import com.boxy.smarter.service.UserExtendsQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.boxy.smarter.domain.UserExtends}.
 */
@RestController
@RequestMapping("/api")
public class UserExtendsResource {

    private final Logger log = LoggerFactory.getLogger(UserExtendsResource.class);

    private static final String ENTITY_NAME = "userExtends";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserExtendsService userExtendsService;

    private final UserExtendsQueryService userExtendsQueryService;

    public UserExtendsResource(UserExtendsService userExtendsService, UserExtendsQueryService userExtendsQueryService) {
        this.userExtendsService = userExtendsService;
        this.userExtendsQueryService = userExtendsQueryService;
    }

    /**
     * {@code POST  /user-extends} : Create a new userExtends.
     *
     * @param userExtends the userExtends to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userExtends, or with status {@code 400 (Bad Request)} if the userExtends has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-extends")
    public ResponseEntity<UserExtends> createUserExtends(@Valid @RequestBody UserExtends userExtends) throws URISyntaxException {
        log.debug("REST request to save UserExtends : {}", userExtends);
        if (userExtends.getId() != null) {
            throw new BadRequestAlertException("A new userExtends cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserExtends result = userExtendsService.save(userExtends);
        return ResponseEntity.created(new URI("/api/user-extends/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-extends} : Updates an existing userExtends.
     *
     * @param userExtends the userExtends to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userExtends,
     * or with status {@code 400 (Bad Request)} if the userExtends is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userExtends couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-extends")
    public ResponseEntity<UserExtends> updateUserExtends(@Valid @RequestBody UserExtends userExtends) throws URISyntaxException {
        log.debug("REST request to update UserExtends : {}", userExtends);
        if (userExtends.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserExtends result = userExtendsService.save(userExtends);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userExtends.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-extends} : get all the userExtends.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userExtends in body.
     */
    @GetMapping("/user-extends")
    public ResponseEntity<List<UserExtends>> getAllUserExtends(UserExtendsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get UserExtends by criteria: {}", criteria);
        Page<UserExtends> page = userExtendsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-extends/count} : count all the userExtends.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/user-extends/count")
    public ResponseEntity<Long> countUserExtends(UserExtendsCriteria criteria) {
        log.debug("REST request to count UserExtends by criteria: {}", criteria);
        return ResponseEntity.ok().body(userExtendsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /user-extends/:id} : get the "id" userExtends.
     *
     * @param id the id of the userExtends to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userExtends, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-extends/{id}")
    public ResponseEntity<UserExtends> getUserExtends(@PathVariable Long id) {
        log.debug("REST request to get UserExtends : {}", id);
        Optional<UserExtends> userExtends = userExtendsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userExtends);
    }

    /**
     * {@code DELETE  /user-extends/:id} : delete the "id" userExtends.
     *
     * @param id the id of the userExtends to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-extends/{id}")
    public ResponseEntity<Void> deleteUserExtends(@PathVariable Long id) {
        log.debug("REST request to delete UserExtends : {}", id);
        userExtendsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
