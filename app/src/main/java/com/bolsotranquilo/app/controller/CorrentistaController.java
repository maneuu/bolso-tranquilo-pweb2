package com.bolsotranquilo.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsotranquilo.app.model.Correntista;
import com.bolsotranquilo.app.model.enums.Papel;
import com.bolsotranquilo.app.service.CorrentistaService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/correntistas")
public class CorrentistaController {

    private final CorrentistaService correntistaService;

    public CorrentistaController(CorrentistaService correntistaService) {
        this.correntistaService = correntistaService;
    }

    // UC20 - Administrador acessa listagem de correntistas
    @GetMapping
    public String listar(Model model, HttpSession session) {
        if (!isAdministrador(session)) {
            return "redirect:/correntistas/" + session.getAttribute("correntistaId") + "/contas";
        }
        model.addAttribute("correntistas", correntistaService.listarTodos());
        return "correntista/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model, HttpSession session) {
        if (!isAdministrador(session)) {
            return "redirect:/correntistas/" + session.getAttribute("correntistaId") + "/contas";
        }
        model.addAttribute("correntista", new Correntista());
        model.addAttribute("papeis", Papel.values());
        return "correntista/form";
    }

    // UC21 - Administrador cadastra correntista
    @PostMapping
    public String salvar(@ModelAttribute Correntista correntista, Model model,
                          RedirectAttributes redirectAttributes,
                          HttpSession session) {
        if (!isAdministrador(session)) {
            return "redirect:/correntistas/" + session.getAttribute("correntistaId") + "/contas";
        }
        try {
            correntistaService.cadastrar(correntista);
            redirectAttributes.addFlashAttribute("sucesso", "Correntista cadastrado com sucesso.");
            return "redirect:/correntistas";
        } catch (IllegalArgumentException e) {
            // login já existe: volta pro form (não redireciona) pra manter os dados digitados
            model.addAttribute("correntista", correntista);
            model.addAttribute("erro", e.getMessage());
            return "correntista/form";
        }
    }

    @PostMapping("/{id}/bloquear")
    public String bloquear(@PathVariable Long id,
                           RedirectAttributes redirectAttributes,
                           HttpSession session) {
        if (!isAdministrador(session)) {
            return "redirect:/correntistas/" + session.getAttribute("correntistaId") + "/contas";
        }
        correntistaService.bloquear(id);
        redirectAttributes.addFlashAttribute("sucesso", "Correntista bloqueado com sucesso.");
        return "redirect:/correntistas";
    }

    @PostMapping("/{id}/desbloquear")
    public String desbloquear(@PathVariable Long id,
                              RedirectAttributes redirectAttributes,
                              HttpSession session) {
        if (!isAdministrador(session)) {
            return "redirect:/correntistas/" + session.getAttribute("correntistaId") + "/contas";
        }
        correntistaService.desbloquear(id);
        redirectAttributes.addFlashAttribute("sucesso", "Correntista desbloqueado com sucesso.");
        return "redirect:/correntistas";
    }

    private boolean isAdministrador(HttpSession session) {
        return Papel.ADMINISTRADOR.equals(session.getAttribute("papel"));
    }
}