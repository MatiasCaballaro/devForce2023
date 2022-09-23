package com.devforce.devForce.controller;
import com.devforce.devForce.model.dto.LicenciaDTO;
import com.devforce.devForce.model.dto.RespuestaDTO;
import com.devforce.devForce.model.dto.SolicitudDTO;
import com.devforce.devForce.model.entity.Solicitud;
import com.devforce.devForce.model.entity.Usuario;
import com.devforce.devForce.service.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    AdminServiceImpl adminServiceImpl;
    @PostMapping("/registrarUsuario")
    public ResponseEntity<RespuestaDTO> crearUsuario(@RequestBody Usuario usuario) {
        return adminServiceImpl.crearUsuario(usuario);
    }

    @PostMapping("/asignarLicencia")
    public ResponseEntity<RespuestaDTO> asignarLicencia(@RequestBody Solicitud solicitud){
        return adminServiceImpl.asignarLicencia(solicitud);
    }

    @GetMapping("/licencias")
    public List<LicenciaDTO> obtenerLicencias (){
        return adminServiceImpl.getLicenciasDTO();
    }
}
