package br.fiap.joao565291.controller;

import br.fiap.joao565291.dto.LeituraDTO;
import br.fiap.joao565291.service.LeituraService;
import br.fiap.joao565291.service.TrechoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/leituras")
@AllArgsConstructor
public class LeituraController {
    private final LeituraService service;
    private final TrechoService trechoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("leituras", service.listar());
        return "leituras/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("leitura", new LeituraDTO());
        model.addAttribute("trechos", trechoService.listar());
        return "leituras/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("leitura", service.buscarPorId(id));
        model.addAttribute("trechos", trechoService.listar());
        return "leituras/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("leitura") LeituraDTO dto, BindingResult result,
                         Model model, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            model.addAttribute("trechos", trechoService.listar());
            return "leituras/form";
        }
        LeituraDTO salva = service.salvar(dto);
        redirect.addFlashAttribute("sucesso", "Leitura salva com status " + salva.getStatus() + "!");
        return "redirect:/leituras";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {
        service.excluir(id);
        redirect.addFlashAttribute("sucesso", "Leitura excluída com sucesso!");
        return "redirect:/leituras";
    }
}
