package com.mottu.mottu.service;

import com.mottu.mottu.dto.UsuarioDTO;
import com.mottu.mottu.exception.UsuarioDTOException;
import com.mottu.mottu.model.Usuario;
import com.mottu.mottu.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroUsuario {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioConversor service;

    public void cadastro(UsuarioDTO usuarioDTO) {

        try {
            Usuario usuario = service.conversor(usuarioDTO);
            usuarioRepository.saveAndFlush(usuario);
        } catch (UsuarioDTOException e) {
            e.printStackTrace();
        }
    }
}
