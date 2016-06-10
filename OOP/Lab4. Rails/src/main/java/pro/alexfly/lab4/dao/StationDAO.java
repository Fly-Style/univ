package pro.alexfly.lab4.dao;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import pro.alexfly.lab4.model.Station;

import javax.ejb.Stateless;

/**
 * @Author is flystyle
 * Created on 05.06.16.
 */
@Stateless
public class StationDAO implements IStationDAO {

    private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public Station getStationById(int id) {
        Session session = sessionFactory.openSession();
        Transaction tr = null;
        tr = session.beginTransaction();

        String sql = "SELECT * FROM rails_station WHERE station_id = :id";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Station.class);
        query.setParameter("id", id);
        Station res = (Station) query.uniqueResult();
        tr.commit();
        session.close();
        return res;
    }

    public Station getStationByName(String name) {
        Session session = sessionFactory.openSession();
        Transaction tr = session.beginTransaction();

        String sql = "SELECT * FROM rails_station WHERE station_name = :nm";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Station.class);
        query.setParameter("nm", name);
        Station res = (Station) query.uniqueResult();
        tr.commit();
        session.close();
        return res;
    }

    public void setName(Station station) {
        station = getStationById(station.getId());
    }
}
