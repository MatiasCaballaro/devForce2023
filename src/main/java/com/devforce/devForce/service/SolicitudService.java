package com.devforce.devForce.service;

import com.devforce.devForce.model.dto.SolicitudDTO;
import com.devforce.devForce.model.entity.Solicitud;
import com.devforce.devForce.model.entity.Usuario;

import java.util.List;

public interface SolicitudService {
    //TODO: REEMPLAZAR EL Usuario usuario POR AUTHENTICATION CUANDO LE INTEGREMOS EL SECURITY
    public Solicitud crearSolicitud(Solicitud solicitud, Usuario usuario);
    public SolicitudDTO crearSolicitudDTO(Solicitud solicitud);
    public List<SolicitudDTO> getSolicitudesUsuario(Usuario usuario);
    public List<SolicitudDTO> getSolicitudesMentor(Usuario usuario);
    public List<SolicitudDTO> getSolicitudesAdmin(Usuario usuario);
    public Solicitud actualizarSolicitud(Solicitud solicitud, Usuario usuario);
}