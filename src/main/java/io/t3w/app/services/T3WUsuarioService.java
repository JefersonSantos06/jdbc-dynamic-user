package io.t3w.app.services;

import io.t3w.app.entities.T3WUsuarioEntity;
import io.t3w.app.repository.T3WUsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class T3WUsuarioService {

    private final T3WUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public T3WUsuarioService(final T3WUsuarioRepository usuarioRepository, final PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public T3WUsuarioEntity findUsuarioByUsername(final String username) {
       return usuarioRepository.findByUsername(username).orElse(null);
    }

    @Transactional
    public T3WUsuarioEntity saveUsuario(final T3WUsuarioEntity usuario) {
        if (usuario.getId() == null) {
            if (usuario.getSenha() == null || usuario.getSenha().isBlank()) {
                throw new IllegalArgumentException("Senha obrigatoria para criar usuario");
            }
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            usuarioRepository.insert(usuario);
        } else {
            if (usuario.getSenha() != null && !usuario.getSenha().isBlank() && !usuario.getSenha().startsWith("{")) {
                usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            }
            usuarioRepository.update(usuario);
        }
        return usuarioRepository.findById(usuario.getId()).orElseThrow();
    }
}
