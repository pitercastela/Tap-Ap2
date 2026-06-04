package com.example.demo.Aluno;

import com.example.demo.Conexao;
import com.example.demo.CrudDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDao implements CrudDao<Aluno> {

    @Override
    public Aluno salvar(Aluno aluno) {
        String sql = "INSERT INTO alunos (matricula, nome, sexo, data_nascimento, id_curso) VALUES (?, ?, ?, ?, ?)";
        Connection conn = Conexao.getInstanciaConexao();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, aluno.getMatricula());
            stmt.setString(2, aluno.getNome());
            stmt.setString(3, aluno.getSexo());
            stmt.setDate(4, Date.valueOf(aluno.getDataNascimento()));

            if (aluno.getIdCurso() != null) {
                stmt.setInt(5, aluno.getIdCurso());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar aluno", e);
        }
        return aluno;
    }

    @Override
    public Aluno buscar(Integer matricula) {
        String sql = "SELECT * FROM alunos WHERE matricula = ?";
        Aluno aluno = null;
        Connection conn = Conexao.getInstanciaConexao();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, matricula);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    aluno = Aluno.builder()
                            .matricula(resultSet.getInt("matricula"))
                            .nome(resultSet.getString("nome"))
                            .sexo(resultSet.getString("sexo"))
                            .dataNascimento(resultSet.getDate("data_nascimento").toLocalDate())
                            .idCurso((Integer) resultSet.getObject("id_curso"))
                            .build();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar aluno", e);
        }
        return aluno;
    }

    @Override
    public List<Aluno> listarTodos() {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM alunos";
        Connection conn = Conexao.getInstanciaConexao();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                alunos.add(Aluno.builder()
                        .matricula(rs.getInt("matricula"))
                        .nome(rs.getString("nome"))
                        .sexo(rs.getString("sexo"))
                        .dataNascimento(rs.getDate("data_nascimento").toLocalDate())
                        .idCurso((Integer) rs.getObject("id_curso"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar alunos", e);
        }
        return alunos;
    }

    @Override
    public boolean atualizar(Aluno aluno) {
        String sql = "UPDATE alunos SET nome = ?, sexo = ?, data_nascimento = ?, id_curso = ? WHERE matricula = ?";
        Connection conn = Conexao.getInstanciaConexao();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getSexo());
            stmt.setDate(3, Date.valueOf(aluno.getDataNascimento()));

            if (aluno.getIdCurso() != null) {
                stmt.setInt(4, aluno.getIdCurso());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }

            stmt.setInt(5, aluno.getMatricula());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar aluno", e);
        }
    }

    @Override
    public boolean deletar(Integer matricula) {
        String sql = "DELETE FROM alunos WHERE matricula = ?";
        Connection conn = Conexao.getInstanciaConexao();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, matricula);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar aluno", e);
        }
    }
}