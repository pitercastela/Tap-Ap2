package com.example.demo.Aluno;

import com.example.demo.DaoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

    private final AlunoDao alunoDao;

    @Autowired
    public AlunoController(DaoFactory daoFactory) {
        this.alunoDao = (AlunoDao) daoFactory.criarDao(AlunoDao.class);
    }

    @PostMapping
    public ResponseEntity<Aluno> criar(
            @RequestParam Integer matricula,
            @RequestParam String nome,
            @RequestParam String sexo,
            @RequestParam LocalDate dataNascimento,
            @RequestParam(required = false) Integer idCurso) {

        Aluno aluno = Aluno.builder()
                .matricula(matricula)
                .nome(nome)
                .sexo(sexo)
                .dataNascimento(dataNascimento)
                .idCurso(idCurso)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(alunoDao.salvar(aluno));
    }

    @GetMapping
    public ResponseEntity<List<Aluno>> listar() {
        return ResponseEntity.ok(alunoDao.listarTodos());
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<Aluno> buscar(@PathVariable Integer matricula) {
        Aluno alunoEncontrado = alunoDao.buscar(matricula);
        if (alunoEncontrado != null) {
            return ResponseEntity.ok(alunoEncontrado);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{matricula}")
    public ResponseEntity<Void> atualizar(
            @PathVariable Integer matricula,
            @RequestParam String nome,
            @RequestParam String sexo,
            @RequestParam LocalDate dataNascimento,
            @RequestParam(required = false) Integer idCurso) {

        Aluno aluno = Aluno.builder()
                .matricula(matricula)
                .nome(nome)
                .sexo(sexo)
                .dataNascimento(dataNascimento)
                .idCurso(idCurso)
                .build();

        boolean sucesso = alunoDao.atualizar(aluno);

        if (sucesso) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{matricula}")
    public ResponseEntity<Void> deletar(@PathVariable Integer matricula) {
        boolean sucesso = alunoDao.deletar(matricula);

        if (sucesso) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}

