package br.fiap.joao565291.controller;

import br.fiap.joao565291.dto.TrechoDTO;
import br.fiap.joao565291.exception.RegraNegocioException;
import br.fiap.joao565291.service.BairroService;
import br.fiap.joao565291.service.TrechoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/trechos")
@AllArgsConstructor
public class TrechoController {
    private final TrechoService service;
    private final BairroService bairroService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("trechos", service.listar());
        return "trechos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("trecho", new TrechoDTO());
        carregarListas(model);
        return "trechos/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("trecho", service.buscarPorId(id));
        carregarListas(model);
        return "trechos/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("trecho") TrechoDTO dto, BindingResult result,
                         Model model, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            carregarListas(model);
            return "trechos/form";
        }
        service.salvar(dto);
        redirect.addFlashAttribute("sucesso", "Trecho salvo com sucesso!");
        return "redirect:/trechos";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            service.excluir(id);
            redirect.addFlashAttribute("sucesso", "Trecho e suas leituras excluídos com sucesso!");
        } catch (RegraNegocioException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/trechos";
    }

    private void carregarListas(Model model) {
        model.addAttribute("bairros", bairroService.listar());
        model.addAttribute("tiposCalcada", List.of("Regular", "Irregular", "Escadaria", "Sem iluminação"));
    }
}
