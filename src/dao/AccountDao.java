package dao;

import model.Account;
import model.TransAction;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class AccountDao extends BaseDao {

    public void save(Account account) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(account);
        transaction.commit();
        session.close();
    }

    public void update(Account account) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(account);
        transaction.commit();
        session.close();
    }

    public Account findAccByCardNumber(Long cardNumber) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("From Account A Where A.cardNumber = :cardNumber ");
        query.setParameter("cardNumber", cardNumber);
        Account account = (Account) query.uniqueResult();
        transaction.commit();
        session.close();
        return account;
    }

    public void saveTransAction(TransAction transAction) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(transAction);
        transaction.commit();
        session.close();
    }

}
