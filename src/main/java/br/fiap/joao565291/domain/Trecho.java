package br.fiap.joao565291.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "GS316_TRECHO")
@Getter
@Setter
public class Trecho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TRECHO")
    private Long id;

    @Column(name = "LOGRADOURO", nullable = false, length = 120)
    private String logradouro;

    @Column(name = "TIPO_CALCADA", nullable = false, length = 20)
    private String tipoCalcada;

    @Column(name = "ALTITUDE", nullable = false)
    private Integer altitude;

    @ManyToOne
    @JoinColumn(name = "BAIRRO_ID", nullable = false)
    private Bairro bairro;
}
