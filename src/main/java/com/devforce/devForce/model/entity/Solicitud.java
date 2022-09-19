package com.devforce.devForce.model.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "solicitud")
public class Solicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name ="tipo", length = 25, nullable = false)
    private String tipo;

    @Column(name ="descripcion", length = 500, nullable = false)
    private String descripcion;

    @Column(name ="estado", length = 25, nullable = false)
    private String estado;

    @Column(name ="area", length = 50)
    private String area;

    @Column(name ="apruebaMentorID")
    private int apruebaMentorID;

    @Column(name ="apruebaAdminID")
    private int apruebaAdminID;

    @Column(name ="tiempoSolicitado", length = 25)
    private int tiempoSolicitado;


    // TODO Relacion con usuario ManyToONE

    // TODO Relacion con licencia ManyToONe

}
