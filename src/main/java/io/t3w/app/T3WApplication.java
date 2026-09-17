package io.t3w.app;

import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.aura.Aura;
import com.vaadin.flow.theme.lumo.Lumo;
import io.t3w.app.services.T3WUsuarioService;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
@StyleSheet(Aura.STYLESHEET)
@StyleSheet(Lumo.UTILITY_STYLESHEET)
@StyleSheet("styles.css")
@Configuration
public class T3WApplication implements AppShellConfigurator, T3WLoggable {

    static void main(String[] args) {
        SpringApplication.run(T3WApplication.class, args);
    }

    @Bean
    SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login").permitAll()
                        .anyRequest().authenticated())
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    UserDetailsService userDetailsService(final T3WUsuarioService usuarioService) {
        return username -> {
            final var usuario = usuarioService.findUsuarioByEmail(username);
            if (usuario == null) {
                throw new UsernameNotFoundException("Usuario nao encontrado: " + username);
            }
            return usuario;
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Primary
    @Bean(name = "dspg")
    DataSource dataSourcePostgres() {
        final var dataSource = new org.apache.tomcat.jdbc.pool.DataSource() {
//            @Override
//            public Connection getConnection() throws SQLException {
//                final var authentication = SecurityContextHolder.getContext().getAuthentication();
//                if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
//                    return super.getConnection();
//                    return getConnection(userDetails.getUsername(), userDetails.getPassword());
//                }
//                getLogger().info("Pegando conexão sem credenciais...");
//                return super.getConnection();
//            }
//
//            @Override
//            public Connection getConnection(final String username, final String password) throws SQLException {
//                getLogger().info("Pegando conexão com credenciais para usuario '{}'...", username);
//                return super.getConnection(username, password);
//            }
        };
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/wmix");
        dataSource.setUsername("postgres");
        dataSource.setPassword(System.getenv("PG_PASS"));
        dataSource.setInitialSize(2); // Conexões iniciais
        dataSource.setMaxActive(10); // Máximo de conexões ativas
        dataSource.setMaxIdle(3); // Máximo de conexões ociosas
        dataSource.setMinIdle(1); // Mínimo de conexões ociosas
        dataSource.setTestOnBorrow(true); // Valida a conexão antes de usar
        dataSource.setValidationQuery("SELECT 1"); // Query de validação
        return dataSource;
    }

//    @Primary
//    @Bean(name = "jc1")
//    JdbcClient jdbcClient1() {
//        getLogger().info("Criando JdbcClient primario...");
//        return JdbcClient.create(dataSourcePostgres());
//    }
//
//    @Bean(name = "jc2")
//    JdbcClient jdbcClient2() {
//        getLogger().info("Criando JdbcClient secundário...");
//        return JdbcClient.create(dataSourcePostgres());
//    }
}
