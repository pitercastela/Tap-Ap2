package com.example.demo.Partida;

import com.example.demo.CrudDao;
import com.example.demo.DaoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/partidas")
public class PartidaController {

    private final CrudDao<Partida> partidaDao;

    @Autowired
    public PartidaController(DaoFactory daoFactory) {
        this.partidaDao = (CrudDao<Partida>) daoFactory.criarDao(PartidaDao.class);
    }

    @PostMapping
    public ResponseEntity<Partida> criar(
            @RequestParam Integer idPartida,
            @RequestParam LocalDate dataPartida,
            @RequestParam String estadio,
            @RequestParam String faseCompeticao,
            @RequestParam(required = false) String placar) {

        Partida partida = Partida.builder()
                .idPartida(idPartida)
                .dataPartida(dataPartida)
                .estadio(estadio)
                .faseCompeticao(faseCompeticao)
                .placar(placar)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(partidaDao.salvar(partida));
    }

    @GetMapping
    public ResponseEntity<List<Partida>> listar() {
        return ResponseEntity.ok(partidaDao.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Partida> buscar(@PathVariable Integer id) {
        Partida partida = partidaDao.buscar(id);

        if (partida != null) {
            return ResponseEntity.ok(partida);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable Integer id,
            @RequestParam LocalDate dataPartida,
            @RequestParam String estadio,
            @RequestParam String faseCompeticao,
            @RequestParam(required = false) String placar) {

        Partida partida = Partida.builder()
                .idPartida(id)
                .dataPartida(dataPartida)
                .estadio(estadio)
                .faseCompeticao(faseCompeticao)
                .placar(placar)
                .build();

        boolean sucesso = partidaDao.atualizar(partida);

        if (sucesso) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        boolean sucesso = partidaDao.deletar(id);

        if (sucesso) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}