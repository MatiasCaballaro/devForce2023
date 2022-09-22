package com.devforce.devForce.service.impl;

import com.devforce.devForce.model.dto.SolicitudDTO;
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
    public List<SolicitudDTO> getSolicitudesUsuario(Usuario usuario) {
        List<SolicitudDTO> solicitudes = usuario.getSolicitudes().stream().map(SolicitudDTO::new).collect(Collectors.toList());
        return solicitudes;
    }

    @Override
    public List<SolicitudDTO> getSolicitudesMentor(Usuario usuario) {
        List<SolicitudDTO> solicitudes = this.solicitudRepository.findAll().stream().filter()
        return solicitudes;
    }

    @Override
    public List<SolicitudDTO> getSolicitudesAdmin(Usuario usuario) {
        List<SolicitudDTO> solicitudes = this.solicitudRepository.findAll().stream().map(SolicitudDTO::new).collect(Collectors.toList());
        return solicitudes;
    }
}
