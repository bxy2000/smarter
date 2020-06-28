package com.boxy.smarter.web.rest;

import com.boxy.smarter.domain.User;
import com.boxy.smarter.domain.UserExtends;
import com.boxy.smarter.domain.enumeration.Gender;
import com.boxy.smarter.repository.UserRepository;
import com.boxy.smarter.service.UserExtendsQueryService;
import com.boxy.smarter.service.UserExtendsServiceExtends;
import com.boxy.smarter.service.dto.UserDTO;
import com.boxy.smarter.service.mapper.UserMapper;
import com.boxy.smarter.service.util.ExcelUtil;
import com.boxy.smarter.web.rest.errors.BadRequestAlertException;
import com.boxy.smarter.web.rest.errors.LoginAlreadyUsedException;
import com.boxy.smarter.web.rest.vm.UserExtendsVM;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserExtends.
 */
@RestController
@RequestMapping("/api/ext")
public class UserExtendsResourceExtends {

    private final Logger log = LoggerFactory.getLogger(UserExtendsResourceExtends.class);

    private static final String ENTITY_NAME = "userExtends";
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    private final UserRepository userRepository;

    private final UserExtendsServiceExtends userExtendsServiceExtends;

    private final UserExtendsQueryService userExtendsQueryService;

    private final UserMapper userMapper;

    public UserExtendsResourceExtends(
        UserRepository userRepository,
        UserExtendsServiceExtends userExtendsServiceExtends,
        UserExtendsQueryService userExtendsQueryService,
        UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userExtendsServiceExtends = userExtendsServiceExtends;
        this.userExtendsQueryService = userExtendsQueryService;
        this.userMapper = userMapper;
    }

    /**
     * POST  /user-extends : Create a new userExtends.
     *
     * @param userExtendsVM the userExtends to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userExtends, or with status 400 (Bad Request) if the userExtends has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-extends")
    public ResponseEntity<UserExtends> createUserExtends(@RequestBody UserExtendsVM userExtendsVM) throws URISyntaxException {
        log.debug("REST request to save create UserExtends : {}", userExtendsVM);
        if (userExtendsVM.getId() != null) {
            throw new BadRequestAlertException("A new userExtends cannot already have an ID", ENTITY_NAME, "idexists");
        }

        if (userExtendsVM.getUser() == null) {
            throw new BadRequestAlertException("The user of userExtends cannot be null", ENTITY_NAME, "user==null");
        }

        if (userExtendsVM.getUser().getId() != null) {
            throw new BadRequestAlertException("A new user Of the userExtends cannot already have an ID", ENTITY_NAME, "idexists");
        }
        userRepository.findOneByLogin(userExtendsVM.getUser().getLogin().toLowerCase()).ifPresent(u -> {
            throw new LoginAlreadyUsedException();
        });
//        userRepository.findOneByEmailIgnoreCase(userExtendsVM.getUser().getEmail()).ifPresent(u -> {
//            throw new EmailAlreadyUsedException();
//        });

        UserExtends result = userExtendsServiceExtends.register(userExtendsVM);

        return ResponseEntity.created(new URI("/api/ext/user-extends/" + result.getId()))
            .headers(HeaderUtil.createAlert(applicationName, "userExtendsManagement.created", result.getId().toString()))
            .body(result);

    }

    /**
     * PUT  /user-extends : Updates an existing userExtends.
     *
     * @param userExtendsVM the userExtends to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userExtends,
     * or with status 400 (Bad Request) if the userExtends is not valid,
     * or with status 500 (Internal Server Error) if the userExtends couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-extends")
    public ResponseEntity<UserExtends> updateUserExtends(@RequestBody UserExtendsVM userExtendsVM) throws URISyntaxException {
        log.debug("REST request to update UserExtends : {}", userExtendsVM);
        if (userExtendsVM.getId() == null) {
            return createUserExtends(userExtendsVM);
        }

        UserDTO userDTO = this.userMapper.userToUserDTO(userExtendsVM.getUser());

//        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
//        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
//            throw new EmailAlreadyUsedException();
//        }
        Optional<User> existingUser = userRepository.findOneByLogin(userDTO.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new LoginAlreadyUsedException();
        }

        UserExtends result = userExtendsServiceExtends.updateUserExtends(userExtendsVM);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createAlert(applicationName, "userExtendsManagement.updated", userExtendsVM.getUser().getId().toString()))
            .body(result);
    }

    /**
     * @param login
     * @param username
     * @param pageable
     * @return
     */
    @GetMapping("/user-extends")
    public ResponseEntity<List<UserExtends>> getAllUserExtends(String login,
                                                               String username,
                                                               String organizationCode,
                                                               Pageable pageable) {
        log.debug("REST request to get UserExtends by admin: {}", login);
        Page<UserExtends> page = userExtendsServiceExtends.findAll(login, username, organizationCode, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-extends/:id : get the "id" userExtends.
     *
     * @param id the id of the userExtends to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userExtends, or with status 404 (Not Found)
     */
    @GetMapping("/user-extends/{id}")
    public ResponseEntity<UserExtends> getUserExtends(@PathVariable Long id) {
        log.debug("REST request to get UserExtends : {}", id);
        Optional<UserExtends> userExtends = userExtendsServiceExtends.findOne(id);
        return ResponseUtil.wrapOrNotFound(userExtends);
    }

    /**
     * DELETE  /user-extends/:id : delete the "id" userExtends.
     *
     * @param id the id of the userExtends to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-extends/{id}")
    public ResponseEntity<Void> deleteUserExtends(@PathVariable Long id) {
        log.debug("REST request to delete UserExtends : {}", id);
        userExtendsServiceExtends.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(applicationName, "userExtendsManagement.deleted", id.toString())).build();
    }

    @PostMapping("/user-extends/reset-password")
    public void resetPassword(@RequestBody String login) {
        log.debug("REST request to reset password by login: {}", login);
        userExtendsServiceExtends.resetPassword("123456", login);
    }

    /**
     * @param login
     * @param username
     * @param pageable
     * @return
     */
    @GetMapping("/user-extends/export")
    public void export(String login, String username, String organizationCode, Pageable pageable, HttpServletResponse response) {
        log.debug("REST request to export UserExtends by admin: {}", login);

        Page<UserExtends> page = userExtendsServiceExtends.findAll(login, username, organizationCode, pageable);

        userExtendsServiceExtends.export(page.getContent(), response);
    }

    private void exportToExcel(Sheet sheet, List<UserExtends> list) {
        int row = 2;
        int lastRow = 2;
        for (UserExtends item : list) {
            ExcelUtil.copyRow(sheet, lastRow, row);

            int col = 0;
            sheet.getRow(row).getCell(col++).setCellValue(item.getUser().getLogin());
            sheet.getRow(row).getCell(col++).setCellValue(item.getUsername());
            String gender = "男";
            if (item.getGender() != null && item.getGender() == Gender.FEMALE) {
                gender = "女";
            }
            sheet.getRow(row).getCell(col++).setCellValue(gender);
            sheet.getRow(row).getCell(col++).setCellValue(item.getMobile());
            if (item.getOrganization() != null) {
                sheet.getRow(row).getCell(col++).setCellValue(item.getOrganization().getName());
            }
            row++;
        }
    }
}
