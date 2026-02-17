package com.example.application.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class Usuario extends AbstractEntity<Long> implements UserDetails {

    private String nome;
    private String email;
    private String senha;
    private String foto;
    private Boolean ativo;

    public String getNome() {
        return nome;
    }

    public Usuario setNome(final String nome) {
        this.nome = nome;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Usuario setEmail(final String email) {
        this.email = email;
        return this;
    }

    public String getSenha() {
        return senha;
    }

    public Usuario setSenha(final String senha) {
        this.senha = senha;
        return this;
    }

    public String getFoto() {
        return foto;
    }

    public Usuario setFoto(final String foto) {
        this.foto = foto;
        return this;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public Usuario setAtivo(final Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return nome;
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
        return ativo == null || ativo;
    }
}
