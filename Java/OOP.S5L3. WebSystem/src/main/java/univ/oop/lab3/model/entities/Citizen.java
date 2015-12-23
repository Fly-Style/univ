package univ.oop.lab3.model.entities;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * Created by flystyle on 19.12.15.
 */
public class Citizen implements Serializable {
    protected int id;
    private String name;
    private String disctrict, street;
    private int house, flat;

    private String login, password;

    public Citizen(int id, String login,String password, String name, String disctrict, String street, int flat, int house) {
        this.disctrict = disctrict;
        this.flat = flat;
        this.house = house;
        this.id = id;
        this.login = login;
        this.name = name;
        this.password = password;
        this.street = street;
    }

    public Citizen(String login, String password, int id) {
        this.login = login;
        this.password = password;
        this.id = id;
    }

    public String getDisctrict() {
        return disctrict;
    }

    public void setDisctrict(String disctrict) {
        this.disctrict = disctrict;
    }

    public int getFlat() {
        return flat;
    }

    public void setFlat(int flat) {
        this.flat = flat;
    }

    public int getHouse() {
        return house;
    }

    public void setHouse(int house) {
        this.house = house;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLogin() {
        return login;
    }

    public int getId() {
        return id;
    }

    public static boolean UserIsNull(Citizen usr, HttpServletRequest request) {

        if(usr == null) {
            request.setAttribute("title", "access");
            request.setAttribute("infoMessage", "access_denied");
            request.getRequestDispatcher("/view/info_page.jsp");
            return true;
        }
        return false;
    }
}



