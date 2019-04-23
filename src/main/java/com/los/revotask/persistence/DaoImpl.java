package com.los.revotask.persistence;

import com.los.revotask.config.SessionFactoryProvider;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class DaoImpl<T> implements Dao<T> {

    final SessionFactory sf;

    public DaoImpl() {
        this.sf = SessionFactoryProvider.getInstance().getSessionFactory();
    }

    @Override
    public long save(T t) {
        return (long) sf.getCurrentSession().save(t);
    }

    @Override
    public T findById(Class<T> c, long id) {
        Session session = sf.getCurrentSession();
        return c.cast(session.get(c, id));
    }
    @Override
    public List<T> getAll(Class<T> c) {
        Session session = sf.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(c);
        Root<T> root = criteriaQuery.from(c);
        criteriaQuery.select(root);
        Query<T> query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public void update(T t) {
        sf.getCurrentSession().saveOrUpdate(t);
    }

    @Override
    public void delete(T t) {
        sf.getCurrentSession().delete(t);
    }
}
