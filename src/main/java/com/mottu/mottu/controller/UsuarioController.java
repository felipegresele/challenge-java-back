package com.mottu.mottu.controller;

import com.mottu.mottu.model.DTO.UsuarioDTO;
import com.mottu.mottu.model.Role;
import com.mottu.mottu.model.Usuario;
import com.mottu.mottu.repository.RoleRepository;
import com.mottu.mottu.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/")
    public String home() {
        return "home"; // home.html
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("showLoginModal", true);
        return "home";
    }

    @GetMapping("/cadastro")
    public String cadastroPage(Model model) {
        model.addAttribute("showCadastroModal", true);
        return "home";
    }

    @GetMapping("/usuarios/listar")
    @ResponseBody
    public ResponseEntity<?> getAll() {
        List<Usuario> listUsuarios = usuarioRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(listUsuarios);
    }

    @PostMapping("/usuarios/save")
    public String cadastrarUsuario(UsuarioDTO userDTO, Model model) {
        try {
            if (usuarioRepository.existsByEmail(userDTO.getEmail())) {
                model.addAttribute("errorMessage", "Erro: já existe um usuário com esse email");
                model.addAttribute("showCadastroModal", true);
                return "home";
            }

            Role role = roleRepository.findById(userDTO.getRoleId())
                    .orElseThrow(() -> new RuntimeException("Role não encontrada"));

            Usuario user = new Usuario();
            user.setEmail(userDTO.getEmail());
            user.setUsername(userDTO.getUsername());
            user.setPassword(userDTO.getPassword());
            user.setRole(role);

            usuarioRepository.save(user);
            model.addAttribute("successMessage", "Usuário cadastrado com sucesso!");
            return "home";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Erro: " + e.getMessage());
            model.addAttribute("showCadastroModal", true);
            return "home";
        }
    }

    @PostMapping("/usuarios/login")
    public String login(UsuarioDTO userDTO, Model model) {
        Usuario usuario = usuarioRepository.findByEmail(userDTO.getEmail());
        if (usuario == null || !usuario.getPassword().equals(userDTO.getPassword())) {
            model.addAttribute("errorMessage", "Usuário ou senha inválidos");
            model.addAttribute("showLoginModal", true);
            return "home";
        }

        model.addAttribute("usuario", usuario); // passa os dados do usuário para o dashboard
        return "dashboard"; // dashboard.html
    }

    @DeleteMapping("/usuarios/{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");
        }
        usuarioRepository.delete(usuario.get());
        return ResponseEntity.status(HttpStatus.OK).body("Usuario deletado");
    }

    @PutMapping("/usuarios/{id}")
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody UsuarioDTO dto) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");
        }
        var usuarioModel = usuario.get();
        BeanUtils.copyProperties(dto, usuarioModel);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioRepository.save(usuarioModel));
    }
}
