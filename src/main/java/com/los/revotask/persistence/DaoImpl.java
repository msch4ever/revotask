package com.los.revotask.persistence;

import com.los.revotask.config.SessionFactoryProvider;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class DaoImpl<T> implements Dao<T> {

    final SessionFactory sf;

    public DaoImpl() {
        this.sf = SessionFactoryProvider.getInstance().getSessionFactory();
    }

    @Override
    public long save(T t) {
        Session session = sf.openSession();
        session.beginTransaction();
        long id = (long) session.save(t);
        session.getTransaction().commit();
        session.close();
        return id;
    }

    @Override
    public T findById(Class<T> c, long id) {
        Session session = sf.openSession();
        T result = c.cast(session.get(c, id));
        session.close();
        return result;
    }
    @Override
    public List<T> getAll(Class<T> c) {
        Session session = sf.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(c);
        Root<T> root = criteriaQuery.from(c);
        criteriaQuery.select(root);
        Query<T> query = session.createQuery(criteriaQuery);
        List<T> resultList = query.getResultList();
        session.close();
        return resultList;
    }

    @Override
    public void update(T t) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.saveOrUpdate(t);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(T t) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.delete(t);
        session.getTransaction().commit();
        session.close();
    }
}
