package com.example.application.repository.usuario;

import com.example.application.entities.Usuario;
import com.example.application.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class UsuarioRepositoryImpl extends AbstractRepository implements UsuarioRepository {

    private Usuario parse(final ResultSet rs) throws SQLException {
        final var usuario = new Usuario();
        usuario.setId(rs.getLong("id"));
        usuario.setEmail(rs.getString("email"));
        usuario.setSenha(rs.getString("senha"));
        usuario.setFoto(rs.getString("foto"));
        usuario.setNome(rs.getString("nome"));
        usuario.setAtivo(rs.getBoolean("ativo"));
        return usuario;
    }


    @Override
    public Optional<Usuario> findByUsername(final String username) {
        final var sql = """
                SELECT id, nome, email, senha, foto, ativo
                FROM usuario
                WHERE nome = :nome
                """;

        return jdbcClient().sql(sql)
                .param("nome", username)
                .query(rs -> {
                    if (rs.next()) {
                        return Optional.of(parse(rs));
                    }
                    return Optional.empty();
                });
    }

    @Override
    public Optional<Usuario> findById(final Long id) {
        final var sql = """
                SELECT id, nome, email, senha, foto, ativo
                FROM usuario
                WHERE id = :id
                """;

        return jdbcClient().sql(sql)
                .param("id", id)
                .query(rs -> {
                    if (rs.next()) {
                        return Optional.of(parse(rs));
                    }
                    return Optional.empty();
                });
    }

    @Override
    public void insert(final Usuario usuario) {
        final var upsertSql = """
                INSERT INTO usuario (nome, email, senha, foto, ativo)
                VALUES (:nome, :email, :senha, :foto, :ativo)
                """;

        jdbcClient().sql(upsertSql)
                .param("nome", usuario.getNome())
                .param("email", usuario.getEmail())
                .param("senha", usuario.getSenha())
                .param("foto", usuario.getFoto())
                .param("ativo", usuario.isAtivo())
                .update();
    }

    @Override
    public void update(final Usuario usuario) {
        final var upsertSql = """
                UPDATE usuario
                SET nome = :nome, email = :email, senha = :senha, foto = :foto, ativo = :ativo
                WHERE id = :id
                """;

        jdbcClient().sql(upsertSql)
                .param("id", usuario.getId())
                .param("nome", usuario.getNome())
                .param("email", usuario.getEmail())
                .param("senha", usuario.getSenha())
                .param("foto", usuario.getFoto())
                .param("ativo", usuario.isAtivo())
                .update();
    }


}