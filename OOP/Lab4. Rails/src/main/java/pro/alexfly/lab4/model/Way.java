package pro.alexfly.lab4.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author is flystyle
 * Created on 05.06.16.
 */
@Entity
@Table(name = "rails_ways")
public class Way implements Serializable {

    @Id
    @Column(name = "way_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(name = "way_stationID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer wayStation;

    @Column(name = "way_trainID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer trainId;

    @Column(name = "way_counter")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer counter;

    public Way() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public Integer getTrainId() {
        return trainId;
    }

    public void setTrainId(Integer trainId) {
        this.trainId = trainId;
    }

    public Integer getWayStation() {
        return wayStation;
    }

    public void setWayStation(Integer wayStation) {
        this.wayStation = wayStation;
    }



}
