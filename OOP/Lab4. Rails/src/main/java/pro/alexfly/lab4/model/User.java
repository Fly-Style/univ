package pro.alexfly.lab4.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author is flystyle
 * Created on 04.06.16.
 */
@Entity
@Table(name = "rails_user", schema = "public")
public class User implements Serializable {

    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "user_name", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String name;

    @Column(name = "user_surname", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String surname;

    @Column(name = "user_pass", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String pass;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
