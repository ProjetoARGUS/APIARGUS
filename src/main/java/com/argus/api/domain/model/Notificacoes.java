package com.argus.api.domain.model;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notificacoes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notificacoes {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String titulo;
    
    private String corpoDeTexto;
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate data; 
    
    @ManyToOne
    @JoinColumn(name = "id_comunicados")  
    private Comunicados comunicado;

    @ManyToOne
    @JoinColumn(name = "id_usuarios") 
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "id_reserva")  
    private Reservas reserva;
}
