package org.game.admin.repository;

import org.game.admin.input.UserPagination;
import org.game.admin.input.UserSearch;
import org.game.admin.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Page<User> findByQuery(UserPagination pageable, UserSearch userSearch) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cqData = cb.createQuery(User.class);
        Root<User> userData = cqData.from(User.class);

        CriteriaQuery<Long> cqCount = cb.createQuery(Long.class);
        Root<User> userCount = cqCount.from(User.class);

        List<Predicate> predicates = getPredicates(pageable, cb, userData, cqData, userSearch);
        cqData.where(predicates.toArray(new Predicate[0]));
        cqCount.where(predicates.toArray(new Predicate[0]));
        Sort sort = pageable.getSort();
        if (sort != null) {
            List<Order> orderList = new ArrayList<>();
            Order nullLast = cb.asc(cb.selectCase().when(userData.get(sort.stream().findFirst().get().getProperty()).isNull(), 1).otherwise(0));
            orderList.add(nullLast);
            orderList.addAll(sort.stream().map(order -> mapSortOrderToCriteriaOrder(cb, userData, order)).collect(Collectors.toList()));
            cqData.orderBy(orderList);
        }

        Query query = entityManager.createQuery(cqData);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getLimit());
        List<User> users = query.getResultList();

        cqCount.select(cb.count(userCount));
        Query countQuery = entityManager.createQuery(cqCount);
        long count = (long) countQuery.getSingleResult();

        return new PageImpl<>(users, pageable, count);
    }


    private List<Predicate> getPredicates(UserPagination pageable, CriteriaBuilder cb, Root<User> userData, CriteriaQuery<User> cqData, UserSearch userSearch) {
        List<Predicate> predicates = new ArrayList<>();

        if (pageable.getQ() != null) {
            Predicate usernameLike = cb.like(cb.upper(userData.get("username")), "%" + pageable.getQ() + "%");
            Predicate emailLike = cb.like(cb.upper(userData.get("email")), "%" + pageable.getQ() + "%");
            Predicate like = cb.or(usernameLike, emailLike);
            predicates.add(like);
        }
        predicates.add(addPredicates(userData, cqData, cb, userSearch));
        return predicates;
    }

    private Predicate addPredicates(Root<User> userData, CriteriaQuery<User> cqData, CriteriaBuilder cb, UserSearch userSearch) {
        return userSearch.build().toPredicate(userData, cqData, cb);
    }


    private Order mapSortOrderToCriteriaOrder(CriteriaBuilder cb, Root<User> userData, Sort.Order order) {
        if (order.isAscending())
            return cb.asc(userData.get(order.getProperty()));
        else
            return cb.desc(userData.get(order.getProperty()));
    }
}
