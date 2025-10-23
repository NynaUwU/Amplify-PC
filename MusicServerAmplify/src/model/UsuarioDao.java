package model;

import factory.Conector;
import java.sql.*;
import java.util.ArrayList;
import com.example.musicplayer.database.Administrador;
import com.example.musicplayer.database.Cliente;
import com.example.musicplayer.database.Usuario;

public class UsuarioDao {

    private Connection con;

    public UsuarioDao() {
        con = Conector.getConnection();
    }

    public Usuario login(Usuario user) {
        Usuario userLogado = null;
        try {
            String sql = "select * from usuarios where email = ? and senha = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getSenha());

            ResultSet res = stmt.executeQuery();
            if (res.next()) {
                if (res.getInt("is_admin") == 0) {
                    // Administrador
                    userLogado = new Administrador(res.getInt("id"),
                                                   res.getString("nome"),
                                                   res.getString("email"),
                                                   res.getString("senha"));
                } else {
                    // Cliente
                    userLogado = new Cliente(res.getInt("id"),
                                             res.getString("nome"),
                                             res.getString("email"),
                                             res.getString("senha"));
                }
                System.out.println(userLogado);
            }
            // fechando as conexões
            res.close();
            stmt.close();
            con.close();
            return userLogado;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    
    public ArrayList<Usuario> getLista() {
        Usuario userLogado = null;
        ArrayList<Usuario> lista = new ArrayList<>();
        try {
            String sql = "select * from usuarios";
            PreparedStatement stmt = con.prepareStatement(sql);

            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                Usuario user;
                if (res.getInt("tipo") == 0) {
                    // Administrador
                    user = new Administrador(res.getInt("codusuario"),
                                                   res.getString("nomeusuario"),
                                                   res.getString("email"),
                                                   res.getString("senha"));
                } else {
                    // Cliente
                    user = new Cliente(res.getInt("codusuario"),
                                             res.getString("nomeusuario"),
                                             res.getString("email"),
                                             res.getString("senha"));
                }
                lista.add(user);
                System.out.println(user);
            }
            // fechando as conexões
            res.close();
            stmt.close();
            con.close();
            return lista;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    public boolean CadastroUsuario(Usuario user){
       // "INSERT INTO `usuarios` (`id`, `nome`, `email`, `senha`, `is_admin`, `data_criacao`, `data_atualizacao`) VALUES (NULL, ?, ?, ?, '0', current_timestamp(), current_timestamp());"
        try {
            String sql = "INSERT INTO usuarios (id, nome, email, senha, is_admin, data_criacao, data_atualizacao) VALUES (NULL, ?, ?, ?, 0, current_timestamp(), current_timestamp())";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, user.getNome());
            stmt.setString(2, user.getEmail());
            stmt.setString(2, user.getSenha());
            stmt.execute();
            
            stmt.close();
            con.close();
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
