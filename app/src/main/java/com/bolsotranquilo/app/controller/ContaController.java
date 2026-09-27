package com.bolsotranquilo.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsotranquilo.app.model.Conta;
import com.bolsotranquilo.app.model.enums.TipoConta;
import com.bolsotranquilo.app.service.ContaService;

@Controller
@RequestMapping("/correntistas/{correntistaId}/contas")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    // UC02 - Correntista acessar contas
    @GetMapping
    public String listar(@PathVariable Long correntistaId, Model model) {
        model.addAttribute("contas", contaService.listarPorCorrentista(correntistaId));
        model.addAttribute("correntistaId", correntistaId);
        return "conta/lista";
    }

    @GetMapping("/novo")
    public String novo(@PathVariable Long correntistaId, Model model) {
        model.addAttribute("conta", new Conta());
        model.addAttribute("tipos", TipoConta.values());
        model.addAttribute("correntistaId", correntistaId);
        return "conta/form";
    }

    // UC01 - Correntista cadastra conta
    @PostMapping
    public String salvar(@PathVariable Long correntistaId,
                          @ModelAttribute Conta conta,
                          RedirectAttributes redirectAttributes) {
        contaService.cadastrar(correntistaId, conta);
        redirectAttributes.addFlashAttribute("sucesso", "Conta cadastrada com sucesso.");
        return "redirect:/correntistas/" + correntistaId + "/contas";
    }
}
