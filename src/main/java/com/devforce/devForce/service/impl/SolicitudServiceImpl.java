package com.devforce.devForce.service.impl;

import com.devforce.devForce.model.dto.LicenciaDTO;
import com.devforce.devForce.model.dto.SolicitudDTO;
import com.devforce.devForce.model.dto.UsuarioDTO;
import com.devforce.devForce.model.entity.Solicitud;
import com.devforce.devForce.model.entity.Usuario;
import com.devforce.devForce.repository.SolicitudRepository;
import com.devforce.devForce.service.SolicitudService;
import com.devforce.devForce.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;



@Service
public class SolicitudServiceImpl implements SolicitudService {

    @Autowired
    SolicitudRepository solicitudRepository;
    @Autowired
    UsuarioService usuarioService;

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
        //TODO: SETEAR EL ESTADO EN PENDIENTE MENTOR
        solicitudNueva.setUsuario(usuario);

        solicitudRepository.save(solicitudNueva);
        return solicitudNueva;//TODO: CAMBIARLO POR UNA RESPUESTA
    }

    @Override
    public SolicitudDTO crearSolicitudDTO(Solicitud solicitud) {

        SolicitudDTO solicitudDTO = new SolicitudDTO();
        solicitudDTO.setId(solicitud.getId());
        solicitudDTO.setTipo(solicitud.getTipo());
        solicitudDTO.setDescripcion(solicitud.getDescripcion());
        solicitudDTO.setApruebaMentorID(solicitud.getApruebaMentorID());
        solicitudDTO.setApruebaAdminID(solicitud.getApruebaAdminID());
        solicitudDTO.setEstado(solicitud.getEstado());
        solicitudDTO.setArea(solicitud.getArea());
        solicitudDTO.setLink(solicitud.getLink());
        solicitudDTO.setTiempoSolicitado(solicitud.getTiempoSolicitado());
        solicitudDTO.setUsuarioDTO(usuarioService.crearUsuarioDTO(solicitud.getUsuario()));

        return solicitudDTO;
    }

    @Override
    public List<SolicitudDTO> getSolicitudesUsuario(Usuario usuario) {
        List<SolicitudDTO> solicitudes = usuario.getSolicitudes().stream().map(solicitud -> crearSolicitudDTO(solicitud)).collect(Collectors.toList());
        return solicitudes;
    }

    @Override
    public List<SolicitudDTO> getSolicitudesMentor(Usuario usuario) {
        List<SolicitudDTO> solicitudes = this.solicitudRepository.findByArea(usuario.getMentorArea()).stream().filter(solicitud -> !solicitud.getUsuario().equals(usuario)).map(solicitud -> crearSolicitudDTO(solicitud)).collect(Collectors.toList());
        return solicitudes;
    }
    @Override
    public List<SolicitudDTO> getSolicitudesAdmin(Usuario usuario) {
        List<SolicitudDTO> solicitudes = this.solicitudRepository.findAll().stream().filter(solicitud -> !solicitud.getUsuario().equals(usuario)).map(solicitud -> crearSolicitudDTO(solicitud)).collect(Collectors.toList());
        return solicitudes;
    }

    //TODO: TERMINAR SERVICIO ACTUALIZAR SOLICITUD
    @Override
    public Solicitud actualizarSolicitud(Solicitud solicitud, Usuario usuario) {
        if(solicitud.getEstado().equals("PENDIENTE-MENTOR") || solicitud.getEstado().equals("DEVUELTO-USER"))
        {
            if(solicitud.getTipo().isEmpty() || solicitud.getDescripcion().isEmpty() || solicitud.getArea().isEmpty() || solicitud.getTiempoSolicitado() <=0)
            {
                //MISSING DATA
            }
            if(solicitud.getTipo().equals("UDEMY") || solicitud.getTipo().equals("OTRA PLATAFORMA"))//TODO: CAMBIAR OTRAS PLATAFORMAS POR LA QUE VAYA
            {
                if(solicitud.getLink().isEmpty())
                {
                    //TENES QUE PONER EL LINK
                }
            }
            //TODO: AGREGAR VERIFICACION DE QUE EL QUE HAGA LA PETICION SEA EL USUARIO QUE ESTA AUTENTICADO

            Solicitud solicitudAnterior = this.solicitudRepository.findById(solicitud.getId()).orElse(null);

            solicitudAnterior.setTipo(solicitud.getTipo());
            solicitudAnterior.setDescripcion(solicitud.getDescripcion());
            solicitudAnterior.setArea(solicitud.getArea());
            solicitudAnterior.setLink(solicitud.getLink());
            solicitudAnterior.setTiempoSolicitado(solicitud.getTiempoSolicitado());

            solicitudRepository.save(solicitudAnterior);
        }
        else
        {
            //LA SOLICITUD NO SE ENCUENTRA EN PENDIENTE MENTOR O DEVUELTO USER
        }
        return null;//TODO: CAMBIARLO POR UNA RESPUESTA
    }

    @Override
    public List<SolicitudDTO> testSolicitudesDTO() {
        return this.solicitudRepository.findAll().stream().map(solicitud -> crearSolicitudDTO(solicitud)).collect(Collectors.toList());
    }
}
