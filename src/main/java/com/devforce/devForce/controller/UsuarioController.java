package com.devforce.devForce.controller;
import com.devforce.devForce.model.entity.Usuario;
import com.devforce.devForce.repository.UsuarioRepository;
import com.devforce.devForce.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    UsuarioServiceImpl usuarioServiceImpl;
    @Autowired
    UsuarioRepository usuariosRepository;

    @PutMapping("/updatedatos")
    public Usuario updateDatos(@RequestBody Usuario usuario) {
        return usuarioServiceImpl.actualizarDatos(usuario);
    }

    @GetMapping("/test/usuarios")
    public List<Usuario> testUsuarios() {
        return usuariosRepository.findAll();
    }
}
