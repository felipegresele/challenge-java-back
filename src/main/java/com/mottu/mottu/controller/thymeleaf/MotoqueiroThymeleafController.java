package com.mottu.mottu.controller.thymeleaf;

import com.mottu.mottu.model.DTO.MotoqueiroDTO;
import com.mottu.mottu.model.Motoqueiro;
import com.mottu.mottu.model.RoleName;
import com.mottu.mottu.model.Usuario;
import com.mottu.mottu.service.MotoqueiroMapper;
import com.mottu.mottu.service.MotoqueiroService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/motoqueiros-view")
public class MotoqueiroThymeleafController {

    @Autowired
    private MotoqueiroService motoqueiroService;

    private boolean checarPermissaoAdmin(Usuario usuario, Model model) {
        if (!usuario.getRole().getNome().equals(RoleName.ADMIN)) {
            model.addAttribute("mensagemErro", "Você não tem permissão para esta ação");
            return false;
        }
        return true;
    }

    // LISTAR MOTOQUEIROS
    @GetMapping("/todos")
    public String listarMotoqueiros(Model model) {
        List<Motoqueiro> motoqueiros = motoqueiroService.listarTodos();
        model.addAttribute("motoqueiros", motoqueiros);

        if (motoqueiros.isEmpty()) {
            model.addAttribute("mensagem", "Nenhum motoqueiro cadastrado.");
        }

        return "motoqueiro/listar";
    }

    // FORMULÁRIO ADICIONAR
    @GetMapping("/adicionar")
    public String mostrarFormularioAdicionar(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";

        model.addAttribute("motoqueiroDTO", new MotoqueiroDTO());
        checarPermissaoAdmin(usuario, model);
        return "motoqueiro/adicionar";
    }

    // ADICIONAR MOTOQUEIRO
    @PostMapping("/adicionar")
    public String adicionarMotoqueiro(@Valid MotoqueiroDTO dto, Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";

        if (!checarPermissaoAdmin(usuario, model)) {
            model.addAttribute("motoqueiroDTO", dto);
            return "motoqueiro/adicionar";
        }

        motoqueiroService.criarMotoqueiro(dto);
        return "redirect:/motoqueiros-view/todos";
    }

    // FORMULÁRIO EDITAR
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";

        Optional<Motoqueiro> optionalMotoqueiro = motoqueiroService.buscarPorId(id);
        if (optionalMotoqueiro.isEmpty()) {
            model.addAttribute("mensagemErro", "Motoqueiro não encontrado.");
            return "motoqueiro/listar";
        }

        Motoqueiro motoqueiro = optionalMotoqueiro.get();
        model.addAttribute("motoqueiroDTO", MotoqueiroMapper.toDTO(motoqueiro));
        model.addAttribute("motoqueiroId", id);
        checarPermissaoAdmin(usuario, model);

        return "motoqueiro/editar";
    }

    // EDITAR MOTOQUEIRO
    @PostMapping("/editar/{id}")
    public String editarMotoqueiro(@PathVariable Long id, @Valid MotoqueiroDTO dto, Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";

        if (!checarPermissaoAdmin(usuario, model)) {
            model.addAttribute("motoqueiroDTO", dto);
            model.addAttribute("motoqueiroId", id);
            return "motoqueiro/editar";
        }

        motoqueiroService.atualizarMotoqueiro(id, dto);
        return "redirect:/motoqueiros-view/todos";
    }

    // FORMULÁRIO EXCLUIR
    @GetMapping("/excluir/{id}")
    public String mostrarFormularioExcluir(@PathVariable Long id, Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";

        Motoqueiro motoqueiro = motoqueiroService.buscarPorId(id).orElse(null);
        if (motoqueiro == null) {
            model.addAttribute("mensagemErro", "Motoqueiro não encontrado.");
            return "motoqueiro/listar";
        }

        model.addAttribute("motoqueiro", motoqueiro);
        checarPermissaoAdmin(usuario, model);

        return "motoqueiro/excluir";
    }

    // EXCLUIR MOTOQUEIRO
    @PostMapping("/excluir/{id}")
    public String excluirMotoqueiro(@PathVariable Long id, Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";

        if (!checarPermissaoAdmin(usuario, model)) {
            motoqueiroService.buscarPorId(id).ifPresent(m -> model.addAttribute("motoqueiro", m));
            return "motoqueiro/excluir";
        }

        try {
            motoqueiroService.excluirMotoqueiro(id);
        } catch (IllegalStateException e) {
            model.addAttribute("mensagemErro", e.getMessage());
            motoqueiroService.buscarPorId(id).ifPresent(m -> model.addAttribute("motoqueiro", m));
            return "motoqueiro/excluir";
        }

        return "redirect:/motoqueiros-view/todos";
    }
}
