/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factory;
import java.sql.*; 
// esse import importa todas as bibliotecas
// necessárias para conectar no banco

/**
 *
 * @author herrmann
 */
public class Conector {
    // esse objeto guarda a conexão com
    // o banco
    private static Connection con;
    
    // Esse método faz a conexão com o banco
    // e devolve essa conexão para quem chamou
    // o método
    public static Connection getConnection(){
        try {
           String url = "jdbc:mysql://localhost:3306/";
           String banco = "petshop";
           String usuario = "root";
           String senha = "aluno@LP3";
           
           con = DriverManager.getConnection(url+banco,usuario,senha);
           
           return con;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
