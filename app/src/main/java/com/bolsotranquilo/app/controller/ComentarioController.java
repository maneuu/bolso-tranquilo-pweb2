package com.bolsotranquilo.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsotranquilo.app.model.Comentario;
import com.bolsotranquilo.app.model.Transacao;
import com.bolsotranquilo.app.service.ComentarioService;
import com.bolsotranquilo.app.service.TransacaoService;

@Controller
public class ComentarioController {

    private final ComentarioService comentarioService;
    private final TransacaoService transacaoService;

    public ComentarioController(ComentarioService comentarioService, TransacaoService transacaoService) {
        this.comentarioService = comentarioService;
        this.transacaoService = transacaoService;
    }

    // UC05 - Correntista adiciona comentário a uma transação
    @PostMapping("/transacoes/{transacaoId}/comentario")
    public String adicionar(@PathVariable Long transacaoId,
                             @RequestParam String texto,
                             RedirectAttributes redirectAttributes) {
        Transacao transacao = transacaoService.buscarPorId(transacaoId);
        try {
            comentarioService.adicionar(transacaoId, texto);
            redirectAttributes.addFlashAttribute("sucesso", "Comentário adicionado.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/contas/" + transacao.getConta().getId() + "/transacoes";
    }

    // UC06 - Correntista edita comentário existente
    @PostMapping("/comentarios/{comentarioId}/editar")
    public String editar(@PathVariable Long comentarioId,
                          @RequestParam String texto,
                          RedirectAttributes redirectAttributes) {
        Comentario comentario = comentarioService.editar(comentarioId, texto);
        redirectAttributes.addFlashAttribute("sucesso", "Comentário atualizado.");
        return "redirect:/contas/" + comentario.getTransacao().getConta().getId() + "/transacoes";
    }

    // UC06 - Correntista exclui comentário existente
    @PostMapping("/comentarios/{comentarioId}/excluir")
    public String excluir(@PathVariable Long comentarioId, RedirectAttributes redirectAttributes) {
        Comentario comentario = comentarioService.buscarPorId(comentarioId);
        Long contaId = comentario.getTransacao().getConta().getId();
        comentarioService.excluir(comentarioId);
        redirectAttributes.addFlashAttribute("sucesso", "Comentário excluído.");
        return "redirect:/contas/" + contaId + "/transacoes";
    }
}