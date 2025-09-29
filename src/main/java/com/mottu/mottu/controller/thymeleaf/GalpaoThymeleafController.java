package com.mottu.mottu.controller.thymeleaf;

import com.mottu.mottu.model.DTO.GalpaoDTO;
import com.mottu.mottu.model.Galpao;
import com.mottu.mottu.model.RoleName;
import com.mottu.mottu.model.Usuario;
import com.mottu.mottu.repository.GalpaoRepository;
import com.mottu.mottu.service.GalpaoMapper;
import com.mottu.mottu.service.GalpaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/galpoes-view")
public class GalpaoThymeleafController {

    @Autowired
    private GalpaoService galpaoService;

    @Autowired
    private GalpaoRepository galpaoRepository;

    private boolean checarPermissaoAdmin(Usuario usuario, Model model) {
        if (!usuario.getRole().getNome().equals(RoleName.ADMIN)) {
            model.addAttribute("mensagemErro", "Você não tem permissão para esta ação");
            return false;
        }
        return true;
    }

    // LISTAR GALPÕES (TODOS VEEM)
    @GetMapping("/todos")
    public String listarGalpoes(Model model) {
        List<Galpao> galpoes = galpaoRepository.findAll();
        model.addAttribute("galpoes", galpoes);

        if (galpoes.isEmpty()) {
            model.addAttribute("mensagem", "Nenhum galpão cadastrado.");
        }

        return "galpao/listar";
    }

    // FORMULÁRIO ADICIONAR
    @GetMapping("/adicionar")
    public String mostrarFormularioAdicionar(Model model,
                                             @SessionAttribute("usuarioLogado") Usuario usuario) {
        model.addAttribute("galpaoDTO", new GalpaoDTO());
        checarPermissaoAdmin(usuario, model);
        return "galpao/adicionar";
    }

    // ADICIONAR GALPÃO
    @PostMapping("/adicionar")
    public String adicionarGalpao(@SessionAttribute("usuarioLogado") Usuario usuario,
                                  @Valid GalpaoDTO dto,
                                  Model model) {

        if (!checarPermissaoAdmin(usuario, model)) {
            model.addAttribute("galpaoDTO", dto);
            return "galpao/adicionar";
        }

        galpaoService.salvar(dto);
        return "redirect:/galpoes-view/todos";
    }

    // FORMULÁRIO EDITAR
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id,
                                          Model model,
                                          @SessionAttribute("usuarioLogado") Usuario usuario) {

        Galpao galpao = galpaoService.buscarPorId(id).orElse(null);
        if (galpao == null) {
            model.addAttribute("mensagemErro", "Galpão não encontrado");
            return "galpao/listar";
        }

        model.addAttribute("galpaoDTO", GalpaoMapper.toDTO(galpao));
        model.addAttribute("galpaoId", id);
        checarPermissaoAdmin(usuario, model);

        return "galpao/editar";
    }

    // EDITAR GALPÃO
    @PostMapping("/editar/{id}")
    public String editarGalpao(@SessionAttribute("usuarioLogado") Usuario usuario,
                               @PathVariable Long id,
                               GalpaoDTO dto,
                               Model model) {

        if (!checarPermissaoAdmin(usuario, model)) {
            model.addAttribute("galpaoDTO", dto);
            model.addAttribute("galpaoId", id);
            return "galpao/editar";
        }

        galpaoService.editar(id, dto);
        return "redirect:/galpoes-view/todos";
    }

    // FORMULÁRIO EXCLUIR
    @GetMapping("/excluir/{id}")
    public String mostrarFormularioExcluir(@PathVariable Long id,
                                           Model model,
                                           @SessionAttribute("usuarioLogado") Usuario usuario) {
        Galpao galpao = galpaoService.buscarPorId(id).orElse(null);
        if (galpao == null) {
            model.addAttribute("mensagemErro", "Galpão não encontrado.");
            return "galpao/listar";
        }

        model.addAttribute("galpao", galpao);
        checarPermissaoAdmin(usuario, model);

        return "galpao/excluir";
    }

    // EXECUTAR EXCLUSÃO
    @PostMapping("/excluir/{id}")
    public String excluirGalpao(@SessionAttribute("usuarioLogado") Usuario usuario,
                                @PathVariable Long id,
                                Model model) {
        if (!checarPermissaoAdmin(usuario, model)) {
            galpaoService.buscarPorId(id).ifPresent(g -> model.addAttribute("galpao", g));
            return "galpao/excluir";
        }

        try {
            galpaoService.excluir(id);
        } catch (IllegalStateException e) {
            model.addAttribute("mensagemErro", e.getMessage());
            galpaoService.buscarPorId(id).ifPresent(g -> model.addAttribute("galpao", g));
            return "galpao/excluir";
        }

        return "redirect:/galpoes-view/todos";
    }
}
