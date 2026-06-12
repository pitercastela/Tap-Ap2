package com.example.demo.Participacao;

import com.example.demo.Conexao;
import com.example.demo.Partida.Partida;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipacaoDao {

    public boolean registrarParticipacao(Integer idSelecao, Integer idPartida) {
        String sql = "INSERT INTO selecao_partida (id_selecao, id_partida) VALUES (?, ?)";
        Connection conn = Conexao.getInstanciaConexao();

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, idSelecao);
            preparedStatement.setInt(2, idPartida);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao registrar participação da seleção na partida", e);
        }
    }

    public boolean removerParticipacao(Integer idSelecao, Integer idPartida) {
        String sql = "DELETE FROM selecao_partida WHERE id_selecao = ? AND id_partida = ?";
        Connection conn = Conexao.getInstanciaConexao();

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, idSelecao);
            preparedStatement.setInt(2, idPartida);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover participação", e);
        }
    }

    public List<Participacao> listarTodas() {
        List<Participacao> participacoes = new ArrayList<>();
        String sql = "SELECT id_selecao, id_partida FROM selecao_partida";
        Connection conn = Conexao.getInstanciaConexao();

        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                participacoes.add(Participacao.builder()
                        .idSelecao(resultSet.getInt("id_selecao"))
                        .idPartida(resultSet.getInt("id_partida"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar participações", e);
        }
        return participacoes;
    }

    public List<Partida> listarPartidasPorSelecao(Integer idSelecao) {
        List<Partida> partidas = new ArrayList<>();

        String sql = "SELECT p.id_partida, p.data_partida, p.estadio, p.fase_competicao, p.placar " +
                "FROM partidas p " +
                "INNER JOIN selecao_partida sp ON p.id_partida = sp.id_partida " +
                "WHERE sp.id_selecao = ?";

        Connection conn = Conexao.getInstanciaConexao();

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, idSelecao);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    partidas.add(Partida.builder()
                            .idPartida(resultSet.getInt("id_partida"))
                            .dataPartida(resultSet.getDate("data_partida").toLocalDate())
                            .estadio(resultSet.getString("estadio"))
                            .faseCompeticao(resultSet.getString("fase_competicao"))
                            .placar(resultSet.getString("placar"))
                            .build());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar partidas da seleção", e);
        }
        return partidas;
    }
}