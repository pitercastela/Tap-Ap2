package com.example.demo.Curso;

import com.example.demo.CrudDao;
import com.example.demo.DaoFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CrudDao<Curso> cursoDao;

    @Autowired
    public CursoController(DaoFactory daoFactory){
        this.cursoDao = (CrudDao<Curso>) daoFactory.criarDao(CursoDao.class);
    }


    @PostMapping
    public ResponseEntity<Curso> criar(
            @RequestParam String nomeCurso,
            @RequestParam Integer idCurso) {

        Curso curso = Curso.builder()
                .nomeCurso(nomeCurso)
                .idCurso(idCurso)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(cursoDao.salvar(curso));
    }

    @GetMapping
    public ResponseEntity<List<Curso>> listar() {
        return ResponseEntity.ok(cursoDao.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> buscar(
            @PathVariable Integer id) {

        Curso curso = cursoDao.buscar(id);
        if (curso != null){
            return ResponseEntity.ok(curso);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable Integer id,
            @RequestParam String nomeCurso) {

        Curso curso = Curso.builder()
                .idCurso(id)
                .nomeCurso(nomeCurso)
                .build();

        boolean sucesso = cursoDao.atualizar(curso);

        if (sucesso){
            return ResponseEntity.noContent().build();
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        boolean sucesso = cursoDao.deletar(id);
        if (sucesso) {
            return ResponseEntity.noContent().build();
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}