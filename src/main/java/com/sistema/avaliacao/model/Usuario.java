package com.sistema.avaliacao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String nome;

    private String senha;

    private String emailInstitucional;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_funcao")
    @JsonIgnore
    private Funcao funcao;

    private LocalDate dataNascimento;
    private String imagem;
    private String descricao;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (funcao == null || funcao.getPerfil() == null) {
            return List.of();
        }
        return List.of(new SimpleGrantedAuthority(funcao.getPerfil().name()));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public abstract String getUsername();

}