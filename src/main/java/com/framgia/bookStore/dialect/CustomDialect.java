package com.framgia.bookStore.dialect;

import org.hibernate.dialect.MySQL57Dialect;
import org.hibernate.type.StandardBasicTypes;

import java.sql.Types;

@SuppressWarnings("unused")
public class CustomDialect extends MySQL57Dialect {
    public CustomDialect(){
        super();
        registerHibernateType(Types.BIGINT, StandardBasicTypes.LONG.getName());
    }
}
