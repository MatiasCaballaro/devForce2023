package com.devforce.devForce.controller;
import com.devforce.devForce.model.dto.SolicitudDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/api/admin")
public class AdminController {
//    @PostMapping("/registrarUsuario")
//    @PostMapping("/asignarLicencia")
    @GetMapping("/licencias")
    public List<SolicitudDTO> obtenerLicencias (@RequestParam long id){
        //TODO: Cambiar requestParam por el authentication y cambiar el return por un servicio.
        //return usuarioRepository.findById(id).getSolicitudes().stream().map(SolicitudDTO::new).collect(Collectors.toList());
        return null;
    }
}
