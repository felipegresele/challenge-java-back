package com.mottu.mottu.controller.thymeleaf;

import com.mottu.mottu.model.DTO.ManutencaoDTO;
import com.mottu.mottu.model.Manutencao;
import com.mottu.mottu.model.RoleName;
import com.mottu.mottu.model.Usuario;
import com.mottu.mottu.service.ManutencaoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/manutencoes-view")
public class ManutencaoThymeleafController {

    @Autowired
    private ManutencaoService manutencaoService;

    private boolean checarPermissaoAdmin(Usuario usuario, Model model) {
        if (!usuario.getRole().getNome().equals(RoleName.ADMIN)) {
            model.addAttribute("mensagemErro", "Você não tem permissão para esta ação");
            return false;
        }
        return true;
    }

    @GetMapping("/todos")
    public String listarManutencoes(Model model) {
        List<Manutencao> manutencoes = manutencaoService.listarTodos();
        model.addAttribute("manutencoes", manutencoes);
        if (manutencoes.isEmpty()) {
            model.addAttribute("mensagem", "Nenhuma manutenção cadastrada.");
        }
        return "manutencao/listar";
    }

    @GetMapping("/adicionar")
    public String mostrarFormularioAdicionar(Model model,
                                             @SessionAttribute("usuarioLogado") Usuario usuario) {
        model.addAttribute("manutencaoDTO", new ManutencaoDTO());
        checarPermissaoAdmin(usuario, model);
        return "manutencao/adicionar";
    }

    @PostMapping("/adicionar")
    public String adicionarManutencao(@SessionAttribute("usuarioLogado") Usuario usuario,
                                      ManutencaoDTO dto,
                                      Model model) {
        if (!checarPermissaoAdmin(usuario, model)) {
            model.addAttribute("manutencaoDTO", dto);
            return "manutencao/adicionar";
        }

        String mensagem = validarDatas(dto);
        if (!mensagem.isEmpty()) {
            model.addAttribute("mensagem", mensagem);
            model.addAttribute("manutencaoDTO", dto);
            return "manutencao/adicionar";
        }

        if (dto.getDataFechamento() != null) {
            dto.setEmAndamento(false);
        }

        dto.setId(null); // garantir que não tente sobrescrever um ID existente
        manutencaoService.salvar(dto);
        return "redirect:/manutencoes-view/todos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id,
                                          Model model,
                                          @SessionAttribute("usuarioLogado") Usuario usuario) {
        Manutencao manutencao = manutencaoService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Manutenção não encontrada"));
        ManutencaoDTO dto = new ManutencaoDTO();
        BeanUtils.copyProperties(manutencao, dto);
        model.addAttribute("manutencaoDTO", dto);

        if (!checarPermissaoAdmin(usuario, model)) {
            return "manutencao/editar";
        }

        return "manutencao/editar";
    }

    @PostMapping("/editar/{id}")
    public String editarManutencao(@PathVariable Long id,
                                   ManutencaoDTO dto,
                                   Model model,
                                   @SessionAttribute("usuarioLogado") Usuario usuario) {
        if (!usuario.getRole().getNome().equals(RoleName.ADMIN)) {
            model.addAttribute("mensagemErro", "Você não tem permissão para editar manutenções.");
            model.addAttribute("manutencaoDTO", dto);
            return "manutencao/editar";
        }

        String mensagem = validarDatas(dto);
        if (!mensagem.isEmpty()) {
            model.addAttribute("mensagem", mensagem);
            model.addAttribute("manutencaoDTO", dto);
            return "manutencao/editar";
        }

        if (dto.getDataFechamento() != null) {
            dto.setEmAndamento(false);
        }

        Optional<Manutencao> manutencaoAtualizada = manutencaoService.atualizar(id, dto);
        if (manutencaoAtualizada.isEmpty()) {
            model.addAttribute("mensagemErro", "Manutenção não encontrada para atualização.");
            model.addAttribute("manutencaoDTO", dto);
            return "manutencao/editar";
        }

        return "redirect:/manutencoes-view/todos";
    }

    @GetMapping("/excluir/{id}")
    public String mostrarFormularioExcluir(@PathVariable Long id,
                                           Model model,
                                           @SessionAttribute("usuarioLogado") Usuario usuario) {
        Manutencao manutencao = manutencaoService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Manutenção não encontrada"));
        model.addAttribute("manutencao", manutencao);

        if (!usuario.getRole().getNome().equals(RoleName.ADMIN)) {
            model.addAttribute("mensagemErro", "Você não tem permissão para excluir manutenções.");
        }

        return "manutencao/excluir";
    }

    @PostMapping("/excluir/{id}")
    public String excluirManutencao(@PathVariable Long id,
                                    @SessionAttribute("usuarioLogado") Usuario usuario,
                                    Model model) {
        if (!usuario.getRole().getNome().equals(RoleName.ADMIN)) {
            model.addAttribute("mensagemErro", "Você não tem permissão para excluir manutenções.");
            return "manutencao/excluir";
        }

        manutencaoService.deletar(id);
        return "redirect:/manutencoes-view/todos";
    }

    // Validações de datas e checkbox
    private String validarDatas(ManutencaoDTO dto) {
        LocalDateTime agora = LocalDateTime.now();

        if (dto.getDataAbertura() != null && dto.getDataAbertura().isAfter(agora)) {
            return "A data de abertura não pode ser no futuro.";
        }

        if (dto.getDataFechamento() != null && dto.getDataFechamento().isAfter(agora)) {
            return "A data de fechamento não pode ser no futuro.";
        }

        if (dto.getDataAbertura() != null && dto.getDataFechamento() != null &&
                dto.getDataAbertura().isAfter(dto.getDataFechamento())) {
            return "A data de abertura deve ser anterior à data de fechamento.";
        }

        return "";
    }
}