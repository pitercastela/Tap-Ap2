package com.example.demo.Participacao;

import com.example.demo.DaoFactory;
import com.example.demo.Partida.Partida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participacoes")
public class ParticipacaoController {

    private final ParticipacaoDao participacaoDao;

    @Autowired
    public ParticipacaoController(DaoFactory daoFactory) {
        this.participacaoDao = daoFactory.criarDao(ParticipacaoDao.class);
    }

    @PostMapping
    public ResponseEntity<Void> registrarParticipacao(
            @RequestParam Integer idSelecao,
            @RequestParam Integer idPartida) {

        boolean sucesso = participacaoDao.registrarParticipacao(idSelecao, idPartida);
        if (sucesso) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removerParticipacao(
            @RequestParam Integer idSelecao,
            @RequestParam Integer idPartida) {

        boolean sucesso = participacaoDao.removerParticipacao(idSelecao, idPartida);
        if (sucesso) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping
    public ResponseEntity<List<Participacao>> listarTodas() {
        return ResponseEntity.ok(participacaoDao.listarTodas());
    }

    @GetMapping("/selecao/{idSelecao}")
    public ResponseEntity<List<Partida>> listarPorSelecao(@PathVariable Integer idSelecao) {
        return ResponseEntity.ok(participacaoDao.listarPartidasPorSelecao(idSelecao));
    }
}