package com.mottu.mottu.controller.thymeleaf;

import com.mottu.mottu.model.DTO.MotoDTO;
import com.mottu.mottu.model.Moto;
import com.mottu.mottu.model.RoleName;
import com.mottu.mottu.model.Usuario;
import com.mottu.mottu.repository.GalpaoRepository;
import com.mottu.mottu.repository.MotoqueiroRepository;
import com.mottu.mottu.service.MotoMapper;
import com.mottu.mottu.service.MotoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/motos-view")
public class MotoThymeleafController {

    @Autowired
    private MotoService motoService;

    @Autowired
    private MotoqueiroRepository motoqueiroRepository;

    @Autowired
    private GalpaoRepository galpaoRepository;

    private boolean checarPermissaoAdmin(Usuario usuario, Model model) {
        if (!usuario.getRole().getNome().equals(RoleName.ADMIN)) {
            model.addAttribute("mensagemErro", "Você não tem permissão para esta ação.");
            return false;
        }
        return true;
    }

    // LISTAR
    @GetMapping("/todos")
    public String listar(Model model) {
        List<Moto> motos = motoService.listarTodas();
        model.addAttribute("motos", motos);
        if (motos.isEmpty()) model.addAttribute("mensagem", "Nenhuma moto cadastrada");
        return "moto/listar";
    }

    // ADICIONAR
    @GetMapping("/adicionar")
    public String mostrarFormularioAdicionar(Model model, @SessionAttribute("usuarioLogado") Usuario usuario) {
        model.addAttribute("motoDTO", new MotoDTO());
        popularCombos(model);
        checarPermissaoAdmin(usuario, model);
        return "moto/adicionar";
    }

    @PostMapping("/adicionar")
    public String adicionar(@ModelAttribute MotoDTO dto,
                            @SessionAttribute("usuarioLogado") Usuario usuario,
                            Model model) {
        if (!checarPermissaoAdmin(usuario, model)) {
            popularCombos(model);
            model.addAttribute("motoDTO", dto);
            return "moto/adicionar";
        }

        try {
            motoService.salvar(dto);
            return "redirect:/motos-view/todos";
        } catch (Exception e) {
            model.addAttribute("mensagemErro", e.getMessage());
            popularCombos(model);
            model.addAttribute("motoDTO", dto);
            return "moto/adicionar";
        }
    }

    // EDITAR
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id,
                                          @SessionAttribute("usuarioLogado") Usuario usuario,
                                          Model model) {
        Optional<Moto> motoOpt = motoService.buscarPorId(id);
        if (motoOpt.isEmpty()) {
            model.addAttribute("mensagemErro", "Moto não encontrada.");
            return "redirect:/motos-view/todos";
        }

        MotoDTO dto = MotoMapper.toDTO(motoOpt.get());
        if (motoOpt.get().getMotoboyEmUso() != null)
            dto.setMotoboyId(motoOpt.get().getMotoboyEmUso().getId());
        if (motoOpt.get().getGalpao() != null)
            dto.setGalpaoId(motoOpt.get().getGalpao().getId());

        model.addAttribute("motoDTO", dto);
        popularCombos(model);
        checarPermissaoAdmin(usuario, model);
        return "moto/editar";
    }

    @PostMapping("/editar/{id}")
    public String editar(@PathVariable Long id,
                         @ModelAttribute MotoDTO dto,
                         @SessionAttribute("usuarioLogado") Usuario usuario,
                         Model model) {
        if (!checarPermissaoAdmin(usuario, model)) {
            popularCombos(model);
            model.addAttribute("motoDTO", dto);
            return "moto/editar";
        }

        try {
            motoService.editar(id, dto);
            return "redirect:/motos-view/todos";
        } catch (Exception e) {
            model.addAttribute("mensagemErro", e.getMessage());
            popularCombos(model);
            model.addAttribute("motoDTO", dto);
            return "moto/editar";
        }
    }

    // EXCLUIR
    @GetMapping("/excluir/{id}")
    public String mostrarFormularioExcluir(@PathVariable Long id,
                                           @SessionAttribute("usuarioLogado") Usuario usuario,
                                           Model model) {
        Optional<Moto> motoOpt = motoService.buscarPorId(id);
        if (motoOpt.isEmpty()) {
            model.addAttribute("mensagemErro", "Moto não encontrada.");
            return "redirect:/motos-view/todos";
        }

        model.addAttribute("moto", motoOpt.get());
        checarPermissaoAdmin(usuario, model);
        return "moto/excluir";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id,
                          @SessionAttribute("usuarioLogado") Usuario usuario,
                          Model model) {
        if (!checarPermissaoAdmin(usuario, model)) {
            motoService.buscarPorId(id).ifPresent(m -> model.addAttribute("moto", m));
            return "moto/excluir";
        }

        try {
            motoService.excluir(id);
            return "redirect:/motos-view/todos";
        } catch (Exception e) {
            motoService.buscarPorId(id).ifPresent(m -> model.addAttribute("moto", m));
            model.addAttribute("mensagemErro", e.getMessage());
            return "moto/excluir";
        }
    }

    private void popularCombos(Model model) {
        model.addAttribute("galpoes", galpaoRepository.findAll());
        model.addAttribute("motoqueiros", motoqueiroRepository.findAll());
    }
}