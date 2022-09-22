package com.devforce.devForce.controller;
import com.devforce.devForce.model.dto.SolicitudDTO;
import com.devforce.devForce.model.entity.Solicitud;
import com.devforce.devForce.model.entity.Usuario;
import com.devforce.devForce.repository.SolicitudRepository;
import com.devforce.devForce.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/solicitudes")
public class SolicitudController {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    SolicitudRepository solicitudRepository;

    @GetMapping("/solicitudesusuario")
    public List<SolicitudDTO> solicitudesUsuario (@RequestParam long id){
        //TODO: Cambiar requestParam por el authentication y cambiar el return por un servicio.
        //return usuarioRepository.findById(id).getSolicitudes().stream().map(SolicitudDTO::new).collect(Collectors.toList());
        return null;
    }

    @GetMapping("/solicitudesmentor")
    public List<SolicitudDTO> solicitudesMentor (@RequestParam long id){
        //TODO: Cambiar requestParam por el authentication y cambiar el solicitudDTO por un servicio.
        //return usuarioRepository.findById(id).getSolicitudes().stream().map(SolicitudDTO::new).collect(Collectors.toList());
        return null;
    }

    @GetMapping("/solicitudesadmin")
    public List<SolicitudDTO> solicitudesAdmin (@RequestParam long id){
        //TODO: Cambiar requestParam por el authentication y cambiar el solicitudDTO por un servicio.
        //return usuarioRepository.findById(id).getSolicitudes().stream().map(SolicitudDTO::new).collect(Collectors.toList());
        return null;
    }

    //@PostMapping("/nuevaSolicitud")

    @GetMapping("/test/solicitudes")
    public List<Solicitud> testSolicitudes() {
        return solicitudRepository.findAll();
    }
    //@GetMapping("/test/solicitudesDTO")
}
