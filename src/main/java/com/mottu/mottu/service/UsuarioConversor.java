package com.mottu.mottu.service;

import com.mottu.mottu.dto.UsuarioDTO;
import com.mottu.mottu.exception.UsuarioDTOException;
import com.mottu.mottu.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioConversor {

    public Usuario conversor(UsuarioDTO usuarioDTO) throws UsuarioDTOException {

        try {
            Usuario usuario = new Usuario();
            usuario.setUsername(usuarioDTO.getUsername());
            usuario.setPassword(usuarioDTO.getPassword());
            usuario.setEmail(usuarioDTO.getEmail());
            usuario.setTelefone(usuarioDTO.getTelefone());
            usuario.setTurno(usuarioDTO.getTurno());
            return usuario;
        } catch (Exception e) {
            throw new UsuarioDTOException(
                    "Falha no conversor do DTO de usuário para entidade " + usuarioDTO
            );
        }
    }
}
