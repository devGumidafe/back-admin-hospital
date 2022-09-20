package org.gumidev.springweb.utils;

import org.gumidev.springweb.model.Menu;
import org.gumidev.springweb.model.SubMenu;

public class MenuFrontend {

    public static Menu[] getMenu(String role) {
        Menu[] menu = new Menu[1];
        menu[0] = new Menu();
        menu[0].setTitle("Mantenimiento");
        menu[0].setIcon("mdi mdi-folder-lock-open");

        if (role.equals("USER")) {
            menu[0].setSubmenu(new SubMenu[2]);
            menu[0].getSubmenu()[0] = new SubMenu();
            menu[0].getSubmenu()[0].setTitle("Hospitales");
            menu[0].getSubmenu()[0].setUrl("hospitals");
            menu[0].getSubmenu()[1] = new SubMenu();
            menu[0].getSubmenu()[1].setTitle("Médicos");
            menu[0].getSubmenu()[1].setUrl("doctors");
        }

        if (role.equals("ADMIN")) {
            menu[0].setSubmenu(new SubMenu[3]);
            menu[0].getSubmenu()[0] = new SubMenu();
            menu[0].getSubmenu()[0].setTitle("Usuarios");
            menu[0].getSubmenu()[0].setUrl("users");
            menu[0].getSubmenu()[1] = new SubMenu();
            menu[0].getSubmenu()[1].setTitle("Hospitals");
            menu[0].getSubmenu()[1].setUrl("hospitals");
            menu[0].getSubmenu()[2] = new SubMenu();
            menu[0].getSubmenu()[2].setTitle("Doctors");
            menu[0].getSubmenu()[2].setUrl("doctors");
        }
        return menu;
    }
}
