package com.framgia.bookStore.dao.impl;

import com.framgia.bookStore.dao.BookDAO;
import com.framgia.bookStore.form.BookSearch;
import com.framgia.bookStore.util.StandardizeDataUtils;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public class BookDAOImpl implements BookDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<BookSearch> getBookByName(String bookName, Pageable pageable) {
        Session session = entityManager.unwrap(Session.class);
        Map<String, Object> paramQuery = new HashMap<>();
        final String countSqlFormat = "SELECT COUNT(1) FROM ( %s ) SUB_COUNT";

        String sql = "SELECT b.name as name, b.price as price, c.name as category  " +
                "FROM books b JOIN categories c on b.category_id = c.id";

        NativeQuery<BookSearch> query = session.createNativeQuery(sql);

        if (bookName != null){
            sql += "AND b.name = :bookname";
            paramQuery.put("bookname", "%" + StandardizeDataUtils.buildLikeClause(StandardizeDataUtils.standardize(bookName)) + "%");
        }

        Set<Map.Entry<String, Object>> paramEntries = paramQuery.entrySet();

        for (Map.Entry<String, Object> param : paramEntries) {
            query.setParameter(param.getKey(), param.getValue());
        }

        String countSql = String.format(countSqlFormat, sql);
        NativeQuery<Long> countQuery = session.createNativeQuery(countSql);

        Long total = countQuery.getSingleResult();

        int maxResult = pageable.getPageSize();


        List<BookSearch> results = query.setResultTransformer(Transformers.aliasToBean(BookSearch.class)).setFirstResult((int) pageable.getOffset()).setMaxResults(maxResult)
                .getResultList();

        return new PageImpl<>(results, pageable, total);
    }
}
