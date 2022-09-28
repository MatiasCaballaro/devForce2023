package com.devforce.devForce.service.impl;

import com.devforce.devForce.model.dto.LicenciaDTO;
import com.devforce.devForce.model.dto.RespuestaDTO;
import com.devforce.devForce.model.entity.Licencia;
import com.devforce.devForce.model.entity.Solicitud;
import com.devforce.devForce.model.entity.Usuario;
import com.devforce.devForce.repository.LicenciaRepository;
import com.devforce.devForce.repository.SolicitudRepository;
import com.devforce.devForce.repository.UsuarioRepository;
import com.devforce.devForce.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
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
                licencia.getVencimiento(), licencia.getPlataforma(), licencia.getSolicitudes().stream().map(solicitud -> solicitudService.crearSolicitudDTO(solicitud)).collect(Collectors.toList()));
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

    //Prueba
    @Override
    public ResponseEntity<RespuestaDTO> asignarLicencia(Solicitud solicitud) {
        RespuestaDTO respuestaDTO;

        if (solicitud.getEstado().equals("APROBADO") == false) {
            respuestaDTO = new RespuestaDTO(false, "lICENCIA DENEGADA. No fue aprobada", null);
            return new ResponseEntity<RespuestaDTO>(respuestaDTO, HttpStatus.FORBIDDEN);
        }

        if (solicitud.getArea().equals(usuarioRepository.findById(solicitud.getApruebaMentorID()).getMentorArea()) == false) {
            respuestaDTO = new RespuestaDTO(false, "lICENCIA DENEGADA. El mentor no pertenece al area de la licencia", null);
            return new ResponseEntity<RespuestaDTO>(respuestaDTO, HttpStatus.FORBIDDEN);
        }

        if (solicitudRepository.findByUsuarioAndTipoAndEstado(solicitud.getUsuario(), solicitud.getTipo(), solicitud.getEstado()) == null) {
            return asignarNuevaLicencia(solicitud);
        } else {

            return renovarLicencia(solicitud, solicitudRepository.findByUsuarioAndTipoAndEstado(solicitud.getUsuario(),
                    solicitud.getTipo(), solicitud.getEstado()).get(1));
        }
    }

    private  ResponseEntity<RespuestaDTO> asignarNuevaLicencia(Solicitud solicitud){

        RespuestaDTO respuestaDTO;

        if (licenciaRepository.findByPlataformaAndEstado(solicitud.getTipo(),"DISPONIBLE").size() == 0) {
            respuestaDTO = new RespuestaDTO(false, "lICENCIA DENEGADA. No hay licencias disponibles por el momento.", null);
            return new ResponseEntity<RespuestaDTO>(respuestaDTO, HttpStatus.FORBIDDEN);
        }

        Licencia licencia = licenciaRepository.findByPlataformaAndEstado(solicitud.getTipo(),"DISPONIBLE").get(1);
        licencia.setVencimiento(LocalDate.now().plusDays(solicitud.getTiempoSolicitado()));
        solicitud.setLicencia(licencia);
        licencia.setEstado("ASIGNADA");
        solicitudRepository.save(solicitud);
        licenciaRepository.save(licencia);

        respuestaDTO = new RespuestaDTO(true, "lICENCIA ASIGNADA", solicitud.getLicencia().getId());
        return new ResponseEntity<RespuestaDTO>(respuestaDTO, HttpStatus.ACCEPTED);

    }

    private  ResponseEntity<RespuestaDTO> renovarLicencia(Solicitud solicitud, Solicitud solicitudAnterior){

        RespuestaDTO respuestaDTO;
        if (solicitudAnterior.getLicencia().getVencimiento().isAfter(LocalDate.now())) {

            return asignarLicencia(solicitud);

        } else {
            solicitudAnterior.getLicencia().setVencimiento(solicitudAnterior.getLicencia().getVencimiento().
                    plusDays(solicitud.getTiempoSolicitado()));

            solicitud.setLicencia(solicitudAnterior.getLicencia());
            solicitudRepository.save(solicitud);
            licenciaRepository.save(solicitudAnterior.getLicencia());

            respuestaDTO = new RespuestaDTO(true, "lICENCIA RENOVADA", solicitud.getLicencia().getId());
            return new ResponseEntity<RespuestaDTO>(respuestaDTO, HttpStatus.ACCEPTED);
        }
    }
}




