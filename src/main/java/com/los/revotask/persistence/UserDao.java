package com.los.revotask.persistence;

import com.los.revotask.model.user.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDao extends DaoImpl<User> {

    public List<User> findByName(String userName) {
        Session session = sf.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root).where(builder.equal(root.get("userName"), userName));
        Query<User> query = session.createQuery(criteriaQuery);
        List<User> result = query.getResultList();
        session.close();
        return result;

    }
}
