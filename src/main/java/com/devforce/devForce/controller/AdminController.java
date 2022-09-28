package com.devforce.devForce.controller;
import com.devforce.devForce.model.dto.SolicitudDTO;
import com.devforce.devForce.model.entity.Licencia;
import com.devforce.devForce.repository.LicenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {


    @Autowired
    LicenciaRepository licenciaRepository;

//    @PostMapping("/registrarUsuario")
//    @PostMapping("/asignarLicencia")
    @GetMapping("/licencias")
    public List<Licencia> obtenerLicencias (){
        //TODO: Cambiar requestParam por el authentication y cambiar el return por un servicio.
        return licenciaRepository.findAll().stream().collect(Collectors.toList());
        //return usuarioRepository.findById(id).getSolicitudes().stream().map(SolicitudDTO::new).collect(Collectors.toList());

    }
}
