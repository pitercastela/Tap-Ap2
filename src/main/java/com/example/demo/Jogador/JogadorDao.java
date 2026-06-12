package com.example.demo.Jogador;

import com.example.demo.Conexao;
import com.example.demo.CrudDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JogadorDao implements CrudDao<Jogador> {

    @Override
    public Jogador salvar(Jogador jogador) {
        String sql = "INSERT INTO jogadores (id_jogador, nome, numero_camisa, posicao, idade, id_selecao) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = Conexao.getInstanciaConexao();

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, jogador.getIdJogador());
            preparedStatement.setString(2, jogador.getNome());
            preparedStatement.setInt(3, jogador.getNumeroCamisa());
            preparedStatement.setString(4, jogador.getPosicao());
            preparedStatement.setInt(5, jogador.getIdade());

            if (jogador.getIdSelecao() != null) {
                preparedStatement.setInt(6, jogador.getIdSelecao());
            } else {
                preparedStatement.setNull(6, Types.INTEGER);
            }

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar jogador", e);
        }
        return jogador;
    }

    @Override
    public Jogador buscar(Integer id) {
        String sql = "SELECT * FROM jogadores WHERE id_jogador = ?";
        Jogador jogador = null;
        Connection conn = Conexao.getInstanciaConexao();

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    jogador = Jogador.builder()
                            .idJogador(resultSet.getInt("id_jogador"))
                            .nome(resultSet.getString("nome"))
                            .numeroCamisa(resultSet.getInt("numero_camisa"))
                            .posicao(resultSet.getString("posicao"))
                            .idade(resultSet.getInt("idade"))
                            .idSelecao((Integer) resultSet.getObject("id_selecao"))
                            .build();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar jogador", e);
        }
        return jogador;
    }

    @Override
    public List<Jogador> listarTodos() {
        List<Jogador> jogadores = new ArrayList<>();
        String sql = "SELECT * FROM jogadores";
        Connection conn = Conexao.getInstanciaConexao();

        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                jogadores.add(Jogador.builder()
                        .idJogador(resultSet.getInt("id_jogador"))
                        .nome(resultSet.getString("nome"))
                        .numeroCamisa(resultSet.getInt("numero_camisa"))
                        .posicao(resultSet.getString("posicao"))
                        .idade(resultSet.getInt("idade"))
                        .idSelecao((Integer) resultSet.getObject("id_selecao"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar jogadores", e);
        }
        return jogadores;
    }

    @Override
    public boolean atualizar(Jogador jogador) {
        String sql = "UPDATE jogadores SET nome = ?, numero_camisa = ?, posicao = ?, idade = ?, id_selecao = ? WHERE id_jogador = ?";
        Connection conn = Conexao.getInstanciaConexao();

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, jogador.getNome());
            preparedStatement.setInt(2, jogador.getNumeroCamisa());
            preparedStatement.setString(3, jogador.getPosicao());
            preparedStatement.setInt(4, jogador.getIdade());

            if (jogador.getIdSelecao() != null) {
                preparedStatement.setInt(5, jogador.getIdSelecao());
            } else {
                preparedStatement.setNull(5, Types.INTEGER);
            }

            preparedStatement.setInt(6, jogador.getIdJogador());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar jogador", e);
        }
    }

    @Override
    public boolean deletar(Integer id) {
        String sql = "DELETE FROM jogadores WHERE id_jogador = ?";
        Connection conn = Conexao.getInstanciaConexao();

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar jogador", e);
        }
    }

    public List<Jogador> listarPorSelecao(Integer idSelecao) {
        List<Jogador> jogadores = new ArrayList<>();
        String sql = "SELECT * FROM jogadores WHERE id_selecao = ?";
        Connection conn = Conexao.getInstanciaConexao();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idSelecao);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    jogadores.add(Jogador.builder()
                            .idJogador(rs.getInt("id_jogador"))
                            .nome(rs.getString("nome"))
                            .numeroCamisa(rs.getInt("numero_camisa"))
                            .posicao(rs.getString("posicao"))
                            .idade(rs.getInt("idade"))
                            .idSelecao((Integer) rs.getObject("id_selecao"))
                            .build());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar jogadores da seleção", e);
        }
        return jogadores;
    }
}