package com.boxy.smarter.web.rest;

import com.boxy.smarter.service.UserExtendsServiceExtends;
import com.boxy.smarter.service.dto.MenuDTO;
import com.boxy.smarter.service.dto.ResponseMessageDTO;
import com.boxy.smarter.service.dto.UserExtendsDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ext")
public class AccountResourceExtends {
    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final UserExtendsServiceExtends userExtendsServiceExtends;

    public AccountResourceExtends(UserExtendsServiceExtends userExtendsServiceExtends) {
        this.userExtendsServiceExtends = userExtendsServiceExtends;
    }

    @GetMapping("/menu-tree")
    public ResponseEntity<List<MenuDTO>> getMenuTree(String login) {
        log.debug("REST request to get Menu Tree by login: {}", login);

        List<MenuDTO> entityList = userExtendsServiceExtends.findMenusByLogin(login);

        return ResponseEntity.ok().body(entityList);
    }

    @GetMapping("/user-extends-dto")
    public ResponseEntity<UserExtendsDTO> getUserExtends(String login) {
        log.debug("REST request to get Menu Tree by login: {}", login);

        UserExtendsDTO userExtends = userExtendsServiceExtends.findUserExtendsByLogin(login);

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userExtends));
    }

    /**
     * POST  /account/change-password : changes the current user's password
     * 比较传入的密码和数据库密码是否相等
     *
     * @param password the new password
     *                 create by lxy
     */
    @PostMapping(path = "/account/compare-password")
    public ResponseEntity<ResponseMessageDTO> comparePassword(@RequestBody String password) {

        boolean flag = userExtendsServiceExtends.comparePassword(password);

        ResponseMessageDTO reponseMessage = new ResponseMessageDTO(flag,
            flag ? "原密码输入正确!" : "原密码输入错误");

        return ResponseEntity.ok().body(reponseMessage);

    }
}
