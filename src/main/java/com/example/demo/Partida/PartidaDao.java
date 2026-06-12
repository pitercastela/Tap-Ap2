package com.example.demo.Partida;

import com.example.demo.Conexao;
import com.example.demo.CrudDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartidaDao implements CrudDao<Partida> {

    @Override
    public Partida salvar(Partida partida) {
        String sql = "INSERT INTO partidas (id_partida, data_partida, estadio, fase_competicao, placar) VALUES (?, ?, ?, ?, ?)";
        Connection conn = Conexao.getInstanciaConexao();

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, partida.getIdPartida());
            preparedStatement.setDate(2, Date.valueOf(partida.getDataPartida()));
            preparedStatement.setString(3, partida.getEstadio());
            preparedStatement.setString(4, partida.getFaseCompeticao());
            preparedStatement.setString(5, partida.getPlacar());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar partida", e);
        }
        return partida;
    }

    @Override
    public Partida buscar(Integer id) {
        String sql = "SELECT * FROM partidas WHERE id_partida = ?";
        Partida partida = null;
        Connection conn = Conexao.getInstanciaConexao();

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    partida = Partida.builder()
                            .idPartida(resultSet.getInt("id_partida"))
                            .dataPartida(resultSet.getDate("data_partida").toLocalDate())
                            .estadio(resultSet.getString("estadio"))
                            .faseCompeticao(resultSet.getString("fase_competicao"))
                            .placar(resultSet.getString("placar"))
                            .build();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar partida", e);
        }
        return partida;
    }

    @Override
    public List<Partida> listarTodos() {
        List<Partida> partidas = new ArrayList<>();
        String sql = "SELECT * FROM partidas";
        Connection conn = Conexao.getInstanciaConexao();

        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                partidas.add(Partida.builder()
                        .idPartida(resultSet.getInt("id_partida"))
                        .dataPartida(resultSet.getDate("data_partida").toLocalDate())
                        .estadio(resultSet.getString("estadio"))
                        .faseCompeticao(resultSet.getString("fase_competicao"))
                        .placar(resultSet.getString("placar"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar partidas", e);
        }
        return partidas;
    }

    @Override
    public boolean atualizar(Partida partida) {
        String sql = "UPDATE partidas SET data_partida = ?, estadio = ?, fase_competicao = ?, placar = ? WHERE id_partida = ?";
        Connection conn = Conexao.getInstanciaConexao();

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setDate(1, Date.valueOf(partida.getDataPartida()));
            preparedStatement.setString(2, partida.getEstadio());
            preparedStatement.setString(3, partida.getFaseCompeticao());
            preparedStatement.setString(4, partida.getPlacar());
            preparedStatement.setInt(5, partida.getIdPartida());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar partida", e);
        }
    }

    @Override
    public boolean deletar(Integer id) {
        String sql = "DELETE FROM partidas WHERE id_partida = ?";
        Connection conn = Conexao.getInstanciaConexao();

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar partida", e);
        }
    }
}