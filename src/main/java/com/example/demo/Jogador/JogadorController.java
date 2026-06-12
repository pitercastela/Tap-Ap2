package com.example.demo.Jogador;

import com.example.demo.DaoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jogadores")
public class JogadorController {

    private final JogadorDao jogadorDao;

    @Autowired
    public JogadorController(DaoFactory daoFactory) {
        this.jogadorDao = (JogadorDao) daoFactory.criarDao(JogadorDao.class);
    }

    @PostMapping
    public ResponseEntity<Jogador> criar(
            @RequestParam Integer idJogador,
            @RequestParam String nome,
            @RequestParam Integer numeroCamisa,
            @RequestParam String posicao,
            @RequestParam Integer idade,
            @RequestParam(required = false) Integer idSelecao) {

        Jogador jogador = Jogador.builder()
                .idJogador(idJogador)
                .nome(nome)
                .numeroCamisa(numeroCamisa)
                .posicao(posicao)
                .idade(idade)
                .idSelecao(idSelecao)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(jogadorDao.salvar(jogador));
    }

    @GetMapping
    public ResponseEntity<List<Jogador>> listar() {
        return ResponseEntity.ok(jogadorDao.listarTodos());
    }

    @GetMapping("/{idJogador}")
    public ResponseEntity<Jogador> buscar(@PathVariable Integer idJogador) {
        Jogador jogadorEncontrado = jogadorDao.buscar(idJogador);
        if (jogadorEncontrado != null) {
            return ResponseEntity.ok(jogadorEncontrado);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{idJogador}")
    public ResponseEntity<Void> atualizar(
            @PathVariable Integer idJogador,
            @RequestParam String nome,
            @RequestParam Integer numeroCamisa,
            @RequestParam String posicao,
            @RequestParam Integer idade,
            @RequestParam(required = false) Integer idSelecao) {

        Jogador jogador = Jogador.builder()
                .idJogador(idJogador)
                .nome(nome)
                .numeroCamisa(numeroCamisa)
                .posicao(posicao)
                .idade(idade)
                .idSelecao(idSelecao)
                .build();

        boolean sucesso = jogadorDao.atualizar(jogador);

        if (sucesso) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{idJogador}")
    public ResponseEntity<Void> deletar(@PathVariable Integer idJogador) {
        boolean sucesso = jogadorDao.deletar(idJogador);

        if (sucesso) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}