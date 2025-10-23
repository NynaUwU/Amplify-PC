package model;

import factory.Conector;
import java.sql.*;
import java.util.ArrayList;
import com.example.musicplayer.Musica;
import com.example.musicplayer.database.Genero;

public class MusicaDao {

    private Connection con;

    public MusicaDao() {
        con = Conector.getConnection();
    }

    /**
     * Cadastra uma nova música no banco de dados
     */
    public boolean cadastrarMusica(Musica musica) {
        try {
            String sql = "INSERT INTO musicas (nome, duracao, artista, usuario_id, genero_id, arquivo_mp3, data_upload) " +
                         "VALUES (?, ?, ?, ?, ?, ?, current_timestamp())";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, musica.getNome());
            stmt.setString(2, musica.getDuracaoStr());
            stmt.setString(3, musica.getArtista());
            stmt.setInt(4, musica.getUsuarioId());
            if (musica.getGeneroId()!=0) {
                stmt.setInt(5, 1);
            }else{
                stmt.setInt(5, 1);
            }
            
            stmt.setString(6, musica.getArquivo());
            
            stmt.execute();
            stmt.close();
            con.close();
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Lista todas as músicas do banco
     */
    public ArrayList<Musica> getListaMusica() {
        ArrayList<Musica> lista = new ArrayList<>();
        try {
            String sql = "SELECT m.*, g.nome as nome_genero FROM musicas m " +
                         "JOIN generos g ON m.genero_id = g.id " +
                         "ORDER BY m.data_upload DESC";
            PreparedStatement stmt = con.prepareStatement(sql);

            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                Musica musica = new Musica(
                    res.getInt("id"),
                    res.getString("nome"),
                    res.getString("duracao"),
                    res.getString("artista"),
                    res.getInt("usuario_id"),
                    res.getInt("genero_id"),
                    res.getString("nome_genero"),
                    res.getString("arquivo_mp3"),
                    res.getTimestamp("data_upload")
                );
                lista.add(musica);
                System.out.println(musica);
            }
            
            res.close();
            stmt.close();
            con.close();
            return lista;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Busca músicas de um usuário específico
     */
    public ArrayList<Musica> getMusicasPorUsuario(int usuarioId) {
        ArrayList<Musica> lista = new ArrayList<>();
        try {
            String sql = "SELECT m.*, g.nome as nome_genero FROM musicas m " +
                         "JOIN generos g ON m.genero_id = g.id " +
                         "WHERE m.usuario_id = ? " +
                         "ORDER BY m.data_upload DESC";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, usuarioId);

            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                Musica musica = new Musica(
                    res.getInt("id"),
                    res.getString("nome"),
                    res.getString("duracao"),
                    res.getString("artista"),
                    res.getInt("usuario_id"),
                    res.getInt("genero_id"),
                    res.getString("nome_genero"),
                    res.getString("arquivo_mp3"),
                    res.getTimestamp("data_upload")
                );
                lista.add(musica);
            }
            
            res.close();
            stmt.close();
            con.close();
            return lista;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Busca músicas por gênero
     */
    public ArrayList<Musica> getMusicasPorGenero(int generoId) {
        ArrayList<Musica> lista = new ArrayList<>();
        try {
            String sql = "SELECT m.*, g.nome as nome_genero FROM musicas m " +
                         "JOIN generos g ON m.genero_id = g.id " +
                         "WHERE m.genero_id = ? " +
                         "ORDER BY m.artista, m.nome";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, generoId);

            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                Musica musica = new Musica(
                    res.getInt("id"),
                    res.getString("nome"),
                    res.getString("duracao"),
                    res.getString("artista"),
                    res.getInt("usuario_id"),
                    res.getInt("genero_id"),
                    res.getString("nome_genero"),
                    res.getString("arquivo_mp3"),
                    res.getTimestamp("data_upload")
                );
                lista.add(musica);
            }
            
            res.close();
            stmt.close();
            con.close();
            return lista;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Busca músicas por artista
     */
    public ArrayList<Musica> buscarPorArtista(String artista) {
        ArrayList<Musica> lista = new ArrayList<>();
        try {
            String sql = "SELECT m.*, g.nome as nome_genero FROM musicas m " +
                         "JOIN generos g ON m.genero_id = g.id " +
                         "WHERE m.artista LIKE ? " +
                         "ORDER BY m.nome";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + artista + "%");

            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                Musica musica = new Musica(
                    res.getInt("id"),
                    res.getString("nome"),
                    res.getString("duracao"),
                    res.getString("artista"),
                    res.getInt("usuario_id"),
                    res.getInt("genero_id"),
                    res.getString("nome_genero"),
                    res.getString("arquivo_mp3"),
                    res.getTimestamp("data_upload")
                );
                lista.add(musica);
            }
            
            res.close();
            stmt.close();
            con.close();
            return lista;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Busca músicas por nome
     */
    public ArrayList<Musica> buscarPorNome(String nome) {
        ArrayList<Musica> lista = new ArrayList<>();
        try {
            String sql = "SELECT m.*, g.nome as nome_genero FROM musicas m " +
                         "JOIN generos g ON m.genero_id = g.id " +
                         "WHERE m.nome LIKE ? " +
                         "ORDER BY m.nome";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + nome + "%");

            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                Musica musica = new Musica(
                    res.getInt("id"),
                    res.getString("nome"),
                    res.getString("duracao"),
                    res.getString("artista"),
                    res.getInt("usuario_id"),
                    res.getInt("genero_id"),
                    res.getString("nome_genero"),
                    res.getString("arquivo_mp3"),
                    res.getTimestamp("data_upload")
                );
                lista.add(musica);
            }
            
            res.close();
            stmt.close();
            con.close();
            return lista;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Deleta uma música do banco
     */
    public boolean deletarMusica(int id) {
        try {
            String sql = "DELETE FROM musicas WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            
            stmt.execute();
            stmt.close();
            con.close();
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Lista todos os gêneros disponíveis
     */
    public ArrayList<Genero> getGeneros() {
        ArrayList<Genero> lista = new ArrayList<>();
        try {
            String sql = "SELECT * FROM generos ORDER BY nome";
            PreparedStatement stmt = con.prepareStatement(sql);

            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                Genero genero = new Genero(
                    res.getInt("id"),
                    res.getString("nome")
                );
                lista.add(genero);
            }
            
            res.close();
            stmt.close();
            con.close();
            return lista;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Conta quantas músicas existem por gênero
     */
    public ArrayList<Object[]> contarMusicasPorGenero() {
        ArrayList<Object[]> lista = new ArrayList<>();
        try {
            String sql = "SELECT g.nome, COUNT(m.id) as total_musicas " +
                         "FROM generos g " +
                         "LEFT JOIN musicas m ON g.id = m.genero_id " +
                         "GROUP BY g.id, g.nome " +
                         "ORDER BY total_musicas DESC";
            PreparedStatement stmt = con.prepareStatement(sql);

            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                Object[] resultado = new Object[2];
                resultado[0] = res.getString("nome");
                resultado[1] = res.getInt("total_musicas");
                lista.add(resultado);
            }
            
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