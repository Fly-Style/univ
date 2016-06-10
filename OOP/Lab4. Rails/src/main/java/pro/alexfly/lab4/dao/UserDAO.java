package pro.alexfly.lab4.dao;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import pro.alexfly.lab4.model.Admin;
import pro.alexfly.lab4.model.User;

import javax.ejb.Stateless;
import java.util.List;

/**
 * @Author is flystyle
 * Created on 06.06.16.
 */
@Stateless
public class UserDAO implements IUserDAO {

    String name, surname, pass;

    private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public User verifyEnter(String name, String pass) {
        Session session = sessionFactory.openSession();
        Transaction tr = null;
        tr = session.beginTransaction();

        String sql = "SELECT * FROM rails_user WHERE user_name = :an AND user_pass = :ap";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(User.class);
        query.setParameter("an", name);
        query.setParameter("ap", pass);
        User res = (User) query.uniqueResult();
        tr.commit();
        session.close();
        return res;
    }

    public Admin adminEnter(String name, String pass) {
        Session session = sessionFactory.openSession();
        Transaction tr = null;
        tr = session.beginTransaction();

        String sql = "SELECT * FROM rails_admin WHERE admin_name = :an AND admin_pass = :ap";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Admin.class);
        query.setParameter("an", name);
        query.setParameter("ap", pass);
        Admin res = (Admin) query.uniqueResult();
        tr.commit();
        session.close();
        return res;
    }

    public Integer register(String name, String surname, String pass) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        transaction = session.beginTransaction();
        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setPass(pass);
        Integer id = (Integer) session.save(user);
        transaction.commit();
        session.close();
        return id;
    }

    public List getAllUsers() {
        Session session = sessionFactory.openSession();
        Transaction tr = null;
        tr = session.beginTransaction();

        String sql = "SELECT * FROM rails_user";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(User.class);
        List res = query.list();
        tr.commit();
        session.close();
        return res;
    }

    public User getUserById(int id) {
        Session session = sessionFactory.openSession();
        Transaction tr = null;
        tr = session.beginTransaction();

        String sql = "SELECT * FROM rails_user WHERE user_id = :id";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(User.class);
        query.setParameter("id", id);
        User res = (User) query.uniqueResult();
        tr.commit();
        session.close();
        return res;
    }

    public void banUser(int id) {
        Session session = sessionFactory.openSession();
        SQLQuery q = session.createSQLQuery("DELETE FROM rails_user WHERE request_id = :id");
        q.addEntity(User.class);
        q.setParameter("id", id);
        q.executeUpdate();
    }

    public boolean verifyAdminEnter(String name, String pass) {
        Session session = sessionFactory.openSession();
        Transaction tr = null;
        tr = session.beginTransaction();

        String sql = "SELECT * FROM rails_admin WHERE admin_name = :an AND admin_pass = :ap";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Admin.class);
        query.setParameter("an", name);
        query.setParameter("ap", pass);
        Admin res = (Admin) query.uniqueResult();
        tr.commit();
        session.close();
        if (res.getName().equals(name) && res.getPass().equals(pass))
            return true;
        return false;
    }

}
