package br.fiap.joao565291.controller;

import br.fiap.joao565291.dto.BairroDTO;
import br.fiap.joao565291.exception.RegraNegocioException;
import br.fiap.joao565291.service.BairroService;
import br.fiap.joao565291.service.PainelService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/bairros")
@AllArgsConstructor
public class BairroController {
    private final BairroService service;
    private final PainelService painelService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("bairros", service.listar());
        return "bairros/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("bairro", new BairroDTO());
        model.addAttribute("zonas", zonas());
        return "bairros/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("bairro", service.buscarPorId(id));
        model.addAttribute("zonas", zonas());
        return "bairros/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("bairro") BairroDTO dto, BindingResult result,
                         Model model, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            model.addAttribute("zonas", zonas());
            return "bairros/form";
        }
        service.salvar(dto);
        redirect.addFlashAttribute("sucesso", "Bairro salvo com sucesso!");
        return "redirect:/bairros";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            service.excluir(id);
            redirect.addFlashAttribute("sucesso", "Bairro excluído com sucesso!");
        } catch (RegraNegocioException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/bairros";
    }

    @GetMapping("/{id}/painel")
    public String painel(@PathVariable Long id, Model model) {
        model.addAttribute("painel", painelService.montarPainel(id));
        return "bairros/painel";
    }

    private List<String> zonas() {
        return List.of("Centro", "Norte", "Sul", "Leste", "Oeste");
    }
}
