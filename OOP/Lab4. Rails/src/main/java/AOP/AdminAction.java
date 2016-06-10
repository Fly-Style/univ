package AOP;

/**
 * @Author is flystyle
 * Created on 06.06.16.
 */
public interface AdminAction {
    void viewAllUsers();
    void viewAllRequests();
    void deleteRequest(int id);
    void deleteUser(int id);
    //...
}
