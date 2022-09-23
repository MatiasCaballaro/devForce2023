package com.devforce.devForce.controller;
import com.devforce.devForce.model.dto.SolicitudDTO;
import com.devforce.devForce.model.dto.UsuarioDTO;
import com.devforce.devForce.model.entity.Solicitud;
import com.devforce.devForce.model.entity.Usuario;
import com.devforce.devForce.repository.SolicitudRepository;
import com.devforce.devForce.repository.UsuarioRepository;
import com.devforce.devForce.service.SolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SolicitudController {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    SolicitudRepository solicitudRepository;
    @Autowired
    SolicitudService solicitudService;

    @GetMapping("/solicitudesusuario")
    public List<SolicitudDTO> solicitudesUsuario (@RequestBody Usuario usuario){
        //TODO: Cambiar requestBody por el authentication
        return solicitudService.getSolicitudesUsuario(usuario);
    }

    @GetMapping("/solicitudesmentor")
    public List<SolicitudDTO> solicitudesMentor (@RequestBody Usuario usuario){
        //TODO: Cambiar requestBody por el authentication
        return solicitudService.getSolicitudesMentor(usuario);
    }

    @GetMapping("/solicitudesadmin")
    public List<SolicitudDTO> solicitudesAdmin (@RequestBody Usuario usuario){
        //TODO: Cambiar requestBody por el authentication
        return solicitudService.getSolicitudesAdmin(usuario);
    }

    @PostMapping("/nuevaSolicitud")
    public Solicitud crearSolicitud(Solicitud solicitud, Usuario usuario){
        return solicitudService.crearSolicitud(solicitud, usuario);
    }

    @GetMapping("/test/solicitudes")
    public List<Solicitud> testSolicitudes() {
        return solicitudRepository.findAll();
    }
    @GetMapping("/test/solicitudesDTO")
    public List<SolicitudDTO> testSolicitudesDTO() {
        return null;
    }
}
