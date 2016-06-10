package pro.alexfly.lab4.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author is flystyle
 * Created on 05.06.16.
 */

@Entity
@Table(name = "rails_admin")
public class Admin implements Serializable {
    @Id
    @Column(name = "admin_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "admin_name", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String name;


    @Column(name = "admin_pass", nullable = false)
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

}
