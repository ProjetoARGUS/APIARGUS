package com.argus.api.domain.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comunicados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comunicados {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensagem;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuarios usuarios;

    @ManyToOne
    @JoinColumn(name = "condominio_id", nullable = false)
    private Condominio condominio;
}
