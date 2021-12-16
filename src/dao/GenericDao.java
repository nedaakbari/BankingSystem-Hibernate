package dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.function.BiConsumer;



public interface GenericDao<T> {
	SessionFactory sessionFactory = new Configuration().configure("config/hibernate.cfg.xml").buildSessionFactory();

	public void save(T entity);

	public void update(T entity);

	public T findByID(T entity);
	public List<T> findAll();

}

