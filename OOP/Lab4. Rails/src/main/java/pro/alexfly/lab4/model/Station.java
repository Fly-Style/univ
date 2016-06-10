package pro.alexfly.lab4.model;

import javax.persistence.*;

/**
 * @Author is flystyle
 * Created on 04.06.16.
 */
@Entity
@Table(name = "rails_station")
public class Station {
    private int id;
    private String name;

    public Station(String name) {
        this.name = name;
        this.id = id;
    }

    public Station(int id) {
        this.id = id;
    }

    public Station() {}

    @Id
    @Column(name = "station_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "station_name")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
