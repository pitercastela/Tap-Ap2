package com.example.demo.Disciplina;

import com.example.demo.CrudDao;
import com.example.demo.DaoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disciplinas")
public class DisciplinaController {

    private final CrudDao<Disciplina> disciplinaDao;

    @Autowired
    public DisciplinaController(DaoFactory daoFactory) {
        this.disciplinaDao = (CrudDao<Disciplina>) daoFactory.criarDao(DisciplinaDao.class);
    }

    @PostMapping
    public ResponseEntity<Disciplina> criar(
            @RequestParam Integer idDisciplina,
            @RequestParam String nomeDisciplina) {
        Disciplina disciplina = Disciplina.builder()
                .nomeDisciplina(nomeDisciplina)
                .idDisciplina(idDisciplina)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(disciplinaDao.salvar(disciplina));
    }

    @GetMapping
    public ResponseEntity<List<Disciplina>> listar() {
        return ResponseEntity.ok(disciplinaDao.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Disciplina> buscar(@PathVariable Integer id) {
        Disciplina disciplina = disciplinaDao.buscar(id);
        if (disciplina != null) {
            return ResponseEntity.ok(disciplina);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable Integer id,
            @RequestParam String nomeDisciplina) {

        Disciplina disciplina = Disciplina.builder()
                .idDisciplina(id)
                .nomeDisciplina(nomeDisciplina)
                .build();

        boolean sucesso = disciplinaDao.atualizar(disciplina);

        if (sucesso) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        boolean sucesso = disciplinaDao.deletar(id);

        if (sucesso) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
