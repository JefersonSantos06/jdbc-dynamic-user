package com.example.application.config;

import com.example.application.Loggable;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class DatasourceConfiguration {

    /**
     * Cria o datasource base da aplicação usando as propriedades {@code spring.datasource.*}.
     *
     * <p>Este bean representa o pool JDBC concreto (Tomcat JDBC Pool) e não aplica nenhuma
     * lógica de segurança adicional por si só.</p>
     *
     * @return datasource base configurado a partir das propriedades da aplicação
     */
    @Bean("baseDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public org.apache.tomcat.jdbc.pool.DataSource baseDataSource() {
        return DataSourceBuilder.create()
                .type(DataSource.class)
                .build();
    }

    /**
     * Expõe o datasource principal da aplicação, encapsulando o datasource base em um wrapper
     * que escolhe as credenciais dinamicamente a partir do contexto de segurança.
     *
     * <p>Por ser {@link Primary}, este bean é o datasource padrão injetado em componentes que dependem de
     * {@link javax.sql.DataSource}.</p>
     *
     * @param baseDataSource datasource base previamente criado e identificado pelo nome {@code baseDataSource}
     * @return datasource primário com seleção dinâmica de credenciais por usuário autenticado
     */
    @Bean
    @Primary
    public javax.sql.DataSource dataSource(@Qualifier("baseDataSource") final DataSource baseDataSource) {
        return new AbstractDataSource() {

            @Override
            public Connection getConnection() throws SQLException {
                final var authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
                    return getConnection(userDetails.getUsername(), userDetails.getUsername());
                }
                Loggable.getLogger(DatasourceConfiguration.class).info("Pegando conexão sem credenciais");
                return baseDataSource.getConnection();
            }

            @Override
            public Connection getConnection(final String username, final String password) throws SQLException {
                Loggable.getLogger(DatasourceConfiguration.class).info("Pegando conexao para o usuario: {}", username);
                return baseDataSource.getConnection(username, password);
            }
        };
    }

}