package com.mottu.mottu.controller;

import com.mottu.mottu.dto.UsuarioDTO;
import com.mottu.mottu.exception.UsuarioNotFoundException;
import com.mottu.mottu.model.Usuario;
import com.mottu.mottu.repository.UsuarioRepository;
import com.mottu.mottu.service.BuscarUsuarioService;
import com.mottu.mottu.service.CadastroUsuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BuscarUsuarioService serviceBuscar;

    @Autowired
    private CadastroUsuario cadastroUsuario;

    // Lista todos os usuários
    @GetMapping(path = "/usuario")
    public List<Usuario> listaUsuario() {
        return serviceBuscar.buscartTodosOsUsuarios();
    }

    // Busca um usuário por ID
    @GetMapping(path = "/usuario/id/{id}")
    public Usuario buscarUsuarioPorId(
            @PathVariable ( name= "id", required = true) Long id) throws UsuarioNotFoundException {
        return serviceBuscar.buscarUsuarioPorId(id);
    }

    // Salva um novo usuário
    @PostMapping(path = "/usuario/save")
    public void salvarUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        cadastroUsuario.cadastro(usuarioDTO);
    }

    // Deleta um usuário por ID
    @DeleteMapping(path = "/usuario/delete/{id}")
    public void deletarUsuario(@PathVariable(name = "id")Long id) throws UsuarioNotFoundException {
        serviceBuscar.deletarUsuarioPorId(id);
    }
}
