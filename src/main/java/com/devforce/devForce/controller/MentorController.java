package com.devforce.devForce.controller;
import com.devforce.devForce.model.dto.RespuestaDTO;
import com.devforce.devForce.model.entity.Solicitud;
import com.devforce.devForce.service.impl.MentorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mentor")
public class MentorController {
    @Autowired
    MentorServiceImpl mentorServiceImpl;
    @PutMapping("/aceptarSolicitud")
    ResponseEntity<RespuestaDTO> aceptarSolicitud (@RequestParam Solicitud solicitud, @RequestParam Integer dias){
        return mentorServiceImpl.aceptarSolicitud(solicitud, dias);
    }

    @PutMapping("/rechazarSolicitud")
    ResponseEntity<RespuestaDTO> rechazarSolicitud (@RequestBody Solicitud solicitud){
        return mentorServiceImpl.rechazarSolicitud(solicitud);
    }

   @PutMapping("/devolverSolicitud")
    ResponseEntity<RespuestaDTO> devolverSolicitud (@RequestBody Solicitud solicitud){
        return mentorServiceImpl.devolverSolicitud(solicitud);
    }

}