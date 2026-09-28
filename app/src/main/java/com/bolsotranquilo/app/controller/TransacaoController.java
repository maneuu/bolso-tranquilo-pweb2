package com.bolsotranquilo.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsotranquilo.app.model.Transacao;
import com.bolsotranquilo.app.model.enums.Movimento;
import com.bolsotranquilo.app.service.CategoriaService;
import com.bolsotranquilo.app.service.ComentarioService;
import com.bolsotranquilo.app.service.ContaService;
import com.bolsotranquilo.app.service.TransacaoService;

@Controller
public class TransacaoController {

    private final TransacaoService transacaoService;
    private final ContaService contaService;
    private final CategoriaService categoriaService;
    private final ComentarioService comentarioService;

    public TransacaoController(TransacaoService transacaoService,
                                ContaService contaService,
                                CategoriaService categoriaService,
                                ComentarioService comentarioService) {
        this.transacaoService = transacaoService;
        this.contaService = contaService;
        this.categoriaService = categoriaService;
        this.comentarioService = comentarioService;
    }

    @GetMapping("/contas/{contaId}/transacoes")
        public String listar(@PathVariable Long contaId, Model model) {
        model.addAttribute("conta", contaService.buscarPorId(contaId));
        model.addAttribute("transacoes", transacaoService.listarPorConta(contaId));
        return "transacao/lista";
    }

    @GetMapping("/contas/{contaId}/transacoes/novo")
    public String novo(@PathVariable Long contaId, Model model) {
        model.addAttribute("transacao", new Transacao());
        model.addAttribute("conta", contaService.buscarPorId(contaId));
        model.addAttribute("contaId", contaId);
        model.addAttribute("categorias", categoriaService.listarAtivas());
        model.addAttribute("movimentos", Movimento.values());
        return "transacao/form";
    }

    // UC03 - Correntista cria transação para conta
    @PostMapping("/contas/{contaId}/transacoes")
    public String salvar(@PathVariable Long contaId,
                          @RequestParam Long categoriaId,
                          @ModelAttribute Transacao transacao,
                          RedirectAttributes redirectAttributes) {
        transacaoService.criar(contaId, categoriaId, transacao);
        redirectAttributes.addFlashAttribute("sucesso", "Transação criada com sucesso.");
        return "redirect:/contas/" + contaId + "/transacoes";
    }

    @GetMapping("/transacoes/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Transacao transacao = transacaoService.buscarPorId(id);
        model.addAttribute("transacao", transacao);
        model.addAttribute("contaId", transacao.getConta().getId());
        model.addAttribute("categorias", categoriaService.listarAtivas());
        model.addAttribute("movimentos", Movimento.values());
        model.addAttribute("comentario", comentarioService.buscarPorTransacao(id));
        return "transacao/form";
    }

    // UC04 - Correntista edita transação existente
    @PostMapping("/transacoes/{id}")
    public String atualizar(@PathVariable Long id,
                             @RequestParam Long categoriaId,
                             @ModelAttribute Transacao transacao,
                             RedirectAttributes redirectAttributes) {
        Transacao atualizada = transacaoService.editar(id, categoriaId, transacao);
        redirectAttributes.addFlashAttribute("sucesso", "Transação atualizada com sucesso.");
        return "redirect:/contas/" + atualizada.getConta().getId() + "/transacoes";
    }
}