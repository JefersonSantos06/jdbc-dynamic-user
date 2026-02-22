package io.t3w.app.repository;

import io.t3w.app.T3WLoggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.simple.JdbcClient;

public abstract class T3WAbstractRepository implements T3WLoggable {

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