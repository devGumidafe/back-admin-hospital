package org.gumidev.springweb.utils;

import org.gumidev.springweb.model.Menu;
import org.gumidev.springweb.model.SubMenu;

public class MenuFrontend {

    public static Menu[] getMenu(String role) {

        Menu[] menu = new Menu[2];

        menu[0] = new Menu();
        menu[0].setTitle("Dashboard");
        menu[0].setIcon("mdi mdi-gauge");
        menu[0].setSubmenu(new SubMenu[5]);
        menu[0].getSubmenu()[0] = new SubMenu();
        menu[0].getSubmenu()[0].setTitle("Home");
        menu[0].getSubmenu()[0].setUrl("/");
        menu[0].getSubmenu()[1] = new SubMenu();
        menu[0].getSubmenu()[1].setTitle("Progress");
        menu[0].getSubmenu()[1].setUrl("progress");
        menu[0].getSubmenu()[2] = new SubMenu();
        menu[0].getSubmenu()[2].setTitle("grafica1");
        menu[0].getSubmenu()[2].setUrl("grafica1");
        menu[0].getSubmenu()[3] = new SubMenu();
        menu[0].getSubmenu()[3].setTitle("Promises");
        menu[0].getSubmenu()[3].setUrl("promises");
        menu[0].getSubmenu()[4] = new SubMenu();
        menu[0].getSubmenu()[4].setTitle("Rxjs");
        menu[0].getSubmenu()[4].setUrl("rxjs");

        menu[1] = new Menu();
        menu[1].setTitle("Maintenance");
        menu[1].setIcon("mdi mdi-folder-lock-open");
        menu[1].setSubmenu(new SubMenu[3]);
        menu[1].getSubmenu()[1] = new SubMenu();
        menu[1].getSubmenu()[1].setTitle("Hospitals");
        menu[1].getSubmenu()[1].setUrl("/hospitals");
        menu[1].getSubmenu()[2] = new SubMenu();
        menu[1].getSubmenu()[2].setTitle("Doctors");
        menu[1].getSubmenu()[2].setUrl("/doctors");

        if (role.equals("ADMIN")) {
            menu[1].getSubmenu()[0] = new SubMenu();
            menu[1].getSubmenu()[0].setTitle("Users");
            menu[1].getSubmenu()[0].setUrl("/users");
        }

        return menu;
    }
}
