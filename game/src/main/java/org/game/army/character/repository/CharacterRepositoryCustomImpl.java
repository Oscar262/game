package org.game.army.character.repository;

import org.game.army.character.input.CharacterSearch;
import org.game.army.character.model.Character;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CharacterRepositoryCustomImpl implements CharacterRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Character> findAll(CharacterSearch search, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Character> cq = cb.createQuery(Character.class);
        Root<Character> root = cq.from(Character.class);

        Predicate[] predicates = buildPredicates(cb, root, search, pageable);
        cq.where(predicates);

        cq.orderBy(cb.asc(root.get("dead")));

        pageable.getSort().forEach(order -> {
            Path<Object> path = root.get(order.getProperty());
            cq.orderBy(order.isAscending() ? cb.asc(path) : cb.desc(path));
        });

        List<Character> results = entityManager.createQuery(cq)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Character> countRoot = countQuery.from(Character.class);
        countQuery.select(cb.count(countRoot)).where(buildPredicates(cb, countRoot, search, pageable));
        Long total = entityManager.createQuery(countQuery).getSingleResult();

        System.out.println("busqueda");
        return new PageImpl<>(results, pageable, total);
    }

    private Predicate[] buildPredicates(CriteriaBuilder cb, Root<Character> root, CharacterSearch search, Pageable pageable) {
        List<Predicate> predicates = new ArrayList<>();

        if (search.getName() != null) {
            String searchTerm = search.getName().toLowerCase().replace("eq:", "");

            Predicate byName = cb.like(cb.lower(root.get("name")), "%" + searchTerm + "%");
            Predicate byLastName = cb.like(cb.lower(root.get("lastName")), "%" + searchTerm + "%");
            Predicate byFullName = cb.like(cb.lower(cb.concat(root.get("name"), cb.concat(" ", root.get("lastName")))), "%" + searchTerm + "%");

            predicates.add(cb.or(byName, byLastName, byFullName));
        }

        return predicates.toArray(new Predicate[0]);
    }

}
