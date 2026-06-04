package com.example.demo.Disciplina;

import com.example.demo.Conexao;
import com.example.demo.CrudDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaDao implements CrudDao<Disciplina> {

    @Override
    public Disciplina salvar(Disciplina disciplina) {
        String sql = "INSERT INTO disciplinas (id_disciplina, nome_disciplina) VALUES (?,?)";
        Connection conn = Conexao.getInstanciaConexao();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, disciplina.getIdDisciplina());
            stmt.setString(2, disciplina.getNomeDisciplina());
            stmt.executeUpdate();

            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar disciplina", e);
        }
        return disciplina;
    }

    @Override
    public Disciplina buscar(Integer id) {
        String sql = "SELECT * FROM disciplinas WHERE id_disciplina = ?";
        Disciplina disciplina = null;
        Connection conn = Conexao.getInstanciaConexao();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    disciplina = Disciplina.builder()
                            .idDisciplina(resultSet.getInt("id_disciplina"))
                            .nomeDisciplina(resultSet.getString("nome_disciplina"))
                            .build();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar disciplina", e);
        }
        return disciplina;
    }

    @Override
    public List<Disciplina> listarTodos() {
        List<Disciplina> disciplinas = new ArrayList<>();
        String sql = "SELECT * FROM disciplinas";
        Connection conn = Conexao.getInstanciaConexao();

        try (Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {

            while (resultSet.next()) {
                disciplinas.add(Disciplina.builder()
                        .idDisciplina(resultSet.getInt("id_disciplina"))
                        .nomeDisciplina(resultSet.getString("nome_disciplina"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar disciplinas", e);
        }
        return disciplinas;
    }

    @Override
    public boolean atualizar(Disciplina disciplina) {
        String sql = "UPDATE disciplinas SET nome_disciplina = ? WHERE id_disciplina = ?";
        Connection conn = Conexao.getInstanciaConexao();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, disciplina.getNomeDisciplina());
            stmt.setInt(2, disciplina.getIdDisciplina());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar disciplina", e);
        }
    }

    @Override
    public boolean deletar(Integer id) {
        String sql = "DELETE FROM disciplinas WHERE id_disciplina = ?";
        Connection conn = Conexao.getInstanciaConexao();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar disciplina", e);
        }
    }
}
