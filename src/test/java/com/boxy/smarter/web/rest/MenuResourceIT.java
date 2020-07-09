package com.boxy.smarter.web.rest;

import com.boxy.smarter.SmarterApp;
import com.boxy.smarter.domain.Menu;
import com.boxy.smarter.repository.MenuRepository;
import com.boxy.smarter.service.MenuService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MenuResource} REST controller.
 */
@SpringBootTest(classes = SmarterApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MenuResourceIT {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_GROUP = false;
    private static final Boolean UPDATED_GROUP = true;

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_EXTERNAL_LINK = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_TARGET = "AAAAAAAAAA";
    private static final String UPDATED_TARGET = "BBBBBBBBBB";

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HIDE = false;
    private static final Boolean UPDATED_HIDE = true;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_MENU_TYPE = 1;
    private static final Integer UPDATED_MENU_TYPE = 2;

    private static final String DEFAULT_MENU_LINK = "AAAAAAAAAA";
    private static final String UPDATED_MENU_LINK = "BBBBBBBBBB";

    private static final Integer DEFAULT_MENU_ORDER = 1;
    private static final Integer UPDATED_MENU_ORDER = 2;

    private static final Integer DEFAULT_MENU_HEIGHT = 1;
    private static final Integer UPDATED_MENU_HEIGHT = 2;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuService menuService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMenuMockMvc;

    private Menu menu;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Menu createEntity(EntityManager em) {
        Menu menu = new Menu()
            .text(DEFAULT_TEXT)
            .group(DEFAULT_GROUP)
            .link(DEFAULT_LINK)
            .externalLink(DEFAULT_EXTERNAL_LINK)
            .target(DEFAULT_TARGET)
            .icon(DEFAULT_ICON)
            .hide(DEFAULT_HIDE)
            .description(DEFAULT_DESCRIPTION)
            .menuType(DEFAULT_MENU_TYPE)
            .menuLink(DEFAULT_MENU_LINK)
            .menuOrder(DEFAULT_MENU_ORDER)
            .menuHeight(DEFAULT_MENU_HEIGHT);
        return menu;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Menu createUpdatedEntity(EntityManager em) {
        Menu menu = new Menu()
            .text(UPDATED_TEXT)
            .group(UPDATED_GROUP)
            .link(UPDATED_LINK)
            .externalLink(UPDATED_EXTERNAL_LINK)
            .target(UPDATED_TARGET)
            .icon(UPDATED_ICON)
            .hide(UPDATED_HIDE)
            .description(UPDATED_DESCRIPTION)
            .menuType(UPDATED_MENU_TYPE)
            .menuLink(UPDATED_MENU_LINK)
            .menuOrder(UPDATED_MENU_ORDER)
            .menuHeight(UPDATED_MENU_HEIGHT);
        return menu;
    }

    @BeforeEach
    public void initTest() {
        menu = createEntity(em);
    }

    @Test
    @Transactional
    public void createMenu() throws Exception {
        int databaseSizeBeforeCreate = menuRepository.findAll().size();

        // Create the Menu
        restMenuMockMvc.perform(post("/api/menus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(menu)))
            .andExpect(status().isCreated());

        // Validate the Menu in the database
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeCreate + 1);
        Menu testMenu = menuList.get(menuList.size() - 1);
        assertThat(testMenu.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testMenu.isGroup()).isEqualTo(DEFAULT_GROUP);
        assertThat(testMenu.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testMenu.getExternalLink()).isEqualTo(DEFAULT_EXTERNAL_LINK);
        assertThat(testMenu.getTarget()).isEqualTo(DEFAULT_TARGET);
        assertThat(testMenu.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testMenu.isHide()).isEqualTo(DEFAULT_HIDE);
        assertThat(testMenu.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMenu.getMenuType()).isEqualTo(DEFAULT_MENU_TYPE);
        assertThat(testMenu.getMenuLink()).isEqualTo(DEFAULT_MENU_LINK);
        assertThat(testMenu.getMenuOrder()).isEqualTo(DEFAULT_MENU_ORDER);
        assertThat(testMenu.getMenuHeight()).isEqualTo(DEFAULT_MENU_HEIGHT);
    }

    @Test
    @Transactional
    public void createMenuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = menuRepository.findAll().size();

        // Create the Menu with an existing ID
        menu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMenuMockMvc.perform(post("/api/menus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(menu)))
            .andExpect(status().isBadRequest());

        // Validate the Menu in the database
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMenus() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get all the menuList
        restMenuMockMvc.perform(get("/api/menus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(menu.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].group").value(hasItem(DEFAULT_GROUP.booleanValue())))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)))
            .andExpect(jsonPath("$.[*].externalLink").value(hasItem(DEFAULT_EXTERNAL_LINK)))
            .andExpect(jsonPath("$.[*].target").value(hasItem(DEFAULT_TARGET)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].hide").value(hasItem(DEFAULT_HIDE.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].menuType").value(hasItem(DEFAULT_MENU_TYPE)))
            .andExpect(jsonPath("$.[*].menuLink").value(hasItem(DEFAULT_MENU_LINK)))
            .andExpect(jsonPath("$.[*].menuOrder").value(hasItem(DEFAULT_MENU_ORDER)))
            .andExpect(jsonPath("$.[*].menuHeight").value(hasItem(DEFAULT_MENU_HEIGHT)));
    }
    
    @Test
    @Transactional
    public void getMenu() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get the menu
        restMenuMockMvc.perform(get("/api/menus/{id}", menu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(menu.getId().intValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.group").value(DEFAULT_GROUP.booleanValue()))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK))
            .andExpect(jsonPath("$.externalLink").value(DEFAULT_EXTERNAL_LINK))
            .andExpect(jsonPath("$.target").value(DEFAULT_TARGET))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON))
            .andExpect(jsonPath("$.hide").value(DEFAULT_HIDE.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.menuType").value(DEFAULT_MENU_TYPE))
            .andExpect(jsonPath("$.menuLink").value(DEFAULT_MENU_LINK))
            .andExpect(jsonPath("$.menuOrder").value(DEFAULT_MENU_ORDER))
            .andExpect(jsonPath("$.menuHeight").value(DEFAULT_MENU_HEIGHT));
    }

    @Test
    @Transactional
    public void getNonExistingMenu() throws Exception {
        // Get the menu
        restMenuMockMvc.perform(get("/api/menus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMenu() throws Exception {
        // Initialize the database
        menuService.save(menu);

        int databaseSizeBeforeUpdate = menuRepository.findAll().size();

        // Update the menu
        Menu updatedMenu = menuRepository.findById(menu.getId()).get();
        // Disconnect from session so that the updates on updatedMenu are not directly saved in db
        em.detach(updatedMenu);
        updatedMenu
            .text(UPDATED_TEXT)
            .group(UPDATED_GROUP)
            .link(UPDATED_LINK)
            .externalLink(UPDATED_EXTERNAL_LINK)
            .target(UPDATED_TARGET)
            .icon(UPDATED_ICON)
            .hide(UPDATED_HIDE)
            .description(UPDATED_DESCRIPTION)
            .menuType(UPDATED_MENU_TYPE)
            .menuLink(UPDATED_MENU_LINK)
            .menuOrder(UPDATED_MENU_ORDER)
            .menuHeight(UPDATED_MENU_HEIGHT);

        restMenuMockMvc.perform(put("/api/menus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMenu)))
            .andExpect(status().isOk());

        // Validate the Menu in the database
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeUpdate);
        Menu testMenu = menuList.get(menuList.size() - 1);
        assertThat(testMenu.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testMenu.isGroup()).isEqualTo(UPDATED_GROUP);
        assertThat(testMenu.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testMenu.getExternalLink()).isEqualTo(UPDATED_EXTERNAL_LINK);
        assertThat(testMenu.getTarget()).isEqualTo(UPDATED_TARGET);
        assertThat(testMenu.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testMenu.isHide()).isEqualTo(UPDATED_HIDE);
        assertThat(testMenu.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMenu.getMenuType()).isEqualTo(UPDATED_MENU_TYPE);
        assertThat(testMenu.getMenuLink()).isEqualTo(UPDATED_MENU_LINK);
        assertThat(testMenu.getMenuOrder()).isEqualTo(UPDATED_MENU_ORDER);
        assertThat(testMenu.getMenuHeight()).isEqualTo(UPDATED_MENU_HEIGHT);
    }

    @Test
    @Transactional
    public void updateNonExistingMenu() throws Exception {
        int databaseSizeBeforeUpdate = menuRepository.findAll().size();

        // Create the Menu

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMenuMockMvc.perform(put("/api/menus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(menu)))
            .andExpect(status().isBadRequest());

        // Validate the Menu in the database
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMenu() throws Exception {
        // Initialize the database
        menuService.save(menu);

        int databaseSizeBeforeDelete = menuRepository.findAll().size();

        // Delete the menu
        restMenuMockMvc.perform(delete("/api/menus/{id}", menu.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
