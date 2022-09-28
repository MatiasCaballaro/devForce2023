package com.devforce.devForce.service;

import com.devforce.devForce.model.dto.RespuestaDTO;
import com.devforce.devForce.model.dto.authRequestDTO.LoginRequest;
import com.devforce.devForce.model.entity.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import com.devforce.devForce.security.services.UserDetailsImpl;

import javax.validation.Valid;

public interface UsuarioService {

    public ResponseEntity<Object> updateDatos(Usuario usuario);

    public UserDetailsImpl obtenerUsuario();

    public ResponseEntity<RespuestaDTO> login(@Valid @RequestBody LoginRequest loginRequest);

}
