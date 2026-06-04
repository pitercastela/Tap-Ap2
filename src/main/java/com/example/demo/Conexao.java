package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static Connection instanciaConexao;

    private static final String URL = "jdbc:mysql://u5rmb2b6oxjirntn:F7qPW2uKTjcy57cRpX7K@b6612dny6lzzxtcdiwjc-mysql.services.clever-cloud.com:3306/b6612dny6lzzxtcdiwjc";
    private static final String USUARIO = "u5rmb2b6oxjirntn";
    private static final String SENHA = "F7qPW2uKTjcy57cRpX7K";

    private Conexao() {
    }

    public static Connection getInstanciaConexao(){
        try{
        if (instanciaConexao == null) {
            new Conexao();
            instanciaConexao = DriverManager.getConnection(URL, USUARIO, SENHA);
        }
    } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return instanciaConexao;
    }
}
