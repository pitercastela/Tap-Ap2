package com.example.demo.Matricula;

import com.example.demo.Conexao;
import com.example.demo.Disciplina.Disciplina;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatriculaDao {

    public boolean matricular(Integer matricula, Integer idDisciplina) {
        String sql = "INSERT INTO aluno_disciplina (matricula_aluno, id_disciplina) VALUES (?, ?)";
        Connection conn = Conexao.getInstanciaConexao();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, matricula);
            stmt.setInt(2, idDisciplina);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao matricular aluno na disciplina", e);
        }
    }

    public boolean removerMatricula(Integer matricula, Integer idDisciplina) {
        String sql = "DELETE FROM aluno_disciplina WHERE matricula_aluno = ? AND id_disciplina = ?";
        Connection conn = Conexao.getInstanciaConexao();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, matricula);
            stmt.setInt(2, idDisciplina);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover matrícula", e);
        }
    }

    public List<Matricula> listarTodas() {
        List<Matricula> matriculas = new ArrayList<>();
        String sql = "SELECT matricula_aluno, id_disciplina FROM aluno_disciplina";
        Connection conn = Conexao.getInstanciaConexao();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                matriculas.add(Matricula.builder()
                        .matriculaAluno(rs.getInt("matricula_aluno"))
                        .idDisciplina(rs.getInt("id_disciplina"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar matrículas", e);
        }
        return matriculas;
    }

    public List<Disciplina> listarDisciplinasPorAluno(Integer matricula) {
        List<Disciplina> disciplinas = new ArrayList<>();
        String sql = "SELECT d.id_disciplina, d.nome_disciplina " +
                "FROM disciplinas d " +
                "INNER JOIN aluno_disciplina ad ON d.id_disciplina = ad.id_disciplina " +
                "WHERE ad.matricula_aluno = ?";
        Connection conn = Conexao.getInstanciaConexao();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, matricula);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    disciplinas.add(Disciplina.builder()
                            .idDisciplina(rs.getInt("id_disciplina"))
                            .nomeDisciplina(rs.getString("nome_disciplina"))
                            .build());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar disciplinas do aluno", e);
        }
        return disciplinas;
    }
}
