package pro.alexfly.lab4.dao;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import pro.alexfly.lab4.model.Request;
import pro.alexfly.lab4.model.Station;
import pro.alexfly.lab4.model.User;

import javax.ejb.Stateless;
import java.sql.Date;
import java.util.List;
/**
 * @Author is flystyle
 * Created on 05.06.16.
 */

@Stateless
public class RequestDAO implements IRequestDAO {

    private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public Integer createRequest(Request source) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        transaction = session.beginTransaction();
        Integer id = (Integer) session.save(source);
        transaction.commit();
        session.close();
        return id;
    }

    public List list(Date date) {
        Session session = sessionFactory.openSession();
        Transaction tr = null;
        tr = session.beginTransaction();

        String sql = "SELECT * FROM rails_requests WHERE request_date = :date";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Request.class);
        query.setParameter("date", date);
        List res = query.list();
        tr.commit();
        session.close();
        return res;
    }

    public List<Request> list() {
        Session session = sessionFactory.openSession();
        Transaction tr = null;
        tr = session.beginTransaction();

        String sql = "SELECT * FROM rails_requests";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Request.class);
        List res = query.list();
        tr.commit();
        session.close();
        return res;
    }

    public List list(Station begin, Station end) {
        Session session = sessionFactory.openSession();
        Transaction tr = null;
        tr = session.beginTransaction();

        String sql = "SELECT * FROM rails_requests WHERE request_station_begin = :beg AND request_station_end = :endd";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Request.class);
        query.setParameter("beg", begin);
        query.setParameter("endd", end);
        List res = query.list();
        tr.commit();
        session.close();
        return res;
    }

    public Request getRequestById(int id) {
        Session session = sessionFactory.openSession();
        Transaction tr = null;
        tr = session.beginTransaction();

        String sql = "SELECT * FROM rails_requests WHERE request_id = :id";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Request.class);
        query.setParameter("id", id);
        Request res = (Request) query.uniqueResult();
        tr.commit();
        session.close();
        return res;
    }

    public List<Request> getRequestsByUserId(int userId) {
        Session session = sessionFactory.openSession();
        Transaction tr = null;
        tr = session.beginTransaction();

        String sql = "SELECT * FROM rails_requests WHERE request_user_id = :id";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Request.class);
        query.setParameter("id", userId);
        List res = query.list();
        tr.commit();
        session.close();
        return res;
    }

    public void removeRequestById(int id) {
        Session session = sessionFactory.openSession();
        SQLQuery q = session.createSQLQuery("DELETE FROM rails_requests WHERE request_id = :id");
        q.addEntity(Request.class);
        q.setParameter("id", id);
        q.executeUpdate();
    }

    public void removeRequestByUser(User user) {
        Session session = sessionFactory.openSession();
        SQLQuery query = session.createSQLQuery("DELETE FROM rails_requests WHERE request_user_id = :id");
        query.addEntity(Request.class);
        query.setParameter("id", user.getId());
        query.executeUpdate();
    }

    public void removeAllRequests() {
        Session session = sessionFactory.openSession();
        SQLQuery query = session.createSQLQuery("DELETE  FROM rails_requests");
        query.addEntity(Request.class);
        query.executeUpdate();
    }
}
