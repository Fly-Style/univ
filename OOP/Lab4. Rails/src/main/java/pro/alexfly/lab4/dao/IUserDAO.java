package pro.alexfly.lab4.dao;

import pro.alexfly.lab4.model.Admin;
import pro.alexfly.lab4.model.User;

import java.util.List;

/**
 * @Author is flystyle
 * Created on 06.06.16.
 */
public interface IUserDAO {
    User verifyEnter(String name, String pass);
    Admin adminEnter(String name, String pass);
    Integer register(String name, String surname, String pass);
    List getAllUsers();
    User getUserById(int id);
    void banUser(int id);
    boolean verifyAdminEnter(String name, String pass);
}
