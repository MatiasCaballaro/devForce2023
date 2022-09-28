package com.devforce.devForce.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @JsonIgnore
    @OneToMany(mappedBy="usuario", fetch=FetchType.EAGER)
    private List<Solicitud> solicitudes;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                //", password='" + password + '\'' +
                ", roles='" + roles + '\'' +
                //", phone='" + phone + '\'' +
                //", hasTeams=" + hasTeams +
                //", solicitudes=" + solicitudes.stream().map(s -> s.getSolicitudId()).collect(Collectors.toList()) +
                '}';
    }
}
