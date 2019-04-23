package com.los.revotask.persistence;

import com.los.revotask.transaction.Transfer;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

public class TransferDao extends DaoImpl<Transfer> {

    public List<Transfer> findAllByAccountId(long accountId) {
        Session session = sf.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Transfer> criteriaQuery = builder.createQuery(Transfer.class);
        Root<Transfer> root = criteriaQuery.from(Transfer.class);

        Predicate sourceAccountId = builder.equal(root.get("sourceAccountId"), accountId);
        Predicate destinationAccountId = builder.equal(root.get("destinationAccountId"), accountId);

        criteriaQuery.select(root).where(builder.or(sourceAccountId, destinationAccountId));
        Query<Transfer> query = session.createQuery(criteriaQuery);
        List<Transfer> resultList = query.getResultList();
        return resultList;
    }

}
