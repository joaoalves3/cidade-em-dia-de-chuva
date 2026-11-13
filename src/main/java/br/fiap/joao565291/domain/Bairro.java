package br.fiap.joao565291.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "GS316_BAIRRO")
@Getter
@Setter
public class Bairro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_BAIRRO")
    private Long id;

    @Column(name = "NOME", nullable = false, length = 80)
    private String nome;

    @Column(name = "ZONA", nullable = false, length = 10)
    private String zona;

    @Column(name = "AREA_KM2", nullable = false)
    private Double areaKm2;
}
