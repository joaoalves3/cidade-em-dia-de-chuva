package br.fiap.joao565291.controller;

import br.fiap.joao565291.service.BairroService;
import br.fiap.joao565291.service.LeituraService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class HomeController {
    private final BairroService bairroService;
    private final LeituraService leituraService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("bairros", bairroService.listar());
        model.addAttribute("leituras", leituraService.listar());
        return "index";
    }
}
