package com.devforce.devForce.model.dto;

import com.devforce.devForce.model.entity.Solicitud;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    // TODO: Hacer UsuarioDTO
    private long id;
    private String nombre;
    private String apellido;
    private String username;
    private String email;
    private String password;
    private String phone;
    private Boolean hasTeams;
    private String mentorArea;
    private List<Solicitud> solicitudes;
}
