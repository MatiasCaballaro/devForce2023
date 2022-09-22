package com.devforce.devForce.service;

import com.devforce.devForce.model.dto.SolicitudDTO;
import com.devforce.devForce.model.entity.Usuario;

import java.util.List;

public interface SolicitudService {
    List<SolicitudDTO> getSolicitudesUsuario(Usuario usuario); //TODO: comprobar que sean solicitudes de usuario logeado
    List<SolicitudDTO> getSolicitudesMentor(Usuario usuario);
    List<SolicitudDTO> getSolicitudesAdmin(Usuario usuario);
}

//api