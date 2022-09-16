package org.gumidev.springweb.model;

public class Menu {
    private String title;
    private String icon;
    private SubMenu[] submenu;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public SubMenu[] getSubmenu() {
        return submenu;
    }

    public void setSubmenu(SubMenu[] submenu) {
        this.submenu = submenu;
    }
}
