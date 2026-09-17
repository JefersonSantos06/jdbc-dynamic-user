package io.t3w.app.entities;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.beans.Transient;
import java.util.Collection;
import java.util.List;

@Table(schema = "public", name = "usuario")
public class T3WUsuarioEntity extends T3WAbstractEntity<T3WUsuarioEntity, Long> implements UserDetails {

    private String nome;
    private String email;
    private String senha;
    private boolean ativo;

    @Column("id_estabelecimento") // Troque para o nome correto da chave estrangeira caso seja diferente
    private AggregateReference<T3WEstabelecimentoEntity, Long> estabelecimento;

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

    public boolean isAtivo() {
        return ativo;
    }

    public T3WUsuarioEntity setAtivo(final boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public AggregateReference<T3WEstabelecimentoEntity, Long> getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(AggregateReference<T3WEstabelecimentoEntity, Long> estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return getSenha();
    }

    @Override
    @Transient
    public String getUsername() {
        return getEmail();
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return isAtivo();
    }
}
