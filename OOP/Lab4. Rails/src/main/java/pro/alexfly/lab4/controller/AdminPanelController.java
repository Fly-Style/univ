package pro.alexfly.lab4.controller;

import AOP.AdminAction;
import pro.alexfly.lab4.dao.IRequestDAO;
import pro.alexfly.lab4.dao.IUserDAO;
import pro.alexfly.lab4.model.Request;
import pro.alexfly.lab4.model.User;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;

/**
 * @Author is flystyle
 * Created on 06.06.16.
 */

@ManagedBean(name = "rails_admin_panel")
@SessionScoped
public class AdminPanelController implements AdminAction{

    private List<User> allUsers;
    private List<Request> allRequests;

    @EJB
    private IUserDAO userDAO;
    @EJB
    private IRequestDAO requestDAO;

    public void viewAllUsers() {
        allUsers = userDAO.getAllUsers();
    }

    public void viewAllRequests() {
        allRequests = requestDAO.list();
    }

    public void deleteRequest(int id) {
        requestDAO.removeRequestById(id);
    }

    public void deleteUser(int id) {
        userDAO.banUser(id);
    }

    public List<Request> getAllRequests() {
        return allRequests;
    }

    public List<User> getAllUsers() {
        return allUsers;
    }
}
