package pro.alexfly.lab4.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * @Author is flystyle
 * Created on 04.06.16.
 */

@Entity
@Table(name = "rails_requests")
public class Request implements Serializable {

    @Id
    @Column(name = "request_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "request_user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @OneToOne(targetEntity = User.class)
    private User user;

    @Column(name = "request_station_begin", nullable = false)
    @OneToOne(targetEntity = Station.class)
    private Station stationBegin;

    @Column(name = "request_station_end", nullable = false)
    @OneToOne(targetEntity = Station.class)
    private Station stationEnd;

    @Column(name = "request_date", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Temporal(TemporalType.DATE)
    private Date day;

    public Request() {}

    public Request(int id, Date day, Station stationBegin, Station stationEnd) {
        this.id = id;
        this.day = day;
        this.stationBegin = stationBegin;
        this.stationEnd = stationEnd;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Station getStationBegin() {
        return stationBegin;
    }

    public void setStationBegin(Station stationBegin) {
        this.stationBegin = stationBegin;
    }

    public Station getStationEnd() {
        return stationEnd;
    }

    public void setStationEnd(Station stationEnd) {
        this.stationEnd = stationEnd;
    }
}
