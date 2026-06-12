package com.example.demo.Selecao;

import com.example.demo.Conexao;
import com.example.demo.CrudDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SelecaoDao implements CrudDao<Selecao> {

    @Override
    public Selecao salvar(Selecao selecao) {
        String sql = "INSERT INTO selecoes (id_selecao, nome_pais, tecnico, ranking_fifa) VALUES (?, ?, ?, ?)";

        Connection conn = Conexao.getInstanciaConexao();

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, selecao.getIdSelecao());
            preparedStatement.setString(2, selecao.getNomePais());
            preparedStatement.setString(3, selecao.getTecnico());

            if (selecao.getRankingFifa() != null) {
                preparedStatement.setInt(4, selecao.getRankingFifa());
            } else {
                preparedStatement.setNull(4, Types.INTEGER);
            }

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar seleção", e);
        }
        return selecao;
    }

    @Override
    public Selecao buscar(Integer id) {
        String sql = "SELECT * FROM selecoes WHERE id_selecao = ?";
        Selecao selecaoEncontrada = null;
        Connection conn = Conexao.getInstanciaConexao();

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    selecaoEncontrada = Selecao.builder()
                            .idSelecao(resultSet.getInt("id_selecao"))
                            .nomePais(resultSet.getString("nome_pais"))
                            .tecnico(resultSet.getString("tecnico"))
                            .rankingFifa((Integer) resultSet.getObject("ranking_fifa"))
                            .build();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar seleção por ID", e);
        }
        return selecaoEncontrada;
    }

    @Override
    public List<Selecao> listarTodos() {
        List<Selecao> selecoes = new ArrayList<>();
        String sql = "SELECT * FROM selecoes";

        Connection conn = Conexao.getInstanciaConexao();

        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                selecoes.add(Selecao.builder()
                        .idSelecao(resultSet.getInt("id_selecao"))
                        .nomePais(resultSet.getString("nome_pais"))
                        .tecnico(resultSet.getString("tecnico"))
                        .rankingFifa((Integer) resultSet.getObject("ranking_fifa"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar seleções", e);
        }
        return selecoes;
    }

    @Override
    public boolean atualizar(Selecao selecao) {
        String sql = "UPDATE selecoes SET nome_pais = ?, tecnico = ?, ranking_fifa = ? WHERE id_selecao = ?";
        Connection conn = Conexao.getInstanciaConexao();

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, selecao.getNomePais());
            preparedStatement.setString(2, selecao.getTecnico());

            if (selecao.getRankingFifa() != null) {
                preparedStatement.setInt(3, selecao.getRankingFifa());
            } else {
                preparedStatement.setNull(3, Types.INTEGER);
            }

            preparedStatement.setInt(4, selecao.getIdSelecao());

            int linhasAfetadas = preparedStatement.executeUpdate();

            return linhasAfetadas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar seleção", e);
        }
    }

    @Override
    public boolean deletar(Integer id) {
        String sql = "DELETE FROM selecoes WHERE id_selecao = ?";
        Connection conn = Conexao.getInstanciaConexao();

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int linhasAfetadas = preparedStatement.executeUpdate();

            return linhasAfetadas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar seleção", e);
        }
    }
}