
package factory;
import java.sql.*; 

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
           String banco = "sistema_musica";
           String usuario = "root";
           String senha = "";
           
           con = DriverManager.getConnection(url+banco,usuario,senha);
           
           return con;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
