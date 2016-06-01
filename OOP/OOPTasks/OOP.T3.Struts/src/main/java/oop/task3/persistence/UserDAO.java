package oop.task3.persistence;

import oop.task3.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 * Created on 4/5/2015.
 */
public class UserDAO {

    public User check(String name, String surname) {
        Session session = Configurator.getSession();
        Transaction tx = null;

        User result = null;

        try {
            tx = session.beginTransaction();
            result = (User) session.createCriteria(User.class).
                    add(Restrictions.eq("name", name)).
                    add(Restrictions.eq("surname", surname)).
                    uniqueResult();

            tx.commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            tx.rollback();
        }

        return result;
    }

    public User create(User user) {
        Session session = Configurator.getSession();
        Transaction tx = null;

        User result = null;

        try {
            tx = session.beginTransaction();
            user.setId((Integer) session.save(user));
            result = user;
            tx.commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            tx.rollback();
        }

        return result;
    }
}
