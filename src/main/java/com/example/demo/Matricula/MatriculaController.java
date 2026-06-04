package com.example.demo.Matricula;

import com.example.demo.DaoFactory;
import com.example.demo.Disciplina.Disciplina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matriculas")
public class MatriculaController {

    private final MatriculaDao matriculaDao;

    @Autowired
    public MatriculaController(DaoFactory daoFactory) {
        this.matriculaDao = daoFactory.criarDao(MatriculaDao.class);
    }

    @PostMapping
    public ResponseEntity<Void> matricular(
            @RequestParam Integer matricula,
            @RequestParam Integer idDisciplina) {

        boolean sucesso = matriculaDao.matricular(matricula, idDisciplina);
        if (sucesso) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removerMatricula(
            @RequestParam Integer matricula,
            @RequestParam Integer idDisciplina) {

        boolean sucesso = matriculaDao.removerMatricula(matricula, idDisciplina);
        if (sucesso) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping
    public ResponseEntity<List<Matricula>> listarTodas() {
        return ResponseEntity.ok(matriculaDao.listarTodas());
    }

    @GetMapping("/aluno/{matricula}")
    public ResponseEntity<List<Disciplina>> listarPorAluno(@PathVariable Integer matricula) {
        return ResponseEntity.ok(matriculaDao.listarDisciplinasPorAluno(matricula));
    }
}