package io.t3w.app.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class T3WUsuarioEntity extends T3WAbstractEntity<T3WUsuarioEntity, Long> implements UserDetails {

    private String nome;
    private String email;
    private String senha;
    private String foto;
    private boolean ativo;

    public String getNome() {
        return nome;
    }

    public T3WUsuarioEntity setNome(final String nome) {
        this.nome = nome;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public T3WUsuarioEntity setEmail(final String email) {
        this.email = email;
        return this;
    }

    public String getSenha() {
        return senha;
    }

    public T3WUsuarioEntity setSenha(final String senha) {
        this.senha = senha;
        return this;
    }

    public String getFoto() {
        return foto;
    }

    public T3WUsuarioEntity setFoto(final String foto) {
        this.foto = foto;
        return this;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public T3WUsuarioEntity setAtivo(final boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return getSenha();
    }

    @Override
    public String getUsername() {
        return getNome();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isAtivo();
    }
}
