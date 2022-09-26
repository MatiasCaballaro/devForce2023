package com.devforce.devForce.service.impl;

import com.devforce.devForce.model.dto.RespuestaDTO;
import com.devforce.devForce.model.entity.Solicitud;
import com.devforce.devForce.repository.SolicitudRepository;
import com.devforce.devForce.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MentorServiceImpl implements MentorService {

    @Autowired
    SolicitudRepository solicitudRepository;

    public RespuestaDTO verificarEstado(Solicitud solicitud) {
        RespuestaDTO respuestaDTO =  new RespuestaDTO();
        if (!(solicitud.getEstado().equals("PENDIENTE-MENTOR"))){
            respuestaDTO.setOk(false);
            respuestaDTO.setMensaje("Las solicitud no se encuentra en PENDIENTE-MENTOR");
            respuestaDTO.setContenido(solicitud);
            return respuestaDTO;
        }
        // TODO: COMPLETAR QUE EL MENTOR SEA DEL AREA DETERMINADA Y SEA MENTOR Y NO USUARIO O ADMIN

        // TODO: COMPLETAR CON EL AUTENTICACION DEL MENTOR LOGEADO
        //solicitud.setApruebaMentorID();
        solicitudRepository.save(solicitud);

        respuestaDTO.setOk(true);
        respuestaDTO.setMensaje("La solicitud se encuentra en estado: " + solicitud.getEstado());
        respuestaDTO.setContenido(solicitud);
        return respuestaDTO;
    }

    @Override
    public ResponseEntity<RespuestaDTO> aceptarSolicitud(Solicitud solicitud, Integer dias) {

        RespuestaDTO respuestaDTO = verificarEstado(solicitud);

        if (!respuestaDTO.isOk()){
            return new ResponseEntity<>(respuestaDTO, HttpStatus.BAD_REQUEST);
        } else {
            if (solicitud.getTipo().equals("OTROS") || solicitud.getTipo().equals("ASESORAMIENTO")){
                solicitud.setEstado("ACEPTADO");
            }
            if (solicitud.getTipo().equals("UDEMY") || solicitud.getTipo().equals("OTRA-PLATAFORMA")){
                solicitud.setEstado("PENDIENTE-ADMIN");
                solicitud.setTiempoSolicitado(dias);
            }
            solicitudRepository.save(solicitud);
            respuestaDTO.setMensaje("La solicitud se encuentra en estado: " + solicitud.getEstado());
            return new ResponseEntity<>(respuestaDTO, HttpStatus.ACCEPTED);
        }
    }

    @Override
    public ResponseEntity<RespuestaDTO> rechazarSolicitud(Solicitud solicitud) {

        RespuestaDTO respuestaDTO = verificarEstado(solicitud);

        if (!respuestaDTO.isOk()){
            return new ResponseEntity<>(respuestaDTO, HttpStatus.BAD_REQUEST);
        } else {
            solicitud.setEstado("DENEGADO");
            solicitudRepository.save(solicitud);
            respuestaDTO.setMensaje("La solicitud se encuentra en estado: " + solicitud.getEstado());
            return new ResponseEntity<>(respuestaDTO, HttpStatus.ACCEPTED);
        }
    }

    @Override
    public ResponseEntity<RespuestaDTO> devolverSolicitud(Solicitud solicitud) {

        RespuestaDTO respuestaDTO = verificarEstado(solicitud);

        if (!respuestaDTO.isOk()){
            return new ResponseEntity<>(respuestaDTO, HttpStatus.BAD_REQUEST);
        } else {
            solicitud.setEstado("DEVUELTA-USUARIO");
            solicitudRepository.save(solicitud);
            respuestaDTO.setMensaje("La solicitud se encuentra en estado: " + solicitud.getEstado());
            return new ResponseEntity<>(respuestaDTO, HttpStatus.ACCEPTED);
        }
    }
}
