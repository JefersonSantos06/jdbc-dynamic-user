package io.t3w.app.services;

import io.t3w.app.entities.T3WUsuarioEntity;
import io.t3w.app.repository.T3WUsuarioRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class T3WUsuarioService {

    private final T3WUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public T3WUsuarioService(final T3WUsuarioRepository usuarioRepository, final PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public T3WUsuarioEntity findUsuarioByEmail(final String username) {
        return usuarioRepository.findByEmail(username).setSenha("{noop}admin");
    }

    @Transactional
    public T3WUsuarioEntity saveUsuario(final T3WUsuarioEntity usuario) {
        if (usuario.getSenha() == null || usuario.getSenha().isBlank()) {
            throw new IllegalArgumentException("Senha obrigatoria para criar usuario");
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public List<T3WUsuarioEntity> listPageable(final Pageable pageable) {
        return this.usuarioRepository.findAll(pageable);
    }
}
