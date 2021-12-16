package dao;

import model.UpdateInfo;
import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDao extends BaseDao {

    public void save(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    public void update(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
    }

    public List<User> findByName(String name) {
        List<User> resultList;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<User> query = session.createQuery("From User U Where U.name = :name ");
        query.setParameter("name", name);
        resultList = query.list();
        transaction.commit();
        session.close();
        return resultList;
    }

    public List<User> findByFamily(String family) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<User> query = session.createQuery("From User U Where U.family = :family");
        query.setParameter("family", family);
        List<User> resultList = query.list();
        transaction.commit();
        session.close();
        return resultList;
    }

    public User findByNationalCode(String nationalCode) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("From User U Where U.nationalCode = :nationalCode ");
        query.setParameter("nationalCode", nationalCode);
        User user = (User) query.uniqueResult();
        transaction.commit();
        session.close();
        return user;
    }

    public void saveEditInfo(UpdateInfo update) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(update);
        transaction.commit();
        session.close();
    }

    public List<UpdateInfo> findLast3Update() {
        List<UpdateInfo> updates;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query findId = session.createQuery("SELECT max(id) FROM Update ");
        int lastId = (Integer) findId.list().get(0);
        Query query;
        if (lastId < 3) {
            query = session.createQuery("FROM Update");
        } else {
            query = session.createQuery("FROM Update where id<= " + lastId + " and id>" + (lastId - 3));
        }
        updates = query.list();
        transaction.commit();
        session.close();
        return updates;
    }

}
