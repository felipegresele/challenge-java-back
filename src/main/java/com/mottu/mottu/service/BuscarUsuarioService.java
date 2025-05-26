package com.mottu.mottu.service;

import com.mottu.mottu.exception.UsuarioNotFoundException;
import com.mottu.mottu.model.Usuario;
import com.mottu.mottu.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BuscarUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> buscartTodosOsUsuarios() {
        List<Usuario> listUsuario = usuarioRepository.findAll();
        return listUsuario;
    }

    public Usuario buscarUsuarioPorId(Long id) throws UsuarioNotFoundException {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        Usuario usuario = null;

        if (!optionalUsuario.isPresent()) {
            throw new UsuarioNotFoundException(
                    "Usuário não foi encontrado pelo id: " + id
            );
        } else {
            usuario = optionalUsuario.get();
        }
        return usuario;
    }

    private Optional<Usuario> getOptional(Long id) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        return optionalUsuario;
    }

    public void deletarUsuarioPorId(Long id) throws UsuarioNotFoundException {
        Optional<Usuario> optionalUsuario = getOptional(id);
        if (!optionalUsuario.isPresent()) {
            throw new UsuarioNotFoundException(
                    "Usuário não foi deletado pelo id: " + id
            );
        } else {
            usuarioRepository.delete(optionalUsuario.get());
        }
    }



}
