package com.devforce.devForce.service;

import com.devforce.devForce.model.dto.RespuestaDTO;
import com.devforce.devForce.model.entity.Usuario;
import org.springframework.http.ResponseEntity;

public interface UsuarioService {

    public ResponseEntity<RespuestaDTO> actualizarDatos(Usuario usuario);

}
