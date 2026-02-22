package io.t3w.app.repository;

import io.t3w.app.entities.T3WUsuarioEntity;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class T3WUsuarioRepository extends T3WAbstractRepository {

    private T3WUsuarioEntity parse(final ResultSet rs) throws SQLException {
        return new T3WUsuarioEntity()
                .setId(rs.getLong("registro"))
                .setEmail(rs.getString("email"))
                //.setSenha(rs.getString("senha"))
                .setSenha("{noop}admin")//teste
                .setNome(rs.getString("nome"))
                .setAtivo(rs.getBoolean("ativo"));
    }

    public Optional<T3WUsuarioEntity> findByUsername(final String username) {
        return jdbcClient().sql("SELECT * FROM usuario WHERE email = :username")
                .param("username", username)
                .query(rs -> rs.next() ? Optional.of(parse(rs)) : Optional.empty());
    }

    public Optional<T3WUsuarioEntity> findById(final long id) {
        return jdbcClient().sql("SELECT * FROM usuario WHERE registro = :id")
                .param("id", id)
                .query(rs -> rs.next() ? Optional.of(parse(rs)) : Optional.empty());
    }

    public Optional<T3WUsuarioEntity> insert(final T3WUsuarioEntity usuario) {
        final var sql = """
                INSERT INTO usuario (nome, email, senha, ativo)
                VALUES (:nome, :email, :senha, :ativo)
                RETURNING *
                """;

        return jdbcClient().sql(sql)
                .param("nome", usuario.getNome())
                .param("email", usuario.getEmail())
                .param("senha", usuario.getSenha())
                .param("ativo", usuario.isAtivo())
                .query(rs -> rs.next() ? Optional.of(parse(rs)) : Optional.empty());
    }

    public Optional<T3WUsuarioEntity> update(final T3WUsuarioEntity usuario) {
        final var upsertSql = """
                UPDATE usuario
                SET nome = :nome,
                    email = :email,
                    senha = :senha,
                    ativo = :ativo
                WHERE id = :id
                RETURNING *
                """;

        return jdbcClient().sql(upsertSql)
                .param("id", usuario.getId())
                .param("nome", usuario.getNome())
                .param("email", usuario.getEmail())
                .param("senha", usuario.getSenha())
                .param("foto", usuario.getFoto())
                .param("ativo", usuario.isAtivo())
                .query(rs -> rs.next() ? Optional.of(parse(rs)) : Optional.empty());
    }
}