package com.devforce.devForce.service.impl;

import com.devforce.devForce.model.entity.Usuario;
import com.devforce.devForce.repository.UsuarioRepository;
import com.devforce.devForce.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Override
    public Usuario actualizarDatos(Usuario usuario) {

        Usuario usuarioSeleccionado = usuarioRepository.findByNombreAndApellido(usuario.getNombre(), usuario.getApellido());

        usuarioSeleccionado.setEmail(usuario.getEmail());
        usuarioSeleccionado.setPassword(usuario.getPassword());
        usuarioSeleccionado.setPhone(usuario.getPhone());
        usuarioSeleccionado.setHasTeams(usuario.getHasTeams());
        usuarioSeleccionado.setMentorArea(usuario.getMentorArea());

        return usuarioRepository.save(usuarioSeleccionado);
    }

}
