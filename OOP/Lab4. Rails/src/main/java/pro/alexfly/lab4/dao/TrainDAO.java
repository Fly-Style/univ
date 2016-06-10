package pro.alexfly.lab4.dao;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import pro.alexfly.lab4.model.Station;
import pro.alexfly.lab4.model.Train;
import pro.alexfly.lab4.model.Way;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author is flystyle
 * Created on 06.06.16.
 */
@Stateless
public class TrainDAO implements ITrainDAO {

    private StationDAO stationDAO = new StationDAO();

    private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public Train getTrainById(int id) {
        Session session = sessionFactory.openSession();
        Transaction tr = null;
        tr = session.beginTransaction();

        String sql = "SELECT * FROM rails_train WHERE request_id = :id";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Train.class);
        query.setParameter("id", id);
        Train res = (Train) query.uniqueResult();
        tr.commit();
        session.close();
        return res;
    }

    public List list() {
        Session session = sessionFactory.openSession();
        Transaction tr = null;
        tr = session.beginTransaction();

        String sql = "SELECT * FROM rails_train";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Train.class);
        List res = query.list();
        tr.commit();
        session.close();
        return res;
    }

    public List list(String beg, String end) {
        Station begin = stationDAO.getStationByName(beg);
        Station ends = stationDAO.getStationByName(end);

        List<Train> trains = new ArrayList<Train>();
        List<Way> trains1 = null;
        List<Way> trains2 = null;

        Session session = sessionFactory.openSession();
        Transaction tr = null;
        tr = session.beginTransaction();

        String sql = "SELECT * FROM rails_ways WHERE way_stationid = :st1";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Way.class);
        query.setParameter("st1", begin.getId());
        trains1 = query.list();
        tr.commit();
        session.close();

        tr = session.beginTransaction();
        String sql2 = "SELECT * FROM rails_ways WHERE way_stationid = :st2";
        SQLQuery query2 = session.createSQLQuery(sql2);
        query2.addEntity(Way.class);
        query2.setParameter("st2", ends.getId());
        trains2 = query.list();

        for(int i = 0; i < trains1.size(); ++i) {
            if (trains2.contains(trains1.get(i)))
                trains.add(getTrainById(trains1.get(i).getId()));
        }

        return trains;
    }

    public Way getWay(int trainId) {
        Session session = sessionFactory.openSession();
        Transaction tr = null;
        tr = session.beginTransaction();

        String sql = "SELECT * FROM rails_ways WHERE way_stationid = :id";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Way.class);
        query.setParameter("id", trainId);
        Way res = (Way) query.uniqueResult();
        tr.commit();
        session.close();
        return res;
    }

    public List<Way> getWays() {
        Session session = sessionFactory.openSession();
        Transaction tr = null;
        tr = session.beginTransaction();

        String sql = "SELECT * FROM rails_ways";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Way.class);
        List<Way> res = (List <Way>) query.list();
        tr.commit();
        session.close();
        return res;
    }
}
