package com.mottu.mottu.controller.thymeleaf;

import com.mottu.mottu.model.DTO.UsuarioDTO;
import com.mottu.mottu.model.Role;
import com.mottu.mottu.model.Usuario;
import com.mottu.mottu.repository.RoleRepository;
import com.mottu.mottu.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioThymeleafController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/") // home
    public String home() {
        return "home";
    }

    @GetMapping("/login") // http://localhost:8080/login
    public String loginPage(Model model) {
        model.addAttribute("showLoginModal", true);
        return "home";
    }

    @GetMapping("/cadastro") // http://localhost:8080/cadastro
    public String cadastroPage(Model model) {
        model.addAttribute("showCadastroModal", true);
        return "home";
    }

    @PostMapping("/save") // cadastro
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

    @PostMapping("/login") // login
    public String login(UsuarioDTO userDTO, Model model) {
        Usuario usuario = usuarioRepository.findByEmail(userDTO.getEmail());
        if (usuario == null || !usuario.getPassword().equals(userDTO.getPassword())) {
            model.addAttribute("errorMessage", "Usuário ou senha inválidos");
            model.addAttribute("showLoginModal", true);
            return "home";
        }

        model.addAttribute("usuario", usuario);
        return "dashboard"; // dashboard após login
    }
}
