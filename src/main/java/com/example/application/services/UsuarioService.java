package com.example.application.services;

import com.example.application.entities.Usuario;
import com.example.application.repository.usuario.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(final UsuarioRepository usuarioRepository, final PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public Usuario findUsuarioByUsername(final String username) {
       return usuarioRepository.findByUsername(username).orElse(null);
    }

    @Transactional
    public Usuario saveUsuario(final Usuario usuario) {
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
