package com.devforce.devForce.service.impl;

import com.devforce.devForce.model.dto.LicenciaDTO;
import com.devforce.devForce.model.dto.RespuestaDTO;
import com.devforce.devForce.model.dto.authRequestDTO.RegistroDTO;
import com.devforce.devForce.model.entity.Licencia;
import com.devforce.devForce.model.entity.Role;
import com.devforce.devForce.model.entity.Solicitud;
import com.devforce.devForce.model.entity.Usuario;
import com.devforce.devForce.repository.LicenciaRepository;
import com.devforce.devForce.repository.SolicitudRepository;
import com.devforce.devForce.repository.UsuarioRepository;
import com.devforce.devForce.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    LicenciaRepository licenciaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    SolicitudRepository solicitudRepository;

    @Autowired
    SolicitudServiceImpl solicitudService;

    @Autowired
    PasswordEncoder passwordEncoder;

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
                licencia.getVencimiento(), licencia.getPlataforma(), licencia.getSolicitudes().stream()
                .map(solicitud -> solicitudService.crearSolicitudDTO(solicitud)).collect(Collectors.toList()));
        return licenciaDTO;
    }
    @Override
    public ResponseEntity<RespuestaDTO> crearUsuario(RegistroDTO registroDTO) {

        // TODO FALTA SECURITY SERVICE

        RespuestaDTO respuestaDTO;
        if (usuarioRepository.findByUsername(registroDTO.getUsername()) != null)
        {
            respuestaDTO = new RespuestaDTO(false, "El usuario ya existe", null);
            return new ResponseEntity<>(respuestaDTO, HttpStatus.FORBIDDEN);
        }

        if (usuarioRepository.findByEmail(registroDTO.getEmail()) != null)
        {
            respuestaDTO = new RespuestaDTO(false, "El Mail ya esta en uso", null);
            return new ResponseEntity<>(respuestaDTO, HttpStatus.FORBIDDEN);
        }

        Usuario usuario = new Usuario(registroDTO.getNombre(), registroDTO.getApellido(), registroDTO.getUsername(),
                registroDTO.getEmail(),passwordEncoder.encode(registroDTO.getPassword()), registroDTO.getPhone(),
                registroDTO.getHasTeams(),registroDTO.getMentorArea());

        Set<String> rolesUsuarioString = registroDTO.getRole();
        Set<Role> rolesUsuario = new HashSet<>();

        usuarioRepository.save(usuario);

        respuestaDTO = new RespuestaDTO(true, "El usuario ha sido creado correctamente", usuario);
        return new ResponseEntity<>(respuestaDTO, HttpStatus.CREATED);
    }

    //Prueba
    @Override
    public ResponseEntity<RespuestaDTO> asignarLicencia(Solicitud solicitud) {
        RespuestaDTO respuestaDTO;

        if (solicitud.getEstado().equals("PENDIENTE-MENTOR") == false) {
            respuestaDTO = new RespuestaDTO(false, "lICENCIA DENEGADA. No fue aprobada", null);
            return new ResponseEntity<RespuestaDTO>(respuestaDTO, HttpStatus.FORBIDDEN);
        }

        // Esta verificacion se podra utilizar cuando se hayan asignado los ID de los mentores a las solicitudes:
        /*
        if (solicitud.getArea().equals(usuarioRepository.findById(solicitud.getApruebaMentorID()).getMentorArea()) == false) {
            respuestaDTO = new RespuestaDTO(false, "lICENCIA DENEGADA. El mentor no pertenece al area de la licencia", null);
            return new ResponseEntity<RespuestaDTO>(respuestaDTO, HttpStatus.FORBIDDEN);
        }
         */


        int solicitudesSimilares = solicitudRepository.findByUsuarioAndTipoAndEstado(solicitud.getUsuario(), solicitud.getTipo(), "ACEPTADA").size();

        if ( solicitudesSimilares == 0) {
            return asignarNuevaLicencia(solicitud);
        } else {
            List<Solicitud> solicitudesSimilaresAceptadas = solicitudRepository.findByUsuarioAndTipoAndEstado(solicitud.getUsuario(),
                    solicitud.getTipo(), "ACEPTADA");
            int prueba = 1;
            return renovarLicencia(solicitud, solicitudesSimilaresAceptadas.get(0));
        }
    }

    private  ResponseEntity<RespuestaDTO> asignarNuevaLicencia(Solicitud solicitud){

        RespuestaDTO respuestaDTO;

        if (licenciaRepository.findByPlataformaAndEstado(solicitud.getTipo(),"DISPONIBLE").size() == 0) {
            respuestaDTO = new RespuestaDTO(false, "lICENCIA DENEGADA. No hay licencias disponibles por el momento.", null);
            return new ResponseEntity<RespuestaDTO>(respuestaDTO, HttpStatus.FORBIDDEN);
        } else {

            Licencia licencia = licenciaRepository.findByPlataformaAndEstado(solicitud.getTipo(), "DISPONIBLE").get(1);
            licencia.setVencimiento(LocalDate.now().plusDays(/*solicitud.getTiempoSolicitado()*/1));
            solicitud.setLicencia(licencia);
            licencia.setEstado("ASIGNADA");
            solicitud.setEstado("ACEPTADA");
            solicitudRepository.save(solicitud);
            licenciaRepository.save(licencia);

            respuestaDTO = new RespuestaDTO(true, "lICENCIA ASIGNADA", solicitud.getLicencia().getId());
            return new ResponseEntity<RespuestaDTO>(respuestaDTO, HttpStatus.ACCEPTED);
        }
    }

    private  ResponseEntity<RespuestaDTO> renovarLicencia(Solicitud solicitud, Solicitud solicitudAnterior){

        RespuestaDTO respuestaDTO;
        if (solicitudAnterior.getLicencia().getVencimiento().isBefore(LocalDate.now())) {
            return asignarNuevaLicencia(solicitud);
        } else {

            if(solicitud.getTiempoSolicitado() == 0){
                solicitudAnterior.getLicencia().setVencimiento(solicitudAnterior.getLicencia().getVencimiento().
                        plusDays(25));
            } else {
                solicitudAnterior.getLicencia().setVencimiento(solicitudAnterior.getLicencia().getVencimiento().
                        plusDays(solicitud.getTiempoSolicitado()));
            }

            solicitud.setLicencia(solicitudAnterior.getLicencia());
            solicitud.setEstado("ACEPTADA");
            solicitudRepository.save(solicitud);
            licenciaRepository.save(solicitudAnterior.getLicencia());

            respuestaDTO = new RespuestaDTO(true, "lICENCIA RENOVADA", solicitud.getLicencia().getId());
            return new ResponseEntity<RespuestaDTO>(respuestaDTO, HttpStatus.ACCEPTED);
        }
    }
}




