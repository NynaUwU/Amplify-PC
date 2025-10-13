/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import factory.Conector;
import java.sql.*;
import java.util.ArrayList;
import modelDominio.Administrador;
import modelDominio.Cliente;
import modelDominio.Usuario;

/**
 *
 * @author herrmann
 */
public class UsuarioDao {

    private Connection con;

    public UsuarioDao() {
        con = Conector.getConnection();
    }

    public Usuario login(Usuario user) {
        Usuario userLogado = null;
        try {
            String sql = "select * from usuario where email = ? and senha = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getSenha());

            ResultSet res = stmt.executeQuery();
            if (res.next()) {
                if (res.getInt("tipo") == 0) {
                    // Administrador
                    userLogado = new Administrador(res.getInt("codusuario"),
                                                   res.getString("nomeusuario"),
                                                   res.getString("email"),
                                                   res.getString("senha"));
                } else {
                    // Cliente
                    userLogado = new Cliente(res.getInt("codusuario"),
                                             res.getString("nomeusuario"),
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
            String sql = "select * from usuario";
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
    
}
