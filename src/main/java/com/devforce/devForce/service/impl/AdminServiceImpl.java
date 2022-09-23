package com.devforce.devForce.service.impl;

import com.devforce.devForce.model.dto.LicenciaDTO;
import com.devforce.devForce.model.dto.RespuestaDTO;
import com.devforce.devForce.model.entity.Licencia;
import com.devforce.devForce.model.entity.Solicitud;
import com.devforce.devForce.model.entity.Usuario;
import com.devforce.devForce.repository.LicenciaRepository;
import com.devforce.devForce.repository.UsuarioRepository;
import com.devforce.devForce.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    LicenciaRepository licenciaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public List<Licencia> getLicencias() {
        return licenciaRepository.findAll();
    }

    @Override
    public List<LicenciaDTO> getLicenciasDTO() {
        return licenciaRepository.findAll().stream().map(licencia -> createLicenciaDTO(licencia)).collect(Collectors.toList());
    }


    private LicenciaDTO createLicenciaDTO(Licencia licencia) {
        LicenciaDTO licenciaDTO = new LicenciaDTO(licencia.getId(), licencia.getSerie(), licencia.getEstado(),
                licencia.getVencimiento(), licencia.getPlataforma(), licencia.getSolicitudes());
        return licenciaDTO;
    }
    @Override
    public ResponseEntity<RespuestaDTO> crearUsuario(Usuario usuario) {

        // TODO FALTA SECURITY SERVICE

        RespuestaDTO respuestaDTO;
        if ((usuarioRepository.findByUsername(usuario.getUsername()) != null) || (usuarioRepository.findByEmail(usuario.getEmail()) != null))
        {
            respuestaDTO = new RespuestaDTO(false, "El usuario ya existe", null);
            return new ResponseEntity<>(respuestaDTO, HttpStatus.FORBIDDEN);
        }
        usuarioRepository.save(usuario);

        respuestaDTO = new RespuestaDTO(true, "El usuario ha sido creado correctamente", usuario);
        return new ResponseEntity<>(respuestaDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<RespuestaDTO> asignarLicencia(Solicitud solicitud) {
        RespuestaDTO respuestaDTO;

        if (solicitud.getDescripcion().isEmpty() ||
        !(solicitud.getArea().equals("MENTOR")) ||
        !(solicitud.getEstado().equals("ACEPTADO")) ||
                !(solicitud.getLicencia().getEstado().equals("DISPONIBLE"))){
            respuestaDTO = new RespuestaDTO(false, "lICENCIA DENEGADA", null);
            return new ResponseEntity<RespuestaDTO>(respuestaDTO, HttpStatus.FORBIDDEN);
        }
        Licencia licencia = new Licencia();
        solicitud.setLicencia(licencia);
        respuestaDTO = new RespuestaDTO(true, "lICENCIA ACEPTADA", solicitud.getLicencia().getId());
        return new ResponseEntity<RespuestaDTO>(respuestaDTO, HttpStatus.ACCEPTED);
    }

}




