package io.t3w.app.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(schema = "public", name = "estabelecimento")
public class T3WEstabelecimentoEntity extends T3WAbstractEntity<T3WEstabelecimentoEntity, Long> {

    private String razaoSocial;
    private T3WCorreiosEstadoEntity estado;

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public T3WCorreiosEstadoEntity getEstado() {
        return estado;
    }

    public void setEstado(T3WCorreiosEstadoEntity estado) {
        this.estado = estado;
    }
}
