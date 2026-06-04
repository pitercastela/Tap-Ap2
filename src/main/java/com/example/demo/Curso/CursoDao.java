package com.example.demo.Curso;

import com.example.demo.Conexao;
import com.example.demo.CrudDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDao implements CrudDao<Curso> {

    @Override
    public Curso salvar(Curso curso) {
        String sql = "INSERT INTO cursos (id_curso, nome_curso) VALUES (?,?)";

        Connection conn = Conexao.getInstanciaConexao();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, curso.getIdCurso());
            stmt.setString(2, curso.getNomeCurso());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar curso", e);
        }
        return curso;
    }

    @Override
    public List<Curso> listarTodos() {
        List<Curso> cursos = new ArrayList<>();
        String sql = "SELECT * FROM cursos";

        Connection conn = Conexao.getInstanciaConexao();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                cursos.add(Curso.builder()
                        .idCurso(rs.getInt("id_curso"))
                        .nomeCurso(rs.getString("nome_curso"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar cursos", e);
        }
        return cursos;
    }

    @Override
    public Curso buscar(Integer id) {
        String sql = "SELECT * FROM cursos WHERE id_curso = ?";
        Curso cursoencontrado = null;
        Connection conn = Conexao.getInstanciaConexao();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try(ResultSet rs = stmt.executeQuery()){
                if (rs.next()){
                    cursoencontrado = Curso.builder()
                            .idCurso(rs.getInt("id_curso"))
                            .nomeCurso(rs.getString("nome_curso"))
                            .build();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar por este ID", e);
        }
        return cursoencontrado;
    }

    @Override
    public boolean atualizar(Curso curso) {
        String sql = "UPDATE cursos SET nome_curso = ? WHERE id_curso = ?";
        Connection conn = Conexao.getInstanciaConexao();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, curso.getNomeCurso());
            stmt.setInt(2, curso.getIdCurso());
            int linhasAfetadas = stmt.executeUpdate();

            return linhasAfetadas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar curso", e);
        }
    }

    @Override
    public boolean deletar(Integer id) {
        String sql = "DELETE FROM cursos WHERE id_curso = ?";
        Connection conn = Conexao.getInstanciaConexao();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            return linhasAfetadas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar curso", e);
        }
    }
}