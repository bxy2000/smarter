package com.boxy.smarter.service.dto;

import com.boxy.smarter.domain.Menu;

import java.util.ArrayList;
import java.util.List;

public class MenuDTO {
    private Long id;

    private String text;

    private Boolean group;

    private String link;

    private String externalLink;

    private String target;

    private String icon;

    private Boolean hide;

    private String description;

    private List<MenuDTO> children = new ArrayList<>();

    public MenuDTO(Menu menu) {
    	this.id = menu.getId();

    	this.text = menu.getText();

    	this.group = menu.isGroup();

    	this.link = menu.getLink();

    	this.externalLink = menu.getExternalLink();

    	this.target = menu.getTarget();

    	this.icon = menu.getIcon();

    	this.hide = menu.isHide();

    	this.description = menu.getDescription();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getGroup() {
        return group;
    }

    public void setGroup(Boolean group) {
        this.group = group;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getExternalLink() {
        return externalLink;
    }

    public void setExternalLink(String externalLink) {
        this.externalLink = externalLink;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getHide() {
        return hide;
    }

    public void setHide(Boolean hide) {
        this.hide = hide;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MenuDTO> getChildren() {
        return children;
    }

    public void setChildren(List<MenuDTO> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "MenuDTO{" +
            "id=" + id +
            ", text='" + text + '\'' +
            ", group=" + group +
            ", link='" + link + '\'' +
            ", externalLink='" + externalLink + '\'' +
            ", target='" + target + '\'' +
            ", icon='" + icon + '\'' +
            ", hide=" + hide +
            ", description='" + description + '\'' +
            ", children=" + children +
            '}';
    }
}
