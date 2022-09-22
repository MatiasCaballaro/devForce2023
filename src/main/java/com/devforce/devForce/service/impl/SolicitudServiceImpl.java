package com.devforce.devForce.service.impl;

import com.devforce.devForce.model.dto.LicenciaDTO;
import com.devforce.devForce.model.dto.SolicitudDTO;
import com.devforce.devForce.model.dto.UsuarioDTO;
import com.devforce.devForce.model.entity.Solicitud;
import com.devforce.devForce.model.entity.Usuario;
import com.devforce.devForce.repository.SolicitudRepository;
import com.devforce.devForce.service.SolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SolicitudServiceImpl implements SolicitudService {

    @Autowired
    SolicitudRepository solicitudRepository;

    @Override
    public Solicitud crearSolicitud(Solicitud solicitud, Usuario usuario) {
        //TODO: IMPLEMENTAR LAS VERIFICACIONES
        //TODO: METER RESPONSE ENTITY

        Solicitud solicitudNueva = new Solicitud();
        solicitudNueva.setTipo(solicitud.getTipo());
        solicitudNueva.setDescripcion(solicitud.getDescripcion());
        solicitudNueva.setArea(solicitud.getArea());
        solicitudNueva.setLink(solicitud.getLink());
        solicitudNueva.setTiempoSolicitado(solicitud.getTiempoSolicitado());
        solicitudNueva.setUsuario(usuario);

        solicitudRepository.save(solicitudNueva);
        return solicitudNueva;
    }

    @Override
    public SolicitudDTO crearSolicitudDTO(Solicitud solicitud) {

        SolicitudDTO solicitudDTO = new SolicitudDTO();
        solicitudDTO.setTipo(solicitud.getTipo());
        solicitudDTO.setDescripcion(solicitud.getDescripcion());
        solicitudDTO.setApruebaMentorID(solicitud.getApruebaMentorID());
        solicitudDTO.setApruebaAdminID(solicitud.getApruebaAdminID());
        solicitudDTO.setEstado(solicitud.getEstado());
        solicitudDTO.setArea(solicitud.getArea());
        solicitudDTO.setLink(solicitud.getLink());
        solicitudDTO.setTiempoSolicitado(solicitud.getTiempoSolicitado());

        return solicitudDTO;
    }

    @Override
    public List<SolicitudDTO> getSolicitudesUsuario(Usuario usuario) {
        List<SolicitudDTO> solicitudes = usuario.getSolicitudes().stream().map(solicitud -> crearSolicitudDTO(solicitud)).collect(Collectors.toList());
        return solicitudes;
    }

    @Override
    public List<SolicitudDTO> getSolicitudesMentor(Usuario usuario) {
        List<SolicitudDTO> solicitudes = this.solicitudRepository.findAll().stream().filter(solicitud -> solicitud.getArea().equals(usuario.getMentorArea())).map(solicitud -> crearSolicitudDTO(solicitud)).collect(Collectors.toList());
        //TODO: HACER QUE EL MENTOR NO PUEDA VER SUS SOLICITUDES
        return solicitudes;
    }
    @Override
    public List<SolicitudDTO> getSolicitudesAdmin(Usuario usuario) {
        List<SolicitudDTO> solicitudes = this.solicitudRepository.findAll().stream().map(solicitud -> crearSolicitudDTO(solicitud)).collect(Collectors.toList());
        //TODO: HACER QUE EL ADMIN NO PUEDA VER SUS SOLICITUDES
        return solicitudes;
    }

    //TODO: TERMINAR SERVICIO ACTUALIZAR SOLICITUD
    @Override
    public Solicitud actualizarSolicitud(Solicitud solicitud, Usuario usuario) {
        if(solicitud.getEstado().equals("PENDIENTE-MENTOR") || solicitud.getEstado().equals("DEVUELTO-USER"))
        {

        }
        return null;
    }
}
