package com.devforce.devForce.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "phone")
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name ="name", length = 50, nullable = false)
    private String nombre;

    @Column(name ="lastname", length = 50, nullable = false)
    private String apellido;

    @Column(name ="username", length = 50, nullable = false)
    private String username;

    @Column(name ="email", length = 100)
    private String email;

    @Column(name ="password", length = 255, nullable = false)
    private String password;

    @Column(name ="phone", length = 50)
    private String phone;

    @Column(name ="hasTeams")
    private Boolean hasTeams;

    @Column(name ="mentorArea", length = 25)
    private String mentorArea;

    @OneToMany(mappedBy="usuario", fetch=FetchType.EAGER)
    private List<Solicitud> solicitudes;

    // TODO Hacer los roles

}
