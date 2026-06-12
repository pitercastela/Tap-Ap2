package com.example.demo.Selecao;

import com.example.demo.CrudDao;
import com.example.demo.DaoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/selecoes")
public class SelecaoController {

    private final CrudDao<Selecao> selecaoDao;

    @Autowired
    public SelecaoController(DaoFactory daoFactory){
        this.selecaoDao = (CrudDao<Selecao>) daoFactory.criarDao(SelecaoDao.class);
    }

    @PostMapping
    public ResponseEntity<Selecao> criar(
            @RequestParam Integer idSelecao,
            @RequestParam String nomePais,
            @RequestParam String tecnico,
            @RequestParam(required = false) Integer rankingFifa) {

        Selecao selecao = Selecao.builder()
                .idSelecao(idSelecao)
                .nomePais(nomePais)
                .tecnico(tecnico)
                .rankingFifa(rankingFifa)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(selecaoDao.salvar(selecao));
    }

    @GetMapping
    public ResponseEntity<List<Selecao>> listar() {
        return ResponseEntity.ok(selecaoDao.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Selecao> buscar(@PathVariable Integer id) {
        Selecao selecao = selecaoDao.buscar(id);

        if (selecao != null){
            return ResponseEntity.ok(selecao);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable Integer id,
            @RequestParam String nomePais,
            @RequestParam String tecnico,
            @RequestParam(required = false) Integer rankingFifa) {

        Selecao selecao = Selecao.builder()
                .idSelecao(id)
                .nomePais(nomePais)
                .tecnico(tecnico)
                .rankingFifa(rankingFifa)
                .build();

        boolean sucesso = selecaoDao.atualizar(selecao);

        if (sucesso){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        boolean sucesso = selecaoDao.deletar(id);

        if (sucesso) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}