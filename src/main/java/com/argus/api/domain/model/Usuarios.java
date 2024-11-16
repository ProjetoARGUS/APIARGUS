package com.argus.api.domain.model;

import com.argus.api.domain.TipoDoUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String nome;

    @Column(length = 50, nullable = false, unique = true)
    private String cpf;

    @Column(length = 100, nullable = false)
    private String senha;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoDoUsuario tipoDoUsuario;

    @Column(nullable = true)
    private Character bloco;

    @Column(nullable = true)
    private Integer apartamento;

    @ManyToOne
    @JoinColumn(name = "condominio_id", nullable = true)
    private Condominio condominio;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
