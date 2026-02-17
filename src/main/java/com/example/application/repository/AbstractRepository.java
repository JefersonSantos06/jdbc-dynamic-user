package com.example.application.repository;

import com.example.application.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.simple.JdbcClient;

public abstract class AbstractRepository implements Loggable {

    @Autowired
    private JdbcClient jdbcClient;

    @Autowired
    private NamedParameterJdbcOperations namedJdbc;

    protected JdbcClient jdbcClient() {
        return jdbcClient;
    }

    protected NamedParameterJdbcOperations namedJdbc() {
        return namedJdbc;
    }

}