package oop.task3.actions;

import oop.task3.model.User;
import oop.task3.persistence.UserDAO;

import java.util.Date;

public class Registrar {
    private User user;
    private UserDAO dao = new UserDAO();

    public String register() {
        if (user == null) {
            return "invalid";
        }
        user.setRegistration(new Date());   // Add registration Date

        if (dao.check(user.getName(), user.getSurname()) != null) {
            return "exists";
        }

        user = dao.create(user);
        return "success";
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
