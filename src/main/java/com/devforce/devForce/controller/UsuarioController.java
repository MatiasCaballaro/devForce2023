package com.devforce.devForce.controller;
import com.devforce.devForce.model.dto.UsuarioDTO;
import com.devforce.devForce.model.entity.Usuario;
import com.devforce.devForce.repository.UsuarioRepository;
import com.devforce.devForce.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    UsuarioServiceImpl usuarioServiceImpl;

    @Autowired
    UsuarioRepository usuarioRepository;

    @PutMapping("/updatedatos")
    public ResponseEntity<Object> updateDatos(@RequestBody Usuario usuario) {
        return usuarioServiceImpl.updateDatos(usuario);
    }

    @GetMapping("/test/usuariosDTO")
    public List<UsuarioDTO> testUsuariosDTO() {
        // TODO : Cambiar el return por un servicio
        // return usuarioRepository.findAll();
        return null;
    }

    @GetMapping("/test/usuario")
    public Usuario testUsuario() {
        return usuarioRepository.findAll().stream().findAny().get();
    }

    @GetMapping("/test/usuarios")
    public List<Usuario> testUsuarios() {
        return usuarioRepository.findAll();
    }
}
