package com.example.application.repository.usuario;

import com.example.application.entities.Usuario;

import java.util.Optional;

public interface UsuarioRepository {

    Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findById(Long id);

    void update(Usuario usuario);

    void insert(Usuario usuario);

}
