package pro.alexfly.lab4.controller;

import AOP.UserAction;
import pro.alexfly.lab4.dao.IUserDAO;
import pro.alexfly.lab4.model.User;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * @Author is flystyle
 * Created on 06.06.16.
 */
@ManagedBean(name = "rails_user")
@SessionScoped
public class UserController implements UserAction {
    private String username;
    private String surname;
    private String pass;

    public static User currentUser;

    @EJB
    IUserDAO userDAO;

    public String verifyEnter() {
        if (userDAO.verifyAdminEnter(username, pass))
            return "adminka";
        else if (userDAO.verifyEnter(username, pass) != null) {
            UserController.currentUser = userDAO.verifyEnter(username, pass);
            return "main";
        }
        else return "index";
    }

    public String register() {
        if (userDAO.register(username, surname, pass) > 0)
            return "index";
        else return "regerror";
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}
